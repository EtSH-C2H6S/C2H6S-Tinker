package com.c2h6s.etshtinker.Entities;

import com.c2h6s.etshtinker.init.etshtinkerEffects;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import slimeknights.tconstruct.gadgets.entity.shuriken.ShurikenEntityBase;

import java.security.SecureRandom;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.ParticleChainUtil.*;


public class shadowaxeEntity extends ShurikenEntityBase {
    public float baseDamage;
    public int time =0;

    public float setDamage(float damage){
        baseDamage =damage;
        return damage;
    }
    public shadowaxeEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(etshtinkerEntity.shadowaxeentity.get(), world);
    }
    public shadowaxeEntity(EntityType<? extends ShurikenEntityBase> type, double x, double y, double z, Level worldIn){
        super(type, x, y, z, worldIn);
    }
    public shadowaxeEntity(EntityType<? extends shadowaxeEntity> type, Level world) {
        super(type, world);
    }
    public shadowaxeEntity(Level worldIn, LivingEntity throwerIn) {
        super(etshtinkerEntity.shadowaxeentity.get(), throwerIn, worldIn);
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
        return new ItemStack(etshtinkerItems.shadowaxe.get());
    }

    @Override
    public float getDamage() {
        return baseDamage;
    }

    @Override
    public float getKnockback() {
        return 5;
    }

    @Override
    public void tick() {
        this.noPhysics=true;
        this.noCulling=true;
        super.tick();
        List<LivingEntity> ls =this.level.getEntitiesOfClass(LivingEntity.class,new AABB(this.getX()+16,this.getY()+16,this.getZ()+16,this.getX()-16,this.getY()-16,this.getZ()-16));
        if (!this.isNoGravity()){
            this.setNoGravity(true);
        }
        time++;
        if (time>60){
            this.remove(RemovalReason.KILLED);
        }
        if (time>=2){
            this.setDeltaMovement(this.getDeltaMovement().scale(0.90));
        }
        if (!ls.isEmpty()){
            int i=0;
            for (LivingEntity entity : ls) {
                SecureRandom RANDOM =EtSHrnd();
                if (entity != null && !(entity instanceof Player)&&i<4&&RANDOM.nextInt(5)==0&&this.getOwner() instanceof Player player) {
                    entity.invulnerableTime=0;
                    entity.hurt(DamageSource.playerAttack(player),this.getDamage());
                    entity.invulnerableTime=0;
                    if (entity.hasEffect(etshtinkerEffects.novaradiation.get())){
                        entity.removeEffect(etshtinkerEffects.novaradiation.get());
                    }
                    entity.forceAddEffect(new MobEffectInstance(etshtinkerEffects.novaradiation.get(),100,10,false,false),player);
                    Level level1 =this.level;
                    if (level1!=null){
                        summonLaserFromTo(level1,this.getId(),entity.getId());
                    }
                    this.playSound(SoundEvents.FIREWORK_ROCKET_BLAST_FAR,1,2.25f);
                    i++;
                }
            }
        }
    }
}
