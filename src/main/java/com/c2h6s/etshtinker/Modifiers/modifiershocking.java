package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.hooks.FoilModifierHook;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class modifiershocking extends etshmodifieriii implements FoilModifierHook {
    private final ResourceLocation charge = new ResourceLocation(MOD_ID, "charge");
    private final ResourceLocation sound3 = new ResourceLocation(MOD_ID, "sound3");

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, etshtinkerHook.FOIL);
    }

    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(charge);
        tool.getPersistentData().remove(sound3);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if (toolData.getInt(charge)>99&&toolData.getInt(sound3)==0&&holder instanceof Player player){
            player.playSound(SoundEvents.PUFFER_FISH_BLOW_UP,1.25f,1.25f);
            toolData.putInt(sound3, 1);
        }
        if (toolData.getInt(charge)<100&&holder instanceof ServerPlayer player&&getMold(player.getDeltaMovement())>0.1){
            toolData.putInt(charge, toolData.getInt(charge)+1);
        }
    }

    @Override
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        ModDataNBT toolData = tool.getPersistentData();
        if (toolData.getInt(charge) > 99 && livingEntity instanceof ServerPlayer player) {
            toolData.putInt(charge, 0);
            toolData.putInt(sound3, 0);
            if (abstractArrow!=null) {
                abstractArrow.addTag("shocking_arrow");
            }
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 2));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
        }
        if (toolData.getInt(charge) < 100) {
            toolData.putInt(charge, toolData.getInt(charge) + 25);
        }
    }

    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&target!=null&&attacker instanceof Player player&&arrow.getTags().contains("shocking_arrow")){
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 2));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
            target.invulnerableTime = 0;
            target.hurt(DamageSource.playerAttack(player), (float) ((1 + modifier.getLevel()) * arrow.getBaseDamage()*getMold(arrow.getDeltaMovement())));
            target.invulnerableTime = 0;
            target.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 1f, 2f);
            int lvl000 = modifier.getLevel();
            double xx = target.getX();
            double yy = target.getY();
            double zz = target.getZ();
            List<Mob> ls001 = attacker.level.getEntitiesOfClass(Mob.class, new AABB(xx + 2 * lvl000, yy + lvl000, zz + 2 * lvl000, xx - 2 * lvl000, yy - lvl000, zz - 2 * lvl000));
            for (Mob mob1 : ls001) {
                if (mob1 != null) {
                    mob1.invulnerableTime = 0;
                    mob1.hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor(), (float) (arrow.getBaseDamage()*getMold(arrow.getDeltaMovement())));
                    mob1.invulnerableTime = 0;
                    if (mob1.level instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,mob1.getX(),mob1.getY()+0.5*mob1.getBbHeight(),mob1.getZ(),8,0.5,0.5,0.5,0.1);
                    }
                }
            }
        }
        return false;
    }

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        ModDataNBT toolData = tool.getPersistentData();
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (toolData.getInt(charge) > 99 && attacker instanceof ServerPlayer player) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), (1 +modifier.getLevel()) * damage);
                target.invulnerableTime = 0;
                toolData.putInt(charge, 0);
                toolData.putInt(sound3, 0);
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 2));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
                target.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 1f, 2f);
                int lvl000 = tool.getModifierLevel(this);
                double xx = target.getX();
                double yy = target.getY();
                double zz = target.getZ();
                List<Mob> ls001 = attacker.level.getEntitiesOfClass(Mob.class, new AABB(xx + 2 * lvl000, yy + lvl000, zz + 2 * lvl000, xx - 2 * lvl000, yy - lvl000, zz - 2 * lvl000));
                for (Mob mob1 : ls001) {
                    if (mob1 != null) {
                        mob1.invulnerableTime = 0;
                        mob1.hurt(DamageSource.playerAttack(player).bypassMagic().bypassArmor(), tool.getStats().getInt(ToolStats.ATTACK_DAMAGE));
                        mob1.invulnerableTime = 0;
                        if (mob1.level instanceof ServerLevel serverLevel){
                            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,mob1.getX(),mob1.getY()+0.5*mob1.getBbHeight(),mob1.getZ(),8,0.5,0.5,0.5,0.1);
                        }
                    }
                }
            }
            if (toolData.getInt(charge) < 100) {
                toolData.putInt(charge, toolData.getInt(charge) + 25);
            }
        }
        return baseKnockback;
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            tooltip.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.charge").append(String.valueOf((int) (toolData.getInt(charge)/20)))));

        }
    }

    public void modifierAfterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        ModDataNBT toolData = tool.getPersistentData();
        if (context.getPlayer()!=null) {
            Player player = context.getPlayer();
            Level world = player.level;
            if (toolData.getInt(charge) >= 100) {
                toolData.putInt(charge, 0);
                toolData.putInt(sound3, 0);
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 2));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 1, 2);
            }
            if (toolData.getInt(charge) < 100) {
                toolData.putInt(charge, toolData.getInt(charge) + 25);
            }
        }
    }

    @Override
    public boolean isFoil(IToolStackView tool, Boolean isFoil) {
        return isFoil||tool.getPersistentData().getInt(charge) > 99;
    }
}
