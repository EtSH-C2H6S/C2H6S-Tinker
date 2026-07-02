package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import com.c2h6s.etshtinker.util.attackUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class PlasmaSlashEntity extends Projectile {
    public float angle ;
    public final float size =1;
    public Vec3 offset =new Vec3(0,0,0);
    public float damage=0;
    public ToolStack tool;
    public float CriticalRate;
    public boolean isCritical = false;
    public List<Entity> hitList = new ArrayList<>(List.of());
    public double SCALE ;
    public int hitRemain =16;
    public float echoTriggerChance = 0.25f;
    public boolean isEcho = false;
    public static final EntityDataAccessor<Integer> KEY_ECHO = SynchedEntityData.defineId(PlasmaSlashEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> KEY_IS_CRITICAL = SynchedEntityData.defineId(PlasmaSlashEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> KEY_SLASH_TYPE = SynchedEntityData.defineId(PlasmaSlashEntity.class, EntityDataSerializers.INT);

    public PlasmaSlashEntity(EntityType<? extends Projectile> type,Level p_37249_) {
        super(type, p_37249_);
        this.angle = EtSHrnd().nextFloat(-15,15);
    }
    public PlasmaSlashEntity(Level p_37249_) {
        this(etshtinkerEntity.PLASMA_SLASH.get(), p_37249_);
    }
    public PlasmaSlashEntity(Level p_37249_, int slash) {
        this(p_37249_);
        this.setSlashType(slash);
    }

    public double getScale(){
        this.SCALE = Math.max(1, getMold(this.getDeltaMovement()));
        return this.SCALE;
    }
    public int getSlashType(){
        return this.entityData.get(KEY_SLASH_TYPE);
    }
    public void setSlashType(int type){
        this.entityData.set(KEY_SLASH_TYPE,type);
    }
    public String getTexturePath(){
        return "textures/projectile/plasma_slash/plasma_slash_"+(this.isVisuallyCritical()?"powered_":"")+this.getSlashType()+"_";
    }

    public void setToolstack(ToolStack tool){
        this.tool =tool;
    }
    public boolean isVisuallyCritical(){
        return this.entityData.get(KEY_IS_CRITICAL);
    }

    public void setEcho(int value){
        this.entityData.set(KEY_ECHO,value);
    }

    public int getEcho(){
        return this.entityData.get(KEY_ECHO);
    }


    @Override
    public void tick() {
        if (this.firstTick) {
            this.isCritical = EtSHrnd().nextFloat(0, 1) <= this.CriticalRate;
            if (this.isCritical) {
                this.entityData.set(KEY_IS_CRITICAL, true);
                this.setDeltaMovement(this.getDeltaMovement().scale(1.5f));
                this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP,1.25f,1.25f);
            }
            else this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP,1,1);
        }

        if (this.tool==null&&this.getOwner() instanceof Player player){
            this.tool=ToolStack.from( player.getMainHandItem());
        }
        Vec3 rayVec3 =this.getDeltaMovement();
        super.tick();
        if (this.tickCount>=5){
            if (getEcho()>0){
                if (!isEcho) this.isEcho = true;
                this.tickCount = 2;
                this.setEcho(getEcho()-1);
                if (!this.level.isClientSide) {
                    this.hitList.clear();
                    if (this.isCritical) {
                        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.6f, 1.25f);
                    } else this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.6f, 1);
                }
                return;
            }
            this.discard();
            return;
        }
        if (this.tickCount<=1){
            return;
        }
        Entity entity =this.getOwner();
        if (entity==null){
            return;
        }
        if (entity instanceof Player player) {
            Vec3 vec3 = new Vec3(rayVec3.x, rayVec3.y, rayVec3.z);
            double x = player.getX();
            double y = player.getY() + 0.5 * player.getBbHeight();
            double z = player.getZ();
            double dx = vec3.x * getScale()+offset.x;
            double dy = vec3.y * getScale()+offset.y;
            double dz = vec3.z * getScale()+offset.z;
            this.setPos(x + dx, y + dy, z + dz);
            AABB aabb = this.getBoundingBox().expandTowards(vec3.scale(2)).expandTowards(vec3.scale(-1)).expandTowards(new Vec3(0,dy,0).cross(vec3)).expandTowards(new Vec3(0,-dy,0).cross(vec3));
            List<Entity> ls0 = this.level.getEntitiesOfClass(Entity.class, aabb,this::canHitEntity);
            float overCrit =Math.max( CriticalRate -1,0);
            this.hitRemain=16;
            for (Entity targets : ls0) {
                if (targets instanceof LivingEntity living) {
                    for (ModifierEntry modifier : this.tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.BEFORE_SLASH_HIT).beforePlasmaSlashHit(this.tool, living, this, this.isCritical);
                    }
                    targets.invulnerableTime = 0;
                    if (!isEcho||EtSHrnd().nextFloat()<=echoTriggerChance)
                        attackUtil.attackEntity(this.tool, player, InteractionHand.MAIN_HAND, targets, ()->1, true, Util.getSlotType(InteractionHand.MAIN_HAND), this.damage, this.isCritical, true, true, true,overCrit);
                    else {
                        float criticalModifier = this.isCritical ? 1.5f+overCrit : 1.0f;
                        CriticalHitEvent hitResult = ForgeHooks.getCriticalHit(player, living, this.isCritical, criticalModifier);
                        this.isCritical = hitResult != null;
                        if (this.isCritical) {
                            criticalModifier = hitResult.getDamageModifier();
                        }
                        targets.hurt(DamageSource.playerAttack(player),this.damage*criticalModifier);
                    }
                    targets.invulnerableTime=0;
                    for (ModifierEntry modifier : this.tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.AFTER_SLASH_HIT).afterPlasmaSlashHit(this.tool, living, this, this.isCritical, this.damage);
                    }
                    hitList.add(targets);
                    this.hitRemain--;
                    if (this.hitRemain <= 0) {
                        break;
                    }
                }else if (targets != null && !hitList.contains(targets)){
                    targets.invulnerableTime = 0;
                    attackUtil.attackEntity(this.tool, player, InteractionHand.MAIN_HAND, targets, ()->1, true, Util.getSlotType(InteractionHand.MAIN_HAND), this.damage, false, true, true, true,overCrit);
                    targets.invulnerableTime=0;
                }
            }
        }
    }

    @Override
    protected boolean canHitEntity(Entity targets) {
        Entity entity =this.getOwner();
        if (entity==null){
            return false;
        }
        return targets instanceof LivingEntity && targets.isAlive() && targets != this.getOwner() && !hitList.contains(targets) && !(targets instanceof Player);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(KEY_ECHO,0);
        this.entityData.define(KEY_IS_CRITICAL,false);
        this.entityData.define(KEY_SLASH_TYPE,0);
    }
}
