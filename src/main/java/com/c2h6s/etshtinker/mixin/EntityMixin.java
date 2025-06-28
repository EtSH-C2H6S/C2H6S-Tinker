package com.c2h6s.etshtinker.mixin;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.ThroughSources;
import com.c2h6s.etshtinker.capability.IDampenCapability;
import com.c2h6s.etshtinker.capability.etshCap;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.util.Cap;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

import static com.c2h6s.etshtinker.util.vecCalc.getNearestLiEntWithEntBL;

@Mixin({Entity.class})
public class EntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "dampensVibrations",cancellable = true)
    public void etshDampen(CallbackInfoReturnable<Boolean> callbackinfo){
        float dampen =0;
        List<EquipmentSlot> Slots =List.of(EquipmentSlot.CHEST,EquipmentSlot.FEET,EquipmentSlot.HEAD,EquipmentSlot.LEGS,EquipmentSlot.MAINHAND,EquipmentSlot.MAINHAND);
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living) {
            for (EquipmentSlot slot : Slots) {
                ItemStack stack =living.getItemBySlot(slot);
                Optional<IDampenCapability> capability = Cap.getCapability(stack, etshCap.DAMPEN_CAPABILITY,null).resolve();
                if(capability.isPresent()){
                    dampen+= capability.get().getDampenCap();
                }
            }
        }
        callbackinfo.setReturnValue(dampen>=1);
    }

    @Inject(at = @At(value = "RETURN"), method = "isInvulnerableTo",cancellable = true)
    public void rejectInvulerable(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity living && !(entity instanceof Player player)) {
            if (living.hasEffect(etshtinkerEffects.ionized.get()) || living.hasEffect(etshtinkerEffects.novaradiation.get())||living.getPersistentData().contains("quark_disassemble")) {
                cir.setReturnValue(false);
            }
            if(source instanceof playerThroughSource||source instanceof ThroughSources){
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(at = @At(value = "RETURN"), method = "isInvulnerable",cancellable = true)
    public void removeInvulerable(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity living && !(entity instanceof Player player)) {
            if ( living.hasEffect(etshtinkerEffects.novaradiation.get())||living.getPersistentData().contains("quark_disassemble")) {
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "tick")
    public void tick(CallbackInfo ci){
        Entity entity0 =(Entity) (Object)this;
        if(entity0 instanceof AbstractArrow arrow&&arrow.getTags().contains("warping")&&arrow.piercedAndKilledEntities != null&&arrow.piercedAndKilledEntities.size()<arrow.getPierceLevel()+1){
            arrow.removeTag("warping");
            LivingEntity entity =getNearestLiEntWithEntBL(16f,arrow,arrow.level,arrow.piercedAndKilledEntities);
            if (entity!=null) {
                if (arrow.level instanceof ServerLevel serverLevel) {
                    ParticleChainUtil.SummonParticleChain(serverLevel, arrow.position(), entity.position().add(0, entity.getBbHeight() * 0.5, 0), ParticleTypes.ELECTRIC_SPARK);
                }
                arrow.setPos(entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ());
                EntityHitResult entityHitResult = new EntityHitResult(entity);
                ForgeEventFactory.onProjectileImpact(arrow, entityHitResult);
                arrow.onHit(entityHitResult);
                arrow.piercedAndKilledEntities.add(entity);
            }
        }
    }

}
