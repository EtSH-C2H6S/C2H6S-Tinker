package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PlasmaSlashEntity extends Projectile {
    public PlasmaSlashEntity(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    public ItemStack Slash = new ItemStack(etshtinkerItems.plasma_slash_red.get());
    public float angle =0;


    @Override
    protected void defineSynchedData() {

    }
}
