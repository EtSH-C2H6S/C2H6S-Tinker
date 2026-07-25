package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Modifiers.modifiers.EtshModifieriii;
import com.c2h6s.etshtinker.etshtinker;
import com.c2h6s.etshtinker.util.slotUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

@Mod.EventBusSubscriber
public class extralight extends EtshModifieriii implements DamageBlockModifierHook {
    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_EXTRA_LIGHT = TinkerDataCapability.TinkerDataKey.of(etshtinker.getResourceLoc("extra_light"));
    public static boolean ATTACK_LOCK = false;

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.DAMAGE_BLOCK);
        builder.addModule(new ArmorLevelModule(KEY_EXTRA_LIGHT,false, TinkerTags.Items.MODIFIABLE));
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingAttackLowest(LivingAttackEvent event){
        if (!event.isCanceled()&&!ATTACK_LOCK){
            event.getEntity().getCapability(TinkerDataCapability.CAPABILITY).ifPresent(cap->{
                if (cap.get(KEY_EXTRA_LIGHT,0)>0){
                    ATTACK_LOCK = true;
                    if (EtSHrnd().nextFloat()<=0.75f&&MinecraftForge.EVENT_BUS.post(new LivingAttackEvent(event.getEntity(),event.getSource(),event.getAmount())))
                        event.setCanceled(true);
                    ATTACK_LOCK = false;
                }
            });
        }
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        if(isCorrectSlot&&!tool.isBroken()&&holder!=null){
            int lvl = slotUtil.getAllTotalLevel(holder,this.getId());
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,lvl+1,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,400,lvl*3,false,false));
        }
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry entry, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        return EtSHrnd().nextFloat() <0.25f;
    }
}
