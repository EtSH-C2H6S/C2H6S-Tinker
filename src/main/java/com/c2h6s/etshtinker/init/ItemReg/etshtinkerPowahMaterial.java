package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerPowahMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> earth_crystal = ITEMS.register("earth_crystal",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//天地宝晶
}
