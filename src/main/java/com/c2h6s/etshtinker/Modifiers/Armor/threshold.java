package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import vazkii.botania.api.mana.ManaItemHandler;

import static com.c2h6s.etshtinker.util.modloaded.BOTloaded;

public class threshold extends etshmodifieriii {
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("threshold");
    public threshold(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        if (BOTloaded&&living instanceof Player player){
            for (ItemStack stack:player.getInventory().armor){
                if (stack.getItem() instanceof ModifiableArmorItem){
                    ToolStack tool = ToolStack.from(stack);
                    float amount = player.getMaxHealth()/(tool.getModifierLevel(this)+1);
                    if (tool.getModifierLevel(this)>0&&event.getAmount()> amount ){
                        float amount2 =event.getAmount()- amount;
                        if (ManaItemHandler.INSTANCE.requestManaExactForTool(stack,player,(int) (20* amount2),true)){
                            event.setAmount(amount);
                        }
                    }
                }
            }
        }
    }
}
