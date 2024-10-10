package com.c2h6s.etshtinker.init;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class etshtinkerTab {
    public static final CreativeModeTab MATERIALS = new CreativeModeTab("etshtinker.materials") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(etshtinkerItems.energized_sculk_alloy.get());
        };
    };
    public static final CreativeModeTab TOOLS = new CreativeModeTab("etshtinker.toolsandparts") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(etshtinkerItems.sculk_energycore.get());
        };
    };
    public static final CreativeModeTab MIXC = new CreativeModeTab("etshtinker.mixc") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(etshtinkerItems.etshtinker_guide.get());
        };
    };
    public static final CreativeModeTab BLOCKS = new CreativeModeTab("etshtinker.blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DEEPSLATE);
        };
    };

}