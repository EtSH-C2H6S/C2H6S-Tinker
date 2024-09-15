package com.c2h6s.etshtinker.util;

import com.c2h6s.etshtinker.Entities.PlasmaSlashEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class SlashColor {
    public static final List<Item> Slash = List.of(
            etshtinkerItems.plasma_slash_red.get(),
            etshtinkerItems.plasma_slash_orange.get(),
            etshtinkerItems.plasma_slash_yellow.get(),
            etshtinkerItems.plasma_slash_lime.get(),
            etshtinkerItems.plasma_slash_green.get(),
            etshtinkerItems.plasma_slash_cyan.get(),
            etshtinkerItems.plasma_slash_blue.get(),
            etshtinkerItems.plasma_slash_purple.get(),
            etshtinkerItems.plasma_slash_anti.get(),
            etshtinkerItems.plasma_slash_dark.get()
    );
    public static final List<EntityType<PlasmaSlashEntity>> SlashEntity = List.of(
            etshtinkerEntity.plasma_slash_red.get(),
            etshtinkerEntity.plasma_slash_orange.get(),
            etshtinkerEntity.plasma_slash_yellow.get(),
            etshtinkerEntity.plasma_slash_lime.get(),
            etshtinkerEntity.plasma_slash_green.get(),
            etshtinkerEntity.plasma_slash_cyan.get(),
            etshtinkerEntity.plasma_slash_blue.get(),
            etshtinkerEntity.plasma_slash_purple.get(),
            etshtinkerEntity.plasma_slash_anti.get(),
            etshtinkerEntity.plasma_slash_dark.get(),
            etshtinkerEntity.plasma_slash_rainbow.get()
    );
    public static final List<String> SlashCol = List.of(
            "etshtinker.tooltip.red",
            "etshtinker.tooltip.orange",
            "etshtinker.tooltip.yellow",
            "etshtinker.tooltip.lime",
            "etshtinker.tooltip.green",
            "etshtinker.tooltip.cyan",
            "etshtinker.tooltip.blue",
            "etshtinker.tooltip.purple",
            "etshtinker.tooltip.anti",
            "etshtinker.tooltip.dark"
    );
    public static ItemStack getSlash(int index){
        return index<10? new ItemStack(Slash.get(index)):new ItemStack(Slash.get(EtSHrnd().nextInt(8)));
    }
    public static String getSlashColorStr(int index){
        return index<10?SlashCol.get(index):"etshtinker.tooltip.rainbow";
    }
    public static EntityType<PlasmaSlashEntity> getSlashType(int index){
        return SlashEntity.get( Math.min(10,index));
    }
}
