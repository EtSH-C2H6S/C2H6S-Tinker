package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.Entities.damageSources.ThroughSources;
import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import com.hoshino.cti.Entity.Projectiles.base.BasicElementalOrbEntity;
import com.hoshino.cti.util.AttackUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ExoSlashProjectile extends BasicElementalOrbEntity implements ItemSupplier {
    public int count;
    public ExoSlashProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.maxNodes = 10;
        this.isTrueHurt = true;
    }
    public ExoSlashProjectile(Level pLevel) {
        super(etshtinkerEntity.EXO_SLASH.get(), pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 3; i++)
            this.level.addParticle(etshtinkerParticleType.exo.get(), this.getX() + random.nextDouble() * 0.3 - 0.15,
                    this.getY() + 0.5 * this.getBbHeight() + random.nextDouble() * 0.3 - 0.15,
                    this.getZ() + random.nextDouble() * 0.3 - 0.15, 0, 0, 0);
    }

    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        if (target instanceof LivingEntity living) {
            var baseDamage = this.tool.getStats().getInt(ToolStats.ATTACK_DAMAGE);
            if (this.getOwner() instanceof Player player) {
                living.invulnerableTime = 0;
                AttackUtil.attackEntity(this.tool,player, InteractionHand.MAIN_HAND,living,()->1,true, EquipmentSlot.MAINHAND,false,0,1);
                living.getPersistentData().putInt("quark_disassemble", living.getPersistentData().getInt("quark_disassemble") + 30);
                slashentity slash = new slashentity(etshtinkerEntity.slashentity.get(), this.level);
                slash.setOwner(this.getOwner());
                slash.exo = true;
                slash.target = living;
                slash.damage = baseDamage / 2f;
                slash.count = this.count;
                slash.setPos(living.getX(), living.getY() + 0.5 * living.getBbHeight(), living.getZ());
                this.level.addFreshEntity(slash);
            } else {
                living.invulnerableTime = 0;
                ThroughSources.quark(baseDamage).hurtEntity(living);
                living.getPersistentData().putInt("quark_disassemble", living.getPersistentData().getInt("quark_disassemble") + 30);
            }
        }
    }

    protected void defineSynchedData() {
        this.getEntityData().define(KEY_HOMING_ENTITY_ID,-1);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.exoslash.get());
    }
}
