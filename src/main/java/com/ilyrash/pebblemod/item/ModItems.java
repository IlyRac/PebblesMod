package com.ilyrash.pebblemod.item;

import com.ilyrash.pebblemod.Pebblemod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pebblemod.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static final Item STONE_PEBBLE = register(
            "stone_pebble",
            PebbleItem::new,
            new Item.Settings().maxCount(64));

    public static final Item SANDSTONE_PEBBLE = register(
            "sandstone_pebble",
            PebbleItem::new,
            new Item.Settings().maxCount(64));

    public static final Item RED_SANDSTONE_PEBBLE = register(
            "red_sandstone_pebble",
            PebbleItem::new,
            new Item.Settings().maxCount(64));

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(STONE_PEBBLE);
            entries.add(SANDSTONE_PEBBLE);
            entries.add(RED_SANDSTONE_PEBBLE);
        });
    }
}