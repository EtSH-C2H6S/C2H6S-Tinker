package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.NightSlashEntity;
import com.c2h6s.etshtinker.Entities.NightSlashEntityB;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifierfluxed;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.nightslashPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class nightsedge extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public nightsedge(){
        MinecraftForge.EVENT_BUS.addListener(this::leftclick);
    }

    private void leftclick(PlayerInteractEvent.LeftClickEmpty event) {
        packetHandler.INSTANCE.sendToServer(new nightslashPacket());
    }

    public float modifierBeforeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        if (context.getPlayerAttacker()!=null&&context.isFullyCharged()&&!context.isExtraAttack()) {
            createNightSlash(context.getPlayerAttacker());
        }
        return knockback;
    }

    public static void createNightSlash(Player player){
        if (player.getAttackStrengthScale(0)==1&&ToolStack.from(player.getMainHandItem()).getModifierLevel(etshtinkerModifiers.nightsedge_STATIC_MODIFIER.get())>0) {
            ToolStack tool =ToolStack.from(player.getMainHandItem());
            SecureRandom random = EtSHrnd();
            if (random.nextBoolean()){
                NightSlashEntityB entity = new NightSlashEntityB(etshtinkerEntity.nights_slash_entity_b.get(), player.level);
                entity.setDeltaMovement(player.getLookAngle().scale(0.35));
                entity.damage = tool.getStats().getInt(ToolStats.ATTACK_DAMAGE);
                if (etshmodifierfluxed.getEnergyStored(tool)>0){
                    float dmgup = (float) etshmodifierfluxed.getEnergyStored(tool) /5000000;
                    entity.damage += dmgup;
                    etshmodifierfluxed.removeEnergy(tool,etshmodifierfluxed.getEnergyStored(tool)/10,false,false);
                }
                entity.setOwner(player);
                entity.setPos(player.getX(), player.getY() + 0.5 * player.getBbHeight(), player.getZ());
                player.level.addFreshEntity(entity);
            }
            else{
                NightSlashEntity entity = new NightSlashEntity(etshtinkerEntity.nights_slash_entity.get(), player.level);
                entity.setDeltaMovement(player.getLookAngle().scale(0.35));
                entity.damage = tool.getStats().getInt(ToolStats.ATTACK_DAMAGE);
                if (etshmodifierfluxed.getEnergyStored(tool)>0){
                    float dmgup = (float) etshmodifierfluxed.getEnergyStored(tool) /5000000;
                    entity.damage += dmgup;
                    etshmodifierfluxed.removeEnergy(tool,etshmodifierfluxed.getEnergyStored(tool)/10,false,false);
                }
                entity.setOwner(player);
                entity.setPos(player.getX(), player.getY() + 0.5 * player.getBbHeight(), player.getZ());
                player.level.addFreshEntity(entity);
            }
        }
    }
}
