package com.ilyrash.pebblemod.item;

import com.ilyrash.pebblemod.block.ModBlocks;
import com.ilyrash.pebblemod.block.PebbleBlock;
import com.ilyrash.pebblemod.entity.ThrownPebbleEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PebbleItem extends Item {

    public PebbleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Check if we're looking at a block for placement first
        var hitResult = user.raycast(5.0, 0.0F, true);
        if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
            var blockHit = (net.minecraft.util.hit.BlockHitResult) hitResult;
            var context = new ItemUsageContext(user, hand, blockHit);
            ActionResult placeResult = useOnBlock(context);
            if (placeResult == ActionResult.SUCCESS) {
                return placeResult;
            }
        }

        // If not placing, throw the pebble
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!world.isClient()) {
            ThrownPebbleEntity thrownPebble = new ThrownPebbleEntity(world, user, stack);
            thrownPebble.setItem(stack);
            thrownPebble.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(thrownPebble);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos clickedPos = context.getBlockPos();
        Direction clickedSide = context.getSide();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        ItemStack stack = context.getStack();

        // Only place on top of blocks
        if (clickedSide != Direction.UP) {
            return ActionResult.PASS;
        }

        BlockPos placePos = clickedPos.offset(clickedSide);
        BlockState existingState = world.getBlockState(placePos);

        // Check if we can add to existing pebble block
        if (existingState.getBlock() instanceof PebbleBlock) {
            int pebbles = existingState.get(PebbleBlock.PEBBLES);
            PebbleBlock.PebbleType existingType = existingState.get(PebbleBlock.PEBBLE_TYPE);
            PebbleBlock.PebbleType thisType = getPebbleTypeFromItem(stack, world, placePos);

            // Normal case: same type and less than 4
            boolean canAddNormal = existingType == thisType && pebbles < 4;

            if (pebbles < 4 && canAddNormal) {
                if (!world.isClient()) {
                    world.setBlockState(placePos, existingState.with(PebbleBlock.PEBBLES, pebbles + 1));
                    world.playSound(null, placePos, existingState.getSoundGroup().getPlaceSound(),
                            SoundCategory.BLOCKS, 1f, 0.8f);
                    if (player != null && !player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                } else if (player != null) {
                    player.swingHand(hand);
                }
                return ActionResult.SUCCESS;
            }
        }

        // Place new pebble block
        if (world.getBlockState(placePos).isAir()) {
            BlockPos below = placePos.down();
            if (world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP)) {
                PebbleBlock.PebbleType type = getPebbleTypeFromItem(stack, world, placePos);

                assert player != null;
                BlockState newState = ModBlocks.PEBBLE_BLOCK.getDefaultState()
                        .with(PebbleBlock.PEBBLE_TYPE, type)
                        .with(PebbleBlock.PEBBLES, 1)
                        .with(PebbleBlock.FACING, player.getHorizontalFacing().getOpposite());

                if (!world.isClient()) {
                    world.setBlockState(placePos, newState);
                    world.playSound(null, placePos, newState.getSoundGroup().getPlaceSound(),
                            SoundCategory.BLOCKS, 1f, 0.8f);
                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                } else {
                    player.swingHand(hand);
                }
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private PebbleBlock.PebbleType getPebbleTypeFromItem(ItemStack stack, World world, BlockPos pos) {
        // Check for snowy biomes using the available methods
        RegistryEntry<Biome> biomeEntry = world.getBiome(pos);
        Biome biome = biomeEntry.value();
        boolean isSnowyBiome = biome.isCold(pos, world.getSeaLevel());

        // Only stone pebbles become snow stone in snowy biomes
        if (stack.isOf(ModItems.STONE_PEBBLE) && isSnowyBiome) {
            return PebbleBlock.PebbleType.SNOW_STONE;
        }

        if (stack.isOf(ModItems.STONE_PEBBLE)) return PebbleBlock.PebbleType.STONE;
        if (stack.isOf(ModItems.SANDSTONE_PEBBLE)) return PebbleBlock.PebbleType.SANDSTONE;
        if (stack.isOf(ModItems.RED_SANDSTONE_PEBBLE)) return PebbleBlock.PebbleType.RED_SANDSTONE;

        return PebbleBlock.PebbleType.STONE;
    }
}