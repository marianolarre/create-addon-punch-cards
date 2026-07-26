package com.example.createpunchcards;

import com.example.createpunchcards.content.kinetics.ComputerBlock;
import com.example.createpunchcards.content.kinetics.ComputerBlockEntity;
import com.example.createpunchcards.content.kinetics.ComputerRenderer;
import com.example.createpunchcards.content.kinetics.CreatePunchCardsKineticBlockEntity;
import com.example.createpunchcards.content.kinetics.CreatePunchCardsShaftRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

/**
 * Block entity type registration.
 */
public class AllBlockEntityTypes {

    public static final BlockEntityEntry<ComputerBlockEntity> COMPUTER_BLOCK_ENTITY = CreatePunchCards.REGISTRATE
            .blockEntity("computer_block_entity", ComputerBlockEntity::new)
            .visual(() -> (context, be, pt) -> {
                Direction shaft = ComputerBlock.getShaftFacing(be.getBlockState());
                return new OrientedRotatingVisual<>(context, be, pt, Direction.SOUTH, shaft,
                        Models.partial(AllPartialModels.SHAFT_HALF));
            }, false)
            .validBlock(AllBlocks.COMPUTER_BLOCK)
            .renderer(() -> ComputerRenderer::new)
            .register();

    public static final BlockEntityEntry<CreatePunchCardsKineticBlockEntity> DRUM_BLOCK_ENTITY = CreatePunchCards.REGISTRATE
            .blockEntity("drum_block_entity", CreatePunchCardsKineticBlockEntity::new)
            // visual for flywheel renderer
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.DRUM_BLOCK)
            // fallback renderer if flywheel is not available
            .renderer(() -> CreatePunchCardsShaftRenderer::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
