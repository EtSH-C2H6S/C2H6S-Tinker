package com.c2h6s.etshtinker.Entities.damageSources;

import net.minecraft.world.damagesource.DamageSource;

public class ThroughSources extends DamageSource implements IPierceThroughSource{
    private final float AMOUNT;
    public ThroughSources(String p_19333_, float am) {
        super(p_19333_);
        this.AMOUNT=am;
    }
    public static ThroughSources annihilate(float am){
        return new ThroughSources("etshtinker.annihilate",am);
    }

    public static ThroughSources atomic(float am){
        return new ThroughSources("etshtinker.atomic_dec",am);
    }

    public static ThroughSources quark(float am){
        return new ThroughSources("etshtinker.quark",am);
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
