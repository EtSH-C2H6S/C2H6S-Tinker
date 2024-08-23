package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import slimeknights.tconstruct.gadgets.entity.shuriken.ShurikenEntityBase;
import slimeknights.tconstruct.tools.TinkerTools;


import static com.c2h6s.etshtinker.util.vecCalc.*;

public class exoSlashEntity extends ShurikenEntityBase {
    public float baseDamage;
    public int time =0;
    public boolean homing=false;

    public float setDamage(float damage){
        baseDamage =damage;
        return damage;
    }
    public exoSlashEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(etshtinkerEntity.exoslash.get(), world);
    }
    public exoSlashEntity(EntityType<? extends ShurikenEntityBase> type, double x, double y, double z, Level worldIn){
        super(type, x, y, z, worldIn);
    }
    public exoSlashEntity(EntityType<? extends exoSlashEntity> type, Level world) {
        super(type, world);
    }
    public exoSlashEntity(Level worldIn, LivingEntity throwerIn) {
        super(etshtinkerEntity.exoslash.get(), throwerIn, worldIn);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.exoslash.get());
    }

    @Override
    public float getDamage() {
        return baseDamage;
    }

    @Override
    public float getKnockback() {
        return 5;
    }
    public void tick() {
        time++;
        LivingEntity entity =null;
        Level world1 =this.getLevel();
        if (time>=2&&!homing) {
            world1.addParticle(ParticleTypes.ELECTRIC_SPARK,  this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        if (time>100){
            this.remove(RemovalReason.KILLED);
        }
        if (time>=8){
            if (!homing&&getNearestLiEnt(32f, this, this.level)!=null) {
                entity = getNearestLiEnt(32f, this, this.level);
                homing=true;
            }
            if (entity!=null) {
                if (entity.isDeadOrDying()) {
                    homing = false;
                }
                if (homing) {
                    world1.addParticle((SimpleParticleType) etshtinkerParticleType.nova.get(),  this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                    Vec3 vec3 = getUnitizedVec3(Entity1ToEntity2(this, entity));
                    if (vec3 != null) {
                        this.lerpMotion(vec3.x, vec3.y, vec3.z);
                    }
                }
            }
            if (entity==null){
                homing =false;
            }
        }
        super.tick();
    }
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Level world =entity.level;
        world.addAlwaysVisibleParticle(TinkerTools.axeAttackParticle.get(),true,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),0,0,0);
        if (entity!=this.getOwner()&&entity instanceof LivingEntity&&this.getOwner() instanceof Player player){
            entity.hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor().bypassInvul(), this.getDamage()*10);
            entity.invulnerableTime=0;
            if (((LivingEntity) entity).hasEffect(etshtinkerEffects.novaradiation.get())) {
                ((LivingEntity) entity).removeEffect(etshtinkerEffects.novaradiation.get());
            }
            ((LivingEntity) entity).forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, 10, false, false), player);
            this.playSound(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH,1,1.75f);
            if (!level.isClientSide() && entity instanceof LivingEntity) {
                Vec3 motion = this.getDeltaMovement().normalize();
                ((LivingEntity) entity).knockback(this.getKnockback(), -motion.x, -motion.z);
            }
        }
    }
}
