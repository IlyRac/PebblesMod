package com.ilyrac.pebblesandtwigs.block;

import com.ilyrac.pebblesandtwigs.PebblesAndTwigs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModBlocks {
    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PebblesAndTwigs.MOD_ID, name));
        Block block = blockFactory.apply(settings.setId(blockKey));

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static final Block ANDESITE_PEBBLE = register("andesite_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block BLACKSTONE_PEBBLE = register("blackstone_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.GILDED_BLACKSTONE).noOcclusion()
    );

    public static final Block DEEPSLATE_PEBBLE = register("deepslate_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.DEEPSLATE).noOcclusion()
    );

    public static final Block DIORITE_PEBBLE = register("diorite_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block END_STONE_PEBBLE = register("end_stone_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block GRANITE_PEBBLE = register("granite_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block RED_SANDSTONE_PEBBLE = register("red_sandstone_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block SANDSTONE_PEBBLE = register("sandstone_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block STONE_PEBBLE = register("stone_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.STONE).noOcclusion()
    );

    public static final Block TUFF_PEBBLE = register("tuff_pebble",
            PebbleBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.TUFF).noOcclusion()
    );

    /*----------------------------------------------------------------------*/

    public static final Block ACACIA_TWIG = register("acacia_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block BIRCH_TWIG = register("birch_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block CHERRY_TWIG = register("cherry_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.CHERRY_WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block CRIMSON_TWIG = register("crimson_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.NETHER_WOOD).noOcclusion()
    );

    public static final Block DARK_OAK_TWIG = register("dark_oak_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block JUNGLE_TWIG = register("jungle_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block MANGROVE_TWIG = register("mangrove_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block OAK_TWIG = register("oak_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block PALE_OAK_TWIG = register("pale_oak_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block SPRUCE_TWIG = register("spruce_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block WARPED_TWIG = register("warped_twig",
            TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.NETHER_WOOD).noOcclusion()
    );

    /*----------------------------------------------------------------------*/

    public static final Block STRIPPED_ACACIA_TWIG = register("stripped_acacia_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_BIRCH_TWIG = register("stripped_birch_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_CHERRY_TWIG = register("stripped_cherry_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.CHERRY_WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_CRIMSON_TWIG = register("stripped_crimson_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.NETHER_WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_DARK_OAK_TWIG = register("stripped_dark_oak_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_JUNGLE_TWIG = register("stripped_jungle_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_MANGROVE_TWIG = register("stripped_mangrove_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_OAK_TWIG = register("stripped_oak_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_PALE_OAK_TWIG = register("stripped_pale_oak_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_SPRUCE_TWIG = register("stripped_spruce_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD).noOcclusion().ignitedByLava()
    );

    public static final Block STRIPPED_WARPED_TWIG = register("stripped_warped_twig", TwigBlock::new,
            BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.NETHER_WOOD).noOcclusion()
    );

    /*----------------------------------------------------------------------*/

    private static final Map<Block, Block> STRIPPABLES = new HashMap<>();

    private static void registerStrippable(Block bark, Block stripped) {
        STRIPPABLES.put(bark, stripped);
    }

    public static Block getStripped(Block bark) {
        return STRIPPABLES.get(bark);
    }

    /*----------------------------------------------------------------------*/

    public static void initializer() {
        registerStrippable(ACACIA_TWIG, STRIPPED_ACACIA_TWIG);
        registerStrippable(BIRCH_TWIG, STRIPPED_BIRCH_TWIG);
        registerStrippable(CHERRY_TWIG, STRIPPED_CHERRY_TWIG);
        registerStrippable(CRIMSON_TWIG, STRIPPED_CRIMSON_TWIG);
        registerStrippable(DARK_OAK_TWIG, STRIPPED_DARK_OAK_TWIG);
        registerStrippable(JUNGLE_TWIG, STRIPPED_JUNGLE_TWIG);
        registerStrippable(MANGROVE_TWIG, STRIPPED_MANGROVE_TWIG);
        registerStrippable(OAK_TWIG, STRIPPED_OAK_TWIG);
        registerStrippable(PALE_OAK_TWIG, STRIPPED_PALE_OAK_TWIG);
        registerStrippable(SPRUCE_TWIG, STRIPPED_SPRUCE_TWIG);
        registerStrippable(WARPED_TWIG, STRIPPED_WARPED_TWIG);
    }
}
