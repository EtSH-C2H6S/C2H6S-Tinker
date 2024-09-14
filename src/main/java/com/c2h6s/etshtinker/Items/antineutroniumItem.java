package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.Entities.annihilateexplosionentity;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerMekansimMaterial.ultra_dense;
import static com.c2h6s.etshtinker.util.vecCalc.*;

import java.util.List;

public class antineutroniumItem extends Item {
    public antineutroniumItem(Properties p_41383_) {
        super(p_41383_.tab(etshtinkerTab.MATERIALS).fireResistant().stacksTo(64).rarity(Rarity.RARE));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("etshtinker.item.tooltip.antineutronium").withStyle(ChatFormatting.YELLOW));
            list.add(Component.translatable("etshtinker.item.tooltip.antineutroniumwarn").withStyle(ChatFormatting.RED));
        }else {
            list.add(Component.translatable("etshtinker.item.tooltip.shift").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(itemstack, world, list, flag);
    }
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity){
        Level level =entity.getLevel();
        if (level!=null){
            ItemEntity neut =getNearestItemEntWithItem(5f,entity,level, ultra_dense.get());
            if (neut!=null){
                Vec3 vec3 =Entity1ToEntity2(neut,entity);
                if (getMold(vec3)>0.25){
                    Vec3 vec31 =vec3.scale(0.5);
                    neut.setPos(neut.getX()+vec31.x,neut.getY()+vec31.y,neut.getZ()+vec31.z);
                }else {
                    int amount =Math.min(entity.getItem().getCount(),neut.getItem().getCount());
                    annihilateexplosionentity explosion =new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(),level);
                    explosion.damage =amount*1024;
                    explosion.radius =Math.min(amount*2,32);
                    explosion.proceedRecipe =true;
                    explosion.proceedamount =amount;
                    explosion.setPos(entity.getX(),entity.getY()+0.25,entity.getZ());
                    level.addFreshEntity(explosion);
                    entity.getItem().setCount(entity.getItem().getCount()-amount);
                    neut.getItem().setCount(neut.getItem().getCount()-amount);
                }
            }
        }
        return false;
    }
}
