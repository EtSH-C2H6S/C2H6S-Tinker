package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.SculkSwordEntity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import com.c2h6s.etshtinker.init.etshtinkerParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class AmplitudeCharacteristic extends etshmodifieriii implements GeneralInteractionModifierHook {
    private final ResourceLocation charge = new ResourceLocation(MOD_ID, "charge");

    @Override
    public void onModifierRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(charge);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionHand == InteractionHand.MAIN_HAND){
            GeneralInteractionModifierHook.startUsing(iToolStackView,modifierEntry.getId(),player,interactionHand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int maxCharge =40;
        int maxMaxCharge = 100*modifier.getLevel();
        int useTime =this.getUseDuration(tool,modifier)-timeLeft;
        if (entity instanceof ServerPlayer player){
            ServerLevel level = player.getLevel();
            ModDataNBT nbt =tool.getPersistentData();
            if (nbt.getInt(charge)<=maxMaxCharge&&player.totalExperience>0){
                player.giveExperiencePoints(-1);
                nbt.putInt(charge,nbt.getInt(charge)+1);
                if (nbt.getInt(charge)<maxCharge) {
                    level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY()+player.getBbHeight(), player.getZ(), 1, 0.25, 0.5, 0.25, 0.25);
                }else if (nbt.getInt(charge)<=maxMaxCharge){
                    level.sendParticles(ParticleTypes.SCULK_CHARGE_POP, player.getX(), player.getY()+player.getBbHeight(), player.getZ(), 1, 0.25, 0.5, 0.25, 0.1);
                }
            }
            Vec3 offset = new Vec3(Math.cos((player.yBodyRot-90)*Math.PI/180),0,Math.sin((player.yBodyRot-90)*Math.PI/180)).cross(new Vec3(0,-1,0)).scale(0.25);
            Vec3 direction = new Vec3(Math.cos((player.yBodyRot-90)*Math.PI/180),0.4,Math.sin((player.yBodyRot-90)*Math.PI/180)).scale(1.5);
            Vec3 position = new Vec3(player.getX(),player.getEyeY(),player.getZ()).add(offset);
            position.add(direction);
            int amount = Math.min(useTime/5,8);
            for(int i =0;i<amount;i++){
                double x =direction.x*(i+1) +position.x+0.1*EtSHrnd().nextFloat() -0.05;
                double y =direction.y*(i+1) +position.y+0.1*EtSHrnd().nextFloat() -0.05;
                double z =direction.z*(i+1) +position.z+0.1*EtSHrnd().nextFloat() -0.05;
                level.sendParticles(etshtinkerParticleType.sonic_energy.get(),x,y,z,1,0,0,0,0);
            }
        }
    }


    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int useTime =this.getUseDuration(tool,modifier)-timeLeft;
        if (useTime>=40&&entity instanceof ServerPlayer player){
            float angle = Mth.lerp(1, player.yRotO, player.getYRot());
            double angleSin =  Math.sin((angle-90)*Math.PI/180);
            double angleCos =  Math.cos((angle-90)*Math.PI/180);
            Vec3 offset = new Vec3(angleCos,0,angleSin).cross(new Vec3(0,-1,0)).scale(0.25);
            Vec3 entDirect = new Vec3(angleCos,0.4,angleSin).scale(1.5);
            ServerLevel serverLevel =player.getLevel();
            SculkSwordEntity swordEntity =new SculkSwordEntity(etshtinkerEntity.sculk_sword.get(),serverLevel);
            swordEntity.charge=tool.getPersistentData().getInt(charge);
            swordEntity.offset =offset;
            swordEntity.Rawdirection =entDirect;
            swordEntity.setOwner(player);
            swordEntity.tool = (ToolStack) tool;
            swordEntity.damage = (float) tool.getPersistentData().getInt(charge) /2;
            serverLevel.addFreshEntity(swordEntity);
            tool.getPersistentData().putInt(charge,0);
            OffhandCooldownTracker.swingHand(player,InteractionHand.MAIN_HAND,false);
        }
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.SPEAR;
    }
}
