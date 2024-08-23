package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;


public class enchantedswordentity extends ItemProjectile {
    public int time =0;
    public float damage =0;
    public List<LivingEntity> ls0 =new ArrayList<>(List.of());

    public enchantedswordentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        Level world = this.level;
        time++;
        Vec3 movement =this.getDeltaMovement();
        if (time>=20){
            this.remove(RemovalReason.DISCARDED);
        }
        if (world!=null){
            world.addParticle(etshtinkerParticleType.mana.get(),this.getX(),this.getY(),this.getZ(),0,0,0);
        }
        this.setPos(movement.x+this.getX(),movement.y+this.getY(),movement.z+this.getZ());
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(movement));
        for (LivingEntity entity :ls){
            if (entity!=null&&entity!=this.getOwner()&&this.getOwner() instanceof Player player&&!ls0.contains(entity)){
                this.ls0.add(entity);
                entity.invulnerableTime=0;
                entity.hurt(DamageSource.playerAttack(player),this.damage);
                this.discard();
            }
        }
        super.tick();
    }

    protected void onHitBlock(BlockHitResult result) {
        this.discard();
        super.onHitBlock(result);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.enchantedsword.get()));
    }
    @Override
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.enchantedsword.get());
    }
}
