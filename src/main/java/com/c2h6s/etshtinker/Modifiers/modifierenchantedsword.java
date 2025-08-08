package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.enchantedswordentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.EtshtinkerModifiers;
import com.hoshino.cti.library.modifier.CtiModifierHook;
import com.hoshino.cti.library.modifier.hooks.LeftClickModifierHook;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class modifierenchantedsword extends EtshModifieriii implements LeftClickModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, CtiModifierHook.LEFT_CLICK);
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide && player.getAttackStrengthScale(0)>0.9){
            createEnchantedSword(player);
        }
    }

    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getPlayerAttacker() != null && context.isFullyCharged() &&context.getHand()== InteractionHand.MAIN_HAND) {
            Player player = context.getPlayerAttacker();
            createEnchantedSword(player);
        }
    }

    public static void createEnchantedSword(Player player) {
        enchantedswordentity entity = new enchantedswordentity(etshtinkerEntity.enchantedswordentity.get(), player.level);
        ToolStack tool = ToolStack.from(player.getMainHandItem());
        entity.damage = (tool.getStats().getInt(ToolStats.ATTACK_DAMAGE) * tool.getModifierLevel(EtshtinkerModifiers.modifierenchantedsword_STATIC_MODIFIER.get()));
        entity.setDeltaMovement(player.getLookAngle().scale(2.5));
        entity.setPos(player.getX(), player.getEyeY(), player.getZ());
        entity.setOwner(player);
        player.level.addFreshEntity(entity);
    }
}
