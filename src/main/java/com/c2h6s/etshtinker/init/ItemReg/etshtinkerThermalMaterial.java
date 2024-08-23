package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerThermalMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> chroma_plate = ITEMS.register("chroma_plate",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//彩钢板
    public static final RegistryObject<Item> activated_chroma_plate = ITEMS.register("activated_chroma_plate",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//活化彩钢板
    public static final RegistryObject<Item> basalz_signalum = ITEMS.register("basalz_signalum",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//地岩信素
    public static final RegistryObject<Item> blitz_lumium = ITEMS.register("blitz_lumium",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//震荡流明
    public static final RegistryObject<Item> blizz_enderium = ITEMS.register("blizz_enderium",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//覆雪末影
    public static final RegistryObject<Item> sandwich_alloy = ITEMS.register("sandwich_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> triostatealloy = ITEMS.register("triostatealloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));
}
