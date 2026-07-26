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
 * Example kinetic generator. It uses the same axle-style base as CreatePunchCardsKineticBlock;
 * the difference is entirely in the block entity, CreatePunchCardsGeneratorBlockEntity.
 */
public class CreatePunchCardsGeneratorBlock extends RotatedPillarKineticBlock implements IBE<CreatePunchCardsGeneratorBlockEntity> {

    public CreatePunchCardsGeneratorBlock(Properties properties) {
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
    public Class<CreatePunchCardsGeneratorBlockEntity> getBlockEntityClass() {
        return CreatePunchCardsGeneratorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CreatePunchCardsGeneratorBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.EXAMPLE_GENERATOR.get();
    }
}
