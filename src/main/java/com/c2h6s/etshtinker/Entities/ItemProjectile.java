package com.c2h6s.etshtinker.Entities;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ItemProjectile extends Projectile implements ItemSupplier {
    static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK;
    public float Xdeg =0;
    protected ItemProjectile(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    public void setItem(ItemStack p_37447_) {
        if (!p_37447_.is(this.getDefaultItem()) || p_37447_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, (ItemStack)Util.make(p_37447_.copy(), (p_37451_) -> {
                p_37451_.setCount(1);
            }));
        }

    }

    protected abstract Item getDefaultItem();

    protected ItemStack getItemRaw() {
        return (ItemStack)this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack $$0 = this.getItemRaw();
        return $$0.isEmpty() ? new ItemStack(this.getDefaultItem()) : $$0;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag p_37449_) {
        super.addAdditionalSaveData(p_37449_);
        ItemStack $$1 = this.getItemRaw();
        if (!$$1.isEmpty()) {
            p_37449_.put("Item", $$1.save(new CompoundTag()));
        }

    }

    public void readAdditionalSaveData(CompoundTag p_37445_) {
        super.readAdditionalSaveData(p_37445_);
        ItemStack $$1 = ItemStack.of(p_37445_.getCompound("Item"));
        this.setItem($$1);
    }

    static {
        DATA_ITEM_STACK = SynchedEntityData.defineId(ItemProjectile.class, EntityDataSerializers.ITEM_STACK);
    }


}
