package com.example.createpunchcards.content.kinetics;

import com.example.createpunchcards.AllBlockEntityTypes;
import com.example.createpunchcards.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class DrumBlock extends RotatedPillarKineticBlock implements IBE<DrumBlockEntity> {

    public DrumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        checkAlignmentOrBreak(level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && level instanceof Level realLevel)
            checkAlignmentOrBreak(realLevel, pos);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    /** If a computer is below and axes differ, destroy this drum and drop its item. */
    public static void checkAlignmentOrBreak(Level level, BlockPos drumPos) {
        if (level.isClientSide)
            return;
        BlockState below = level.getBlockState(drumPos.below());
        if (!below.is(AllBlocks.COMPUTER_BLOCK.get()))
            return;
        BlockState drum = level.getBlockState(drumPos);
        if (!(drum.getBlock() instanceof DrumBlock))
            return;
        Direction.Axis computerAxis = ((ComputerBlock) below.getBlock()).getRotationAxis(below);
        if (DrumComputerKinetics.axesAligned(
                drum.getValue(AXIS).getSerializedName(),
                computerAxis.getSerializedName()))
            return;
        level.destroyBlock(drumPos, true);
    }

    @Override
    public Class<DrumBlockEntity> getBlockEntityClass() {
        return DrumBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DrumBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.DRUM_BLOCK_ENTITY.get();
    }
}
