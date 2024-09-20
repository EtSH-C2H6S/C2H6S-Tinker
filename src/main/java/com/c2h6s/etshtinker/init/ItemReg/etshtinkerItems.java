package com.c2h6s.etshtinker.init.ItemReg;

import com.c2h6s.etshtinker.Entities.*;
import com.c2h6s.etshtinker.init.etshtinkerBlocks;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import com.c2h6s.etshtinker.tools.item.tinker.ConstrainedPlasmaSaber;
import com.c2h6s.etshtinker.tools.item.tinker.IonizedCannon;
import com.c2h6s.etshtinker.tools.stats.PlasmaGeneratorMaterialStats;
import com.c2h6s.etshtinker.tools.stats.fluidChamberMaterialStats;
import com.c2h6s.etshtinker.tools.stats.ionizerMaterialStats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.c2h6s.etshtinker.Items.*;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.gadgets.item.ShurikenItem;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import com.c2h6s.etshtinker.tools.definition.toolDefinitions;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.init.etshtinkerTab.TOOLS;


public class etshtinkerItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);
    public static final ItemDeferredRegisterExtension EXTRA_ITEMS = new ItemDeferredRegisterExtension(MOD_ID);;
    private static final Item.Properties THROWABLE_PROPS = new Item.Properties().stacksTo(16);
    private static final Item.Properties TOOL =(new Item.Properties().tab(TOOLS).stacksTo(1));
    private static final Item.Properties CASTS =(new Item.Properties().tab(TOOLS));
    private static final Item.Properties PARTS =(new Item.Properties().tab(TOOLS));

    //材料
    public static final RegistryObject<Item> lightless_alloy = ITEMS.register("lightless_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));
    public static final RegistryObject<Item> energized_sculk_alloy = ITEMS.register("energized_sculk_alloy",( ) -> new EnergizedSculkAlloyItem(new Item.Properties().tab(etshtinkerTab.MATERIALS)));


    //方块



    //杂项
    public static final RegistryObject<Item> sculk_energycore = ITEMS.register("sculk_energycore", ()->new sculk_energycore(new Item.Properties().stacksTo(64).tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> etshtinker_guide = ITEMS.register("etshtinker_guide", () -> new etshbookitem(new Item.Properties().stacksTo(1).tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> ionized_cannon_prototype = ITEMS.register("ionized_cannon_prototype", ()->new IonizedCannonPrototype(new Item.Properties().stacksTo(1).tab(etshtinkerTab.MIXC)));
    public static final RegistryObject<Item> knsu = ITEMS.register("knsu",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MIXC)));



    //弹射物
    public static final RegistryObject<Item> novasickle = ITEMS.register("novasickle", ()->new ShurikenItem(THROWABLE_PROPS, novasickleEntity::new));
    public static final RegistryObject<Item> exoslash = ITEMS.register("exoslash", ()->new ShurikenItem(THROWABLE_PROPS, exoSlashEntity::new));
    public static final RegistryObject<Item> lightningarrow = ITEMS.register("lightningarrow", ()->new lightningarrowitem(new Item.Properties()));
    public static final RegistryObject<Item> shadowaxe = ITEMS.register("shadowaxe", ()->new ShurikenItem(THROWABLE_PROPS, shadowaxeEntity::new));
    public static final RegistryObject<Item> plasmawaveslash = ITEMS.register("plasmawaveslash", ()->new Item(THROWABLE_PROPS));
    public static final RegistryObject<Item> plasmarrowitem = ITEMS.register("plasmarrowitem", ()->new plasmarrowitem(new Item.Properties()));
    public static final RegistryObject<Item> plasmaexplosion = ITEMS.register("plasmaexplosion", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> phantom_sword = ITEMS.register("phantom_sword", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> enchantedsword = ITEMS.register("enchantedsword", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> night_slash_a = ITEMS.register("night_slash_a", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> night_slash_b = ITEMS.register("night_slash_b", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_anti = ITEMS.register("plasma_slash_anti", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_blue = ITEMS.register("plasma_slash_blue", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_cyan = ITEMS.register("plasma_slash_cyan", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_green = ITEMS.register("plasma_slash_green", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_lime = ITEMS.register("plasma_slash_lime", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_orange = ITEMS.register("plasma_slash_orange", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_purple = ITEMS.register("plasma_slash_purple", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_red = ITEMS.register("plasma_slash_red", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_yellow = ITEMS.register("plasma_slash_yellow", ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> plasma_slash_dark = ITEMS.register("plasma_slash_dark", ()->new Item(new Item.Properties()));



    //工具
    public static final RegistryObject<ModifiableItem> IONIZED_CANNON = ITEMS.register("ionized_cannon", () -> new IonizedCannon(TOOL, toolDefinitions.IONIZED_CANNON));
    public static final RegistryObject<ModifiableItem> constrained_plasma_saber = ITEMS.register("constrained_plasma_saber", () -> new ConstrainedPlasmaSaber(TOOL, toolDefinitions.CONSTRAINED_PLASMA_SABER));





    //部件
    public static final RegistryObject<ToolPartItem> ionizer = ITEMS.register("ionizer", () -> new ToolPartItem(PARTS, ionizerMaterialStats.ID));
    public static final RegistryObject<ToolPartItem> fluid_chamber = ITEMS.register("fluid_chamber", () -> new ToolPartItem(PARTS, fluidChamberMaterialStats.ID));
    public static final RegistryObject<ToolPartItem> plasma_generator = ITEMS.register("plasma_generator", () -> new ToolPartItem(PARTS, PlasmaGeneratorMaterialStats.ID));



    //铸膜
    public static final RegistryObject<Item> fluid_chamber_cast = ITEMS.register("fluid_chamber_cast", ()->new Item(new Item.Properties().stacksTo(64).tab(TOOLS)));
    public static final RegistryObject<Item> fluid_chamber_sand_cast = ITEMS.register("fluid_chamber_sand_cast", ()->new Item(new Item.Properties().stacksTo(64).tab(TOOLS)));
    public static final RegistryObject<Item> fluid_chamber_red_sand_cast = ITEMS.register("fluid_chamber_red_sand_cast", ()->new Item(new Item.Properties().stacksTo(64).tab(TOOLS)));

    public class configuredMaterial{
        public static final DeferredRegister<Item> ITEMSC = DeferredRegister.create( ForgeRegistries.ITEMS, MOD_ID);
        public static final RegistryObject<Item> exo_alloy = ITEMSC.register("exo_alloy",( ) -> new Item(new Item.Properties().tab(etshtinkerTab.MATERIALS)));//星流合金
    }

}