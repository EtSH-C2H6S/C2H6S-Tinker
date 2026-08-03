package com.c2h6s.etshtinker.Modifiers;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.etshtinker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;

import java.util.UUID;

public class shattered extends EtshModifieriii implements VolatileDataModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }

    public static boolean enabled = ModList.get().isLoaded("cofh_core");
    public static final UUID SHATTERED_UUID = UUID.fromString("9f3a7f2b-5967-7592-6fdc-5562fb986a79");
    public static final ResourceLocation KEY_MAX_ARMOR_REDUCE = etshtinker.getResourceLoc("shattered");
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isSelected&&holder!=null&&level.getGameTime()%20==0&&enabled){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(),1200,4,false,false));
        }
    }

    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        if (entity instanceof LivingEntity target&&context.isFullyCharged()){
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            if (attribute!=null){
                var maxValue = tool.getVolatileData().getFloat(KEY_MAX_ARMOR_REDUCE);
                double originalValue = 0;
                if (attribute.getModifier(SHATTERED_UUID)!=null)
                    originalValue -= attribute.getModifier(SHATTERED_UUID).getAmount();
                if (originalValue<maxValue){
                    double value = Math.min(maxValue,originalValue+0.02*modifier.getLevel());
                    attribute.removeModifier(SHATTERED_UUID);
                    attribute.addTransientModifier(new AttributeModifier(SHATTERED_UUID,Attributes.ARMOR.getDescriptionId(),-value, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }

    @Override
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        namespacedNBT.putFloat(KEY_MAX_ARMOR_REDUCE,tool.getVolatileData().getFloat(KEY_MAX_ARMOR_REDUCE));
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (target instanceof LivingEntity&&projectile instanceof AbstractArrow arrow&&arrow.isCritArrow()){
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            if (attribute!=null){
                var maxValue = persistentData.getFloat(KEY_MAX_ARMOR_REDUCE);
                double originalValue = 0;
                if (attribute.getModifier(SHATTERED_UUID)!=null)
                    originalValue -= attribute.getModifier(SHATTERED_UUID).getAmount();
                if (originalValue<maxValue){
                    double value = Math.min(maxValue,originalValue+0.02*modifier.getLevel());
                    attribute.removeModifier(SHATTERED_UUID);
                    attribute.addTransientModifier(new AttributeModifier(SHATTERED_UUID,Attributes.ARMOR.getDescriptionId(),-value, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
        return false;
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ModDataNBT modDataNBT) {
        modDataNBT.putFloat(KEY_MAX_ARMOR_REDUCE,modDataNBT.getFloat(KEY_MAX_ARMOR_REDUCE)+0.4f);
    }
}
