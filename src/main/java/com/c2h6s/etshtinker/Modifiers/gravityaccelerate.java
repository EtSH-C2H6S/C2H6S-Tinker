package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Objects;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class gravityaccelerate extends etshmodifieriii {
    public boolean isNoLevels() {
        return false;
    }
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage){
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (attacker instanceof Player player && getMainLevel(player, this) > 0 && player.getDeltaMovement().y < 0) {
                float vy = (float) Math.abs(Math.pow(player.getDeltaMovement().y, 4));
                damage = damage * (1 + vy * getMainLevel(player, this));
                Vec3 horizonal = Objects.requireNonNull(getUnitizedVec3(new Vec3(player.getLookAngle().x, 0, player.getLookAngle().z)));
                target.setDeltaMovement(-5 * player.getDeltaMovement().y * horizonal.x, -1 * player.getDeltaMovement().y, -5 * player.getDeltaMovement().y * horizonal.z);
            }
        }
        return damage;
    }
}