package com.example.createpunchcards.content.kinetics;

import com.example.createpunchcards.AllBlockEntityTypes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

/**
 * Kinetic computer block. Stays upright with horizontal facing; a half-shaft connects
 * only on the left side relative to that facing.
 */
public class ComputerBlock extends HorizontalKineticBlock implements IBE<ComputerBlockEntity> {

    public ComputerBlock(Properties properties) {
        super(properties);
    }

    /** World face that accepts a shaft (left of {@link #HORIZONTAL_FACING}). */
    public static Direction getShaftFacing(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getCounterClockWise();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = getPreferredHorizontalFacing(context);
        if (preferred != null)
            // preferred is the face that should get our shaft → facing whose left is that face
            return defaultBlockState().setValue(HORIZONTAL_FACING, preferred.getClockWise());
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == getShaftFacing(state);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return getShaftFacing(state).getAxis();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        DrumBlock.checkAlignmentOrBreak(level, pos.above());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (!level.isClientSide && fromPos.equals(pos.above()))
            DrumBlock.checkAlignmentOrBreak(level, pos.above());
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState rotated = getRotatedBlockState(state, context.getClickedFace());
        if (!rotated.canSurvive(level, pos))
            return InteractionResult.PASS;

        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(rotated, context));

        if (level.getBlockState(pos) != state)
            IWrenchable.playRotateSound(level, pos);

        if (!level.isClientSide)
            DrumBlock.checkAlignmentOrBreak(level, pos.above());

        return InteractionResult.SUCCESS;
    }

    @Override
    public Class<ComputerBlockEntity> getBlockEntityClass() {
        return ComputerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ComputerBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.COMPUTER_BLOCK_ENTITY.get();
    }
}
