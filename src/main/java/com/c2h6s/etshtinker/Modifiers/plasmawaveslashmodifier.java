package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.plasmawaveslashentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

import static com.c2h6s.etshtinker.util.getMainOrOff.*;
import static com.c2h6s.etshtinker.init.etshtinkerEntity.*;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class plasmawaveslashmodifier extends etshmodifieriii implements GeneralInteractionModifierHook {
    public boolean isNoLevels() {
        return true;
    }
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modfier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionHand == InteractionHand.MAIN_HAND&&getMainLevel(player,this)>0){
            GeneralInteractionModifierHook.startUsingWithDrawtime(tool,modfier.getId(),player,InteractionHand.MAIN_HAND,0.5f);
            return InteractionResult.CONSUME;
        }
        else return InteractionResult.PASS;
    }
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        float amplifier = Math.min(1.25F,(float) (72000-timeLeft)/8.0F);
        if (entity instanceof Player player&&amplifier>=1){
            OffhandCooldownTracker.applyCooldown(player,10);
            this.createslash(player,(float)tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*amplifier,tool);
            OffhandCooldownTracker.swingHand(player,InteractionHand.MAIN_HAND,false);
        }
    }
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return BlockingModifier.blockWhileCharging(tool,UseAnim.SPEAR);
    }
    public void createslash(Player player, Float damage, IToolStackView tool){
        if (player != null){
            Level world =player.level;
            plasmawaveslashentity slash =new plasmawaveslashentity(plasmawaveslashEntity.get(),world);
            world.noCollision(slash);
            slash.noCulling=true;
            slash.baseDamage=damage;
            slash.setOwner(player);
            slash.setPos(player.getX(),player.getY()+0.5*player.getBbHeight(),player.getZ());
            slash.tool=tool;
            Vec3 vec3 =getUnitizedVec3(player.getLookAngle());
            if (vec3!=null) {
                slash.setDeltaMovement(vec3.scale(5));
                slash.lerpMotion(vec3.x*5,vec3.y*5,vec3.z*5);
            }
            world.addFreshEntity(slash);
        }
    }
}
