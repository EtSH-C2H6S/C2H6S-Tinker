package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.FluidChamberSync;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource.PlayerPierce;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class adrenaline extends etshmodifieriii implements DurabilityDisplayModifierHook {
    public boolean isNoLevels() {
        return true;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }
    public adrenaline(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity target =event.getEntity();
        if (target !=null){
            if (target.getMainHandItem().getItem() instanceof ModifiableItem) {
                IToolStackView maintool = ToolStack.from(target.getMainHandItem());
                if (!maintool.isBroken()&&maintool.getPersistentData().getInt(adrenaline)>0&&event.getAmount()>=0.5){
                    maintool.getPersistentData().putInt(adrenaline,0);
                    if (maintool.getPersistentData().getInt(adrenaline)>75){
                        event.setAmount(event.getAmount()*0.5f);
                        target.playSound(SoundEvents.BEACON_DEACTIVATE,1,1);
                    }
                }
            }
            if (target.getOffhandItem().getItem() instanceof ModifiableItem) {
                IToolStackView offtool = ToolStack.from(target.getOffhandItem());
                if (!offtool.isBroken() && offtool.getPersistentData().getInt(adrenaline) > 0 && event.getAmount() >= 0.5) {
                    offtool.getPersistentData().putInt(adrenaline, 0);
                    if (offtool.getPersistentData().getInt(adrenaline) > 75) {
                        event.setAmount(event.getAmount() * 0.5f);
                        target.playSound(SoundEvents.BEACON_DEACTIVATE, 1, 1);
                    }
                }
            }
        }
    }

    private final ResourceLocation adrenaline = new ResourceLocation(MOD_ID, "adrenaline");
    private final ResourceLocation sound1 = new ResourceLocation(MOD_ID, "sound1");
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(sound1);
        tool.getPersistentData().remove(adrenaline);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if (isSelected){
            if (toolData.getInt(adrenaline)<100) {
                toolData.putFloat(adrenaline, toolData.getFloat(adrenaline) + 0.4f);
            }
        }
        else {
            if (toolData.getInt(adrenaline)>15) {
                toolData.putFloat(adrenaline, toolData.getFloat(adrenaline) - 2.0f);
            }
        }
        if (toolData.getInt(sound1)==1&&toolData.getInt(adrenaline)==100&&livingEntity!=null){
            livingEntity.playSound( SoundEvents.BEACON_ACTIVATE,  1.2F, 2.0F);
            toolData.putInt(sound1,0);
        }
        if (toolData.getInt(adrenaline)<90&&toolData.getInt(sound1)==0){
            toolData.putInt(sound1, 1);
        }
    }

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        ModDataNBT toolData = tool.getPersistentData();
        LivingEntity attacker =context.getAttacker();
        Entity target =context.getTarget();
        Level world = attacker.getLevel();
        if (toolData.getInt(adrenaline) > 99&&attacker instanceof Player player){
            attacker.invulnerableTime = 5;
            attacker.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 9));
            attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 4));
            attacker.heal(5);
            if (target instanceof LivingEntity) {
                if (getMainLevel(player,this)>0) {
                    DamageSource.playerAttack(player).bypassArmor().bypassMagic();
                    target.invulnerableTime = 0;
                    target.hurt(PlayerPierce(player,damage*25), damage * 25);
                }
            }
            world.playSound( null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 0.7F, 1.0F);
            toolData.putFloat(adrenaline,0.0f);
        }
        return knockback;
    }


    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity entity =context.getEntity();
        ModDataNBT toolData =tool.getPersistentData();
        if (entity instanceof Player player&&getMainLevel(player,this) > 0){
            if (toolData.getInt(adrenaline)>85) {
                Level world = entity.getLevel();
                world.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.NEUTRAL, 1.0F, 1.0F);
                return 0.5f*amount;
            }
            toolData.putFloat(adrenaline, 0.0f);
        }
        return amount;
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf( toolData.getInt(adrenaline)) + "%")));
        }
        super.addTooltip(tool,modifier,player,list,TooltipKey.UNKNOWN,tooltipFlag);
    }
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        return Component.translatable(  this.getDisplayName().getString()+"  ").append(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf( toolData.getInt(adrenaline)) +"%")).withStyle(this.getDisplayName().getStyle());
    }
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(adrenaline)>99) {
            return true;
        }
        else return tool.getDamage()>0;
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifierEntry) {
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        int amount =tool.getCurrentDurability();
        return amount >= max ? 13 : 1 + 13 * (amount - 1) / max;
    }
    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        ModDataNBT toolData =tool.getPersistentData();
        return toolData.getInt(adrenaline)>99 ? 0x9530FF : - 1;
    }
}
