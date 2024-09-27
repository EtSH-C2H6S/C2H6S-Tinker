package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.exoOrb;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

import javax.annotation.Nullable;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;
import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

public class heavenlyGaleModifier extends etshmodifieriii implements RequirementsModifierHook {

    private final ResourceLocation bow_charge = new ResourceLocation(MOD_ID, "bow_charge");
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(bow_charge);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    public heavenlyGaleModifier(){
        MinecraftForge.EVENT_BUS.addListener(this::RightClick);
    }

    private void RightClick(PlayerInteractEvent.RightClickEmpty event) {
    }

    @Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.exoblade");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(etshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this,ModifierHooks.REQUIREMENTS);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot) {
            if (tool.getPersistentData().getInt(GeneralInteractionModifierHook.KEY_DRAWTIME) != 0) {
                tool.getPersistentData().putInt(bow_charge, tool.getPersistentData().getInt(bow_charge) + 1);
                if (tool.getPersistentData().getInt(bow_charge) % 10 == 0 && tool.getPersistentData().getInt(bow_charge) <= 80) {
                    livingEntity.playSound(SoundEvents.NOTE_BLOCK_HAT, 1, 1);
                }
                if (tool.getPersistentData().getInt(bow_charge) == 80) {
                    livingEntity.playSound(SoundEvents.NOTE_BLOCK_BIT, 1.25f, 1.25f);
                    if (livingEntity.level instanceof ServerLevel serverLevel) {
                        double dx = livingEntity.getLookAngle().x + livingEntity.getX();
                        double dy = livingEntity.getLookAngle().y + livingEntity.getEyeY();
                        double dz = livingEntity.getLookAngle().z + livingEntity.getZ();
                        serverLevel.sendParticles(etshtinkerParticleType.exo.get(), dx, dy, dz, 5, 0.02, 0.02, 0.02, 0.02);
                    }
                }
            }
        }
    }

    @Override
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity livingEntity, Projectile projectile, @org.jetbrains.annotations.Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (projectile instanceof AbstractArrow arrow&&livingEntity instanceof Player player) {
            for (ModifierEntry entry : tool.getModifierList()) {
                if (entry.getModifier()!=this) {
                    entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, player, arrow, arrow, tool.getPersistentData(), true);
                }
            }
            int charge = tool.getPersistentData().getInt(bow_charge);
            int amount = Math.min(9, (charge / 10) + 1);
            for (int i = 0; i < amount; i++) {
                exoOrb exoOrb = new exoOrb(etshtinkerEntity.exo_orb.get(), player.level);
                exoOrb.baseDamage = (float) (arrow.getBaseDamage() * getMold(arrow.getDeltaMovement()) * 0.5 * amount);
                exoOrb.tool = tool;
                exoOrb.setDeltaMovement(getScatteredVec3(player.getLookAngle(), 0.6).scale(2));
                exoOrb.setPos(arrow.getX(), arrow.getY(), arrow.getZ());
                exoOrb.setOwner(player);
                if (amount >= 7) {
                    exoOrb.summonLIGH = true;
                }
                player.level.addFreshEntity(exoOrb);
            }
            arrow.discard();
            tool.getPersistentData().putInt(bow_charge,0);
        }
    }



}
