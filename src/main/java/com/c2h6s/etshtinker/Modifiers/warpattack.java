package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.warpattackPacket;
import com.c2h6s.etshtinker.util.meleSpecialAttackUtil;
import com.google.common.collect.Lists;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
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

import static com.c2h6s.etshtinker.util.vecCalc.*;

public class warpattack extends EtshModifieriii {
    public warpattack(){
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
    }
    private void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        packetHandler.INSTANCE.sendToServer(new warpattackPacket(false));
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public static void tryWarp(Player player, ToolStack tool, InteractionHand hand){
        int lvl = tool.getModifierLevel(EtshtinkerModifiers.warpattack_STATIC_MODIFIER.get());
        if (hand == InteractionHand.MAIN_HAND&&lvl>0&&player.getAttackStrengthScale(0)>0.8) {
            meleSpecialAttackUtil.createWarp(player, lvl * 8f, tool.getStats().get(ToolStats.ATTACK_DAMAGE) * lvl, tool,hand);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getPlayerAttacker()!=null&&!context.isExtraAttack()&&context.isFullyCharged()) {
            warpattack.tryWarp(context.getPlayerAttacker(), (ToolStack) tool,context.getPlayerAttacker().getUsedItemHand());
        }
    }

    public void modifierOnProjectileLaunch(IToolStackView tool, ModifierEntry modifiers, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, NamespacedNBT namespacedNBT, boolean primary) {
        if (livingEntity instanceof Player player&&!player.isShiftKeyDown()&&abstractArrow !=null){
            abstractArrow.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
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
