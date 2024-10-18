package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Entities.CustomSonicBoomEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

public class ReactiveResonateArmor extends etshmodifieriii {
    @Override
    public boolean isNoLevels() {
        return true;
    }
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("reactive_resonate_armor");
    public ReactiveResonateArmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    private void livinghurtevent(LivingHurtEvent event) {
        if (event.getSource() instanceof EntityDamageSource entityDamageSource&&entityDamageSource.isThorns()){
            return;
        }
        if (event.getEntity()==event.getSource().getEntity()){
            return;
        }
        LivingEntity living = event.getEntity();
        float amount = event.getAmount();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (living instanceof LivingEntity) {
                    CustomSonicBoomEntity entity2 = new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(), living.level);
                    entity2.setOwner(living);
                    entity2.direction = getScatteredVec3(new Vec3(0, 1, 0), 87);
                    entity2.damage = amount;
                    entity2.setPos(living.getX(), living.getY() +0.5* living.getBbHeight(), living.getZ());
                    entity2.range = 8;
                    entity2.level.addFreshEntity(entity2);
                    CustomSonicBoomEntity entity1 = new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(), living.level);
                    entity1.setOwner(living);
                    entity1.direction = getScatteredVec3(new Vec3(0, -1, 0), 87);
                    entity1.damage = amount;
                    entity1.setPos(living.getX(), +0.5* living.getBbHeight(), living.getZ());
                    entity1.level.addFreshEntity(entity1);
                    entity1.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1, 1);
                    entity1.range = 8;
                }
                event.setAmount(event.getAmount()*0.8f);
            }
        });
    }

}
