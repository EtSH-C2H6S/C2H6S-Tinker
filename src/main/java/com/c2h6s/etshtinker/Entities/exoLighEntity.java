package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class exoLighEntity extends ItemProjectile{
    public int time =0;
    public float damage=256;
    public exoLighEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void tick() {
        AABB aabb =new AABB(this.getX()-0.75,this.getY()-10,this.getZ()-0.75,this.getX()+0.75,this.getY()+10,this.getZ()+0.75);
        if (this.time==0){
            double y =this.getY()+10;
            double x =this.getX();
            double z =this.getZ();
            while (y>this.getY()-10){
                if (this.level instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(etshtinkerParticleType.exo_ligh.get(),x,y,z,1,0,0,0,0);
                }
                y--;
            }
        }
        List<LivingEntity> ls = this.level.getEntitiesOfClass(LivingEntity.class,aabb);
        for (LivingEntity living :ls){
            if (living !=this.getOwner()){
                if (this.getOwner() instanceof Player player){
                    living.invulnerableTime=0;
                    living.hurt(playerThroughSource.PlayerQuark(player,this.damage),this.damage);
                    living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+5);
                }else {
                    living.invulnerableTime=0;
                    living.hurt(throughSources.quark(this.damage),this.damage);
                    living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+5);
                }
            }
        }

        time++;
        if (this.time>=16){
            this.discard();
        }
        super.tick();
    }
}
