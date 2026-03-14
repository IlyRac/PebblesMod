package com.ilyrac.pebblesandtwigs.item;

import com.ilyrac.pebblesandtwigs.PebblesAndTwigs;
import com.ilyrac.pebblesandtwigs.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static <T extends Item> T register(String name,
                                              Function<Item.Properties, T> itemFactory,
                                              Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(PebblesAndTwigs.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static final Item ANDESITE_PEBBLE = register("andesite_pebble",
            properties -> new PebbleItem(ModBlocks.ANDESITE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item BLACKSTONE_PEBBLE = register("blackstone_pebble",
            properties -> new PebbleItem(ModBlocks.BLACKSTONE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item DEEPSLATE_PEBBLE = register("deepslate_pebble",
            properties -> new PebbleItem(ModBlocks.DEEPSLATE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item DIORITE_PEBBLE = register("diorite_pebble",
            properties -> new PebbleItem(ModBlocks.DIORITE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item END_STONE_PEBBLE = register("end_stone_pebble",
            properties -> new PebbleItem(ModBlocks.END_STONE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item GRANITE_PEBBLE = register("granite_pebble",
            properties -> new PebbleItem(ModBlocks.GRANITE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item RED_SANDSTONE_PEBBLE = register("red_sandstone_pebble",
            properties -> new PebbleItem(ModBlocks.RED_SANDSTONE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item SANDSTONE_PEBBLE = register("sandstone_pebble",
            properties -> new PebbleItem(ModBlocks.SANDSTONE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item STONE_PEBBLE = register("stone_pebble",
            properties -> new PebbleItem(ModBlocks.STONE_PEBBLE, properties),
            new Item.Properties()
    );
    public static final Item TUFF_PEBBLE = register("tuff_pebble",
            properties -> new PebbleItem(ModBlocks.TUFF_PEBBLE, properties),
            new Item.Properties()
    );

    /*----------------------------------------------------------------------*/

    public static final Item ACACIA_TWIG = register("acacia_twig",
            properties -> new BlockItem(ModBlocks.ACACIA_TWIG, properties),
            new Item.Properties());

    public static final Item BIRCH_TWIG = register("birch_twig",
            properties -> new BlockItem(ModBlocks.BIRCH_TWIG, properties),
            new Item.Properties());

    public static final Item CHERRY_TWIG = register("cherry_twig",
            properties -> new BlockItem(ModBlocks.CHERRY_TWIG, properties),
            new Item.Properties());

    public static final Item CRIMSON_TWIG = register("crimson_twig",
            properties -> new BlockItem(ModBlocks.CRIMSON_TWIG, properties),
            new Item.Properties());

    public static final Item DARK_OAK_TWIG = register("dark_oak_twig",
            properties -> new BlockItem(ModBlocks.DARK_OAK_TWIG, properties),
            new Item.Properties());

    public static final Item JUNGLE_TWIG = register("jungle_twig",
            properties -> new BlockItem(ModBlocks.JUNGLE_TWIG, properties),
            new Item.Properties());

    public static final Item MANGROVE_TWIG = register("mangrove_twig",
            properties -> new BlockItem(ModBlocks.MANGROVE_TWIG, properties),
            new Item.Properties());

    public static final Item OAK_TWIG = register("oak_twig",
            properties -> new BlockItem(ModBlocks.OAK_TWIG, properties),
            new Item.Properties());

    public static final Item PALE_OAK_TWIG = register("pale_oak_twig",
            properties -> new BlockItem(ModBlocks.PALE_OAK_TWIG, properties),
            new Item.Properties());

    public static final Item SPRUCE_TWIG = register("spruce_twig",
            properties -> new BlockItem(ModBlocks.SPRUCE_TWIG, properties),
            new Item.Properties());

    public static final Item WARPED_TWIG = register("warped_twig",
            properties -> new BlockItem(ModBlocks.WARPED_TWIG, properties),
            new Item.Properties());

    /*----------------------------------------------------------------------*/

    public static final Item STRIPPED_ACACIA_TWIG = register("stripped_acacia_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_ACACIA_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_BIRCH_TWIG = register("stripped_birch_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_BIRCH_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_CHERRY_TWIG = register("stripped_cherry_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_CHERRY_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_CRIMSON_TWIG = register("stripped_crimson_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_CRIMSON_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_DARK_OAK_TWIG = register("stripped_dark_oak_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_DARK_OAK_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_JUNGLE_TWIG = register("stripped_jungle_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_JUNGLE_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_MANGROVE_TWIG = register("stripped_mangrove_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_MANGROVE_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_OAK_TWIG = register("stripped_oak_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_OAK_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_PALE_OAK_TWIG = register("stripped_pale_oak_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_PALE_OAK_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_SPRUCE_TWIG = register("stripped_spruce_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_SPRUCE_TWIG, p), new Item.Properties());

    public static final Item STRIPPED_WARPED_TWIG = register("stripped_warped_twig",
            p -> new BlockItem(ModBlocks.STRIPPED_WARPED_TWIG, p), new Item.Properties());

    /*----------------------------------------------------------------------*/

    public static void initializer() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.accept(ANDESITE_PEBBLE);
            entries.accept(BLACKSTONE_PEBBLE);
            entries.accept(DEEPSLATE_PEBBLE);
            entries.accept(DIORITE_PEBBLE);
            entries.accept(END_STONE_PEBBLE);
            entries.accept(GRANITE_PEBBLE);
            entries.accept(RED_SANDSTONE_PEBBLE);
            entries.accept(SANDSTONE_PEBBLE);
            entries.accept(STONE_PEBBLE);
            entries.accept(TUFF_PEBBLE);

            entries.accept(ACACIA_TWIG);
            entries.accept(BIRCH_TWIG);
            entries.accept(CHERRY_TWIG);
            entries.accept(CRIMSON_TWIG);
            entries.accept(DARK_OAK_TWIG);
            entries.accept(JUNGLE_TWIG);
            entries.accept(MANGROVE_TWIG);
            entries.accept(OAK_TWIG);
            entries.accept(PALE_OAK_TWIG);
            entries.accept(SPRUCE_TWIG);
            entries.accept(WARPED_TWIG);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.accept(STRIPPED_ACACIA_TWIG);
            entries.accept(STRIPPED_BIRCH_TWIG);
            entries.accept(STRIPPED_CHERRY_TWIG);
            entries.accept(STRIPPED_CRIMSON_TWIG);
            entries.accept(STRIPPED_DARK_OAK_TWIG);
            entries.accept(STRIPPED_JUNGLE_TWIG);
            entries.accept(STRIPPED_MANGROVE_TWIG);
            entries.accept(STRIPPED_OAK_TWIG);
            entries.accept(STRIPPED_PALE_OAK_TWIG);
            entries.accept(STRIPPED_SPRUCE_TWIG);
            entries.accept(STRIPPED_WARPED_TWIG);
        });
    }
}
