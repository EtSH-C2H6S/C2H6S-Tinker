package com.c2h6s.etshtinker.Entities.damageSources;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class playerThroughSource extends EntityDamageSource {
    private final float AMOUNT;
    public playerThroughSource(String p_19394_, Entity p_19395_,Float am) {
        super(p_19394_, p_19395_);
        this.AMOUNT =am;
    }
    public static DamageSource PlayerAnnihilate(Player player,Float am){
        return new playerThroughSource("etshtinker.annihilate",player,am);
    }

    public float getgetAmount(){
        return this.AMOUNT;
    }
}
