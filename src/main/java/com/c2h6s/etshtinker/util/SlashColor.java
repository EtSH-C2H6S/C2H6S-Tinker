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
    public static int getSlash(int index){
        return index<10?index: EtSHrnd().nextInt(8);
    }
    public static String getSlashColorStr(int index){
        return index<10?SlashCol.get(index):"etshtinker.tooltip.rainbow";
    }
}
