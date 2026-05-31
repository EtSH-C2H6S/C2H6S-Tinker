package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtSTBaseModifier;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.warpattackPacket;
import com.c2h6s.etshtinker.util.meleSpecialAttackUtil;
import com.google.common.collect.Lists;
import com.hoshino.cti.library.modifier.CtiModifierHook;
import com.hoshino.cti.library.modifier.hooks.LeftClickModifierHook;
import com.hoshino.cti.netwrok.CtiPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static com.c2h6s.etshtinker.util.vecCalc.*;

public class warpattackex extends EtSTBaseModifier implements RequirementsModifierHook, LeftClickModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.REQUIREMENTS, CtiModifierHook.LEFT_CLICK);
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (player.getAttackStrengthScale(0)>0.8)
            packetHandler.INSTANCE.sendToServer(new warpattackPacket(true));
    }

    @Override
    public void onLeftClickBlock(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot, BlockState state, BlockPos pos) {
        if (player instanceof ServerPlayer&&player.getAttackStrengthScale(0)>0.8)
            tryWarp(player, (ToolStack) tool,InteractionHand.MAIN_HAND);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @javax.annotation.Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.warpattackex");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(EtshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }

    public static void tryWarp(Player player, ToolStack tool, InteractionHand hand){
        int lvl = tool.getModifierLevel(EtshtinkerModifiers.warpattackEX_STATIC_MODIFIER.get());
        if (hand == InteractionHand.MAIN_HAND) {
            meleSpecialAttackUtil.createWarpEx(player, lvl * 8f, tool.getStats().get(ToolStats.ATTACK_DAMAGE) * lvl, tool,hand);
        }
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (context.getPlayerAttacker()!=null&&!context.isExtraAttack()&&context.isFullyCharged()) {
            warpattackex.tryWarp(context.getPlayerAttacker(), (ToolStack) tool,context.getHand());
        }
    }


    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&!player.isShiftKeyDown()&&abstractArrow !=null){
            abstractArrow.setDeltaMovement(abstractArrow.getDeltaMovement().scale(100));
            abstractArrow.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
            abstractArrow.setPierceLevel((byte) 20);
            Entity entity = getNearestMobWithinAngle(modifiers.getLevel()*16f,player,player.level,player.getLookAngle(),0.88);
            if (entity instanceof Mob){
                abstractArrow.addTag("warping");
                entity.invulnerableTime =0;
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow&&target!=null) {
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
