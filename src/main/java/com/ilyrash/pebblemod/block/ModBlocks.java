package com.ilyrash.pebblemod.block;

import com.ilyrash.pebblemod.Pebblemod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    private static Block register(Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Pebblemod.MOD_ID, "pebbles"));
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static final Block PEBBLE_BLOCK = register(
            PebbleBlock::new,
            AbstractBlock.Settings.create()
                    .noCollision()
                    .nonOpaque()
                    .sounds(BlockSoundGroup.STONE)
                    .strength(0.1f)
    );

    public static void initialize() {
    }
}