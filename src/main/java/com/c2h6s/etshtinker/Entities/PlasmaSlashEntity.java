package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerHook;
import com.c2h6s.etshtinker.init.etshtinkerModifiers;
import com.c2h6s.etshtinker.util.attackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.vecCalc.*;

public class PlasmaSlashEntity extends ItemProjectile {
    public final ItemStack Slash;
    public float angle ;
    public final float size =1;
    public Vec3 offset =new Vec3(0,0,0);
    public float damage=0;
    public ToolStack tool;
    public float CriticalRate;
    public List<LivingEntity> hitList = new ArrayList<>(List.of());
    public double SCALE =Math.max(1, getMold(this.getDeltaMovement()));
    public int hitRemain =16;

    public PlasmaSlashEntity(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_, ItemStack slash) {
        super(p_37248_, p_37249_);
        this.Slash = slash;
        this.angle = EtSHrnd().nextFloat(-15,15);
    }
    public ItemStack getSlash(){
        return this.Slash;
    }
    public void setToolstack(ToolStack tool){
        this.tool =tool;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }


    @Override
    public void tick() {
        if (this.tool==null&&this.getOwner() instanceof Player player){
            this.tool=ToolStack.from( player.getMainHandItem());
        }
        Vec3 rayVec3 =this.getDeltaMovement();
        super.tick();
        if (this.tickCount>=5){
            this.discard();
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
            double dx = vec3.x * SCALE+offset.x;
            double dy = vec3.y * SCALE+offset.y;
            double dz = vec3.z * SCALE+offset.z;
            this.setPos(x + dx, y + dy, z + dz);
            AABB aabb = this.getBoundingBox().expandTowards(vec3.scale(2)).expandTowards(vec3.scale(-1)).expandTowards(new Vec3(0,dy,0).cross(vec3)).expandTowards(new Vec3(0,-dy,0).cross(vec3));
            List<LivingEntity> ls0 = this.level.getEntitiesOfClass(LivingEntity.class, aabb);
            float overCrit =Math.max( CriticalRate -1,0);
            this.hitRemain=16;
            for (LivingEntity targets : ls0) {
                if (targets != null && targets.isAlive() && targets != this.getOwner() && !hitList.contains(targets)) {
                    boolean isCrit =EtSHrnd().nextFloat(0, 1) <= this.CriticalRate;
                    for (ModifierEntry modifier : this.tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.BEFORE_SLASH_HIT).beforePlasmaSlashHit(this.tool, targets, this, isCrit);
                    }
                    targets.invulnerableTime = 0;
                    attackUtil.attackEntity(this.tool, player, InteractionHand.MAIN_HAND, targets, ()->1, true, Util.getSlotType(InteractionHand.MAIN_HAND), this.damage, isCrit, true, true, true,overCrit);
                    targets.invulnerableTime=0;
                    for (ModifierEntry modifier : this.tool.getModifierList()) {
                        modifier.getHook(etshtinkerHook.AFTER_SLASH_HIT).afterPlasmaSlashHit(this.tool, targets, this, isCrit, this.damage);
                    }
                    hitList.add(targets);
                    this.hitRemain--;
                    if (this.hitRemain <= 0) {
                        break;
                    }
                }
            }
        }
    }
}
