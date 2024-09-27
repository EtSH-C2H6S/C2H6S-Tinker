package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class warpengineex extends etshmodifieriii implements GeneralInteractionModifierHook{
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource==InteractionSource.RIGHT_CLICK){
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        }
        else return InteractionResult.PASS;
    }

    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (tool.getModifierLevel(this)>0&&entity instanceof Player player&&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())){
            Vec3 vec3 =new Vec3(player.getLookAngle().x,0,player.getLookAngle().z);
            getUnitizedVec3(vec3);
            vec3 = getUnitizedVec3(vec3);
            player.setPos(player.getX()+vec3.x*2500*tool.getModifierLevel(this),player.getY(),player.getZ()+vec3.z*2500*tool.getModifierLevel(this));
            player.sendSystemMessage(Component.translatable("etshtinker.modifier.tooltip.tpto").append(String.valueOf(entity.position())));
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),200);
        }
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 20;
    }
}
