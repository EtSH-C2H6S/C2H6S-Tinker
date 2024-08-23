package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerAEMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> blood_certus = ITEMS.register("blood_certus",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//赛特斯神晶
    public static final RegistryObject<Item> soul_fluix = ITEMS.register("soul_fluix",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//福鲁伊克斯魂晶
    public static final RegistryObject<Item> devil_resonate = ITEMS.register("devil_resonate",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//谐振魔晶
    public static final RegistryObject<Item> evil_orb = ITEMS.register("evil_orb",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//邪理魔球
    public static final RegistryObject<Item> exo_alloy = ITEMS.register("exo_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//星流合金
}
