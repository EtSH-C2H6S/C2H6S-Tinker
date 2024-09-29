package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.ShockWaveEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class AtomicRestructer extends etshmodifieriii {
    private final ResourceLocation des = new ResourceLocation(MOD_ID, "des");
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity attacker =context.getAttacker();
        LivingEntity target =context.getLivingTarget();
        int x =0;
        if (target!=null&&context.isCritical()){
            Collection<MobEffectInstance> harmeffect;
            harmeffect = target.getActiveEffects();
            x+=harmeffect.size();
            for (int i = 0; i < harmeffect.size(); i++){
                MobEffectInstance effect = harmeffect.stream().toList().get(i);
                MobEffect harm = effect.getEffect();
                target.removeEffect(harm);
            }
            harmeffect = attacker.getActiveEffects();
            x+=harmeffect.size();
            for (int i = 0; i < harmeffect.size(); i++){
                MobEffectInstance effect = harmeffect.stream().toList().get(i);
                MobEffect harm = effect.getEffect();
                attacker.removeEffect(harm);
            }
            if (x>2) {
                ShockWaveEntity shockWave = new ShockWaveEntity(etshtinkerEntity.shock_wave.get(), attacker.level);
                shockWave.damage = Math.min(80,x*5F);
                shockWave.setOwner(attacker);
                shockWave.setDeltaMovement(new Vec3(1, 0, 0).scale(Math.min(8, x)));
                shockWave.setPos(target.getX(), target.getY() + 0.5 * target.getBbHeight(), target.getZ());
                target.level.addFreshEntity(shockWave);
                if (target.level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION,target.getX(), target.getY() + 0.5 * target.getBbHeight(), target.getZ(),1,0,0,0,0);
                }
                target.playSound(SoundEvents.GENERIC_EXPLODE,1,1);
            }
        }
        return knockback;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (tool.getPersistentData().getInt(des)>=0&&tool.isBroken()){
            tool.getPersistentData().remove(des);
        }
    }
}
