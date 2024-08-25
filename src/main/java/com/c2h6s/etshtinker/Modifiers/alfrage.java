package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.alfburst;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.network.handler.packetHandler;
import com.c2h6s.etshtinker.network.packet.alfbeamPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.handler.BotaniaSounds;


public class alfrage extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    public static boolean MBOTenabled = ModList.get().isLoaded("mythicbotany");
    public alfrage(){
        MinecraftForge.EVENT_BUS.addListener(this::LeftClick);
    }

    private void LeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (MBOTenabled&&event.getEntity().getMainHandItem().getItem() instanceof ModifiableItem){
            ToolStack tool =ToolStack.from(event.getEntity().getMainHandItem());
            if (tool.getModifierLevel(this)>0){
                packetHandler.INSTANCE.sendToServer(new alfbeamPacket());
            }
        }
    }

    public static void trySpawnAlfBurst(Player player) {
        if (MBOTenabled){
            float motionModifier = 16.0F;
            alfburst burst = new alfburst(player);
            burst.setColor(0xF79100);
            burst.setMana(100);
            burst.setStartingMana(100);
            burst.setMinManaLoss(20);
            burst.setManaLossPerTick(2.0F);
            burst.setGravity(0.0F);
            burst.setSourceLens(ItemStack.EMPTY);
            burst.setDeltaMovement(burst.getDeltaMovement().scale(motionModifier));
            burst.setOwner(player);
            if (player.getAttackStrengthScale(0)==1&& ManaItemHandler.INSTANCE.requestManaExactForTool(player.getMainHandItem(),player,200,true)){
                player.level.addFreshEntity(burst);
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraBlade, SoundSource.PLAYERS, 1, 1);
            }
        }
    }
}
