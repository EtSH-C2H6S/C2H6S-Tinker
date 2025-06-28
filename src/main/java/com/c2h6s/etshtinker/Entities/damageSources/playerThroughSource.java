package com.c2h6s.etshtinker.Entities.damageSources;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class playerThroughSource extends EntityDamageSource implements IPierceThroughSource {
    private float AMOUNT;
    public playerThroughSource(String p_19394_, Entity p_19395_,Float am) {
        super(p_19394_, p_19395_);
        this.AMOUNT =am;
    }
    public static playerThroughSource PlayerAnnihilate(Player player,float am){
        return new playerThroughSource("etshtinker.annihilate",player,am);
    }

    public static playerThroughSource PlayerPierce(Player player,float am){
        return new playerThroughSource("etshtinker.pierce",player,am);
    }

    public static playerThroughSource PlayerAtomic(Player player,float am){
        return new playerThroughSource("etshtinker.atomic_dec",player,am);
    }

    public static playerThroughSource PlayerQuark(Player player,float am){
        return new playerThroughSource("etshtinker.quark",player,am);
    }


    @Override
    public float getAmount() {
        return this.AMOUNT;
    }

    @Override
    public DamageSource getSource() {
        return this;
    }
}
