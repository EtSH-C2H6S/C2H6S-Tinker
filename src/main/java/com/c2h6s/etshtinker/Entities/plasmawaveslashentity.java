package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class plasmawaveslashentity extends ItemProjectile {
    public float baseDamage;
    public int time =0;
    public IToolStackView tool =null;
    public plasmawaveslashentity(EntityType<? extends ItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.plasmawaveslash.get()));
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        time++;
        Vec3 movement =this.getDeltaMovement();
        if (time>=8){
            this.remove(RemovalReason.DISCARDED);
        }
        this.setPos(movement.x+this.getX(),movement.y+this.getY(),movement.z+this.getZ());
        super.tick();
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(movement));
        for (LivingEntity entity :ls){
            if (entity!=null&&entity!=this.getOwner()&&this.getOwner() instanceof Player player){
                if (tool!=null) {
                    entity.invulnerableTime = 0;
                    ToolAttackUtil.attackEntity(tool,player,entity);
                }
                entity.setSecondsOnFire(65535);
                entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),1000,0,false,false),player);
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.plasmawaveslash.get());
    }
}
