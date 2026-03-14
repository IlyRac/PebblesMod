package com.ilyrac.pebblesandtwigs;

import com.ilyrac.pebblesandtwigs.block.ModBlocks;
import com.ilyrac.pebblesandtwigs.entity.ModEntities;
import com.ilyrac.pebblesandtwigs.entity.ThrownPebbleEntity;
import com.ilyrac.pebblesandtwigs.item.ModItems;
import com.ilyrac.pebblesandtwigs.world.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Position;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PebblesAndTwigs implements ModInitializer {
	public static final String MOD_ID = "pebblesandtwigs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.initializer();
		ModItems.initializer();
		ModEntities.initializer();
		ModWorldGeneration.initializer();

		registerFlammables();
		registerDefaults();

		LOGGER.info("Pebbles & Twigs Loaded Successfully!");
	}

	private void registerFlammables() {
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ACACIA_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BIRCH_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CHERRY_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DARK_OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.JUNGLE_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MANGROVE_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PALE_OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SPRUCE_TWIG, 5, 20);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_ACACIA_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_BIRCH_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_CHERRY_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DARK_OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_JUNGLE_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_MANGROVE_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_PALE_OAK_TWIG, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_SPRUCE_TWIG, 5, 20);
	}

	public static void registerDefaults() {
		DefaultDispenseItemBehavior pebbleBehavior = new DefaultDispenseItemBehavior() {
			@Override
			protected @NonNull ItemStack execute(BlockSource blockSource, @NonNull ItemStack itemStack) {
				Level level = blockSource.level();
				Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
				Position position = DispenserBlock.getDispensePosition(blockSource);
				ThrownPebbleEntity pebble = new ThrownPebbleEntity(level, position.x(), position.y(), position.z());
				pebble.setItem(itemStack);
				pebble.shoot(
						direction.getStepX(),
						(double)direction.getStepY() + 0.1D,
						direction.getStepZ(),
						1.1F,
						6.0F
				);
				level.addFreshEntity(pebble);
				itemStack.shrink(1);
				return itemStack;
			}

			@Override
			protected void playSound(BlockSource blockSource) {
				blockSource.level().levelEvent(1002, blockSource.pos(), 0);
			}
		};

		DispenserBlock.registerBehavior(ModBlocks.ANDESITE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.BLACKSTONE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.DEEPSLATE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.DIORITE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.END_STONE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.GRANITE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.RED_SANDSTONE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.SANDSTONE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.STONE_PEBBLE.asItem(), pebbleBehavior);
		DispenserBlock.registerBehavior(ModBlocks.TUFF_PEBBLE.asItem(), pebbleBehavior);
	}
}