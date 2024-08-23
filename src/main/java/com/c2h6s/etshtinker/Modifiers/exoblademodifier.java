package com.c2h6s.etshtinker.Modifiers;


import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.exoslashPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;
import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.meleSpecialAttackUtil.*;

public class exoblademodifier extends etshmodifieriii implements RequirementsModifierHook {
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation attimes = new ResourceLocation(MOD_ID, "attimes");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(attimes);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.REQUIREMENTS);
    }

    @Nullable
    @Override
    public Component requirementsError(ModifierEntry entry) {
        return Component.translatable("recipe.etshtinker.modifier.exoblade");
    }

    @Override
    public @NotNull List<ModifierEntry> displayModifiers(ModifierEntry entry) {
        return List.of(new ModifierEntry(etshtinkerModifiers.godlymetal_STATIC_MODIFIER.getId(),1));
    }

    public exoblademodifier(){
        MinecraftForge.EVENT_BUS.addListener(this::leftclick);
    }

    private void leftclick(PlayerInteractEvent.LeftClickEmpty event) {
        ToolStack tool =ToolStack.from( event.getEntity().getMainHandItem());
        int lvl = tool.getModifierLevel(etshtinkerModifiers.exobladeModifier_STATIC_MODIFIER.get());
        if (lvl>0) {
            packetHandler.INSTANCE.sendToServer(new exoslashPacket());
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        if (context.getPlayerAttacker()!=null&&modifier.getLevel()>0) {
            Player player =context.getPlayerAttacker();
            SecureRandom random = EtSHrnd();
            int a = random.nextInt(2) + 1;
            while (a > 0) {
                a--;
                createExoSlash(player, damageDealt, getScatteredVec3(player.getLookAngle(), 1.732));
            }
        }
    }

    public static void summonScattererExoslash(Player player){
        ToolStack tool =null;
        if (player.getUsedItemHand() ==InteractionHand.MAIN_HAND){
            tool =ToolStack.from(player.getMainHandItem());
        }
        if (player.getUsedItemHand() ==InteractionHand.OFF_HAND){
            tool =ToolStack.from(player.getOffhandItem());
        }
        if (tool!=null) {
            Float damage = (float) tool.getStats().getInt(ToolStats.ATTACK_DAMAGE);
            SecureRandom random = EtSHrnd();
            int a = random.nextInt(2) + 1;
            while (a > 0) {
                a--;
                createExoSlash(player, damage, getScatteredVec3(player.getLookAngle(), 1.732));
            }
        }
    }
}
