package com.ilyrac.pebblesandtwigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class PebbleBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1, 4);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // MATHEMATICALLY ROTATED SHAPES
    // Amount 1: Elements 0, 1, 2
    // North (0°): X[1-9] Z[1-8]
    private static final VoxelShape N_1 = Block.box(1.0, 0.0, 1.0, 9.0, 2.0, 8.0);
    // East (90°):  X[8-15] Z[1-9]
    private static final VoxelShape E_1 = Block.box(8.0, 0.0, 1.0, 15.0, 2.0, 9.0);
    // South (180°): X[7-15] Z[8-15]
    private static final VoxelShape S_1 = Block.box(7.0, 0.0, 8.0, 15.0, 2.0, 15.0);
    // West (270°):  X[1-8] Z[7-15]
    private static final VoxelShape W_1 = Block.box(1.0, 0.0, 7.0, 8.0, 2.0, 15.0);

    // Amount 2: Elements 0, 1, 2 + 3
    // North (0°): X[1-9] Z[1-14]
    private static final VoxelShape N_2 = Block.box(1.0, 0.0, 1.0, 9.0, 2.0, 14.0);
    // East (90°):  X[2-15] Z[1-9]
    private static final VoxelShape E_2 = Block.box(2.0, 0.0, 1.0, 15.0, 2.0, 9.0);
    // South (180°): X[7-15] Z[2-15]
    private static final VoxelShape S_2 = Block.box(7.0, 0.0, 2.0, 15.0, 2.0, 15.0);
    // West (270°):  X[1-14] Z[7-15]
    private static final VoxelShape W_2 = Block.box(1.0, 0.0, 7.0, 14.0, 2.0, 15.0);

    // Amount 3 & 4: Full area
    private static final VoxelShape SHAPE_FULL = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);

    @Override
    public @NonNull VoxelShape getShape(
            BlockState state,
            @NonNull BlockGetter level,
            @NonNull BlockPos pos,
            @NonNull CollisionContext context) {
        Direction dir = state.getValue(FACING);
        int amount = state.getValue(AMOUNT);

        if (amount >= 3) return SHAPE_FULL;

        return switch (amount) {
            case 1 -> switch (dir) {
                case EAST -> E_1;
                case WEST -> W_1;
                case SOUTH -> S_1;
                default -> N_1;
            };
            case 2 -> switch (dir) {
                case EAST -> E_2;
                case WEST -> W_2;
                case SOUTH -> S_2;
                default -> N_2;
            };
            default -> SHAPE_FULL;
        };
    }

    public PebbleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(AMOUNT, 1)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public boolean canBeReplaced(@NonNull BlockState state, BlockPlaceContext context) {
        if (!context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem())
                && state.getValue(AMOUNT) < 4) {
            return true;
        }
        return super.canBeReplaced(state, context);
    }

    @Override
    protected boolean canBeReplaced(@NonNull BlockState state, Fluid fluid) {
        //noinspection deprecation
        if (fluid.is(FluidTags.LAVA)) {
            return true;
        }
        return super.canBeReplaced(state, fluid);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        BlockState currentState = context.getLevel().getBlockState(pos);

        if (currentState.is(this)) {
            return currentState.setValue(AMOUNT, Math.min(4, currentState.getValue(AMOUNT) + 1));
        }

        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    public @NonNull BlockState updateShape(
            BlockState state,
            @NonNull LevelReader level,
            @NonNull ScheduledTickAccess tickAccess,
            @NonNull BlockPos pos, @NonNull Direction direction,
            @NonNull BlockPos neighborPos, @NonNull BlockState neighborState,
            @NonNull RandomSource random
    ){
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (!state.canSurvive(level, pos)) {
            return state.getValue(WATERLOGGED) ?
                    Fluids.WATER.defaultFluidState().createLegacyBlock() : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public @NonNull List<ItemStack> getDrops(BlockState state, LootParams.@NonNull Builder builder) {
        int amount = state.getValue(AMOUNT);

        List<ItemStack> drops = new java.util.ArrayList<>();
        drops.add(new ItemStack(this.asItem(), amount));

        return drops;
    }
}