package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.Items.meteoralloy_block;
import com.c2h6s.etshtinker.init.etshtinkerBlocks;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerAdastraMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> coronation_alloy = ITEMS.register("coronation_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//星冕合金锭
    public static final RegistryObject<Item> stellaralloy = ITEMS.register("stellaralloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//星钢锭

    public static final RegistryObject<Item> advanced_sandwich_alloy = ITEMS.register("advanced_sandwich_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));


    public static final RegistryObject<Item> meteoralloy_block = ITEMS.register("meteoralloy_block",( ) -> new meteoralloy_block(etshtinkerBlocks.meteoralloy_block.get(),new Item.Properties()));//半熔融陨铁合金
}
