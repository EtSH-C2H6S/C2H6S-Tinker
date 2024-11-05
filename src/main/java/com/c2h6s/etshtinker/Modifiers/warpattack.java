package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.warpattackPacket;
import com.c2h6s.etshtinker.util.ParticleChainUtil;
import com.c2h6s.etshtinker.util.meleSpecialAttackUtil;
import com.google.common.collect.Lists;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
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
        packetHandler.INSTANCE.sendToServer(new warpattackPacket());
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
        if (livingEntity instanceof Player player&&!player.isShiftKeyDown()){
            Entity entity = getNearestMobWithinAngle(modifiers.getLevel()*16f,player,player.level,player.getLookAngle(),0.88);
            if (entity instanceof Mob&&abstractArrow !=null){
                abstractArrow.setPos(entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ());
                abstractArrow.setPierceLevel((byte) (abstractArrow.getPierceLevel()+ modifiers.getLevel()*2));
                if (abstractArrow.level instanceof ServerLevel serverLevel) {
                    ParticleChainUtil.SummonParticleChain(serverLevel,player.position().add(0,player.getEyeHeight(),0),abstractArrow.position(), ParticleTypes.ELECTRIC_SPARK);
                }
                EntityHitResult entityHitResult =new EntityHitResult(entity);
                ForgeEventFactory.onProjectileImpact(abstractArrow, entityHitResult);
                abstractArrow.onHit(entityHitResult);
                abstractArrow.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
                abstractArrow.piercedAndKilledEntities.add(entity);
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
