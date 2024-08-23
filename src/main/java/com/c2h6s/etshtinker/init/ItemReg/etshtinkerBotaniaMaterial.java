package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerBotaniaMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> manalyn_queen = ITEMS.register("manalyn_queen",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//魔灵皇钢
}
