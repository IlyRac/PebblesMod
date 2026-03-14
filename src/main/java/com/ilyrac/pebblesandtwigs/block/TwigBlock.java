package com.ilyrac.pebblesandtwigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half; // Changed from BlockHalf
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class TwigBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty STANDING = BooleanProperty.create("standing");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF; // Changed to Half

    // Bottom Shapes
    private static final VoxelShape SLEEPING_Z = Block.box(6.0, 0.0, 0.0, 10.0, 4.0, 16.0);
    private static final VoxelShape SLEEPING_X = Block.box(0.0, 0.0, 6.0, 16.0, 4.0, 10.0);

    // Top Shapes (Ceiling)
    private static final VoxelShape SLEEPING_Z_TOP = Block.box(6.0, 12.0, 0.0, 10.0, 16.0, 16.0);
    private static final VoxelShape SLEEPING_X_TOP = Block.box(0.0, 12.0, 6.0, 16.0, 16.0, 10.0);

    private static final VoxelShape STANDING_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    public TwigBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(STANDING, false)
                .setValue(HALF, Half.BOTTOM) // Changed to Half
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state,
                                        @NonNull BlockGetter level,
                                        @NonNull BlockPos pos,
                                        @NonNull CollisionContext context) {
        if (state.getValue(STANDING)) {
            return STANDING_SHAPE;
        }

        boolean isTop = state.getValue(HALF) == Half.TOP;
        if (state.getValue(FACING).getAxis() == Direction.Axis.X) {
            return isTop ? SLEEPING_X_TOP : SLEEPING_X;
        }
        return isTop ? SLEEPING_Z_TOP : SLEEPING_Z;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        boolean isStanding = face.getAxis().isVertical();
        Half half = Half.BOTTOM; // Changed to Half

        // Logic for "Sleeping" placement on the side of a block
        if (!isStanding) {
            // In 1.21, getHitPos() is called getClickLocation()
            double hitY = context.getClickLocation().y - (double)context.getClickedPos().getY();
            if (hitY > 0.5) {
                half = Half.TOP;
            }
        }
        else if (face == Direction.DOWN) {
            isStanding = false;
            half = Half.TOP;
        }

        Direction facing = isStanding ? context.getHorizontalDirection().getOpposite() : (face.getAxis().isVertical() ? context.getHorizontalDirection() : face);

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(STANDING, isStanding)
                .setValue(HALF, half)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STANDING, HALF, WATERLOGGED);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state,
                                                        @NonNull Level level,
                                                        @NonNull BlockPos pos,
                                                        @NonNull Player player,
                                                        @NonNull BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() instanceof AxeItem) {
            Block strippedBlock = ModBlocks.getStripped(state.getBlock());
            if (strippedBlock != null) {
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!level.isClientSide()) {
                    BlockState newState = strippedBlock.defaultBlockState()
                            .setValue(FACING, state.getValue(FACING))
                            .setValue(STANDING, state.getValue(STANDING))
                            .setValue(HALF, state.getValue(HALF))
                            .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

                    level.setBlock(pos, newState, 11);
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public @NonNull BlockState updateShape(BlockState state,
                                           @NonNull LevelReader level,
                                           @NonNull ScheduledTickAccess tickAccess,
                                           @NonNull BlockPos pos,
                                           @NonNull Direction direction,
                                           @NonNull BlockPos neighborPos,
                                           @NonNull BlockState neighborState,
                                           @NonNull RandomSource random) {
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
    public @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder builder) {
        List<ItemStack> drops = new java.util.ArrayList<>();
        drops.add(new ItemStack(this.asItem(), 1));

        return drops;
    }
}