package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.vecCalc.*;

import java.util.List;

public class warpprotection extends etshmodifieriii {
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot&&holder!=null&&level!=null){
            double x=holder.getX();
            double y=holder.getY();
            double z=holder.getZ();
            int l =modifier.getLevel();
            List<Entity> ls0 = level.getEntitiesOfClass(Entity.class,new AABB(x+l*1.5,y+holder.getBbHeight()+l*1.5,z+l*1.5,x-l*1.5,y-l*1.5,z-l*1.5));
            for(Entity entity:ls0){
                if (entity!=null&&!(entity instanceof LivingEntity)){
                    if (entity instanceof Projectile projectile&&projectile.getOwner()!=holder){
                        Entity entity1 =projectile.getOwner();
                        if (entity1 instanceof LivingEntity entity2) {
                            double xx =entity2.getX();
                            double yy =entity2.getY()+entity2.getBbHeight()*0.5;
                            double zz =entity2.getZ();
                            double vx =projectile.getDeltaMovement().x;
                            double vy =projectile.getDeltaMovement().y;
                            double vz =projectile.getDeltaMovement().z;
                            projectile.setPos(xx-1.5*vx,yy-1.5*vy,zz-1.5*vz);
                            projectile.setOwner(holder);
                        }
                    }
                    if (!(entity instanceof Projectile)){
                        LivingEntity entity1 =getNearestLiEnt(32f,holder,level);
                        if (entity1 !=null) {
                            double xx =entity1.getX();
                            double yy =entity1.getY()+entity1.getBbHeight()*0.5;
                            double zz =entity1.getZ();
                            double vx =entity.getDeltaMovement().x;
                            double vy =entity.getDeltaMovement().y;
                            double vz =entity.getDeltaMovement().z;
                            entity.setPos(xx-1.5*vx,yy-1.5*vy,zz-1.5*vz);
                        }
                        else entity.discard();
                    }
                }
            }
        }
    }
}
