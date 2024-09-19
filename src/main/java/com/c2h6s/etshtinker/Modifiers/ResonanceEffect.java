package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.VibrationAcceptor;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.capability.IDampenCapability;
import com.c2h6s.etshtinker.capability.etshCap;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerToolStats;
import com.c2h6s.etshtinker.util.Cap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;
import java.util.Optional;

import static com.c2h6s.etshtinker.util.vecCalc.getMold;


public class ResonanceEffect extends etshmodifieriii implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.TOOL_STATS);
    }
    public ResonanceEffect(){
        MinecraftForge.EVENT_BUS.addListener(this::LivingHurt);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (isCorrectSlot&&holder instanceof ServerPlayer player&&player.level.getGameTime()%10==0){
            VibrationAcceptor acceptor = new VibrationAcceptor(etshtinkerEntity.vibration_acceptor.get(),player.level);
            acceptor.setOwner(player);
            acceptor.setPos(player.getX(),player.getEyeY(),player.getZ());
            player.level.addFreshEntity(acceptor);
        }
    }

    private void LivingHurt(LivingHurtEvent event) {
        if (event.getSource().getMsgId().equals("sonic_boom")){
            float red =1;
            List<EquipmentSlot> Slots =List.of(EquipmentSlot.CHEST,EquipmentSlot.FEET,EquipmentSlot.HEAD,EquipmentSlot.LEGS,EquipmentSlot.MAINHAND,EquipmentSlot.MAINHAND);
            Entity entity = event.getEntity();
            if(entity instanceof LivingEntity living) {
                for (EquipmentSlot slot : Slots) {
                    ItemStack stack =living.getItemBySlot(slot);
                    Optional<IDampenCapability> capability = Cap.getCapability(stack, etshCap.DAMPEN_CAPABILITY,null).resolve();
                    if(capability.isPresent()){
                        red+= capability.get().getDampenCap();
                    }
                }
            }
            if (red>1) {
                event.setAmount(event.getAmount() / red);
            }
        }
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        etshtinkerToolStats.DAMPEN.add(builder,1f);
        etshtinkerToolStats.VIBR.add(builder,1f);
    }

    @Override
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity entity =context.getLivingTarget();
        LivingEntity entity1 =context.getAttacker();
        if (entity != null){
            entity.invulnerableTime=0;
            entity.hurt(DamageSource.sonicBoom(entity1),damageDealt*0.5f);
            if (entity.level instanceof ServerLevel level){
                level.sendParticles(ParticleTypes.SONIC_BOOM,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),1,0,0,0,0);
            }
        }
    }

    @Override
    public boolean modifierOnProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target!=null&&attacker!=null&&projectile instanceof AbstractArrow arrow){
            target.hurt(DamageSource.sonicBoom(attacker),0.5f*(float) arrow.getBaseDamage()*(float)getMold(arrow.getDeltaMovement()));
            if (target.level instanceof ServerLevel level){
                level.sendParticles(ParticleTypes.SONIC_BOOM,target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),1,0,0,0,0);
            }
        }
        return false;
    }

}
