package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import mekanism.api.MekanismAPI;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.util.modloaded.Mekenabled;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class plasmaexplosionentity extends ItemProjectile{
    public Vec3 rayVec3 =new Vec3(0,0,0);
    public SimpleParticleType particle = null;
    public float damage =0;
    public int time =0;
    public String special =null;
    public ToolStack tool =null;
    public float scale =1;

    public void getRayVec3(Vec3 vec3){
        this.rayVec3 =vec3;
    }
    public void getExplosionParticle(SimpleParticleType particleType){
        this.particle =particleType;
    }
    public void getBaseDamage(Float damage){
        this.damage =damage;
    }

    public plasmaexplosionentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.plasmaexplosion.get()));
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
    @Override
    public void tick() {
        time++;
        if (time>12){
            this.discard();
        }
        if (time >=1){
            if (time ==1) {
                Level world = this.level;
                int range = (int) getMold(rayVec3);
                Vec3 vec3 = getUnitizedVec3(rayVec3);
                if (special != null && special.equals("tracking")) {
                    Vec3 vec31 = getUnitizedVec3(Entity1ToEntity2(this, getNearestLiEnt((float) range, this, this.level)));
                    if (vec31 != null) {
                        vec3 = vec31;
                    }
                }
                Vec3 pos = new Vec3(this.getX(), this.getY(), this.getZ());
                if (world != null && vec3 != null && range > 0 && particle != null) {
                    int i = 0;
                    List<LivingEntity> ls1 = new ArrayList<>(List.of());
                    while (i < range) {
                        i += 2;
                        double x = pos.x + i * vec3.x;
                        double y = pos.y + 0.5 * this.getBbHeight() + i * vec3.y;
                        double z = pos.z + i * vec3.z;
                        if (i <= 48) {
                            if (world.isClientSide) {
                                world.addAlwaysVisibleParticle(particle, true, x, y, z, 0, 0, 0);
                            }
                            else {
                                ((ServerLevel)world).sendParticles(particle,x,y,z,1,0,0,0,0);
                            }
                        }
                        AABB aabb = new AABB(x + 0.75*scale, y + 0.5+0.75*scale, z + 0.75*scale, x - 0.75*scale, y - 0.75*scale+0.5, z - 0.75*scale);
                        List<LivingEntity> ls0 = this.level.getEntitiesOfClass(LivingEntity.class, aabb.expandTowards(vec3));
                        if (!ls0.isEmpty()) {
                            for (LivingEntity target : ls0) {
                                if (target != null && target != this.getOwner() && this.getOwner() instanceof Player player && tool != null && !ls1.contains(target)) {
                                    target.invulnerableTime = 0;
                                    ToolAttackUtil.attackEntity(tool, player, target);
                                    target.invulnerableTime = 0;
                                    target.hurt(DamageSource.playerAttack(player), damage * 0.5f);
                                    target.invulnerableTime = 0;
                                    ls1.add(target);
                                }
                            }
                        }
                        if (special != null && special.equals("random_scatter")) {
                            vec3 = getScatteredVec3(vec3, 0.25);
                        }
                    }
                    if (!ls1.isEmpty() && special != null) {
                        if (ls1.get(0) != null) {
                            LivingEntity entity = ls1.get(0);
                            if (special.equals("antimatter_explosion")) {
                                this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 16f, Explosion.BlockInteraction.NONE);
                            }
                            if (special.equals("explosion")) {
                                this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 2f, Explosion.BlockInteraction.NONE);
                            }
                        }
                        if (!special.equals("tracking") && !special.equals("antimatter_explosion") && !special.equals("explosion")) {
                            for (LivingEntity targets : ls1) {
                                if (targets != null) {
                                    if (special.equals("ionize")) {
                                        targets.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(), 100, 9, false, false), this.getOwner());
                                    } else if (special.equals("burn")) {
                                        targets.setSecondsOnFire(200);
                                    } else if (special.equals("magic_damage")) {
                                        targets.invulnerableTime = 0;
                                        targets.hurt(DamageSource.MAGIC.bypassArmor().bypassMagic(), damage * 0.75f);
                                    } else if (special.equals("nova_radiation")) {
                                        targets.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, 9, false, false), this.getOwner());
                                    } else if (special.equals("radiation") && Mekenabled) {
                                        MekanismAPI.getRadiationManager().radiate(targets, 50);
                                    } else if (special.equals("poison")) {
                                        targets.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 300, 9), this.getOwner());
                                    } else if (special.equals("corrosive")) {
                                        if (targets.getAttributes().getInstance(Attributes.ARMOR) != null && targets.getArmorValue() > 0) {
                                            targets.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(0);
                                            targets.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(-targets.getArmorValue());
                                        }
                                        if (targets.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS) != null && targets.getArmorValue() > 0) {
                                            targets.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS).setBaseValue(-1024);
                                        }
                                        if (targets.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE) != null) {
                                            targets.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(-10);
                                        }
                                        targets.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 100, 4), this.getOwner());
                                        targets.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 4), this.getOwner());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (time ==9){
            int range =(int) getMold(rayVec3);
            Vec3 vec3 = getUnitizedVec3(rayVec3);
            if (special!=null&&special.equals("tracking")){
                Vec3 vec31 =getUnitizedVec3( Entity1ToEntity2(this,getNearestLiEnt((float)range,this,this.level)));
                if (vec31!=null){
                    vec3 =vec31;
                }
            }
            Vec3 pos =new Vec3(this.getX(),this.getY(),this.getZ());
            if (vec3!=null&&range>0&&particle!=null){
                int i = 0;
                List<LivingEntity> ls1 =new ArrayList<>(List.of());
                while (i<range){
                    i+=2;
                    double x=pos.x + i * vec3.x;
                    double y=pos.y + 0.5*this.getBbHeight() + i * vec3.y;
                    double z=pos.z + i * vec3.z;
                    AABB aabb = new AABB(x + 2*scale, y + 0.5+2*scale, z + 2*scale, x - 2*scale, y - 2*scale+0.5, z - 2*scale);
                    List<LivingEntity> ls0 =this.level.getEntitiesOfClass(LivingEntity.class,aabb.expandTowards(vec3.scale(2)));
                    for (LivingEntity target:ls0){
                        if (target!=null&&target!=this.getOwner()&&this.getOwner() instanceof Player player&&tool!=null&&!ls1.contains(target)){
                            target.invulnerableTime=0;
                            ToolAttackUtil.attackEntity(tool,player,target);
                            target.invulnerableTime=0;
                            target.hurt(DamageSource.playerAttack(player),damage);
                            target.invulnerableTime=0;
                            ls1.add(target);
                        }
                    }
                    if (special!=null&&special.equals("random_scatter")){
                        vec3 =getScatteredVec3(vec3,0.25);
                    }
                }
            }
        }
    }
}
