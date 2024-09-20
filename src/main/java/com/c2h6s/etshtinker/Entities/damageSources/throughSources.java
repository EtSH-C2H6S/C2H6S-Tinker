package com.c2h6s.etshtinker.Entities.damageSources;

import net.minecraft.world.damagesource.DamageSource;

public class throughSources extends DamageSource {
    private final float AMOUNT;
    public throughSources(String p_19333_,float am) {
        super(p_19333_);
        this.AMOUNT=am;
    }
    public static DamageSource annihilate(Float am){
        return new throughSources("etshtinker.annihilate",am);
    }

    public float getgetAmount(){
        return this.AMOUNT;
    }
}
