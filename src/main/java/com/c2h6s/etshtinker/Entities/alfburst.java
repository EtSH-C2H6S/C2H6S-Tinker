package com.c2h6s.etshtinker.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import vazkii.botania.common.entity.ManaBurstEntity;

import java.util.ArrayList;
import java.util.List;

public class alfburst extends ManaBurstEntity {
    public List<LivingEntity> entitieshit = new ArrayList<>(List.of());
    public float damage = 16;
    public alfburst(EntityType<ManaBurstEntity> type, Level world) {
        super(type, world);
    }

    public alfburst(Level level, BlockPos pos, float rotX, float rotY, boolean fake) {
        super(level, pos, rotX, rotY, fake);
    }

    public alfburst(Player player) {
        super(player);
    }


    @Override
    public void tick() {
        AABB aabb = new AABB(
                this.getX(), this.getY(), this.getZ(),
                this.xOld, this.yOld, this.zOld
        ).inflate(1);
        Entity thrower = this.getOwner();
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
        for (LivingEntity living : entities) {
            if (living == thrower || living instanceof Player livingPlayer && thrower instanceof Player throwingPlayer && !throwingPlayer.canHarmPlayer(livingPlayer)) {
                continue;
            }
            if (living.hurtTime == 0) {
                int mana = this.getMana();
                if (mana >= 33) {
                    this.setMana(mana - 33);
                    if (!this.isFake() && !this.level.isClientSide) {
                        DamageSource source = DamageSource.MAGIC;
                        if (thrower instanceof Player player) {
                            source = DamageSource.playerAttack(player);
                        }
                        living.hurt(source, 16);
                        if (this.getMana() <= 0) this.discard();
                    }
                }
            }
        }
        super.tick();
    }
}
