package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableBowItem;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etshtinker.util.vecCalc.getUnitizedVec3;
import static slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem.getAngleStart;


public class electrified extends etshmodifieriii implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.DRAW_SPEED.add(builder,ToolStats.DRAW_SPEED.getMaxValue());
        ToolStats.PROJECTILE_DAMAGE.multiply(builder,0.25);
        ToolStats.VELOCITY.multiply(builder,2);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (holder instanceof Player player&&tool.getItem() instanceof ModifiableLauncherItem bow &&tool.getPersistentData().getInt(GeneralInteractionModifierHook.KEY_DRAWTIME) != 0) {
            boolean creative = player.getAbilities().instabuild;
            boolean hasAmmo = creative || BowAmmoModifierHook.hasAmmo(tool, ((ToolStack)tool).createStack(), player,bow.getSupportedHeldProjectiles());
            if (hasAmmo){
                float velocity = ConditionalStatModifierHook.getModifiedStat(tool, player, ToolStats.VELOCITY);
                if (!level.isClientSide){
                    ItemStack ammo = BowAmmoModifierHook.findAmmo(tool, ((ToolStack)tool).createStack(), player,bow.getSupportedHeldProjectiles());
                    if (ammo.isEmpty()) {
                        ammo = new ItemStack(Items.ARROW);
                    }
                    ArrowItem arrowItem = ammo.getItem() instanceof ArrowItem arrow ? arrow : (ArrowItem)Items.ARROW;
                    float inaccuracy = ModifierUtil.getInaccuracy(tool, player)+1.5f;
                        AbstractArrow arrow = arrowItem.createArrow(level, ammo, player);
                        arrow.shootFromRotation(player, player.getXRot() , player.getYRot(), 0, 3.0f*velocity, inaccuracy);
                        float baseArrowDamage = (float)(arrow.getBaseDamage() - 2 + tool.getStats().get(ToolStats.PROJECTILE_DAMAGE));
                        arrow.setBaseDamage(ConditionalStatModifierHook.getModifiedStat(tool, player, ToolStats.PROJECTILE_DAMAGE, baseArrowDamage));
                        ModifierNBT modifiers = tool.getModifiers();
                        arrow.getCapability(EntityModifierCapability.CAPABILITY).ifPresent(cap -> cap.setModifiers(modifiers));
                        NamespacedNBT arrowData = PersistentDataCapability.getOrWarn(arrow);
                        if (creative) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        for (ModifierEntry entry : modifiers.getModifiers()) {
                            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, player, arrow, arrow, arrowData, true);
                        }
                        arrow.setCritArrow(true);
                        arrow.setBaseDamage(arrow.getBaseDamage()/10);
                        level.addFreshEntity(arrow);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

                    ToolDamageUtil.damageAnimated(tool, ammo.getCount(), player, player.getUsedItemHand());
                }
                player.awardStat(Stats.ITEM_USED.get(bow));
            }
        }
    }

    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (attacker instanceof Player player&&modifiers.getLevel(this.getId())>0&&target!=null&&!(target instanceof Player)) {
            int lvl000 = modifiers.getLevel(this.getId());
            target.invulnerableTime=0;
            target.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE,1.2f,1.2f);
            target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(),100,2*lvl000,false,false),attacker);
            target.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3*lvl000, false, false), attacker);
        }
        return false;
    }
}
