package com.c2h6s.etshtinker.Modifiers;


import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.Entities.damageSources.playerThroughSource;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.exoslashPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.RequirementsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;
import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.meleSpecialAttackUtil.*;

public class exoblademodifier extends etshmodifieriii implements RequirementsModifierHook {
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return 512;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
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
        MinecraftForge.EVENT_BUS.addListener(this::leftclickblock);
    }


    private void leftclickblock(PlayerInteractEvent.LeftClickBlock event) {
        InteractionHand hand =event.getHand();
        if (event.getEntity().getItemInHand(hand).getItem() instanceof ModifiableItem) {
            ToolStack tool = ToolStack.from(event.getEntity().getMainHandItem());
            int lvl = tool.getModifierLevel(etshtinkerModifiers.exobladeModifier_STATIC_MODIFIER.get());
            if (lvl > 0) {
                packetHandler.INSTANCE.sendToServer(new exoslashPacket());
            }
        }
    }

    private void leftclick(PlayerInteractEvent.LeftClickEmpty event) {
        InteractionHand hand =event.getHand();
        if (event.getEntity().getItemInHand(hand).getItem() instanceof ModifiableItem) {
            ToolStack tool = ToolStack.from(event.getEntity().getMainHandItem());
            int lvl = tool.getModifierLevel(etshtinkerModifiers.exobladeModifier_STATIC_MODIFIER.get());
            if (lvl > 0) {
                packetHandler.INSTANCE.sendToServer(new exoslashPacket());
            }
        }
    }
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        if (context.getPlayerAttacker()!=null&&!context.isExtraAttack()) {
            exoblademodifier.summonScattererExoslash(context.getPlayerAttacker());
            if (context.getTarget() instanceof LivingEntity living&&context.getAttacker() instanceof Player player){
                living.invulnerableTime=0;
                living.hurt(playerThroughSource.PlayerQuark(player,damageDealt/2),damageDealt/2);
                living.getPersistentData().putInt("quark_disassemble",living.getPersistentData().getInt("quark_disassemble")+40);
            }
        }
    }

    public static void summonScattererExoslash(Player player){
        ToolStack tool;
        InteractionHand hand =player.getUsedItemHand();
        if (!(player.getItemInHand(hand).getItem() instanceof ModifiableItem)){
            return;
        }
        tool =ToolStack.from(player.getMainHandItem());
        float damage = tool.getStats().get(ToolStats.ATTACK_DAMAGE);
        Level level =player.getLevel();
        EntityType<PlasmaSlashEntity> entityType = etshtinkerEntity.plasma_slash_lime.get();
        PlasmaSlashEntity slash =new PlasmaSlashEntity(entityType,level,new ItemStack(etshtinkerItems.plasma_slash_lime.get()));
        slash.damage=damage/20;
        slash.setOwner(player);
        slash.setDeltaMovement(player.getLookAngle().scale(1.5));
        slash.setToolstack(tool);
        slash.CriticalRate=256;
        double x =player.getLookAngle().x;
        double y =player.getLookAngle().y;
        double z =player.getLookAngle().z;
        slash.setPos(player.getX()+x*1.5,player.getY()+0.6*player.getBbHeight()+y*1.5,player.getZ()+z*1.5);
        level.addFreshEntity(slash);

        SecureRandom random = EtSHrnd();
        int a = random.nextInt(3) + 1;
        while (a > 0) {
            a--;
            createExoSlash(player, damage*10, getScatteredVec3(player.getLookAngle().scale(1.5), 1.732));
        }
    }
}
