package com.c2h6s.etshtinker.tools.item.tinker;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.FluidChamberSync;
import com.c2h6s.etshtinker.network.packet.plasmaSlashPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelLookup;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;

import java.util.Iterator;
import java.util.List;

import static com.c2h6s.etshtinker.util.SlashColor.getSlash;
import static com.c2h6s.etshtinker.util.SlashColor.getSlashType;
import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;


public class ConstrainedPlasmaSaber extends ModifiableSwordItem {
    public ConstrainedPlasmaSaber(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
        MinecraftForge.EVENT_BUS.addListener(this::LeftClick);
        MinecraftForge.EVENT_BUS.addListener(this::LeftClickBlock);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (stack.getItem() instanceof ConstrainedPlasmaSaber&&isSelected&&entity instanceof ServerPlayer player){
            ToolStack tool = ToolStack.from(stack);
            FluidStack fluidStack  = TANK_HELPER.getFluid(tool);
            int vol = TANK_HELPER.getCapacity(tool);
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("amount",fluidStack.getAmount());
            nbt.putInt("max",vol);
            packetHandler.sendToPlayer(new FluidChamberSync(nbt),player);
        }
    }

    public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        tooltips = this.getPlasmaSaberStats(tool, player, tooltips, key, tooltipFlag);
        return tooltips;
    }


    private void LeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity() !=null&&event.getEntity().getMainHandItem().getItem() instanceof ConstrainedPlasmaSaber) {
            packetHandler.INSTANCE.sendToServer(new plasmaSlashPacket());
        }
    }
    private void LeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity() !=null&&event.getEntity().getMainHandItem().getItem() instanceof ConstrainedPlasmaSaber) {
            packetHandler.INSTANCE.sendToServer(new plasmaSlashPacket());
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        if (player instanceof ServerPlayer serverPlayer){
            createSlash(serverPlayer);
        }
        return super.onLeftClickEntity(stack, player, target);
    }

    public static void createSlash(ServerPlayer player){
        if (!(player.getMainHandItem().getItem() instanceof ConstrainedPlasmaSaber)||player.getAttackStrengthScale(0)!=1||!checkOffHand(player)){
            return;
        }
        ToolStack tool = ToolStack.from(player.getMainHandItem());
        if (!checkFluid(tool)||tool.isBroken()){
            return;
        }
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        MeltingFuel fuel = getFuel(fluidStack.getFluid());
        int consumption =getFuelCumsp(fuel, fluidStack.getFluid(), tool);
        for (ModifierEntry modifier : tool.getModifierList()) {
            consumption = modifier.getHook(etshtinkerHook.FLUID_CONSUMPTION).getFluidConsumption(tool, fluidStack, player, consumption, consumption);
        }
        if (fluidStack.getAmount()< consumption){
            return;
        }
        float damage = (1 + tool.getStats().get(etshtinkerToolStats.DAMAGEMULTIPLIER))*tool.getStats().get(ToolStats.ATTACK_DAMAGE)*getFuelDamage(fuel);
        ItemStack color = getSlash(tool.getStats().getInt(etshtinkerToolStats.SLASH_COLOR));
        Level level =player.getLevel();
        EntityType<PlasmaSlashEntity> entityType = getSlashType(tool.getStats().getInt(etshtinkerToolStats.SLASH_COLOR));
        PlasmaSlashEntity slash =new PlasmaSlashEntity(entityType,level,color);
        slash.damage=damage;
        slash.setOwner(player);
        slash.setDeltaMovement(player.getLookAngle().scale(tool.getModifierLevel(ModifierIds.reach)*0.5+1));
        slash.setToolstack(tool);
        slash.CriticalRate=tool.getStats().get(etshtinkerToolStats.CRITICAL_RATE);
        double x =player.getLookAngle().x;
        double y =player.getLookAngle().y;
        double z =player.getLookAngle().z;
        slash.setPos(player.getX()+x*1.5,player.getY()+0.6*player.getBbHeight()+y*1.5,player.getZ()+z*1.5);
        for (ModifierEntry modifier : tool.getModifierList()) {
            slash = modifier.getHook(etshtinkerHook.SLASH_CREATE).plasmaSlashCreate(tool, fluidStack, player, slash);
        }
        level.addFreshEntity(slash);
        fluidStack.shrink(consumption);
        TANK_HELPER.setFluid(tool,fluidStack);
        ToolDamageUtil.damageAnimated(tool,1,player, InteractionHand.MAIN_HAND);
    }

    public static boolean checkOffHand(Player player){
        return player!=null&& !(player.getOffhandItem().getItem() instanceof IModifiable);
    }

    public static boolean noFluid(IToolStackView tool){
        FluidStack fluid =TANK_HELPER.getFluid(tool);
        return fluid.getAmount()<=0;
    }

    public static boolean checkFluid(IToolStackView tool){
        Fluid fluid =TANK_HELPER.getFluid(tool).getFluid();
        return MeltingFuelLookup.isFuel(fluid);
    }

    public static MeltingFuel getFuel(Fluid fluid){
        return MeltingFuelLookup.findFuel(fluid);
    }

    public static float getFuelDamage(MeltingFuel fuel){
        return fuel.getTemperature()*0.00085f;
    }

    public static int getFuelCumsp(MeltingFuel fuel,Fluid fluid,IToolStackView tool){
        return (int) Math.max( fuel.getAmount(fluid)*20/(fuel.getDuration()*tool.getStats().get(etshtinkerToolStats.FLUID_EFFICIENCY)),1);
    }


    public List<Component> getPlasmaSaberStats(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        TooltipBuilder builder = new TooltipBuilder(tool, tooltips);
        if (tool.hasTag(TinkerTags.Items.DURABILITY)) {
            builder.add(ToolStats.DURABILITY);
        }
        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            builder.add(ToolStats.ATTACK_DAMAGE);
            builder.add(ToolStats.ATTACK_SPEED);
        }
        builder.add(Component.translatable("etshtinker.tool.tooltip.damagemultiplier").append(":"+String.format("%.2f",(1+tool.getStats().get(etshtinkerToolStats.DAMAGEMULTIPLIER)))));
        builder.add(Component.translatable("etshtinker.tool.tooltip.critical_rate").append(":"+String.format("%.2f", tool.getStats().get(etshtinkerToolStats.CRITICAL_RATE)*100)+"%").withStyle(ChatFormatting.AQUA));
        builder.add(Component.translatable("etshtinker.tool.tooltip.fluid_efficiency").append(":"+String.format("%.2f",tool.getStats().get(etshtinkerToolStats.FLUID_EFFICIENCY))).withStyle(ChatFormatting.DARK_AQUA));
        builder.addAllFreeSlots();

        if (!checkOffHand(player)){
            builder.add(Component.translatable("etshtinker.tool.tooltip.offhand_hastool").withStyle(ChatFormatting.RED));
        }
        else if (checkFluid(tool)){
            builder.add(Component.translatable("etshtinker.tool.tooltip.effectivefluid").append(":" +String.format("%.2f",getFuelDamage(getFuel(TANK_HELPER.getFluid(tool).getFluid())))).withStyle(ChatFormatting.GOLD));
            builder.add(Component.translatable("etshtinker.tool.tooltip.powerfactor").append(":" +String.valueOf(getFuelCumsp(getFuel(TANK_HELPER.getFluid(tool).getFluid()),TANK_HELPER.getFluid(tool).getFluid(),tool))).withStyle(ChatFormatting.YELLOW));
        }else{
            if (noFluid(tool)){
                builder.add(Component.translatable("etshtinker.tool.tooltip.nofluid").withStyle(ChatFormatting.RED));
            }
            else {
                builder.add(Component.translatable("etshtinker.tool.tooltip.wrongfluid").withStyle(ChatFormatting.RED));
            }
        }

        Iterator var7 = tool.getModifierList().iterator();

        while(var7.hasNext()) {
            ModifierEntry entry = (ModifierEntry)var7.next();
            ((TooltipModifierHook)entry.getHook(ModifierHooks.TOOLTIP)).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }

        return tooltips;
    }
}
