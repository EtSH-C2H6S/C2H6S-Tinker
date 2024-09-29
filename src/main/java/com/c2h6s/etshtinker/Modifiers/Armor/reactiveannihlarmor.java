package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerMekansimMaterial.anti_neutronium;

public class reactiveannihlarmor extends etshmodifieriii implements DurabilityDisplayModifierHook {
    private final ResourceLocation antineutron = new ResourceLocation(MOD_ID, "antineutron");
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(antineutron);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }

    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        return true;
    }
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int anti =tool.getPersistentData().getInt(antineutron);
        return anti>0 ? Math.min((int) (13 *  ( (float) anti/100*modifier.getLevel())+1),13):1;
    }
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        return  0xEE0000 ;
    }
    public reactiveannihlarmor(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        Entity entity =event.getSource().getEntity();
        if (living instanceof Player player){
            for (int i=0;i<player.getInventory().armor.size();i++){
                IToolStackView tool =ToolStack.from( player.getInventory().armor.get(i));
                if (tool.getModifierLevel(this)>0){
                    int lvl =tool.getModifierLevel(this);
                    ModDataNBT toolData =tool.getPersistentData();
                    if (toolData.getFloat(antineutron)>event.getAmount()){
                        toolData.putFloat(antineutron,toolData.getFloat(antineutron)-event.getAmount());
                        annihilateexplosionentity explode =new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(),player.getLevel());
                        if (entity instanceof LivingEntity attacker){
                            explode.target =attacker;
                        }
                        explode.setPos(player.getX(),player.getY()+0.5*player.getBbHeight(),player.getZ());
                        explode.damage=1024*lvl;
                        explode.setOwner(player);
                        player.level.addFreshEntity(explode);
                        event.setCanceled(true);
                        break;
                    }else {
                        for (int j = 0; j < player.getInventory().items.size(); j++) {
                            ItemStack stack = player.getInventory().getItem(j);
                            if (stack.getItem() == anti_neutronium.get() && stack.getCount() > 0) {
                                while (stack.getCount()>0&&event.getAmount()>0){
                                    stack.setCount(stack.getCount()-1);
                                    if (event.getAmount()>1024*lvl){
                                        event.setAmount(event.getAmount()-1024*lvl);
                                    }else {
                                        event.setAmount(0);
                                        toolData.putFloat(antineutron,toolData.getFloat(antineutron)-event.getAmount()+1024*lvl);
                                    }
                                    if (event.getAmount()<=0){
                                        event.setCanceled(true);
                                        break;
                                    }
                                    annihilateexplosionentity explode =new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(),player.getLevel());
                                    if (entity instanceof LivingEntity attacker){
                                        explode.target =attacker;
                                    }
                                    explode.setPos(player.getX(),player.getY()+0.5*player.getBbHeight(),player.getZ());
                                    explode.damage=1024*lvl;
                                    explode.setOwner(player);
                                    player.level.addFreshEntity(explode);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("material.etshtinker.anti_neutronium").append(String.valueOf( ":"+toolData.getInt(antineutron) ))));
        }
        super.addTooltip(tool,modifier,player,list,TooltipKey.UNKNOWN,tooltipFlag);
    }
}
