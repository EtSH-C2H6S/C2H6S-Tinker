package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import java.util.Objects;

import static com.c2h6s.etshtinker.Modifiers.godlymetal.enabled2;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class warpattackex extends etshmodifieriii implements RequirementsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.REQUIREMENTS);
    }

    @javax.annotation.Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.warpattackex");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(etshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (holder instanceof Player player&&getMainLevel(player,this)>0&&player.swingTime==-1&&!player.isShiftKeyDown()&&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
            Entity entity1 = getNearestLiEnt(getMainLevel(player,this)* 16f, player, player.level);
            if (entity1 != null) {
                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 2);
                entity1.invulnerableTime = 0;
                entity1.hurt(DamageSource.playerAttack(player).bypassArmor().bypassMagic(), getMainLevel(player,this) * tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*6.4F);
                entity1.invulnerableTime = 0;
                Level level1 = entity1.level;
                if (level1 instanceof ServerLevel serverLevel) {
                    ParticleChainUtil.summonELECSPARKFromTo(serverLevel, player.getId(), entity1.getId());
                }
                entity1.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);
                holder.level.addAlwaysVisibleParticle(ParticleTypes.SWEEP_ATTACK, true, entity1.getX(), 0.5 * (entity1.getY() + entity1.getEyeY()), entity1.getZ(), 0, 0, 0);
                double x = entity1.getX();
                double y = entity1.getY();
                double z = entity1.getZ();
                List<Mob> mobabcd = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 8 * getMainLevel(player,this), y + 8 * getMainLevel(player,this), z + 8 * getMainLevel(player,this), x - (8 * getMainLevel(player,this)), y - (8 * getMainLevel(player,this)), z - (8 * getMainLevel(player,this))));
                for (Mob targets : mobabcd) {
                    if (enabled2 && targets != null) {
                        targets.invulnerableTime = 0;
                        targets.hurt(DamageSource.playerAttack(player).bypassArmor().bypassMagic(), getMainLevel(player,this) * tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*3.2F);
                    }
                }
            }
        }
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&getMainLevel(player,this)>0&!player.isShiftKeyDown()){
            Entity entity = getNearestMobWithinAngle(getMainLevel(player,this)*32f,player,player.level,player.getLookAngle(),0.88);
            if (entity instanceof Mob&&abstractArrow !=null){
                abstractArrow.setPierceLevel((byte) 0);
                double vx = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).x;
                double vy = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).y;
                double vz = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).z;
                abstractArrow.setPos(entity.getX()-1.5*vx,0.5*(entity.getY()+entity.getEyeY())-1.5*vy,entity.getZ()-1.5*vz);
                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(10*getMainLevel(player,this)));
            }
        }
    }

}
