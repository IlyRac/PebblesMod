package com.ilyrac.pebblesandtwigs.entity;

import com.ilyrac.pebblesandtwigs.PebblesAndTwigs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final EntityType<ThrownPebbleEntity> THROWN_PEBBLE = register(
            EntityType.Builder.<ThrownPebbleEntity>of(ThrownPebbleEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
    );

    private static <T extends EntityType<?>> T register(EntityType.Builder<?> builder) {
        String name = "thrown_pebble";
        Identifier id = Identifier.fromNamespaceAndPath(PebblesAndTwigs.MOD_ID, name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        //noinspection unchecked
        return (T) Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(key));
    }

    public static void initializer() {
    }
}