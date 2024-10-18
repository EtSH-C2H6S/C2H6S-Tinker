package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;
import static com.c2h6s.etshtinker.util.getMainOrOff.*;

public class guardless extends etshmodifieriii {
    public guardless(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    private void livinghurtevent(LivingHurtEvent event) {
        LivingEntity holder =event.getEntity();
        Entity entity=event.getSource().getEntity();
        if (entity instanceof LivingEntity attacker&&holder instanceof Player player&&attacker!=holder) {
            if (getMainLevel(holder, this) > 0) {
                event.setAmount(event.getAmount() * 0.75f);
                attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                Objects.requireNonNull(attacker.getAttribute(ARMOR)).setBaseValue(Objects.requireNonNull(attacker.getAttribute(ARMOR)).getValue() * Math.max(0.25, 1 - getMainLevel(player, this) * 0.3));
                Objects.requireNonNull(attacker.getAttribute(ATTACK_DAMAGE)).setBaseValue(Math.max(0.5, Objects.requireNonNull(attacker.getAttribute(ATTACK_DAMAGE)).getValue() * Math.max(0.5, 1 - getMainLevel(player, this) * 0.2)));
            } else if (getOffLevel(holder, this) > 0) {
                event.setAmount(event.getAmount() * 0.75f);
                attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                Objects.requireNonNull(attacker.getAttribute(ARMOR)).setBaseValue(Objects.requireNonNull(attacker.getAttribute(ARMOR)).getValue() * Math.max(0.25, 1 - getMainLevel(player, this) * 0.3));
                Objects.requireNonNull(attacker.getAttribute(ATTACK_DAMAGE)).setBaseValue(Math.max(0.5, Objects.requireNonNull(attacker.getAttribute(ATTACK_DAMAGE)).getValue() * Math.max(0.5, 1 - getMainLevel(player, this) * 0.2)));
            }
        }
    }

}
