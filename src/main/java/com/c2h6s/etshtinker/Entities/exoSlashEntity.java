package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import slimeknights.tconstruct.gadgets.entity.shuriken.ShurikenEntityBase;
import slimeknights.tconstruct.tools.TinkerTools;


import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.*;

public class exoSlashEntity extends ItemProjectile {
    public float baseDamage;
    public int time =0;
    public int count =0;

    public float setDamage(float damage){
        baseDamage =damage;
        return damage;
    }
    public exoSlashEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(etshtinkerEntity.exoslash.get(), world);
    }
    public exoSlashEntity(EntityType<? extends exoSlashEntity> type, Level world) {
        super(type, world);
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
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.exoslash.get());
    }


    public void tick() {
        time++;
        Level world1 = this.getLevel();
        if (time >= 5 && world1 instanceof ServerLevel serverLevel) {
            Vec3 initVec = this.getDeltaMovement();
            initVec = initVec.scale(Math.max(0,1-0.025*time));
            Vec3 vec3 = Entity1ToEntity2(this, getNearestLiEnt(32f, this, this.level));
            double trackVelo;
            if (getMold(vec3) != 0) {
                if (getMold(vec3)<=2){
                    initVec = initVec.scale(0.1);
                }
                trackVelo = Math.min(getMold(vec3), 8 / getMold(vec3));
                trackVelo = Math.min(trackVelo, 2);
            } else trackVelo = 0;
            vec3 = getUnitizedVec3(vec3).scale(trackVelo);
            vec3 = initVec.add(vec3);
            if (getMold(vec3)>=1.5){
                vec3.scale(1.5/getMold(vec3));
            }
            if (getMold(vec3)>=0.1){
                serverLevel.sendParticles(etshtinkerParticleType.exo.get(), this.getX(), this.getY()+0.5*this.getBbHeight(), this.getZ(), 5, 0.05, 0.05, 0.05, 0.0125);
            }
            this.setDeltaMovement(vec3);
        }
        if (time > 100) {
            this.remove(RemovalReason.KILLED);
        }
        this.setPos(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(this.getDeltaMovement()));
        if (!ls.isEmpty()){
            for (LivingEntity living:ls){
                if (living!=null&&living!=this.getOwner()){
                    if (this.getOwner() instanceof Player player){
                        living.invulnerableTime=0;
                        living.hurt(playerThroughSource.PlayerQuark(player,this.baseDamage),this.baseDamage);
                        living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+30);
                        slashentity slash = new slashentity(etshtinkerEntity.slashentity.get(),this.level);
                        slash.setOwner(this.getOwner());
                        slash.exo=true;
                        slash.target=living;
                        slash.damage=this.baseDamage/2;
                        slash.count=this.count;
                        slash.setPos( living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ());
                        this.level.addFreshEntity(slash);
                    }else {
                        living.invulnerableTime=0;
                        living.hurt(throughSources.quark(this.baseDamage),this.baseDamage);
                        living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+30);
                    }
                }
            }
            this.discard();
        }
        super.tick();
    }
}
