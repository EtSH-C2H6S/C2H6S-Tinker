package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.hoshino.cti.util.AttackUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

import static slimeknights.tconstruct.library.tools.helper.ToolAttackUtil.getCooldownFunction;

public class plasmawaveslashentity extends ItemProjectile {
    public int time =0;
    public IToolStackView tool =null;
    public IntOpenHashSet hitId = new IntOpenHashSet();
    public boolean notExtra = false;
    public float amplifier = 1f;
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
        if (this.tickCount>=8){
            this.remove(RemovalReason.DISCARDED);
        }
        Vec3 movement =this.getDeltaMovement();
        this.setPos(movement.x+this.getX(),movement.y+this.getY(),movement.z+this.getZ());
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(movement), this::canHitEntity);
        if (this.getOwner() instanceof Player player) {
            for (LivingEntity entity : ls) {
                hitId.add(entity.getId());
                if (tool != null) {
                    entity.invulnerableTime = 0;
                    AttackUtil.attackEntity(tool, player, InteractionHand.MAIN_HAND, entity, () -> 1, !notExtra, EquipmentSlot.MAINHAND,false,-1,amplifier);
                }
                entity.setSecondsOnFire(65535);
                entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(), 1000, 3, false, false), player);
            }
        }
        super.tick();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity)&& entity != this.getOwner() &&!(entity instanceof Player)&&!hitId.contains(entity.getId());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.plasmawaveslash.get());
    }
}
