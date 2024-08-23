package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class crunchyshattered extends etshmodifieriii implements ToolDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_DAMAGE);
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        Entity entity1 =context.getAttacker();
        if (modifier.getLevel()>0&&entity instanceof LivingEntity target&&!tool.isBroken()&&entity1 instanceof LivingEntity attacker){
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            target.forceAddEffect(new MobEffectInstance(MobEffects.WITHER,100,modifier.getLevel()),attacker);
            if (attribute!=null&&target.getArmorValue()>0){
                attribute.setBaseValue(attribute.getBaseValue()-2*modifier.getLevel());
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (modifier.getLevel()>0&&target!=null&&attacker!=null){
            target.forceAddEffect(new MobEffectInstance(MobEffects.WITHER,100,1),attacker);
            AttributeInstance attribute = target.getAttributes().getInstance(Attributes.ARMOR);
            if (attribute!=null){
                attribute.setBaseValue(attribute.getBaseValue()-2*modifier.getLevel());
            }
        }
        return false;
    }


    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity livingEntity) {
        if (!tool.isBroken()){
            tool.setDamage(tool.getDamage()+modifier.getLevel());
        }
        return amount;
    }
}
