package com.c2h6s.etshtinker.Modifiers.Armor;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;
import java.util.List;
import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;
import static com.c2h6s.etshtinker.util.getMainOrOff.getMainLevel;
import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class mindprotection extends etshmodifieriii {
    public static boolean enabled2 = ModList.get().isLoaded("cofh_core");
    private static final TinkerDataCapability.TinkerDataKey<Integer> key = TConstruct.createKey("mindprotection");
    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(new ArmorLevelModule(key, false, (TagKey)null));
    }
    public boolean isNoLevels() {
        return true;
    }
    private final ResourceLocation dpreventcd2 = new ResourceLocation(MOD_ID, "dpreventcd2");
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(dpreventcd2);
    }
    public mindprotection(){
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
        MinecraftForge.EVENT_BUS.addListener(this::livingdeathevent);
    }
    private void livingdeathevent(LivingDeathEvent event) {
        LivingEntity entity =event.getEntity();
        entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                if (entity instanceof Player player ) {
                    List<ItemStack> equipments = player.getInventory().armor;
                    for (ItemStack equipment : equipments) {
                        if (equipment.getItem() instanceof ModifiableArmorItem) {
                            ToolStack tool = ToolStack.from(equipment);
                            if (tool.getModifierLevel(this) > 0 && tool.getPersistentData().getInt(dpreventcd2) == 0) {
                                ModDataNBT toolData = tool.getPersistentData();
                                if (toolData.getInt(dpreventcd2) == 0) {
                                    toolData.putInt(dpreventcd2, 1800);
                                    event.setCanceled(true);
                                    player.deathTime = -2;
                                    player.fallDistance = 0;
                                    player.setHealth(player.getMaxHealth() * 0.5f);
                                    player.invulnerableTime = 100;
                                    entity.sendSystemMessage(Component.translatable("etshtinker.message.death_prevent").withStyle(ChatFormatting.AQUA));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void livinghurtevent(LivingHurtEvent event) {
        Entity entity1 = event.getSource().getEntity();
        SecureRandom random = EtSHrnd();
        LivingEntity entity =event.getEntity();
        if (entity==entity1){
            return;
        }
        entity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(key, 0);
            if (level > 0) {
                event.setAmount(event.getAmount()*0.25f);
                if (event.getSource().isBypassArmor()){
                    event.setCanceled(true);
                }
                else {
                    int i = random.nextInt(100);
                    if (entity instanceof Player player) {
                        if ( i > 40) {
                            event.setCanceled(true);
                            player.invulnerableTime = 40;
                            if (i > 60 && entity1 instanceof LivingEntity attacker) {
                                attacker.invulnerableTime = 0;
                                attacker.hurt(DamageSource.thorns(player), event.getAmount() * 5);
                                attacker.invulnerableTime = 0;
                            }
                            if (i > 75 && entity1 instanceof LivingEntity attacker) {
                                attacker.hurt(DamageSource.thorns(player), event.getAmount() * 20);
                                attacker.invulnerableTime = 0;
                            }
                        }
                    }
                }
            }
        });
    }

    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack itemStack) {
        ModDataNBT toolData = tool.getPersistentData();
        if(holder instanceof Player player && toolData.getInt(dpreventcd2) > 0){
            toolData.putInt(dpreventcd2,toolData.getInt(dpreventcd2)-1);
        }
        if (holder instanceof Player player&&isCorrectSlot){
            int modilvl2 = getMainLevel(player,this);
            if (enabled2) {
                player.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(), 100, modilvl2, false, false));
                player.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(), 100, modilvl2, false, false));
            }
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400,modilvl2,false,false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,100,9*modilvl2,false,false));
            if (player.hasEffect(MobEffects.SLOW_FALLING)){
                player.removeEffect(MobEffects.SLOW_FALLING);
            }
        }
        Collection<MobEffectInstance> harmeffect;
        harmeffect = holder.getActiveEffects();
        for (int i = 0; i < harmeffect.size(); i++){
            MobEffectInstance effect = harmeffect.stream().toList().get(i);
            MobEffect harm = effect.getEffect();
            MobEffect neut = effect.getEffect();
            if (harm.getCategory() == MobEffectCategory.HARMFUL){
                holder.removeEffect(harm);
            }
            if (neut.getCategory() == MobEffectCategory.NEUTRAL){
                holder.removeEffect(neut);
            }
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT toolData = tool.getPersistentData();
            list.add(applyStyle(Component.translatable("etshtinker.modifier.tooltip.deadpreventcd").append(String.valueOf(toolData.getInt(dpreventcd2)+""))));
        };
    }
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry) {
        ModDataNBT toolData =tool.getPersistentData();
        if (toolData.getInt(dpreventcd2)>0) {
            return Component.translatable(this.getDisplayName().getString() + "  " ).append(Component.translatable( "etshtinker.modifier.tooltip.deadpreventcd" ).append( String.valueOf(toolData.getInt(dpreventcd2))).withStyle(this.getDisplayName().getStyle()));
        }
        else return Component.translatable(this.getDisplayName().getString() + "  " ).append(Component.translatable( "etshtinker.modifier.tooltip.deadpreventready" ).withStyle(this.getDisplayName().getStyle()));
    }
}
