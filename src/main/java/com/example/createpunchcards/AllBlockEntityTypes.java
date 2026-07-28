package com.example.createpunchcards;

import com.example.createpunchcards.content.kinetics.ComputerBlockEntity;
import com.example.createpunchcards.content.kinetics.ComputerRenderer;
import com.example.createpunchcards.content.kinetics.ComputerVisual;
import com.example.createpunchcards.content.kinetics.DrumBlockEntity;
import com.example.createpunchcards.content.kinetics.DrumRenderer;
import com.example.createpunchcards.content.kinetics.DrumVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

/**
 * Block entity type registration.
 */
public class AllBlockEntityTypes {

    public static final BlockEntityEntry<ComputerBlockEntity> COMPUTER_BLOCK_ENTITY = CreatePunchCards.REGISTRATE
            .blockEntity("computer_block_entity", ComputerBlockEntity::new)
            .visual(() -> ComputerVisual::new, false)
            .validBlock(AllBlocks.COMPUTER_BLOCK)
            .renderer(() -> ComputerRenderer::new)
            .register();

    public static final BlockEntityEntry<DrumBlockEntity> DRUM_BLOCK_ENTITY = CreatePunchCards.REGISTRATE
            .blockEntity("drum_block_entity", DrumBlockEntity::new)
            .visual(() -> DrumVisual::new, false)
            .validBlock(AllBlocks.DRUM_BLOCK)
            .renderer(() -> DrumRenderer::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
