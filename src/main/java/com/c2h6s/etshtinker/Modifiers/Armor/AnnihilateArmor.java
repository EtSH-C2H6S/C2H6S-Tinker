package com.c2h6s.etshtinker.Modifiers.Armor;

import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class AnnihilateArmor extends etshmodifieriii {
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation des = new ResourceLocation(MOD_ID, "des");
    @Override
    public float modifierDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        Entity attacker =source.getEntity();
        LivingEntity target =context.getEntity();
        if (target instanceof ServerPlayer &&!(target instanceof FakePlayer) &&attacker instanceof LivingEntity living){
            tool.getPersistentData().putInt(des, 114514);
            living.getPersistentData().putInt("annih_countdown",60);
            target.getPersistentData().putInt("annih_countdown",60);
            living.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            target.forceAddEffect(new MobEffectInstance(etshtinkerEffects.annihilating.get(),60,0,false,false),attacker);
            return 0;
        }
        return amount;
    }
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if (livingEntity!=null&&modifier.getLevel()>0&&tool.getPersistentData().getInt(des)>0){
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
