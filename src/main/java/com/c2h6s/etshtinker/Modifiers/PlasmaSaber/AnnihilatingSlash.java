package com.c2h6s.etshtinker.Modifiers.PlasmaSaber;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.hooks.AfterPlasmaSlashHitModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class AnnihilatingSlash extends etshmodifieriii implements AfterPlasmaSlashHitModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, etshtinkerHook.AFTER_SLASH_HIT);
    }

    @Override
    public void afterPlasmaSlashHit(ToolStack tool, LivingEntity target, PlasmaSlashEntity slash, boolean isCritical, float slashDamage) {
        if (EtSHrnd().nextInt(4)==0){
            annihilateexplosionentity explode = new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), target.getLevel());
            explode.damage = slashDamage*(slash.CriticalRate);
            explode.target = target;
            explode.setPos(target.getX(), target.getY() + 0.5 * target.getBbHeight(), target.getZ());
            explode.setOwner(slash.getOwner());
            target.level.addFreshEntity(explode);
            slash.hitRemain-=3;
        }
    }
}
