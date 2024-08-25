package com.c2h6s.etshtinker.init.entityReg;

import com.c2h6s.etshtinker.Entities.alfburst;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;
import vazkii.botania.common.entity.ManaBurstEntity;

import static com.c2h6s.etshtinker.etshtinker.MOD_ID;

public class etshtinkerBotEntity {
    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(MOD_ID);
    public static final RegistryObject<EntityType<ManaBurstEntity>> ALFBURST = ENTITIES.register("alfburst",()-> EntityType.Builder.<ManaBurstEntity>of(
                    alfburst::new, MobCategory.MISC)
            .sized(0, 0)
            .updateInterval(10)
            .clientTrackingRange(6));
}
