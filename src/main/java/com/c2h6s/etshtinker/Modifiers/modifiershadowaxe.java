package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.shadowaxeEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class modifiershadowaxe extends etshmodifieriii implements RequirementsModifierHook,GeneralInteractionModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.REQUIREMENTS,ModifierHooks.GENERAL_INTERACT);
    }

    @Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.modifiershadowaxe");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(etshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation stealth = new ResourceLocation(MOD_ID, "stealth");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(stealth);
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(stealth)<99){
            toolData.putInt(stealth,toolData.getInt(stealth)+3);
        }
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

    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity){
        ModDataNBT toolData =tool.getPersistentData();
        if (!tool.isBroken() && entity instanceof Player player&&tool.getModifierLevel(this.getId())>0) {
            shadowaxeEntity entity1 = new shadowaxeEntity(etshtinkerEntity.shadowaxeentity.get(), player.getX(),player.getEyeY(),player.getZ(),player.level);
            double vx =player.getLookAngle().x;
            double vy =player.getLookAngle().y;
            double vz =player.getLookAngle().z;
            entity1.setDamage((float) (tool.getStats().getInt(ToolStats.ATTACK_DAMAGE)*Math.pow(0.04*toolData.getInt(stealth),1.5)*0.25));
            entity1.noPhysics=true;
            entity1.setOwner(player);
            entity1.setDeltaMovement(vx,vy,vz);
            player.level.addFreshEntity(entity1);
            toolData.putInt(stealth,0);
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(),4);
        }
    }
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier)  {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 1;
    }
}
