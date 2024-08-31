package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import com.c2h6s.etshtinker.init.etshtinkerTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.c2h6s.etshtinker.init.ItemReg.etshtinkerThermalMaterial.*;
import static com.c2h6s.etshtinker.util.modloaded.*;

public class ActivatedChromaPlateItem extends Item {
    public ActivatedChromaPlateItem(Properties p_41383_) {
        super(p_41383_.stacksTo(64).tab(etshtinkerTab.MATERIALS).fireResistant());
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("etshtinker.item.tooltip.activated_chroma_plate").withStyle(ChatFormatting.YELLOW));
        list.add(Component.translatable("etshtinker.item.tooltip.activated_chroma_plate3").withStyle(ChatFormatting.YELLOW));
        list.add(Component.translatable("etshtinker.item.tooltip.activated_chroma_plate2").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(itemstack, world, list, flag);
    }
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity){
        if (Cofhloaded){
            if (!entity.getPersistentData().contains("progress")){
                entity.getPersistentData().putInt("progress",0);
            }
            if (entity.getPersistentData().getInt("progress")>0){
                ((ServerLevel) entity.level).sendParticles(etshtinkerParticleType.nova.get(),entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),2,0,0,0,0.2);
                entity.getPersistentData().putInt("progress",entity.getPersistentData().getInt("progress")-1);
            }
            if (entity.getPersistentData().getInt("progress")>1000){
                ((ServerLevel) entity.level).sendParticles(etshtinkerParticleType.nova.get(),entity.getX(),entity.getY()+0.5,entity.getZ(),32,0,0,0,0.5);
                List<LivingEntity> ls =entity.level.getEntitiesOfClass(LivingEntity.class,new AABB(entity.getX()-6,entity.getY()-6,entity.getZ()-6,entity.getX()+6,entity.getY()+6,entity.getZ()+6));
                for (LivingEntity target:ls){
                    if (target!=null){
                        target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(),100,1,false,false),entity);
                    }
                }
                entity.getItem().setCount(entity.getItem().getCount()-1);
                entity.getPersistentData().putInt("progress",entity.getPersistentData().getInt("progress")-1000);
                ItemEntity alloy =new ItemEntity(EntityType.ITEM,entity.level);
                alloy.setItem(new ItemStack(nights_alloy.get(),1));
                alloy.setPos(entity.getX(),entity.getY(),entity.getZ());
                ((ServerLevel)entity.level).addFreshEntity(alloy);
            }
        }
        return false;
    }
}
