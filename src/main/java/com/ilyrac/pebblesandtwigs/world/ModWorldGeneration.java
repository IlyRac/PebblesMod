package com.ilyrac.pebblesandtwigs.world;

import com.ilyrac.pebblesandtwigs.PebblesAndTwigs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Predicate;

public class ModWorldGeneration {

    public static void initializer() {
        Predicate<BiomeSelectionContext> isStrictlySnowy = BiomeSelectors.includeByKey(
                Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES, Biomes.SNOWY_TAIGA,
                Biomes.GROVE, Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS,
                Biomes.FROZEN_PEAKS, Biomes.SNOWY_BEACH, Biomes.FROZEN_RIVER
        );

        Predicate<BiomeSelectionContext> isWindswept = BiomeSelectors.includeByKey(
                Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_GRAVELLY_HILLS
        );

        Predicate<BiomeSelectionContext> isWater = BiomeSelectors.includeByKey(
                Biomes.RIVER, Biomes.FROZEN_RIVER,
                Biomes.OCEAN, Biomes.DEEP_OCEAN,
                Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN,
                Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN,
                Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.WARM_OCEAN
        );

        Predicate<BiomeSelectionContext> validSurface = isStrictlySnowy.negate().or(isWindswept);
        Predicate<BiomeSelectionContext> isOverworld = BiomeSelectors.tag(BiomeTags.IS_OVERWORLD);

        registerPebbles(isOverworld, validSurface);
        registerUnderwaterPebbles(isWater);
        registerTwigs(validSurface, isWindswept);

        addFeature("end_stone_pebble_placed", BiomeSelectors.foundInTheEnd(), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("blackstone_pebble_placed", BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("crimson_twig_placed", BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("warped_twig_placed", BiomeSelectors.includeByKey(Biomes.WARPED_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    private static void registerPebbles(Predicate<BiomeSelectionContext> isOverworld, Predicate<BiomeSelectionContext> validSurface) {
        var isDesert = BiomeSelectors.includeByKey(Biomes.DESERT);
        var isBadlands = BiomeSelectors.tag(BiomeTags.IS_BADLANDS);
        var isStandard = isOverworld.and(isDesert.negate()).and(isBadlands.negate());

        addFeature("stone_pebble_above", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("sandstone_pebble_above", isDesert.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("red_sandstone_pebble_above", isBadlands.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("andesite_pebble_avg", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("granite_pebble_avg", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("diorite_pebble_avg", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("stone_pebble_avg", isDesert.or(isBadlands).and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("deepslate_pebble_below", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("tuff_pebble_below", isStandard.and(validSurface), GenerationStep.Decoration.UNDERGROUND_DECORATION);

        addFeature("stone_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("andesite_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("granite_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("diorite_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("deepslate_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addFeature("tuff_pebble_cave", isOverworld, GenerationStep.Decoration.UNDERGROUND_DECORATION);
    }

    private static void registerTwigs(Predicate<BiomeSelectionContext> surface, Predicate<BiomeSelectionContext> windswept) {
        var isOakMain = BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.SWAMP, Biomes.WOODED_BADLANDS, Biomes.DESERT).and(surface);
        var isSpruceMain = BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA).and(surface).or(windswept);
        var isBirchMain = BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST).and(surface);
        var isSavanna = BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA).and(surface);
        var isNeutral = BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW, Biomes.STONY_SHORE, Biomes.STONY_PEAKS).and(surface);
        var isOtherForest = isSpruceMain.or(isBirchMain).or(isSavanna).or(BiomeSelectors.tag(BiomeTags.IS_JUNGLE));

        addFeature("oak_twig_above", isOakMain, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("oak_twig_avg", isNeutral, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("oak_twig_below", isOtherForest, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("spruce_twig_above", isSpruceMain, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("birch_twig_above", isBirchMain, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("acacia_twig_above", isSavanna, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("jungle_twig_above", BiomeSelectors.tag(BiomeTags.IS_JUNGLE).and(surface), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("dark_oak_twig_above", BiomeSelectors.includeByKey(Biomes.DARK_FOREST).and(surface), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("pale_oak_twig_above", BiomeSelectors.includeByKey(Biomes.PALE_GARDEN).and(surface), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("cherry_twig_above", BiomeSelectors.includeByKey(Biomes.CHERRY_GROVE).and(surface), GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("mangrove_twig_above", BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP).and(surface), GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    private static void registerUnderwaterPebbles(Predicate<BiomeSelectionContext> isWater) {
        addFeature("stone_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("andesite_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("granite_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("diorite_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("deepslate_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
        addFeature("tuff_pebble_underwater", isWater, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    private static void addFeature(String name, Predicate<BiomeSelectionContext> selector, GenerationStep.Decoration step) {
        ResourceKey<PlacedFeature> featureKey = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(PebblesAndTwigs.MOD_ID, name));
        BiomeModifications.addFeature(selector, step, featureKey);
    }
}