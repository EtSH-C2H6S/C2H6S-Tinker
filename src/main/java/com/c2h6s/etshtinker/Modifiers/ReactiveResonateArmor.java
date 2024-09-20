package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.CustomSonicBoomEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

public class ReactiveResonateArmor extends etshmodifieriii {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        Entity entity =context.getEntity();
        if (entity instanceof LivingEntity) {
            CustomSonicBoomEntity entity2 = new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(), entity.level);
            entity2.setOwner(entity);
            entity2.direction = getScatteredVec3(new Vec3(0, 1, 0), 87);
            entity2.damage = amount;
            entity2.setPos(entity.getX(), entity.getY() +0.5*entity.getBbHeight(), entity.getZ());
            entity2.range = 8;
            entity2.level.addFreshEntity(entity2);
            CustomSonicBoomEntity entity1 = new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(), entity.level);
            entity1.setOwner(entity);
            entity1.direction = getScatteredVec3(new Vec3(0, -1, 0), 87);
            entity1.damage = amount;
            entity1.setPos(entity.getX(), +0.5*entity.getBbHeight(), entity.getZ());
            entity1.level.addFreshEntity(entity1);
            entity1.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1, 1);
            entity1.range = 8;
        }
        return amount*0.75f;
    }
}
