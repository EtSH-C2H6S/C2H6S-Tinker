package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import slimeknights.tconstruct.gadgets.entity.shuriken.ShurikenEntityBase;

import java.util.List;
import java.util.Objects;

import static com.c2h6s.etshtinker.util.vecCalc.*;
import static com.c2h6s.etshtinker.util.ParticleChainUtil.*;
import static com.c2h6s.etshtinker.util.novaExplosionUtil.*;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class novasickleEntity extends ShurikenEntityBase {
    public float baseDamage;
    public int time =0;
    public boolean back =false;
    public int power =0;

    public int getpowah(int power){
        this.power =power;
        return power;
    }
    public float setDamage(float damage){
        baseDamage =damage;
        return damage;
    }
    public novasickleEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(etshtinkerEntity.novascikle.get(), world);
    }
    public novasickleEntity(EntityType<? extends ShurikenEntityBase> type, double x, double y, double z, Level worldIn){
        super(type, x, y, z, worldIn);
    }
    public novasickleEntity(EntityType<? extends novasickleEntity> type, Level world) {
        super(type, world);
    }
    public novasickleEntity(Level worldIn, LivingEntity throwerIn) {
        super(etshtinkerEntity.novascikle.get(), throwerIn, worldIn);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(etshtinkerItems.novasickle.get());
    }

    @Override
    public float getDamage() {
        return baseDamage;
    }

    @Override
    public float getKnockback() {
        return 5;
    }
    public void tick() {
        time++;
        if (time>60){
            this.remove(RemovalReason.KILLED);
        }
        if (time>=2&&time<15){
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
        }
        if (time==15&& !this.back){
            LivingEntity entity =getNearestLiEnt(32f,this,this.level);
            Vec3 vec3 = Entity1ToEntity2(this,entity);
            if (entity!=null) {
                Level world = entity.level;
                if (world instanceof ServerLevel serverLevel) {
                    if (this.getOwner() instanceof Player player) {
                        summonELECSPARKFromTo(serverLevel, this.getId(), entity.getId());
                        double vx = Objects.requireNonNull(getUnitizedVec3(vec3)).x;
                        double vy = Objects.requireNonNull(getUnitizedVec3(vec3)).y;
                        double vz = Objects.requireNonNull(getUnitizedVec3(vec3)).z;
                        double x = entity.getX();
                        double y = entity.getY();
                        double z = entity.getZ();
                        this.setPos(entity.getX() - 2 * vx, 0.5 * (entity.getY() + entity.getEyeY()) - 2 * vy, entity.getZ() - 2 * vz);
                        this.setDeltaMovement(vec3);
                        this.noPhysics = false;
                        novaExplode(this.level,this.power, this.getDamage(), x, y + 0.5 * entity.getBbHeight(), z, player);
                    }
                }
            }
            if (entity==null){
                back =true;
            }
        }
        if(this.back&&this.getOwner()!=null){
            Vec3 vec3=getUnitizedVec3(Entity1ToEntity2Eye(this,this.getOwner()));
            if (vec3 != null) {
                this.setDeltaMovement(vec3.x,vec3.y,vec3.z);
            }
        }
        super.tick();
    }
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Level world =this.level;
        BlockPos block =result.getBlockPos();
        if (this.getOwner() instanceof Player player) {
            double x =block.getX();
            double y =block.getY();
            double z =block.getZ();
            this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 0.75f);
            this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 1f);
            this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 1.5f);
            this.playSound(SoundEvents.GENERIC_EXPLODE, 2, 1.5f);
            novaExplode(this.level,this.power,this.getDamage(),x,y+1.5,z,player);
            List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class, new AABB(x + this.power, y + this.power, z + this.power, x - this.power, y - this.power, z - this.power));
            for (LivingEntity entities : ls) {
                if (entities != null && !(entities instanceof Player)) {
                    entities.invulnerableTime = 0;
                    entities.hurt(DamageSource.thrown(this, this.getOwner()), this.getDamage()*0.1f*this.power);
                    if (entities.hasEffect(etshtinkerEffects.novaradiation.get())){
                        entities.removeEffect(etshtinkerEffects.novaradiation.get());
                    }
                    entities.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, (int) (power*0.1), false, false), player);
                }
            }
        }
    }
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Level world =entity.level;
        if (entity!=this.getOwner()&&entity instanceof LivingEntity){
            entity.hurt(DamageSource.thrown(this, this.getOwner()), this.getDamage());
            entity.invulnerableTime=0;
            if (!level.isClientSide() && entity instanceof LivingEntity) {
                Vec3 motion = this.getDeltaMovement().normalize();
                ((LivingEntity) entity).knockback(this.getKnockback(), -motion.x, -motion.z);
            }
            if (this.getOwner() instanceof Player player) {
                double x =entity.getX();
                double y =entity.getY();
                double z =entity.getZ();
                this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 0.75f);
                this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 1f);
                this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST, 2, 1.5f);
                this.playSound(SoundEvents.GENERIC_EXPLODE, 2, 1.5f);
                novaExplode(this.level,this.power,this.getDamage(),x,y+0.5*entity.getBbHeight(),z,player);
                List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class, new AABB(x + this.power, y + this.power, z + this.power, x - this.power, y - this.power, z - this.power));
                for (LivingEntity entities : ls) {
                    if (entities != null && !(entities instanceof Player)) {
                        entities.invulnerableTime = 0;
                        entities.hurt(DamageSource.thrown(this, this.getOwner()), this.getDamage()*0.1f*this.power);
                        if (entities.hasEffect(etshtinkerEffects.novaradiation.get())){
                            entities.removeEffect(etshtinkerEffects.novaradiation.get());
                        }
                        entities.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(), 100, (int) (power*0.1), false, false), player);
                    }
                }
            }
        }
    }

}
