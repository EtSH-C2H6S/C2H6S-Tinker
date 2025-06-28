package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.vecCalc.*;

import java.util.List;

public class warpprotection extends EtshModifieriii {
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
                            projectile.setPos(xx,yy,zz);
                            projectile.setOwner(holder);
                            EntityHitResult entityHitResult =new EntityHitResult(entity2);
                            ForgeEventFactory.onProjectileImpact(projectile, entityHitResult);
                            projectile.onHit(entityHitResult);
                        }
                        else {
                            LivingEntity entity2 =getNearestLiEnt(32f,holder,level);
                            if (entity2 !=null) {
                                double xx = entity2.getX();
                                double yy = entity2.getY()+ entity2.getBbHeight()*0.5;
                                double zz = entity2.getZ();
                                entity.setPos(xx,yy,zz);
                                EntityHitResult entityHitResult =new EntityHitResult(entity2);
                                ForgeEventFactory.onProjectileImpact(projectile, entityHitResult);
                                projectile.onHit(entityHitResult);
                            }
                        }
                    }
                }
            }
        }
    }
}
