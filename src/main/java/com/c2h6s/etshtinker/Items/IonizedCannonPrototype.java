package com.c2h6s.etshtinker.Items;

import com.c2h6s.etshtinker.Entities.CustomSonicBoomEntity;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IonizedCannonPrototype extends Item {
    public IonizedCannonPrototype(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }

    @Override
    public int getBarWidth(ItemStack p_150900_) {
        return Math.min(13,13*((p_150900_.getMaxDamage() - p_150900_.getDamageValue())/p_150900_.getMaxDamage()));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int useTime =this.getUseDuration(stack)-timeLeft;
        if (useTime>=40&&entity instanceof Player player){
            CustomSonicBoomEntity entity1 = new CustomSonicBoomEntity(etshtinkerEntity.sonic_boom.get(),level);
            entity1.Scatter=false;
            entity1.range=20;
            entity1.damage=64;
            entity1.setOwner(player);
            entity1.direction =player.getLookAngle();
            entity1.setPos(new Vec3(player.getX(),player.getEyeY(),player.getZ()).add(player.getLookAngle()));
            level.addFreshEntity(entity1);
            player.playSound(SoundEvents.WARDEN_SONIC_BOOM,1,1);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        int useTime =this.getUseDuration(stack)-count;
        if (useTime==40){
            Level world =player.level;
            player.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 1, 1);
            Vec3 pos = player.getEyePosition();
            Vec3 ang = player.getLookAngle();
            double x = pos.x + ang.x;
            double y = pos.y + ang.y;
            double z = pos.z + ang.z;
            world.addAlwaysVisibleParticle(ParticleTypes.ELECTRIC_SPARK, true, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack =player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("etshtinker.item.tooltip.ionized_cannon_prototype").withStyle(ChatFormatting.AQUA));
        list.add(Component.translatable("etshtinker.item.tooltip.ionized_cannon_prototype1").withStyle(ChatFormatting.DARK_AQUA));
        list.add(Component.translatable("etshtinker.item.tooltip.ionized_cannon_prototype2").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(itemstack, world, list, flag);
    }
}
