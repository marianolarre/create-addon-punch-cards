package com.example.createpunchcards.content.kinetics;

import com.example.createpunchcards.AllBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Example kinetic block. Extends RotatedPillarKineticBlock, which gives it an axis
 * property and shaft connections on both ends of that axis, and implements IBE to
 * bind it to CreatePunchCardsKineticBlockEntity.
 */
public class CreatePunchCardsKineticBlock extends RotatedPillarKineticBlock implements IBE<CreatePunchCardsKineticBlockEntity> {

    public CreatePunchCardsKineticBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(AXIS);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public Class<CreatePunchCardsKineticBlockEntity> getBlockEntityClass() {
        return CreatePunchCardsKineticBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CreatePunchCardsKineticBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.DRUM_BLOCK_ENTITY.get();
    }
}
