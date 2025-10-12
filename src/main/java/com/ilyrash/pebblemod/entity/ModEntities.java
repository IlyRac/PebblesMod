package com.ilyrash.pebblemod.entity;

import com.ilyrash.pebblemod.Pebblemod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ThrownPebbleEntity> THROWN_PEBBLE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Pebblemod.MOD_ID, "thrown_pebble"),
            EntityType.Builder.<ThrownPebbleEntity>create(ThrownPebbleEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pebblemod.MOD_ID, "thrown_pebble")))
    );

    public static void initialize() {
    }
}