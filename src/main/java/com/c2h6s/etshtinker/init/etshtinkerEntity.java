package com.c2h6s.etshtinker.init;

import com.c2h6s.etshtinker.init.ItemReg.etshtinkerItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import com.c2h6s.etshtinker.Entities.*;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;
import static com.c2h6s.etshtinker.util.SlashColor.getSlash;

public class etshtinkerEntity {


    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(MOD_ID);
    public static final RegistryObject<EntityType<lightningarrow>> lightningarrow = ENTITIES.register("lightningarrow",()-> EntityType.Builder.<lightningarrow>of(lightningarrow::new, MobCategory.MISC).sized(0.3F, 0.3F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<novasickleEntity>> novascikle = ENTITIES.register("novascikle", () -> EntityType.Builder.<novasickleEntity>of(novasickleEntity::new, MobCategory.MISC).sized(0.4F, 0.4F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new novasickleEntity(etshtinkerEntity.novascikle.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<exoSlashEntity>> exoslash = ENTITIES.register("exoslash", () -> EntityType.Builder.<exoSlashEntity>of(exoSlashEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(1).setCustomClientFactory((spawnEntity, world) -> new exoSlashEntity(etshtinkerEntity.exoslash.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<shadowaxeEntity>> shadowaxeentity = ENTITIES.register("shadowaxeentity", () -> EntityType.Builder.<shadowaxeEntity>of(shadowaxeEntity::new, MobCategory.MISC).sized(0.3F, 0.3F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new shadowaxeEntity(etshtinkerEntity.shadowaxeentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<plasmawaveslashentity>> plasmawaveslashEntity = ENTITIES.register("plasmawaveslashentity", () -> EntityType.Builder.of(plasmawaveslashentity::new, MobCategory.MISC).sized(8F, 1F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new plasmawaveslashentity(etshtinkerEntity.plasmawaveslashEntity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<plasmarrowentity>> plasmarrowEntity = ENTITIES.register("plasmarrowentity",()-> EntityType.Builder.<plasmarrowentity>of(plasmarrowentity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<plasmaexplosionentity>> plasmaexplosionentity = ENTITIES.register("plasmaexplosionentity", () -> EntityType.Builder.of(plasmaexplosionentity::new, MobCategory.MISC).sized(0.75F, 0.75F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new plasmaexplosionentity(etshtinkerEntity.plasmaexplosionentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<slashentity>> slashentity = ENTITIES.register("slashentity", () -> EntityType.Builder.of(slashentity::new, MobCategory.MISC).sized(0.01F, 0.01F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new slashentity(etshtinkerEntity.slashentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<phantomswordentity>> phantomswordentity = ENTITIES.register("phantomswordentity", () -> EntityType.Builder.of(phantomswordentity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new phantomswordentity(etshtinkerEntity.phantomswordentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<annihilateexplosionentity>> annihilateexplosionentity = ENTITIES.register("annihilateexplosionentity", () -> EntityType.Builder.of(annihilateexplosionentity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new annihilateexplosionentity(etshtinkerEntity.annihilateexplosionentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<enchantedswordentity>> enchantedswordentity = ENTITIES.register("enchantedswordentity", () -> EntityType.Builder.<enchantedswordentity>of(enchantedswordentity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new enchantedswordentity(etshtinkerEntity.enchantedswordentity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<NightSlashEntity>> nights_slash_entity = ENTITIES.register("nights_slash_entity", () -> EntityType.Builder.<NightSlashEntity>of(NightSlashEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new NightSlashEntity(etshtinkerEntity.nights_slash_entity.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<NightSlashEntityB>> nights_slash_entity_b = ENTITIES.register("nights_slash_entity_b", () -> EntityType.Builder.<NightSlashEntityB>of(NightSlashEntityB::new, MobCategory.MISC).sized(0.25F, 0.25F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new NightSlashEntityB(etshtinkerEntity.nights_slash_entity_b.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> PlasmaSlashType = ENTITIES.register("plasma_slash", () -> EntityType.Builder.<PlasmaSlashEntity>of((entityType, level)-> new PlasmaSlashEntity(entityType, level,getSlash(0)), MobCategory.MISC).sized(0.1F, 0.1F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new PlasmaSlashEntity(etshtinkerEntity.PlasmaSlashType.get(), world,getSlash(0))).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<VibrationAcceptor>> vibration_acceptor = ENTITIES.register("vibration_acceptor", () -> EntityType.Builder.<VibrationAcceptor>of(VibrationAcceptor::new, MobCategory.MISC).sized(0.05F, 0.05F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new VibrationAcceptor(etshtinkerEntity.vibration_acceptor.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<SculkSwordEntity>> sculk_sword = ENTITIES.register("sculk_sword", () -> EntityType.Builder.<SculkSwordEntity>of(SculkSwordEntity::new, MobCategory.MISC).sized(0.05F, 0.05F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new SculkSwordEntity(etshtinkerEntity.sculk_sword.get(), world)).setShouldReceiveVelocityUpdates(true));
    public static final RegistryObject<EntityType<SculkSwordEntity>> sonic_boom = ENTITIES.register("sonic_boom", () -> EntityType.Builder.<SculkSwordEntity>of(SculkSwordEntity::new, MobCategory.MISC).sized(0.05F, 0.05F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new SculkSwordEntity(etshtinkerEntity.sonic_boom.get(), world)).setShouldReceiveVelocityUpdates(true));




    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_red = registerPlasmaSlash("plasma_slash_red",etshtinkerEntity.plasma_slash_red,0);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_orange = registerPlasmaSlash("plasma_slash_orange",etshtinkerEntity.plasma_slash_orange,1);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_yellow = registerPlasmaSlash("plasma_slash_yellow",etshtinkerEntity.plasma_slash_yellow,2);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_lime = registerPlasmaSlash("plasma_slash_lime",etshtinkerEntity.plasma_slash_lime,3);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_green = registerPlasmaSlash("plasma_slash_green",etshtinkerEntity.plasma_slash_green,4);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_cyan = registerPlasmaSlash("plasma_slash_cyan",etshtinkerEntity.plasma_slash_cyan,5);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_blue = registerPlasmaSlash("plasma_slash_blue",etshtinkerEntity.plasma_slash_blue,6);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_purple = registerPlasmaSlash("plasma_slash_purple",etshtinkerEntity.plasma_slash_purple,7);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_anti = registerPlasmaSlash("plasma_slash_anti",etshtinkerEntity.plasma_slash_anti,8);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_dark = registerPlasmaSlash("plasma_slash_dark",etshtinkerEntity.plasma_slash_dark,9);
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> plasma_slash_rainbow = registerPlasmaSlash("plasma_slash_rainbow",etshtinkerEntity.plasma_slash_rainbow,10);
    public static RegistryObject<EntityType<PlasmaSlashEntity>> registerPlasmaSlash(String name,RegistryObject<EntityType<PlasmaSlashEntity>> Type,int index){
        return ENTITIES.register(name, () -> EntityType.Builder.<PlasmaSlashEntity>of((entityType, level)-> new PlasmaSlashEntity(entityType, level,getSlash(index)), MobCategory.MISC).sized(3F, 0.1F).setTrackingRange(4).setUpdateInterval(10).setCustomClientFactory((spawnEntity, world) -> new PlasmaSlashEntity(Type.get(), world,getSlash(index))).setShouldReceiveVelocityUpdates(true));
    }
}
