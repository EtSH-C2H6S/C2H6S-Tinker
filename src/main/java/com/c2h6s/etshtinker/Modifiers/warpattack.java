package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.warpattackPacket;
import com.c2h6s.etshtinker.util.meleSpecialAttackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.Objects;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class warpattack extends etshmodifieriii {
    public warpattack(){
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
    }
    private void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        ToolStack tool =ToolStack.from( event.getEntity().getMainHandItem());
        int lvl = tool.getModifierLevel(etshtinkerModifiers.warpattack_STATIC_MODIFIER.get());
        if (lvl>0) {
            packetHandler.INSTANCE.sendToServer(new warpattackPacket());
        }
    }

    public static void tryWarp(Player player, ToolStack tool, InteractionHand hand){
        int lvl = tool.getModifierLevel(etshtinkerModifiers.warpattack_STATIC_MODIFIER.get());
        if (hand == InteractionHand.MAIN_HAND&&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())&&lvl>0) {
            meleSpecialAttackUtil.createWarp(player, lvl * 8f, tool.getStats().get(ToolStats.ATTACK_DAMAGE) * lvl, tool,hand);
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 10);
        }
        else if (hand == InteractionHand.OFF_HAND&&!player.getCooldowns().isOnCooldown(player.getOffhandItem().getItem())&&lvl>0) {
            meleSpecialAttackUtil.createWarp(player, lvl * 8f, tool.getStats().get(ToolStats.ATTACK_DAMAGE) * lvl, tool,hand);
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 10);
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        if (context.getPlayerAttacker()!=null&&!context.getPlayerAttacker().getCooldowns().isOnCooldown(tool.getItem())&&!context.isExtraAttack()) {
            warpattack.tryWarp(context.getPlayerAttacker(), (ToolStack) tool,context.getPlayerAttacker().getUsedItemHand());
            context.getPlayerAttacker().getCooldowns().addCooldown(tool.getItem(), 10);
        }
    }
    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&getMainLevel(player,this)>0&!player.isShiftKeyDown()){
            Entity entity = getNearestMobWithinAngle(getMainLevel(player,this)*16f,player,player.level,player.getLookAngle(),0.88);
            if (entity instanceof Mob&&abstractArrow !=null){
                abstractArrow.setPierceLevel((byte) 0);
                double vx = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).x;
                double vy = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).y;
                double vz = Objects.requireNonNull(getUnitizedVec3(abstractArrow.getDeltaMovement())).z;
                abstractArrow.setPos(entity.getX()-1.5*vx,0.5*(entity.getY()+entity.getEyeY())-1.5*vy,entity.getZ()-1.5*vz);
            }
        }
    }
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow&&target!=null&&attacker instanceof Player player&&getMainLevel(player,this)>0) {
            target.invulnerableTime =0;
        }
        return false;
    }
}
