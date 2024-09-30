package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class slashentity extends ItemProjectile{
    public LivingEntity target =null;
    public int count =0;
    public int time =0;
    public float damage=0;
    public boolean exo=false;
    public slashentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
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
        super.tick();
        time++;
        if (this.target!=null&&this.target.isAlive()){
            this.setPos(this.target.getX(),this.target.getY()+0.5*this.target.getBbHeight(),this.target.getZ());
        }
        if (time ==1){
            Level world = this.level;
            if (world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(etshtinkerParticleType.slash.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
            if (this.target!=null&&this.getOwner() instanceof Player player){
                this.target.invulnerableTime=0;
                this.target.hurt(DamageSource.MAGIC,this.damage);
                this.target.invulnerableTime=0;
                this.playSound(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH,1.25f,1.25f);
                List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class,new AABB(this.blockPosition()).inflate(1));
                for (LivingEntity targets:ls){
                    if (targets!=null&&targets!=target&targets!=player&&!this.exo){
                        targets.invulnerableTime=0;
                        targets.hurt(DamageSource.MAGIC,this.damage/2);
                        targets.invulnerableTime=0;
                    }
                    if (targets!=null&&targets!=player&&this.exo){
                        targets.invulnerableTime=0;
                        targets.hurt(playerThroughSource.PlayerQuark(player,this.damage),this.damage);
                        targets.invulnerableTime=0;
                        targets.getPersistentData().putInt("quark_disassemble",targets.getPersistentData().getInt("quark_disassemble")+5);
                    }
                }
            }
        }
        if (this.time >=2) {
            if (this.count > 0 && this.target != null && this.target.isAlive() && this.time == 2) {
                slashentity entity = new slashentity(etshtinkerEntity.slashentity.get(), this.level);
                entity.setPos(this.target.getX(),this.target.getY()+0.5*this.target.getBbHeight(),this.target.getZ());
                entity.target = this.target;
                entity.count = this.count - 1;
                entity.damage = this.damage*0.75f;
                entity.setOwner(this.getOwner());
                entity.exo=this.exo;
                this.level.addFreshEntity(entity);
            }
            this.discard();
        }
    }

}
