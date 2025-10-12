package com.ilyrash.pebblemod.block;

import com.ilyrash.pebblemod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.EnumMap;

public class PebbleBlock extends Block {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty PEBBLES = IntProperty.of("pebbles", 1, 4);
    public static final EnumProperty<PebbleType> PEBBLE_TYPE = EnumProperty.of("pebble_type", PebbleType.class);

    private static final EnumMap<Direction, VoxelShape[]> SHAPES = new EnumMap<>(Direction.class);

    static {
        // Based on model coordinates, adjusted for rotation
        SHAPES.put(Direction.NORTH, new VoxelShape[]{
                // 1 pebble - covers the first pebble at (3,0,2) to (8,3,7)
                Block.createCuboidShape(3.0, 0.0, 2.0, 8.0, 3.0, 7.0),
                // 2 pebbles - contains first two pebbles
                Block.createCuboidShape(3.0, 0.0, 2.0, 12.0, 3.0, 15.0),
                // 3 pebbles - contains first three pebbles
                Block.createCuboidShape(1.0, 0.0, 2.0, 12.0, 3.0, 15.0),
                // 4 pebbles - contains all four pebbles
                Block.createCuboidShape(1.0, 0.0, 2.0, 15.0, 3.0, 15.0)
        });

        SHAPES.put(Direction.EAST, new VoxelShape[]{
                // 1 pebble - rotated 90 degrees
                Block.createCuboidShape(9.0, 0.0, 3.0, 14.0, 3.0, 8.0),
                // 2 pebbles
                Block.createCuboidShape(1.0, 0.0, 3.0, 14.0, 3.0, 12.0),
                // 3 pebbles
                Block.createCuboidShape(1.0, 0.0, 1.0, 14.0, 3.0, 12.0),
                // 4 pebbles
                Block.createCuboidShape(1.0, 0.0, 1.0, 14.0, 3.0, 15.0)
        });

        SHAPES.put(Direction.SOUTH, new VoxelShape[]{
                // 1 pebble - rotated 180 degrees
                Block.createCuboidShape(8.0, 0.0, 9.0, 13.0, 3.0, 14.0),
                // 2 pebbles
                Block.createCuboidShape(4.0, 0.0, 1.0, 13.0, 3.0, 14.0),
                // 3 pebbles
                Block.createCuboidShape(4.0, 0.0, 1.0, 15.0, 3.0, 14.0),
                // 4 pebbles
                Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 3.0, 14.0)
        });

        SHAPES.put(Direction.WEST, new VoxelShape[]{
                // 1 pebble - rotated 270 degrees
                Block.createCuboidShape(2.0, 0.0, 8.0, 7.0, 3.0, 13.0),
                // 2 pebbles
                Block.createCuboidShape(2.0, 0.0, 4.0, 15.0, 3.0, 13.0),
                // 3 pebbles
                Block.createCuboidShape(2.0, 0.0, 4.0, 15.0, 3.0, 15.0),
                // 4 pebbles
                Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 3.0, 15.0)
        });
    }

    public PebbleBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(PEBBLES, 1)
                .with(PEBBLE_TYPE, PebbleType.STONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PEBBLES, PEBBLE_TYPE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        ItemStack stack = ctx.getStack();

        if (blockState.getBlock() instanceof PebbleBlock) {
            int currentPebbles = blockState.get(PEBBLES);
            PebbleType currentType = blockState.get(PEBBLE_TYPE);
            PebbleType stackType = getPebbleTypeFromItem(stack, ctx.getWorld(), ctx.getBlockPos());

            if (currentPebbles < 4 && currentType == stackType) {
                return blockState.with(PEBBLES, currentPebbles + 1);
            }
        }

        PebbleType type = getPebbleTypeFromItem(stack, ctx.getWorld(), ctx.getBlockPos());
        return getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(PEBBLE_TYPE, type);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = Hand.MAIN_HAND;
        ItemStack stack = player.getStackInHand(hand);

        // Check if player is holding a pebble item
        if (!stack.isOf(ModItems.STONE_PEBBLE) &&
                !stack.isOf(ModItems.SANDSTONE_PEBBLE) &&
                !stack.isOf(ModItems.RED_SANDSTONE_PEBBLE)) {
            return ActionResult.PASS;
        }

        int currentPebbles = state.get(PEBBLES);
        PebbleType currentType = state.get(PEBBLE_TYPE);
        PebbleType stackType = getPebbleTypeFromItem(stack, world, pos);

        // Check if we can add to this pebble block
        boolean canAdd;

        // For snow stone: only allow adding stone pebbles (which become snow stone in snowy biomes)
        if (currentType == PebbleType.SNOW_STONE) {
            canAdd = stack.isOf(ModItems.STONE_PEBBLE) && currentPebbles < 4;
        }
        // For other types: must match type and have less than 4 pebbles
        else {
            canAdd = (currentType == stackType) && currentPebbles < 4;
        }

        if (!canAdd) {
            return ActionResult.PASS;
        }

        if (!world.isClient()) {
            // Increase pebble count
            world.setBlockState(pos, state.with(PEBBLES, currentPebbles + 1));
            world.playSound(null, pos, state.getSoundGroup().getPlaceSound(),
                    net.minecraft.sound.SoundCategory.BLOCKS, 1f, 0.8f);

            if (!player.isCreative()) {
                stack.decrement(1);
            }
        } else {
            player.swingHand(hand);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        // Check if the update is from the block below us
        if (direction == Direction.DOWN) {
            // Check if the pebble can no longer be placed at this position
            if (!this.canPlaceAt(state, world, pos)) {
                // Return air to break the pebble block
                return Blocks.AIR.getDefaultState();
            }
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        int pebbles = state.get(PEBBLES);
        return SHAPES.get(facing)[pebbles - 1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        PebbleType type = state.get(PEBBLE_TYPE);
        int pebbles = state.get(PEBBLES);
        return new ItemStack(getItemFromPebbleType(type), pebbles);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            int pebbles = state.get(PEBBLES);
            PebbleType type = state.get(PEBBLE_TYPE);
            ItemStack dropStack = new ItemStack(getItemFromPebbleType(type), pebbles);
            dropStack(world, pos, dropStack);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int currentPebbles = state.get(PEBBLES);
        ItemStack stack = context.getStack();
        PebbleType currentType = state.get(PEBBLE_TYPE);
        PebbleType stackType = getPebbleTypeFromItem(stack, context.getWorld(), context.getBlockPos());

        if ((stack.getItem().equals(ModItems.STONE_PEBBLE) ||
                stack.getItem().equals(ModItems.SANDSTONE_PEBBLE) ||
                stack.getItem().equals(ModItems.RED_SANDSTONE_PEBBLE)) &&
                currentPebbles < 4 && currentType == stackType) {
            return true;
        }

        return super.canReplace(state, context);
    }

    private PebbleType getPebbleTypeFromItem(ItemStack stack, World world, BlockPos pos) {
        // Check for snowy biomes - ONLY for stone pebbles
        RegistryEntry<Biome> biomeEntry = world.getBiome(pos);
        Biome biome = biomeEntry.value();
        boolean isSnowyBiome = biome.isCold(pos, world.getSeaLevel());

        // Only stone pebbles become snow stone in snowy biomes
        if (stack.isOf(ModItems.STONE_PEBBLE) && isSnowyBiome) {
            return PebbleType.SNOW_STONE;
        }

        if (stack.isOf(ModItems.STONE_PEBBLE)) return PebbleType.STONE;
        if (stack.isOf(ModItems.SANDSTONE_PEBBLE)) return PebbleType.SANDSTONE;
        if (stack.isOf(ModItems.RED_SANDSTONE_PEBBLE)) return PebbleType.RED_SANDSTONE;

        return PebbleType.STONE;
    }

    private net.minecraft.item.Item getItemFromPebbleType(PebbleType type) {
        return switch (type) {
            case STONE, SNOW_STONE -> ModItems.STONE_PEBBLE;
            case SANDSTONE -> ModItems.SANDSTONE_PEBBLE;
            case RED_SANDSTONE -> ModItems.RED_SANDSTONE_PEBBLE;
        };
    }

    public enum PebbleType implements StringIdentifiable {
        STONE("stone"),
        SANDSTONE("sandstone"),
        RED_SANDSTONE("red_sandstone"),
        SNOW_STONE("snow_stone");

        private final String name;

        PebbleType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}