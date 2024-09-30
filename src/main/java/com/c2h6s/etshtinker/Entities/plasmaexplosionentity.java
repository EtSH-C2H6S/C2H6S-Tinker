package com.c2h6s.etshtinker.Entities;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerThermalMaterial;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import com.c2h6s.etshtinker.util.attackUtil;
import mekanism.api.MekanismAPI;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.modloaded.Cofhloaded;
import static com.c2h6s.etshtinker.util.modloaded.Mekenabled;
import static com.c2h6s.etshtinker.util.vecCalc.*;
import static slimeknights.tconstruct.library.tools.helper.ToolAttackUtil.getCooldownFunction;

public class plasmaexplosionentity extends ItemProjectile{
    public Vec3 rayVec3 =new Vec3(0,0,0);
    public SimpleParticleType particle = null;
    public float damage =0;
    public int time =0;
    public String special =null;
    public ToolStack tool =null;
    public float scale =1;
    public float criticalChance =0.0f;
    public boolean isCritical =Math.abs(EtSHrnd().nextFloat())<criticalChance;
    public InteractionHand HAND = InteractionHand.MAIN_HAND;
    private final SecureRandom random =EtSHrnd();
    public List<SimpleParticleType> lsp=List.of(
            etshtinkerParticleType.plasmaexplosionred.get(),
            etshtinkerParticleType.plasmaexplosionorange.get(),
            etshtinkerParticleType.plasmaexplosionyellow.get(),
            etshtinkerParticleType.plasmaexplosionlime.get(),
            etshtinkerParticleType.plasmaexplosiongreen.get(),
            etshtinkerParticleType.plasmaexplosioncyan.get(),
            etshtinkerParticleType.plasmaexplosionblue.get(),
            etshtinkerParticleType.plasmaexplosionpurple.get()
            );
    public List<AABB> aabbList =new ArrayList<>(List.of());

    public void getRayVec3(Vec3 vec3){
        this.rayVec3 =vec3;
    }
    public void getExplosionParticle(SimpleParticleType particleType){
        this.particle =particleType;
    }
    public void getBaseDamage(Float damage){
        this.damage =damage;
    }

    public plasmaexplosionentity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.DATA_ITEM_STACK, new ItemStack(etshtinkerItems.plasmaexplosion.get()));
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
    @Override
    public void tick() {
        time++;
        if (time > 12) {
            this.discard();
        }
        if (time >= 1) {
            if (time == 1) {
                Level world = this.level;
                int range = (int) getMold(rayVec3);
                Vec3 vec3 = getUnitizedVec3(rayVec3);
                if (special != null && special.equals("tracking")) {
                    Vec3 vec31 = getUnitizedVec3(Entity1ToEntity2(this, getNearestLiEnt((float) range, this, this.level)));
                    if (getMold(vec3)!=0) {
                        vec3 = vec31;
                    }
                }
                Vec3 pos = new Vec3(this.getX(), this.getY(), this.getZ());
                if (range > 0 && particle != null) {
                    int i = 0;

                    while (i < range) {
                        i += 1;
                        if (special != null && (special.equals("elemental") || special.equals("nova_radiation")||special.equals("quark"))) {
                            this.particle = lsp.get(random.nextInt(8));
                        }
                        double x = pos.x + i * vec3.x;
                        double y = pos.y + 0.5 * this.getBbHeight() + i * vec3.y;
                        double z = pos.z + i * vec3.z;
                        if (i <= 48&&i%2==0) {
                            if (world.isClientSide) {
                                world.addAlwaysVisibleParticle(particle, true, x, y, z, 0, 0, 0);
                            } else {
                                ((ServerLevel) world).sendParticles(particle, x, y, z, 1, 0, 0, 0, 0);
                            }
                        }
                        AABB aabb = new AABB(x + 1.25 * scale, y + 0.5 + 1.25 * scale, z + 1.25 * scale, x - 1.25 * scale, y - 1.25 * scale + 0.5, z - 1.25 * scale);
                        aabbList.add(aabb);
                        if (special != null && (special.equals("random_scatter") || special.equals("entropic"))) {
                            vec3 = getScatteredVec3(vec3, 0.25);
                        }
                    }
                }
            }
            List<LivingEntity> ls1 = new ArrayList<>(List.of());
            if (!aabbList.isEmpty()) {
                if (this.time == 1) {
                    for (AABB aabb : aabbList) {
                        List<LivingEntity> ls0 = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
                        List<ItemEntity> ls2;
                        if (special != null && special.equals("elemental") && Cofhloaded) {
                            ls2 = this.level.getEntitiesOfClass(ItemEntity.class, aabb);
                            if (!ls2.isEmpty()) {
                                for (ItemEntity item : ls2) {
                                    if (item.getItem().getItem().equals(etshtinkerThermalMaterial.activated_chroma_plate.get())) {
                                        item.getPersistentData().putInt("progress", item.getPersistentData().getInt("progress") + (int) this.damage);
                                        item.playSound(SoundEvents.FIREWORK_ROCKET_BLAST_FAR, 1.25f, 1.25f);
                                    }
                                }
                            }
                        }
                        if (!ls0.isEmpty()) {
                            for (LivingEntity target : ls0) {
                                if (target != null && target != this.getOwner() && this.getOwner() instanceof Player player && tool != null && !ls1.contains(target)) {
                                    boolean isCrit =this.isCritical || EtSHrnd().nextInt(100) < 10;
                                    for (ModifierEntry modifier : tool.getModifierList()) {
                                        modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).beforePlasmaExplosionHit(tool,target,this,isCrit);
                                    }
                                    target.invulnerableTime = 0;
                                    attackUtil.attackEntity(tool, player, HAND, target, ()->1, true, Util.getSlotType(HAND), this.damage * 0.75f, isCrit, true, true, true, 0);
                                    target.invulnerableTime = 0;
                                    for (ModifierEntry modifier : tool.getModifierList()) {
                                        modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterPlasmaExplosionHit(tool,target,this,isCrit);
                                    }
                                    ls1.add(target);
                                } else if (special != null && special.equals("entropic") && target != null) {
                                    ls1.add(target);
                                }
                            }
                        }
                    }
                    conductSpecial(ls1);
                }
                if (time == 9 && !aabbList.isEmpty()) {
                    for (AABB aabb : aabbList) {
                        List<LivingEntity> ls0 = this.level.getEntitiesOfClass(LivingEntity.class, aabb.inflate(1.5));
                        for (LivingEntity target : ls0) {
                            if (target != null && target != this.getOwner() && this.getOwner() instanceof Player player && tool != null && !ls1.contains(target)) {
                                boolean isCrit =this.isCritical || EtSHrnd().nextInt(100) < 45;
                                for (ModifierEntry modifier : tool.getModifierList()) {
                                    modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).beforePlasmaExplosionHit(tool,target,this,isCrit);
                                }
                                target.invulnerableTime = 0;
                                attackUtil.attackEntity(tool, player, HAND, target, () -> 1, true, Util.getSlotType(HAND), this.damage, this.isCritical || (EtSHrnd().nextInt(100) < 45), true, true, true, 0);
                                target.invulnerableTime = 0;
                                for (ModifierEntry modifier : tool.getModifierList()) {
                                    modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterPlasmaExplosionHit(tool,target,this,isCrit);
                                }
                                ls1.add(target);
                            }
                        }
                    }
                    conductSpecial(ls1);
                }
            }
        }
    }

    public void conductSpecial(List<LivingEntity> ls1){
        String special =this.special;
        if (!ls1.isEmpty() && special != null) {
            if (ls1.get(0) != null) {
                LivingEntity entity = ls1.get(0);
                ls1.clear();
                if (special.equals("antimatter_explosion")) {
                    this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 16f, Explosion.BlockInteraction.NONE);
                }
                if (special.equals("explosion")) {
                    this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 2f, Explosion.BlockInteraction.NONE);
                }
                if (special.equals("annihilate")) {
                    annihilateexplosionentity explosion = new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), level);
                    explosion.damage = 1024;
                    explosion.radius = 20;
                    explosion.proceedRecipe = true;
                    explosion.proceedamount = 8;
                    explosion.setPos(entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ());
                    level.addFreshEntity(explosion);
                }
                if (tool != null) {
                    for (ModifierEntry modifier : tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterSpecialAttack(tool,entity,this,special);
                    }
                }
            }
            if (!special.equals("tracking") && !special.equals("antimatter_explosion") && !special.equals("explosion") && !special.equals("annihilate")) {
                for (LivingEntity targets : ls1) {
                    if (targets != null) {
                        if (special.equals("ionize")) {
                            targets.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(), 100, 9, false, false), this.getOwner());
                        } else if (special.equals("burn")) {
                            targets.setSecondsOnFire(200);
                        } else if (special.equals("magic_damage")) {
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.MAGIC.bypassArmor().bypassMagic(), damage * 0.75f);
                        } else if (special.equals("nova_radiation")) {
                            targets.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, 9, false, false), this.getOwner());
                        } else if (special.equals("radiation") && Mekenabled) {
                            MekanismAPI.getRadiationManager().radiate(targets, 50);
                        } else if (special.equals("poison")) {
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 300, 9), this.getOwner());
                        } else if (special.equals("quark")) {
                            targets.getPersistentData().putInt("quark_disassemble",targets.getPersistentData().getInt("quark_disassemble")+100);
                        } else if (special.equals("corrosive")) {
                            if (targets.getAttributes().getInstance(Attributes.ARMOR) != null && targets.getArmorValue() > 0) {
                                targets.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(0);
                                targets.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(-targets.getArmorValue());
                            }
                            if (targets.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS) != null && targets.getArmorValue() > 0) {
                                targets.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS).setBaseValue(-1024);
                            }
                            if (targets.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE) != null) {
                                targets.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(-10);
                            }
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 100, 4), this.getOwner());
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 4), this.getOwner());
                        } else if (special.equals("elemental") || special.equals("entropic")) {
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.MAGIC.bypassArmor().bypassMagic(), damage * 0.25f);
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.explosion((LivingEntity) this.getOwner()).bypassArmor().bypassMagic(), damage * 0.25f);
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.LAVA.bypassArmor().bypassMagic(), damage * 0.25f);
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.WITHER.bypassArmor().bypassMagic(), damage * 0.25f);
                            targets.invulnerableTime = 0;
                            targets.hurt(DamageSource.DRAGON_BREATH.bypassArmor().bypassMagic(), damage * 0.25f);
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 4, false, false), this.getOwner());
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 4, false, false), this.getOwner());
                            targets.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 50, 4, false, false), this.getOwner());
                            if (Cofhloaded) {
                                targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 50, 4, false, false), this.getOwner());
                                targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(), 50, 4, false, false), this.getOwner());
                                targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(), 50, 4, false, false), this.getOwner());
                                targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(), 50, 4, false, false), this.getOwner());
                            }
                        }
                        if (tool != null) {
                            for (ModifierEntry modifier : tool.getModifierList()) {
                                modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterSpecialAttack(tool,targets,this,special);
                            }
                        }
                    }
                }
            }
        }
    }
}
