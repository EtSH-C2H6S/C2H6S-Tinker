package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.Items.notGeneratedOre;
import com.c2h6s.etshtinker.init.etshtinkerBlocks;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerIEMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> bismuth_ingot = ITEMS.register("bismuth_ingot",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//铋锭
    public static final RegistryObject<Item> hardlead_plate = ITEMS.register("hardlead_plate",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//硬铅板

    public static final RegistryObject<Item> bismuthinite = ITEMS.register("bismuthinite",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));

    public static final RegistryObject<Item> bismuthinite_ore_deepslate = ITEMS.register("bismuthinite_ore_deepslate",( ) -> new notGeneratedOre(etshtinkerBlocks.bismuthinite_ore_deepslate.get(),new Item.Properties().tab(etshtinkerTab.BLOCKS)));//深层辉铋矿
}
