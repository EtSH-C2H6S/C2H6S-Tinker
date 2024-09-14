package com.c2h6s.etshtinker.mixin;

import com.c2h6s.etshtinker.Entities.process.LivingEntityTick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void EtSHTick(CallbackInfo callbackInfo){
        LivingEntity entity = ((LivingEntity) (Object) this);
        Level level = entity.level;
        if (!level.isClientSide) {
            LivingEntityTick.onLivingTick(entity,(ServerLevel) level);
        }
    }
}
