package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerMekansimMaterial.*;

public class annihilateexplosionentity extends ItemProjectile{
    public LivingEntity target =null;
    public int time =0;
    public float damage=0;
    public boolean proceedRecipe =false;
    public int proceedamount =0;
    public double radius =5;
    public annihilateexplosionentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
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
        Level level1 = this.level;
        if (this.time == 2){
            level1.addAlwaysVisibleParticle(etshtinkerParticleType.annihilateexplosionparticle.get(),this.getX(),this.getY(),this.getZ(),0,0,0);
        }
        if (this.time>10){
            this.discard();
        }
        if (this.getOwner() instanceof Player player&&this.target!=null){
            if (this.time ==3) {
                this.target.invulnerableTime = 0;
                this.target.hurt(DamageSource.playerAttack(player), this.damage);
                this.target.invulnerableTime = 0;
                this.target.hurt(DamageSource.MAGIC, this.damage);
                this.target.invulnerableTime = 0;
                this.target.hurt(DamageSource.explosion(player), this.damage);
                this.target.invulnerableTime = 0;
                this.target.hurt(DamageSource.WITHER, this.damage);
                this.target.invulnerableTime = 0;
            }
            if (this.time==6||this.time==9){
                List<LivingEntity> livingEntities =this.level.getEntitiesOfClass(LivingEntity.class,new AABB(this.getX()-this.radius,this.getY()-this.radius,this.getZ()-this.radius,this.getX()+this.radius,this.getY()+this.radius,this.getZ()+this.radius));
                for (LivingEntity targets:livingEntities){
                    if (targets!=this.getOwner()){
                        targets.invulnerableTime=0;
                        targets.hurt(playerThroughSource.PlayerAnnihilate(player,this.damage).bypassArmor(),this.damage);
                    }
                }
            }
        }
        if (this.proceedRecipe){
            if (this.time>=6){
                ((ServerLevel)this.level).sendParticles(etshtinkerParticleType.nova.get(),this.getX(),this.getY(),this.getZ(),64,1,1,1,8);
                AABB aabb =new AABB(this.getX()-this.radius,this.getY()-this.radius,this.getZ()-this.radius,this.getX()+this.radius,this.getY()+this.radius,this.getZ()+this.radius);
                List<LivingEntity> livingEntities =this.level.getEntitiesOfClass(LivingEntity.class,aabb);
                List<ItemEntity> itemEntities =this.level.getEntitiesOfClass(ItemEntity.class,aabb);
                for (LivingEntity targets:livingEntities){
                    if (targets.isAlive()) {
                        targets.invulnerableTime = 0;
                        targets.hurt(DamageSource.explosion(targets), this.damage);
                        targets.invulnerableTime = 0;
                        targets.hurt(DamageSource.explosion(targets), this.damage);
                        if (targets instanceof Player player){
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.playerAttack(player), this.damage);
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.playerAttack(player), this.damage);
                        }
                        targets.playSound(SoundEvents.GENERIC_EXPLODE,1,1);
                    }
                }
                if (this.time==6&&this.level instanceof ServerLevel level2) {
                    int tias =0;
                    for (ItemEntity items : itemEntities) {
                        if (items.getItem().is(trinity_intereactive_alloy.get())) {
                            tias +=items.getItem().getCount();
                        }
                    }
                    int actual1 = Math.min(this.proceedamount, tias);
                    for (ItemEntity items : itemEntities) {
                        if (items.getItem().is(trinity_intereactive_alloy.get())&&actual1>0) {
                            int consume =Math.min(actual1, tias);
                            items.getItem().setCount(items.getItem().getCount()-consume);
                            ItemEntity alloy =new ItemEntity(EntityType.ITEM,this.level);
                            alloy.setItem(new ItemStack(marcoatom.get(),consume));
                            alloy.setPos(this.getX(),this.getY(),this.getZ());
                            level2.addFreshEntity(alloy);
                            actual1-=items.getItem().getCount();
                        }
                        if (actual1==0){
                            break;
                        }
                    }
                }
            }
        }
        time++;
    }
}
