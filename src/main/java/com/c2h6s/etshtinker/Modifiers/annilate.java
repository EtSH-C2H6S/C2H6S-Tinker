package com.c2h6s.etshtinker.Modifiers;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.*;
import com.c2h6s.etshtinker.Modifiers.modifiers.*;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;



public class annilate extends etshmodifieriii  {


    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation des = new ResourceLocation(MOD_ID, "des");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(des);
    }

    @Override
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof FakePlayer){
            return amount;
        }
        Entity attacker =source.getEntity();
        LivingEntity target =context.getEntity();
        if (target instanceof Player&&attacker instanceof LivingEntity living&&tool.getModifierLevel(this)>0){
            tool.getPersistentData().putInt(des, 114514);
            living.getPersistentData().putInt("annih_countdown",60);
            target.getPersistentData().putInt("annih_countdown",60);
            living.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
        }
        return 0;
    }

    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback){
        if (context.getAttacker() instanceof FakePlayer){
            return knockback;
        }
        LivingEntity attacker =context.getAttacker();
        Entity target =context.getTarget();
        if (target instanceof LivingEntity living&&tool.getModifierLevel(this)>0){
            tool.setDamage(Integer.MAX_VALUE);
            tool.getPersistentData().putInt(des, 114514);
            living.getPersistentData().putInt("annih_countdown",60);
            attacker.getPersistentData().putInt("annih_countdown",60);
            living.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            attacker.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
        }
        return knockback;
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return 1;
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (livingEntity!=null&&isCorrectSlot&&!tool.isBroken()&&modifier.getLevel()>0&&tool.getPersistentData().getInt(des)>0){
            destroyTool((ToolStack) tool);
        }
    }
    public void destroyTool(ToolStack tool){
        int length = tool.getMaterials().size();
        List<MaterialVariant> list=new ArrayList<>(List.of());
        for (int i=0;i<length;i++){
            list.add(MaterialVariant.of(MaterialVariantId.create(new MaterialId("etshtinker:annihilate_ember"),"default")));
        }
        MaterialNBT nbt = new MaterialNBT(list);
        tool.setMaterials(nbt);
        tool.setDamage(0);
        tool.rebuildStats();
    }

}
