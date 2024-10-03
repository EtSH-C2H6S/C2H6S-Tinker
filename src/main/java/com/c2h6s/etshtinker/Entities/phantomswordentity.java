package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.tools.TinkerTools;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class phantomswordentity extends ItemProjectile{
    public LivingEntity target =null;
    public int count =0;
    public int time =0;
    public float damage=0;
    public phantomswordentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.phantom_sword.get()));
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
        if(this.time>5){
            this.discard();
        }
        double speed =(Math.pow(this.time,2) -5*this.time)/3;
        this.setDeltaMovement(0,0,speed);
        if (this.target!=null&&target.isAlive()){
            this.setPos(target.getX(), target.getY() + 3.5+speed, target.getZ());
        }
        else {
            this.setPos(this.getX(),this.getY()+speed,this.getZ());
        }
        if (this.time ==3){
            if (this.target!=null&&target.isAlive()&&this.getOwner() instanceof Player){
                this.target.invulnerableTime=0;
                this.target.hurt(DamageSource.playerAttack((Player) this.getOwner()),this.damage);
                this.target.invulnerableTime=0;
                slashentity slash =new slashentity(etshtinkerEntity.slashentity.get(),this.level);
                slash.count=2;
                slash.damage=this.damage*0.5F;
                slash.target=this.target;
                slash.setOwner(this.getOwner());
                this.level.addFreshEntity(slash);
                this.playSound(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH,1.25f,1.25f);
                if (this.count>0){
                    phantomswordentity entity =new phantomswordentity(etshtinkerEntity.phantomswordentity.get(),this.level);
                    entity.count =this.count-1;
                    entity.target =this.target;
                    entity.damage =this.damage;
                    entity.setOwner(this.getOwner());
                    SecureRandom random1 =EtSHrnd();
                    entity.setYRot(random1.nextFloat()*360);
                    entity.setPos(this.target.getX(),this.target.getY()+0.5*this.target.getBbHeight()+3.5,this.target.getZ());
                    int i = 0;
                    while (i<3){
                        if(level1.isClientSide) {
                            level1.addParticle(ParticleTypes.SCULK_SOUL, target.getX(), target.getY() + 0.5 * target.getBbHeight() + 3.5, target.getZ(), random1.nextDouble() * 0.04 - 0.02, random1.nextDouble() * 0.04 - 0.02, random1.nextDouble() * 0.04 - 0.02);
                        }
                        else {
                            ((ServerLevel)level1).sendParticles(ParticleTypes.SCULK_SOUL, target.getX(), target.getY() + 0.5 * target.getBbHeight() + 3.5, target.getZ(), 1,0,0,0, random1.nextDouble() * 0.04 - 0.02);
                        }
                        i++;
                    }
                    this.level.addFreshEntity(entity);
                }
            }
        }
        time++;
        super.tick();
    }
}
