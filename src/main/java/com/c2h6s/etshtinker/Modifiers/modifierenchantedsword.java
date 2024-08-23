package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.enchantedswordentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.enchantedswordPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.modloaded.*;

public class modifierenchantedsword extends etshmodifieriii {
    private static final ResourceLocation manacharge = new ResourceLocation(MOD_ID, "manacharge");
    public modifierenchantedsword(){
        MinecraftForge.EVENT_BUS.addListener(this::leftclick);
    }

    protected boolean canAttack(IToolStackView tool, Player player, InteractionHand hand) {
        return !tool.isBroken() && hand == InteractionHand.OFF_HAND && OffhandCooldownTracker.isAttackReady(player);
    }
    private void leftclick(PlayerInteractEvent.LeftClickEmpty event) {
        if (BOTloaded) {
            packetHandler.INSTANCE.sendToServer(new enchantedswordPacket());
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getPlayerAttacker() != null && modifier.getLevel() > 0&&BOTloaded) {
            Player player = context.getPlayerAttacker();
            if (tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get()) > 0 && tool.getPersistentData().getInt(manacharge)>1) {
                enchantedswordentity entity =new enchantedswordentity(etshtinkerEntity.enchantedswordentity.get(),player.level);
                entity.damage = (tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) * tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get())*(float)(1+ Math.log10(tool.getPersistentData().getInt(manacharge))/2));
                entity.setDeltaMovement(player.getLookAngle().scale(2.5));
                entity.lerpMotion(player.getLookAngle().x * 2.5, player.getLookAngle().y * 2.5, player.getLookAngle().z * 2.5);
                entity.setPos(player.getX(), player.getEyeY(), player.getZ());
                player.level.addFreshEntity(entity);
            }
        }
    }

    public InteractionResult onModifierToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource interactionSource) {
        if (OffhandCooldownTracker.isAttackReady(player)&&BOTloaded) {
            if (this.canAttack(tool, player, hand)) {
                if (tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get()) > 0 && tool.getPersistentData().getInt(manacharge)>1) {
                    enchantedswordentity entity =new enchantedswordentity(etshtinkerEntity.enchantedswordentity.get(),player.level);
                    entity.damage = (tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) * tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get())*(float)(1+ Math.log10(tool.getPersistentData().getInt(manacharge))/2));
                    entity.setDeltaMovement(player.getLookAngle().scale(2.5));
                    entity.lerpMotion(player.getLookAngle().x * 2.5, player.getLookAngle().y * 2.5, player.getLookAngle().z * 2.5);
                    entity.setPos(player.getX(), player.getEyeY(), player.getZ());
                    player.level.addFreshEntity(entity);
                }
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
    public static void createEnchantedSword(Player player){
        enchantedswordentity entity =new enchantedswordentity(etshtinkerEntity.enchantedswordentity.get(),player.level);
        if (player.getAttackStrengthScale(0)==1) {
            ToolStack tool =ToolStack.from(player.getMainHandItem());
            if (tool.getPersistentData().getInt(manacharge)>1) {
                if (tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get()) > 0) {
                    entity.damage =(tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) * tool.getModifierLevel(etshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get())*(float)(1+ Math.log10(tool.getPersistentData().getInt(manacharge))/2));
                    entity.setDeltaMovement(player.getLookAngle().scale(2.5));
                    entity.lerpMotion(player.getLookAngle().x * 2.5, player.getLookAngle().y * 2.5, player.getLookAngle().z * 2.5);
                    entity.setPos(player.getX(), player.getEyeY(), player.getZ());
                    entity.setOwner(player);
                    player.level.addFreshEntity(entity);
                }
            }
        }
    }
}
