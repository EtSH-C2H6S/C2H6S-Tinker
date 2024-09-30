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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.vecCalc.getMold;
import static com.c2h6s.etshtinker.util.vecCalc.getScatteredVec3;

public class heavenlyGaleModifier extends etshmodifieriii implements RequirementsModifierHook , BowAmmoModifierHook {

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

    @Override
    public int getPriority() {
        return 0;
    }

    @Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.heavenly_gale");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(etshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this,ModifierHooks.REQUIREMENTS,ModifierHooks.BOW_AMMO);
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, ItemStack itemStack, Predicate<ItemStack> predicate) {
        return new ItemStack(Items.ARROW,64);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot) {
            if (tool.getPersistentData().getInt(GeneralInteractionModifierHook.KEY_DRAWTIME) != 0) {
                if (tool.getPersistentData().getInt(bow_charge) <= 80) {
                    tool.getPersistentData().putInt(bow_charge, tool.getPersistentData().getInt(bow_charge) + 1);
                }
                if (tool.getPersistentData().getInt(bow_charge) % 10 == 0 && tool.getPersistentData().getInt(bow_charge) <= 80&& tool.getPersistentData().getInt(bow_charge)>0) {
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
        if (projectile instanceof AbstractArrow arrow&&livingEntity instanceof Player player&&primary) {
            int lvlMulti = tool.getModifierLevel(TinkerModifiers.multishot.get())+1;
            int charge = tool.getPersistentData().getInt(bow_charge);
            int amount = Math.min(8, charge / 10)+1;
            int count = (amount/2);
            exoOrb exoorb1 = new exoOrb(etshtinkerEntity.exo_orb.get(), player.level);
            exoorb1.baseDamage = (float) (arrow.getBaseDamage() * getMold(arrow.getDeltaMovement()) * amount*2);
            exoorb1.tool = tool;
            exoorb1.setDeltaMovement(player.getLookAngle().scale(2));
            exoorb1.setPos(arrow.getX(), arrow.getY(), arrow.getZ());
            exoorb1.setOwner(player);
            if (amount >= 7) {
                exoorb1.summonLIGH = true;
            }
            player.level.addFreshEntity(exoorb1);
            for (int i = 0; i < count*lvlMulti; i++) {
                exoOrb exoorb = new exoOrb(etshtinkerEntity.exo_orb.get(), player.level);
                exoorb.baseDamage = (float) (arrow.getBaseDamage() * getMold(arrow.getDeltaMovement()) * amount*2);
                exoorb.tool = tool;
                exoorb.setDeltaMovement(getScatteredVec3(player.getLookAngle(), 0.6).scale(2));
                exoorb.setPos(arrow.getX(), arrow.getY(), arrow.getZ());
                exoorb.setOwner(player);
                if (amount >= 7) {
                    exoorb.summonLIGH = true;
                }
                player.level.addFreshEntity(exoorb);
            }
            tool.getPersistentData().putInt(bow_charge, 0);
        }
        if (abstractArrow != null) {
            abstractArrow.discard();
        }
    }



}
