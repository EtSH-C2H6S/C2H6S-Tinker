package com.c2h6s.etshtinker.Entities;


import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.BiConsumer;

public class VibrationAcceptor extends ItemProjectile implements VibrationListener.VibrationListenerConfig {
    public int cd;
    private final DynamicGameEventListener<VibrationListener> dynamicGameEventListener;
    public final String freq ="etsh.vibr";
    private static final Logger LOGGER = LogUtils.getLogger();
    public VibrationAcceptor(EntityType<? extends ItemProjectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationListener(new EntityPositionSource(this, this.getEyeHeight()), 16, this, (VibrationListener.ReceivingEvent)null, 0.0F, 0));
    }


    @Override
    public void tick() {
        super.tick();
        if (this.level instanceof ServerLevel serverLevel){
            this.dynamicGameEventListener.getListener().tick(serverLevel);
        }
        if (this.tickCount>=40){
            if (this.getOwner() instanceof Player player&&this.tickCount==40) {
                AABB aabb =new AABB(this.getX()-3,this.getY()-3,this.getZ()-3,this.getX()+3,this.getY()+3,this.getZ()+3);
                List<VibrationAcceptor> list = this.level.getEntitiesOfClass(VibrationAcceptor.class,aabb);
                for (VibrationAcceptor acceptor:list){
                    if (acceptor!=null&&acceptor!=this){
                        CompoundTag nbt =acceptor.getPersistentData();
                        CompoundTag nbtThis =this.getPersistentData();
                        if (nbt.getFloat(freq)>0){
                            nbtThis.putFloat(freq,nbtThis.getFloat(freq)+nbt.getFloat(freq));
                        }
                        acceptor.discard();
                    }
                }
                float amount = this.getPersistentData().getFloat(freq);
                int level = (int) (amount / 20);
                if (level > 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION,level*20,level-1,false,false));
                    if (level>1){
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,level*20, Math.min(7,level-1),false,false));
                        if (level>3){
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,level*20,level-2,false,false));
                            if (level>5){
                                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,level*20,level-4,false,false));
                                if (level>7){
                                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,level*20,level-6,false,false));
                                }
                            }
                        }
                    }
                }
            }
            this.discard();
        }
        if (this.cd>0){
            this.cd--;
        }
        if (this.getOwner() instanceof Player player) {
            this.setPos(player.getX(), player.getEyeY(), player.getZ());
        }

    }


    @Override
    public void move(MoverType p_19973_, Vec3 p_19974_) {
        if (this.getOwner() instanceof Player player) {
            this.setPos(player.getX(), player.getEyeY(), player.getZ());
            super.move(p_19973_, p_19974_);
        }
    }

    @Override
    public TagKey<GameEvent> getListenableEvents() {
        return GameEventTags.WARDEN_CAN_LISTEN;
    }
    public boolean canTriggerAvoidVibration() {
        return true;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public boolean shouldListen(ServerLevel serverLevel, GameEventListener listener, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context) {
        return this.level==serverLevel;
    }
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_219413_) {
        Level level = this.level;
        if (level instanceof ServerLevel serverlevel) {
            p_219413_.accept(this.dynamicGameEventListener, serverlevel);
        }

    }
    public void addAdditionalSaveData(CompoundTag p_219434_) {
        super.addAdditionalSaveData(p_219434_);
        VibrationListener.codec(this).encodeStart(NbtOps.INSTANCE, this.dynamicGameEventListener.getListener()).resultOrPartial(LOGGER::error).ifPresent((p_219418_) -> {
            p_219434_.put("listener", p_219418_);
        });
    }

    @Override
    public void onSignalReceive(ServerLevel serverLevel, GameEventListener listener, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity1, float amount) {
        if (this.getOwner() instanceof ServerPlayer player) {
            this.getPersistentData().putFloat(freq,this.getPersistentData().getFloat(freq)+amount);
        }
    }

}
