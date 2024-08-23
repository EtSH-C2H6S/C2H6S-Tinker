package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import mekanism.api.Coord4D;
import mekanism.api.MekanismAPI;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class radiationremoval extends etshmodifieriii implements GeneralInteractionModifierHook{
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource==InteractionSource.RIGHT_CLICK){
            if (player!=null) {
                GeneralInteractionModifierHook.startUsing(tool,modifier.getId(),player,interactionHand);
            }
            return InteractionResult.CONSUME;
        }
        else return InteractionResult.PASS;
    }
    public static boolean enabled = ModList.get().isLoaded("mekanism");
    public boolean isNoLevels() {
        return true;
    }


    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (enabled&&!tool.isBroken() && entity instanceof Player player) {
            Coord4D playerC =new Coord4D(player.getX(),player.getY(),player.getZ(),player.level.dimension());
            MekanismAPI.getRadiationManager().radiate(player,-10);
            MekanismAPI.getRadiationManager().radiate(playerC,-10);
        }
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier)  {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1;
    }
}
