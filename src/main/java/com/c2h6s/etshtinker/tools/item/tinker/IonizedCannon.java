package com.c2h6s.etshtinker.tools.item.tinker;

import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import com.c2h6s.etshtinker.Entities.plasmaexplosionentity;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.tools.capability.inventory.ToolInventoryCapability;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.util.vecCalc.*;


import java.util.Iterator;
import java.util.List;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class IonizedCannon extends ModifiableItem {
    public IonizedCannon(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }
    public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        tooltips = this.getIonizedCannonStats(tool, player, tooltips, key, tooltipFlag);
        return tooltips;
    }
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }
    @Override
    public int getBarColor(ItemStack pStack) {
        return DurabilityDisplayModifierHook.getDurabilityRGB(pStack);
    }
    @Override
    public int getBarWidth(ItemStack pStack) {
        ToolStack tool =ToolStack.from(pStack);
        int amount =TANK_HELPER.getFluid(tool).getAmount();
        int max =TANK_HELPER.getCapacity(tool);
        return amount>0 ? (int) (13* ((double)amount/(double) max)) : 0 ;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        ToolStack tool = ToolStack.from(stack);
        FluidStack fluid = TANK_HELPER.getFluid(tool);
        int fluiddrain = Math.round( tool.getStats().get(ToolStats.ATTACK_DAMAGE)*20);
        if (stack.getCount() > 1) {
            return InteractionResultHolder.pass(stack);
        }
        if (!tool.isBroken()){
            boolean enoughfluid =TANK_HELPER.getFluid(tool).getAmount()>fluiddrain;
            boolean iscreative =playerIn.isCreative();
            if (!worldIn.isClientSide){
                if(!iscreative&&!enoughfluid){
                    return InteractionResultHolder.pass(stack);
                }
                if (!wrongFluid(tool)) {
                    playerIn.startUsingItem(hand);
                    return InteractionResultHolder.consume(stack);
                }
            }
            return InteractionResultHolder.pass(stack);
        }
        return new InteractionResultHolder<>(ToolInventoryCapability.tryOpenContainer(stack, tool, playerIn, Util.getSlotType(hand)), stack);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        ToolStack tool = ToolStack.from(stack);
        if (tool.getModifierLevel(etshtinkerModifiers.autoionizing_STATIC_MODIFIER.get())>0){
            return TANK_HELPER.getFluid(tool).getAmount() > tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) ?Math.max (1,(int) (40 / tool.getStats().get(ToolStats.ATTACK_SPEED))) : 0;
        }
        return TANK_HELPER.getFluid(tool).getAmount() > tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) ? 72000 : 0;
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        ToolStack tool = ToolStack.from(stack);
        Fluid fluid =TANK_HELPER.getFluid(tool).getFluid();
        int times =tool.getStats().getInt(etshtinkerToolStats.MULTIPLASMA);
        int a =0;
        while (a<=times) {
            if (timeLeft <= this.getUseDuration(stack) - (int) (40 / tool.getStats().get(ToolStats.ATTACK_SPEED)) && living instanceof Player player && TANK_HELPER.getFluid(tool).getAmount() > Math.round(tool.getStats().get(ToolStats.ATTACK_DAMAGE) * 20)) {
                plasmaexplosionentity entity = new plasmaexplosionentity(etshtinkerEntity.plasmaexplosionentity.get(), level);
                if (tool.getStats().get(etshtinkerToolStats.SCATTER) > 0) {
                    entity.rayVec3 = getScatteredVec3(living.getLookAngle().scale(tool.getStats().get(etshtinkerToolStats.PLASMARANGE)), Math.tan(tool.getStats().get(etshtinkerToolStats.SCATTER)));
                } else entity.rayVec3 = living.getLookAngle().scale(tool.getStats().get(etshtinkerToolStats.PLASMARANGE));
                entity.particle = getFluidparticle(fluid);
                entity.scale =tool.getStats().get(etshtinkerToolStats.SCALE);
                entity.damage = getFluidDamage(fluid) * (1 + tool.getStats().get(etshtinkerToolStats.DAMAGEMULTIPLIER)) * tool.getStats().get(ToolStats.ATTACK_DAMAGE);
                entity.tool = tool;
                entity.special = getFluidSpecial(fluid);
                entity.setPos(living.getEyePosition().x, living.getEyePosition().y - 0.5 * entity.getBbHeight(), living.getEyePosition().z);
                entity.setOwner(living);
                level.addFreshEntity(entity);
                living.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1, 1);
                player.getCooldowns().addCooldown(stack.getItem(), tool.getStats().getInt(etshtinkerToolStats.COOLDOWN));
                FluidStack fluidStack = new FluidStack(TANK_HELPER.getFluid(tool), TANK_HELPER.getFluid(tool).getAmount() - Math.round(tool.getStats().get(ToolStats.ATTACK_DAMAGE) * 20));
                TANK_HELPER.setFluid(tool, fluidStack);
            }
            else break;
            a++;
        }
    }
    public boolean wrongFluid(IToolStackView tool){
        Fluid fluid =TANK_HELPER.getFluid(tool).getFluid();
        return getFluidparticle(fluid) == null;
    }
    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int chargeRemaining) {
        ToolStack tool = ToolStack.from(stack);
        Level world =living.level;
        if (world!=null) {
            if (this.getUseDuration(stack) - chargeRemaining == (int) (40 / tool.getStats().get(ToolStats.ATTACK_SPEED))) {
                living.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 1, 1);
                Vec3 pos = living.getEyePosition();
                Vec3 ang = living.getLookAngle();
                double x = pos.x + ang.x;
                double y = pos.y + ang.y;
                double z = pos.z + ang.z;
                world.addAlwaysVisibleParticle(ParticleTypes.ELECTRIC_SPARK, true, x, y, z, 0, 0, 0);
            }
        }
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        ToolStack tool = ToolStack.from(stack);
        if (tool.getModifierLevel(etshtinkerModifiers.autoionizing_STATIC_MODIFIER.get())>0){
            Fluid fluid =TANK_HELPER.getFluid(tool).getFluid();
            int times =tool.getStats().getInt(etshtinkerToolStats.MULTIPLASMA);
            int a =0;
            while (a<=times) {
                if (living instanceof Player player && TANK_HELPER.getFluid(tool).getAmount() > Math.round(tool.getStats().get(ToolStats.ATTACK_DAMAGE) * 20)) {
                    plasmaexplosionentity entity = new plasmaexplosionentity(etshtinkerEntity.plasmaexplosionentity.get(), level);
                    if (tool.getStats().get(etshtinkerToolStats.SCATTER) > 0) {
                        entity.rayVec3 = getScatteredVec3(living.getLookAngle().scale(tool.getStats().get(etshtinkerToolStats.PLASMARANGE)), Math.tan(tool.getStats().get(etshtinkerToolStats.SCATTER)));
                    } else entity.rayVec3 = living.getLookAngle().scale(tool.getStats().get(etshtinkerToolStats.PLASMARANGE));
                    entity.scale=tool.getStats().get(etshtinkerToolStats.SCALE);
                    entity.particle = getFluidparticle(fluid);
                    entity.damage = getFluidDamage(fluid) * (1 + tool.getStats().get(etshtinkerToolStats.DAMAGEMULTIPLIER)) * tool.getStats().get(ToolStats.ATTACK_DAMAGE);
                    entity.tool = tool;
                    entity.special = getFluidSpecial(fluid);
                    entity.setPos(living.getEyePosition().x, living.getEyePosition().y - 0.5 * entity.getBbHeight(), living.getEyePosition().z);
                    entity.setOwner(living);
                    level.addFreshEntity(entity);
                    living.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1, 1);
                    player.getCooldowns().addCooldown(stack.getItem(), tool.getStats().getInt(etshtinkerToolStats.COOLDOWN));
                    FluidStack fluidStack = new FluidStack(TANK_HELPER.getFluid(tool), TANK_HELPER.getFluid(tool).getAmount() - Math.round(tool.getStats().get(ToolStats.ATTACK_DAMAGE) * 20*tool.getStats().getInt(etshtinkerToolStats.FLUIDMULTIPLIER)));
                    TANK_HELPER.setFluid(tool, fluidStack);
                }
                else break;
                a++;
            }
        }
        return stack;
    }

    public List<Component> getIonizedCannonStats(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        TooltipBuilder builder = new TooltipBuilder(tool, tooltips);

        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            builder.add(ToolStats.ATTACK_DAMAGE);
            builder.add(ToolStats.ATTACK_SPEED);
        }
        builder.add(Component.translatable("etshtinker.tool.tooltip.plasmarange").append(":"+String.format("%.01f",tool.getStats().get(etshtinkerToolStats.PLASMARANGE))));
        builder.add(Component.translatable("etshtinker.tool.tooltip.damagemultiplier").append(":"+String.format("%.01f",(1+tool.getStats().get(etshtinkerToolStats.DAMAGEMULTIPLIER))*tool.getStats().get(ToolStats.ATTACK_DAMAGE))));
        builder.add(Component.translatable("etshtinker.tool.tooltip.chargespeed").append(":"+String.valueOf((int) (40/tool.getStats().get(ToolStats.ATTACK_SPEED)))));
        builder.add(Component.translatable("etshtinker.tool.tooltip.cooldown").append(":"+String.valueOf(tool.getStats().get(etshtinkerToolStats.COOLDOWN))).withStyle(ChatFormatting.GOLD));
        builder.add(Component.translatable("etshtinker.tool.tooltip.powerfactor").append(":"+String.valueOf(Math.round( tool.getStats().get(ToolStats.ATTACK_DAMAGE)*20*tool.getStats().getInt(etshtinkerToolStats.FLUIDMULTIPLIER)))).withStyle(ChatFormatting.YELLOW));
        builder.add(Component.translatable("etshtinker.tool.tooltip.scatter").append(":"+String.format("%.01f",tool.getStats().get(etshtinkerToolStats.SCATTER))).withStyle(ChatFormatting.GOLD));
        builder.addAllFreeSlots();
        if (!wrongFluid(tool)){
            builder.add(Component.translatable("etshtinker.tool.tooltip.effectivefluid").append(":"+String.format("%.001f",getFluidDamage(TANK_HELPER.getFluid(tool).getFluid()))).withStyle(ChatFormatting.GREEN));
            if (getFluidSpecial(TANK_HELPER.getFluid(tool).getFluid())!=null){
                builder.add(Component.translatable("etshtinker.tool.tooltip.fluidhasspecial").append(":").append(Component.translatable("etshtinker.tool.tooltip.fluidspecial."+getFluidSpecial(TANK_HELPER.getFluid(tool).getFluid()))).withStyle(ChatFormatting.AQUA));
            }
        }
        else{
            builder.add(Component.translatable("etshtinker.tool.tooltip.wrongfluid").withStyle(ChatFormatting.RED));
        }
        Iterator var7 = tool.getModifierList().iterator();

        while(var7.hasNext()) {
            ModifierEntry entry = (ModifierEntry)var7.next();
            ((TooltipModifierHook)entry.getHook(ModifierHooks.TOOLTIP)).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }

        return tooltips;
    }
}
