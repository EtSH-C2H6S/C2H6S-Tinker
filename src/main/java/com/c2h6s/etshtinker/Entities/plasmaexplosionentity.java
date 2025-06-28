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
import net.minecraft.world.entity.Entity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.modloaded.Cofhloaded;
import static com.c2h6s.etshtinker.util.modloaded.Mekenabled;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class plasmaexplosionentity extends ItemProjectile{
    private static final Logger log = LoggerFactory.getLogger(plasmaexplosionentity.class);
    public Vec3 rayVec3 =new Vec3(0,0,0);
    public SimpleParticleType particle = null;
    public float damage =0;
    public int time =0;
    public String special =null;
    public ToolStack tool =null;
    public float scale =1;
    public float criticalChance =0.0f;
    public boolean isCritical =Math.abs(EtSHrnd().nextFloat())<criticalChance;
    public boolean forcedCrit =false;
    public List<Entity> entityToHit = new ArrayList<>();
    public List<ItemEntity> chromaPlate = new ArrayList<>();
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
                            if (!world.isClientSide) {
                                ((ServerLevel) world).sendParticles(particle, x, y, z, 1, 0, 0, 0, 0);
                            }
                        }
                        AABB aabb = new AABB(x + 1.25 * scale, y + 0.5 + 1.25 * scale, z + 1.25 * scale, x - 1.25 * scale, y - 1.25 * scale + 0.5, z - 1.25 * scale);
                        world.getEntitiesOfClass(ItemEntity.class,aabb,itemEntity -> itemEntity.getItem().is(etshtinkerThermalMaterial.activated_chroma_plate.get())&&!chromaPlate.contains(itemEntity)).forEach(itemEntity -> {
                            processChromaPlate(itemEntity);
                            chromaPlate.add(itemEntity);
                        });
                        entityToHit.addAll( world.getEntitiesOfClass(Entity.class,aabb, entity -> entity.isAttackable()&&!entityToHit.contains(entity)&&entity!=this.getOwner()));
                        if (special != null && (special.equals("random_scatter") || special.equals("entropic"))) {
                            vec3 = getScatteredVec3(vec3, 0.25);
                        }
                    }
                }
                entityToHit.forEach(entity -> this.hitEntity(entity,this.damage*0.75f,0.1f));
            }
            if (this.time==9){
                entityToHit.forEach(entity -> this.hitEntity(entity,this.damage,0.33f));
            }
        }
    }

    public void processChromaPlate(ItemEntity entity){
        if (special != null && special.equals("elemental") && Cofhloaded) {
            if (entity.getItem().getItem().equals(etshtinkerThermalMaterial.activated_chroma_plate.get())) {
                entity.getPersistentData().putInt("progress", entity.getPersistentData().getInt("progress") + (int) this.damage);
                entity.playSound(SoundEvents.FIREWORK_ROCKET_BLAST_FAR, 1.25f, 1.25f);
            }
        }
    }

    public void hitEntity(Entity entity,float damage,float critChance){
        if (entity instanceof LivingEntity living){
            if (this.getOwner()==null){
                living.hurt(DamageSource.mobAttack(living),damage);
            }else if (this.getOwner() instanceof LivingEntity attacker){
                boolean isCrit =this.isCritical || EtSHrnd().nextFloat() <= critChance;
                for (ModifierEntry modifier : tool.getModifierList()) {
                    modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).beforePlasmaExplosionHit(tool,living,this,isCrit);
                }
                living.invulnerableTime = 0;
                attackUtil.attackEntity(tool, attacker, HAND, living, ()->1, true, Util.getSlotType(HAND), damage, isCrit||forcedCrit, true, true, true, 0);
                living.invulnerableTime = 0;
                this.conductSpecial(living);
                for (ModifierEntry modifier : tool.getModifierList()) {
                    modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterPlasmaExplosionHit(tool,living,this,isCrit);
                }
            }
        }else if (this.getOwner() instanceof LivingEntity attacker){
            entity.hurt(DamageSource.mobAttack(attacker),damage);
        }
    }

    public void conductSpecial(LivingEntity entity) {
        String special = this.special;
        if (special==null) return;
        if (!this.getTags().contains("do_second_special")) {
            this.addTag("do_second_special");
            if (!(entity instanceof Player)) {
                if (special.equals("antimatter_explosion")) {
                    this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 16f, Explosion.BlockInteraction.NONE);
                }
                if (special.equals("explosion")) {
                    this.level.explode(this.getOwner(), entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ(), 2f, Explosion.BlockInteraction.NONE);
                }
                if (special.equals("annihilate")) {
                    annihilateexplosionentity explosion = new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), level);
                    explosion.damage = 2048;
                    explosion.radius = 16;
                    explosion.proceedRecipe = true;
                    explosion.proceedamount = 0;
                    explosion.setPos(entity.getX(), entity.getY() + 0.5 * entity.getBbHeight(), entity.getZ());
                    level.addFreshEntity(explosion);
                }
                if (tool != null) {
                    for (ModifierEntry modifier : tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterSpecialAttack(tool, entity, this, special);
                    }
                }
            }
        }
        if (!special.equals("tracking") && !special.equals("antimatter_explosion") && !special.equals("explosion") && !special.equals("annihilate")) {
            if (entity != null && special.equals("entropic")) {
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.MAGIC.bypassArmor(), damage * 0.25f);
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.explosion((LivingEntity) this.getOwner()).bypassArmor(), damage * 0.25f);
                entity.invulnerableTime = 0;
                entity.hurt(DamageSource.LAVA.bypassArmor(), damage * 0.25f);
                entity.invulnerableTime = 0;
                entity.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 2, false, false), this.getOwner());
                entity.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 2, false, false), this.getOwner());
                entity.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 50, 4, false, false), this.getOwner());
                if (Cofhloaded) {
                    entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 50, 4, false, false), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(), 50, 4, false, false), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(), 50, 4, false, false), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(), 50, 4, false, false), this.getOwner());
                }
            }
            if (entity != null && !(entity instanceof Player)) {
                if (special.equals("ionize")) {
                    entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.ionized.get(), 100, 9, false, false), this.getOwner());
                } else if (special.equals("burn")) {
                    entity.setSecondsOnFire(200);
                } else if (special.equals("magic_damage")) {
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.MAGIC.bypassArmor().bypassMagic(), damage * 0.75f);
                } else if (special.equals("nova_radiation")) {
                    entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, 9, false, false), this.getOwner());
                } else if (special.equals("radiation") && Mekenabled) {
                    MekanismAPI.getRadiationManager().radiate(entity, 50);
                } else if (special.equals("poison")) {
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 300, 9), this.getOwner());
                } else if (special.equals("quark")) {
                    entity.getPersistentData().putInt("quark_disassemble", entity.getPersistentData().getInt("quark_disassemble") + 100);
                } else if (special.equals("corrosive")) {
                    if (entity.getAttributes().getInstance(Attributes.ARMOR) != null && entity.getArmorValue() > 0) {
                        entity.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(0);
                        entity.getAttributes().getInstance(Attributes.ARMOR).setBaseValue(-entity.getArmorValue());
                    }
                    if (entity.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS) != null && entity.getArmorValue() > 0) {
                        entity.getAttributes().getInstance(Attributes.ARMOR_TOUGHNESS).setBaseValue(-1024);
                    }
                    if (entity.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE) != null) {
                        entity.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(-10);
                    }
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.POISON, 100, 4), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 4), this.getOwner());
                } else if (special.equals("elemental")) {
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.MAGIC.bypassArmor().bypassMagic(), damage * 0.25f);
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.explosion((LivingEntity) this.getOwner()).bypassArmor().bypassMagic(), damage * 0.25f);
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.LAVA.bypassArmor().bypassMagic(), damage * 0.25f);
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.WITHER.bypassArmor().bypassMagic(), damage * 0.25f);
                    entity.invulnerableTime = 0;
                    entity.hurt(DamageSource.DRAGON_BREATH.bypassArmor().bypassMagic(), damage * 0.25f);
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 4, false, false), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 4, false, false), this.getOwner());
                    entity.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 50, 4, false, false), this.getOwner());
                    if (Cofhloaded) {
                        entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.ENDERFERENCE.get(), 50, 4, false, false), this.getOwner());
                        entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(), 50, 4, false, false), this.getOwner());
                        entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(), 50, 4, false, false), this.getOwner());
                        entity.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(), 50, 4, false, false), this.getOwner());
                    }
                }
                if (tool != null) {
                    for (ModifierEntry modifier : tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.PLASMA_EXPLOSION_HIT).afterSpecialAttack(tool, entity, this, special);
                    }
                }
            }
        }
    }

}
