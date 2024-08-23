package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.util.vecCalc.*;

import java.util.List;

public class extraweight extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(holder instanceof Player player&&isCorrectSlot&&player.getDeltaMovement().y<-0.1){
            double xx = player.getX();
            double yy = player.getY();
            double zz = player.getZ();
            player.setDeltaMovement(player.getDeltaMovement().x,(player.getDeltaMovement().y)-0.15,player.getDeltaMovement().z);
            List<Mob> ls0000 = player.level.getEntitiesOfClass(Mob.class,new AABB(xx+16,yy+2,zz+16,xx-16,yy-2,zz-16));
            for (Mob mob:ls0000){
                if (mob!=null){
                    Vec3 base = getUnitizedVec3(Entity1ToEntity2(player,mob));
                    if (base != null) {
                        Vec3 vec3=new Vec3(-base.x*player.getDeltaMovement().y*3,-player.getDeltaMovement().y*1,-base.z*player.getDeltaMovement().y*3);
                        mob.setDeltaMovement(vec3);
                    }
                }
            }
        }
    }
}
