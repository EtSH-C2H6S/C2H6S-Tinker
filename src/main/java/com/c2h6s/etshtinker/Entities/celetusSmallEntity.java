package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class celetusSmallEntity extends ItemProjectile{
    protected celetusSmallEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected Item getDefaultItem() {
        return etshtinkerItems.celetus.get();
    }

}
