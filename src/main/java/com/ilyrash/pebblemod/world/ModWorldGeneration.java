package com.ilyrash.pebblemod.world;

import com.ilyrash.pebblemod.Pebblemod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {

    public static void initialize() {
        // Wait until after the registries are loaded to add our features
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client) { // Only on server/dedicated server
                registerBiomeModifications();
            }
        });
    }

    private static void registerBiomeModifications() {
        // Stone pebbles in all overworld biomes (common) except deserts, badlands, and snowy biomes
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(BiomeSelectors.excludeByKey(
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("desert")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("badlands")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("wooded_badlands")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("eroded_badlands"))
                        ))
                        .and(BiomeSelectors.excludeByKey( // Exclude snowy biomes
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_plains")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("ice_spikes")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_taiga")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_beach")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_slopes")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("jagged_peaks")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_peaks")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_river")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_ocean")),
                                RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("deep_frozen_ocean"))
                        )),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(Pebblemod.MOD_ID, "stone_pebbles_common"))
        );

        // Snow stone pebbles in snowy biomes
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_plains")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("ice_spikes")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_taiga")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_beach")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("snowy_slopes")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("jagged_peaks")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_peaks")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_river")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("frozen_ocean")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("deep_frozen_ocean"))
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(Pebblemod.MOD_ID, "snow_stone_pebbles"))
        );

        // Stone pebbles in deserts/badlands (rare) - unchanged
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("desert")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("badlands")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("wooded_badlands")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("eroded_badlands"))
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(Pebblemod.MOD_ID, "stone_pebbles_rare_desert_badlands"))
        );

        // Sandstone pebbles only in deserts - unchanged
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("desert"))
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(Pebblemod.MOD_ID, "sandstone_pebbles"))
        );

        // Red sandstone pebbles only in badlands - unchanged
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("badlands")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("wooded_badlands")),
                        RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla("eroded_badlands"))
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        Identifier.of(Pebblemod.MOD_ID, "red_sandstone_pebbles"))
        );

        Pebblemod.LOGGER.info("Pebble world generation registered!");
    }
}