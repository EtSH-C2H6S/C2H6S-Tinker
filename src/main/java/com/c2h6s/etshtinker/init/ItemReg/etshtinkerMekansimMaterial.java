package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.Items.antineutroniumItem;
import com.c2h6s.etshtinker.Items.marcoatomItem;
import com.c2h6s.etshtinker.Items.trinityalloyitem;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerMekansimMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> ultra_dense = ITEMS.register("ultra_dense", ( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//超致密锭
    public static final RegistryObject<Item> exotic_matter = ITEMS.register("exotic_matter",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//奇异物质
    public static final RegistryObject<Item> anti_neutronium = ITEMS.register("anti_neutronium",( ) -> new antineutroniumItem(new Item.Properties()));//反中子锭
    public static final RegistryObject<Item> trinity_intereactive_alloy = ITEMS.register("trinity_intereactive_alloy",( ) -> new trinityalloyitem(new Item.Properties().stacksTo(64).tab(etshtinkerTab.MATERIALS).fireResistant()));//三位一体
    public static final RegistryObject<Item> marcoatom = ITEMS.register("marcoatom",( ) -> new marcoatomItem(new Item.Properties().stacksTo(64).tab(etshtinkerTab.MATERIALS).fireResistant()));//宏原子
    public static final RegistryObject<Item> electronium = ITEMS.register("electronium",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//电子锭
    public static final RegistryObject<Item> protonium = ITEMS.register("protonium",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//质子锭


    public static final RegistryObject<Item> stable_plasma = ITEMS.register("stable_plasma",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//稳态等离子体
    public static final RegistryObject<Item> os_induced_netherstarshard = ITEMS.register("os_induced_netherstarshard",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> gs_indused_netherite_dust = ITEMS.register("gs_indused_netherite_dust",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> activated_neutronium_dust = ITEMS.register("activated_neutronium_dust",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> alloy_fracture = ITEMS.register("alloy_fracture",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));

}
