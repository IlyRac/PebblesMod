package com.ilyrash.pebblemod;

import com.ilyrash.pebblemod.block.ModBlocks;
import com.ilyrash.pebblemod.entity.ModEntities;
import com.ilyrash.pebblemod.entity.ThrownPebbleEntity;
import com.ilyrash.pebblemod.item.ModItems;
import com.ilyrash.pebblemod.world.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pebblemod implements ModInitializer {
	public static final String MOD_ID = "pebblemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.initialize();
        ModEntities.initialize();
        ModBlocks.initialize();

        ModWorldGeneration.initialize();

        registerDispenserBehaviors();

		LOGGER.info("... Pebbles Mod Initialized ...");
	}

    private void registerDispenserBehaviors() {
        DispenserBehavior pebbleDispenserBehavior = (pointer, stack) -> {
            World world = pointer.world();
            Position position = DispenserBlock.getOutputLocation(pointer);
            Direction direction = pointer.state().get(DispenserBlock.FACING);

            ThrownPebbleEntity pebbleEntity = new ThrownPebbleEntity(world, position.getX(), position.getY(), position.getZ(), stack);
            pebbleEntity.setItem(stack);

            // Set velocity in the direction the dispenser is facing
            pebbleEntity.setVelocity(
                    direction.getOffsetX(),
                    direction.getOffsetY() + 0.1F, // Slightly upward
                    direction.getOffsetZ(),
                    1.5F, // Speed
                    1.0F  // Spread
            );

            world.spawnEntity(pebbleEntity);

            // Add throwing sound - same as when players throw pebbles
            world.playSound(
                    null,
                    position.getX(),
                    position.getY(),
                    position.getZ(),
                    SoundEvents.ENTITY_SNOWBALL_THROW,
                    SoundCategory.NEUTRAL,
                    0.5F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
            );

            stack.decrement(1);
            return stack;
        };

        // Register for all pebble types
        DispenserBlock.registerBehavior(ModItems.STONE_PEBBLE, pebbleDispenserBehavior);
        DispenserBlock.registerBehavior(ModItems.SANDSTONE_PEBBLE, pebbleDispenserBehavior);
        DispenserBlock.registerBehavior(ModItems.RED_SANDSTONE_PEBBLE, pebbleDispenserBehavior);
    }
}