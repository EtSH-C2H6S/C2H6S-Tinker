package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber;
import com.c2h6s.etshtinker.tools.item.tinker.IonizedCannon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;


import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class ultradenseex extends etshmodifieriii {
    private final ResourceLocation multiplier = new ResourceLocation(MOD_ID, "multiplier");
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("ultradenseex");
    public ultradenseex(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }

    private void livinghurtevent(LivingHurtEvent event) {
        event.setAmount(event.getAmount()*0.75f);
        LivingEntity living = event.getEntity();
        Entity entity =event.getSource().getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (event.getSource().isBypassArmor()||event.getSource().isBypassEnchantments()||event.getSource().isBypassInvul()||event.getSource().isBypassMagic()||event.getSource().isMagic()||event.getSource().isProjectile()||event.getSource().isExplosion()||event.getSource().isFall()||event.getSource().isFire()||event.getSource()==DamageSource.OUT_OF_WORLD||event.getSource().isDamageHelmet()){
                    event.setAmount(event.getAmount()/2);
                }
            }
        });
    }
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(multiplier);
    }
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (tool.getModifierLevel(this)>0){
            if (source.getEntity()==null){
                return 0;
            }
        }
        return amount;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData =tool.getPersistentData();
        int modlvl10=tool.getModifierLevel(this);
        if (modlvl10>0&&isCorrectSlot&&!tool.isBroken()){
            if (toolData.getFloat(multiplier)<=3*modlvl10 ){
                toolData.putFloat(multiplier,toolData.getFloat(multiplier)+0.015f*modlvl10);
                if (toolData.getFloat(multiplier)<=1) {
                    holder.attackAnim = (float) 5 / 6;
                    holder.oAttackAnim = (float) 5 / 6;
                }
            }
        }
        if (!isCorrectSlot&&toolData.getFloat(multiplier)!=0f){
            toolData.putFloat(multiplier,0f);
        }
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        ModDataNBT toolData =tool.getPersistentData();
        damage =damage*(float) Math.pow(toolData.getFloat(multiplier),2d);
        if (tool instanceof ConstrainedPlasmaSaber||tool instanceof IonizedCannon){
            toolData.putFloat(multiplier, toolData.getFloat(multiplier)-0.1f);
            return damage;
        }
        else {
            toolData.putFloat(multiplier, 0);
        }
        return damage;

    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile arrow, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        ModDataNBT toolData =tool.getPersistentData();
        if(arrow!=null){
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale( Math.pow(toolData.getFloat(multiplier),2d)));
            toolData.putFloat(multiplier,0);
        }
    }
}
