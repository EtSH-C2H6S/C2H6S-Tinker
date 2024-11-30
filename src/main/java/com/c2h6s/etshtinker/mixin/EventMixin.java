package com.c2h6s.etshtinker.mixin;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Event.class,remap = false)
public class EventMixin {
    @Inject(at = @At(value = "HEAD"), method = "setCanceled", cancellable = true)
    private void rejectCancel(boolean cancel, CallbackInfo ci) {
        Event event1 = (Event) (Object) this;
        if (event1 instanceof LivingDeathEvent event){
            if (event.getSource() instanceof playerThroughSource||event.getSource() instanceof throughSources){
                ci.cancel();
            }
        }
        if (event1 instanceof LivingAttackEvent event){
            if (event.getSource() instanceof playerThroughSource||event.getSource() instanceof throughSources){
                ci.cancel();
            }
        }
        if (event1 instanceof LivingHurtEvent event){
            if (event.getSource() instanceof playerThroughSource||event.getSource() instanceof throughSources){
                ci.cancel();
            }
        }
        if (event1 instanceof LivingDamageEvent event){
            if (event.getSource() instanceof playerThroughSource||event.getSource() instanceof throughSources){
                ci.cancel();
            }
        }
    }

}
