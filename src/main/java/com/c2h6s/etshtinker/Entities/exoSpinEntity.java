package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class exoSpinEntity extends ItemProjectile{
    public Entity entity;
    public int life =4;
    public int time =0;
    public float rotationSpeed =30;
    public float scale =1;
    protected exoSpinEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected Item getDefaultItem() {
        return etshtinkerItems.exo_spin.get();
    }

    @Override
    public void tick() {
        super.tick();
        time++;
        if (this.entity!=null){
            this.setPos(this.entity.getX(),this.entity.getY(),this.entity.getZ());
        }
        if (time>=life){
            this.discard();
        }
    }
}
