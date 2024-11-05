package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.damageSources.throughSources;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.c2h6s.etshtinker.util.vecCalc.*;

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
        if (holder instanceof Player player&&modifier.getLevel()>0&&player.swingTime==-1&&!player.isShiftKeyDown()&&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
            Entity entity1 = getNearestLiEnt(modifier.getLevel()* 16f, player, player.level);
            if (entity1 != null) {
                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 2);
                entity1.invulnerableTime = 0;
                entity1.hurt(throughSources.quark(tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*modifier.getLevel()),tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*modifier.getLevel());
                entity1.getPersistentData().putInt("quark_disassemble",entity1.getPersistentData().getInt("quark_disassemble")+30);
                Level level1 = entity1.level;
                if (level1 instanceof ServerLevel serverLevel) {
                    ParticleChainUtil.summonELECSPARKFromTo(serverLevel, player.getId(), entity1.getId());
                }
                entity1.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);
                holder.level.addAlwaysVisibleParticle(ParticleTypes.SWEEP_ATTACK, true, entity1.getX(), 0.5 * (entity1.getY() + entity1.getEyeY()), entity1.getZ(), 0, 0, 0);
                double x = entity1.getX();
                double y = entity1.getY();
                double z = entity1.getZ();
                List<Mob> mobabcd = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 8 * modifier.getLevel(), y + 8 *modifier.getLevel(), z + 8 * modifier.getLevel(), x - (8 * modifier.getLevel()), y - (8 * modifier.getLevel()), z - (8 * modifier.getLevel())));
                for (Mob targets : mobabcd) {
                    if ( targets != null) {
                        targets.invulnerableTime = 0;
                        targets.hurt(throughSources.quark(tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*modifier.getLevel()),tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*modifier.getLevel());
                        targets.getPersistentData().putInt("quark_disassemble",targets.getPersistentData().getInt("quark_disassemble")+30);
                    }
                }
            }
        }
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&!player.isShiftKeyDown()){
            Entity entity = getNearestMobWithinAngle(modifiers.getLevel()*32f,player,player.level,player.getLookAngle(),0.88);
            if (entity instanceof Mob&&abstractArrow !=null){
                abstractArrow.setPos(entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ());
                abstractArrow.setPierceLevel((byte) (abstractArrow.getPierceLevel()+ modifiers.getLevel()*4));
                if (abstractArrow.level instanceof ServerLevel serverLevel) {
                    ParticleChainUtil.SummonParticleChain(serverLevel,player.position().add(0,player.getEyeHeight(),0),abstractArrow.position(), ParticleTypes.ELECTRIC_SPARK);
                }
                abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(10*modifiers.getLevel()));
                EntityHitResult entityHitResult =new EntityHitResult(entity);
                ForgeEventFactory.onProjectileImpact(abstractArrow, entityHitResult);
                abstractArrow.onHit(entityHitResult);
                entity.invulnerableTime =0;
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&target!=null) {
            target.invulnerableTime =0;
            if (arrow.getPierceLevel()>0){
                arrow.addTag("warping");
            }
        }
        return false;
    }

    @Override
    public boolean modifierOnProjectileHitBlock(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        if (projectile instanceof AbstractArrow arrow) {
            if (arrow.getPierceLevel()>0){
                arrow.addTag("warping");
            }
        }
        return false;
    }
}
