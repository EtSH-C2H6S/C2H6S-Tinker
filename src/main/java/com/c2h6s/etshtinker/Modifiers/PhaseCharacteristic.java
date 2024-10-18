package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.CustomSonicBoomEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import java.util.function.Predicate;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;
import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

public class PhaseCharacteristic extends etshmodifieriii {
    private final String sonic ="etsh.sonic";

    @Override
    public int getPriority() {
        return 512;
    }

    @Override
    public ItemStack modifierFindAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        if (livingEntity instanceof ServerPlayer player){
            if (player.totalExperience>10){
                player.giveExperiencePoints(-10);
                return new ItemStack(Items.ARROW,64);
            }
        }
        return super.modifierFindAmmo(tool, modifiers, livingEntity, itemStack, predicate);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (abstractArrow!=null){
            if (livingEntity instanceof ServerPlayer player){
                if (player.totalExperience>30){
                    player.giveExperiencePoints(-30);
                }
                else return;
            }
            abstractArrow.getPersistentData().putInt(sonic,modifiers.getLevel());
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&attacker instanceof ServerPlayer serverPlayer){
            if (arrow.getPersistentData().getInt(sonic)>0){
                int amount = arrow.getPersistentData().getInt(sonic)*2;
                for(int i=0;i<amount;i++){
                    arrow.playSound(SoundEvents.WARDEN_SONIC_BOOM,1,1);
                    CustomSonicBoomEntity entity =new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),arrow.level);
                    entity.setOwner(serverPlayer);
                    entity.direction=getScatteredVec3( new Vec3(0,1,0),87);
                    entity.damage= (float) (0.5*arrow.getBaseDamage()*getMold(arrow.getDeltaMovement()));
                    entity.setPos(arrow.getX(),arrow.getY()-0.25,arrow.getZ());
                    entity.range=4;
                    arrow.level.addFreshEntity(entity);
                    CustomSonicBoomEntity entity1 =new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),arrow.level);
                    entity1.setOwner(serverPlayer);
                    entity1.direction=getScatteredVec3( new Vec3(0,-1,0),87);
                    entity1.damage= (float) (0.5*arrow.getBaseDamage()*getMold(arrow.getDeltaMovement()));
                    entity1.setPos(arrow.getX(),arrow.getY()-0.25,arrow.getZ());
                    arrow.level.addFreshEntity(entity1);
                    entity1.range=4;
                }
                ExperienceOrb orb =new ExperienceOrb(EntityType.EXPERIENCE_ORB,arrow.level);
                orb.setPos(arrow.getX(),arrow.getY(),arrow.getZ());
                orb.value=10;
                arrow.level.addFreshEntity(orb);
            }
        }
        return false;
    }

    @Override
    public boolean modifierOnProjectileHitBlock(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        if (projectile instanceof AbstractArrow arrow&&attacker instanceof ServerPlayer serverPlayer){
            if (arrow.getPersistentData().getInt(sonic)>0){
                int amount = arrow.getPersistentData().getInt(sonic)*2;
                for(int i=0;i<amount;i++){
                    arrow.playSound(SoundEvents.WARDEN_SONIC_BOOM,1,1);
                    CustomSonicBoomEntity entity =new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),arrow.level);
                    entity.setOwner(serverPlayer);
                    entity.direction=getScatteredVec3( new Vec3(0,1,0),87);
                    entity.damage= (float) (0.5*arrow.getBaseDamage()*getMold(arrow.getDeltaMovement()));
                    entity.setPos(arrow.getX(),arrow.getY(),arrow.getZ());
                    entity.range=4;
                    arrow.level.addFreshEntity(entity);
                    CustomSonicBoomEntity entity1 =new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),arrow.level);
                    entity1.setOwner(serverPlayer);
                    entity1.direction=getScatteredVec3( new Vec3(0,-1,0),87);
                    entity1.damage= (float) (0.5*arrow.getBaseDamage()*getMold(arrow.getDeltaMovement()));
                    entity1.setPos(arrow.getX(),arrow.getY(),arrow.getZ());
                    arrow.level.addFreshEntity(entity1);
                    entity1.range=4;
                }
                ExperienceOrb orb =new ExperienceOrb(EntityType.EXPERIENCE_ORB,arrow.level);
                orb.setPos(arrow.getX(),arrow.getY(),arrow.getZ());
                orb.value=10;
                arrow.level.addFreshEntity(orb);
            }
        }
        return false;
    }
}
