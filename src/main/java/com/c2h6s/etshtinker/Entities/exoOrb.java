package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.ThroughSources;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.network.NetworkHooks;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class exoOrb extends ItemProjectile{
    public float baseDamage;
    public int time =0;
    public IToolStackView tool;
    public boolean summonLIGH =false;
    public exoOrb(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
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

    @Override
    public boolean isAttackable() {
        return false;
    }


    public void tick() {
        time++;
        Level world1 = this.getLevel();
        if (time >= 10 ) {
            Vec3 initVec = this.getDeltaMovement();
            Vec3 vec3 = Entity1ToEntity2(this, getNearestLiEnt(16f, this, this.level));
            double trackVelo;
            if (getMold(vec3) != 0) {
                if (getMold(vec3)<=2){
                    initVec = initVec.scale(0.1);
                }
                initVec = initVec.scale(Math.max(0,1-0.05*time));
                trackVelo = Math.min(getMold(vec3), 8 / getMold(vec3));
                trackVelo = Mth.clamp(0.5,trackVelo, 2);
            } else trackVelo = 0;
            vec3 = getUnitizedVec3(vec3).scale(trackVelo);
            vec3 = initVec.add(vec3);
            if (getMold(vec3)>=2.5){
                vec3.scale(2.5/getMold(vec3));
            }

            this.setDeltaMovement(vec3);
        }
        if (getMold(this.getDeltaMovement())>=0.1&& world1 instanceof ServerLevel serverLevel&&time>2){
            serverLevel.sendParticles(etshtinkerParticleType.exo.get(), this.getX(), this.getY()+0.5*this.getBbHeight(), this.getZ(), 5, 0.05, 0.05, 0.05, 0.0125);
        }
        if (time > 1000) {
            this.remove(RemovalReason.KILLED);
        }
        this.setPos(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(this.getDeltaMovement()));
        if (!ls.isEmpty()){
            for (LivingEntity living:ls){
                if (living!=null&&living!=this.getOwner()&&!(living instanceof Player)){
                    if (this.getOwner() instanceof Player player){
                        living.invulnerableTime=0;
                        playerThroughSource.PlayerQuark(player,this.baseDamage/8).hurtEntity(living);
                        living.invulnerableTime=0;
                        if (this.getCapability(EntityModifierCapability.CAPABILITY).isPresent()&&this.getLivingOwner()!=null){
                            AbstractArrow arrow =new Arrow(this.level,this.getLivingOwner());
                            arrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.setModifiers(this.getCapability(EntityModifierCapability.CAPABILITY).orElse(null).getModifiers()));
                            arrow.setBaseDamage(this.baseDamage);
                            ProjectileImpactEvent event = new ProjectileImpactEvent(arrow,new EntityHitResult(living));
                            MinecraftForge.EVENT_BUS.post(event);
                            living.hurt(DamageSource.thrown(this,this.getOwner()),(float)( arrow.getBaseDamage()*arrow.getDeltaMovement().length()));
                            arrow.discard();
                        }
                        living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+10);
                        if (this.summonLIGH) {
                            exoLighEntity entity = new exoLighEntity(etshtinkerEntity.exo_ligh.get(), this.level);
                            entity.damage = this.baseDamage/8;
                            entity.setOwner(player);
                            entity.setPos(living.getX() + EtSHrnd().nextDouble() - 0.5, living.getY() + EtSHrnd().nextDouble() - 0.5 + living.getBbHeight() / 2, living.getZ() + EtSHrnd().nextDouble() - 0.5);
                            this.level.addFreshEntity(entity);
                        }
                    }else {
                        living.invulnerableTime=0;
                        ThroughSources.quark(this.baseDamage).hurtEntity(living);
                        living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+10);
                    }
                }
            }
            this.discard();
        }
        super.tick();
    }
}
