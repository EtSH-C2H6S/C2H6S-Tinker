package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;

public class modifiershocking extends etshmodifieriii {
    private final ResourceLocation charge = new ResourceLocation(MOD_ID, "charge");
    private final ResourceLocation sound3 = new ResourceLocation(MOD_ID, "sound3");
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
        if (toolData.getInt(charge)<=90&&holder instanceof ServerPlayer player&&getMold(player.getDeltaMovement())>0.1){
            toolData.putInt(charge, toolData.getInt(charge)+10);
        }
    }
    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        ModDataNBT toolData = tool.getPersistentData();
        LivingEntity attacker =context.getAttacker();
        Entity entity =context.getTarget();
        if (entity instanceof LivingEntity target) {
            if (toolData.getInt(charge) > 99 && attacker instanceof ServerPlayer player) {
                target.invulnerableTime = 0;
                target.hurt(DamageSource.playerAttack(player), (1 + getMainLevel(player, this)) * damage);
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

}
