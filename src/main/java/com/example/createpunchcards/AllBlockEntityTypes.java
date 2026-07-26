package com.example.createpunchcards;

import com.example.createpunchcards.content.kinetics.CreatePunchCardsGeneratorBlockEntity;
import com.example.createpunchcards.content.kinetics.CreatePunchCardsKineticBlockEntity;
import com.example.createpunchcards.content.kinetics.CreatePunchCardsShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

/**
 * Block entity type registration.
 */
public class AllBlockEntityTypes {

    /**
     * Block entity for EXAMPLE_KINETIC_BLOCK, rendered with CreatePunchCardsShaftRenderer so a
     * shaft visibly spins through the casing.
     */
    public static final BlockEntityEntry<CreatePunchCardsKineticBlockEntity> EXAMPLE_KINETIC = CreatePunchCards.REGISTRATE
            .blockEntity("createpunchcards_kinetic", CreatePunchCardsKineticBlockEntity::new)
            // visual for flywheel renderer
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.EXAMPLE_KINETIC_BLOCK)
            // fallback renderer if flywheel is not available
            .renderer(() -> CreatePunchCardsShaftRenderer::new)
            .register();

    /**
     * Block entity for EXAMPLE_GENERATOR_BLOCK, also rendered with CreatePunchCardsShaftRenderer.
     */
    public static final BlockEntityEntry<CreatePunchCardsGeneratorBlockEntity> EXAMPLE_GENERATOR = CreatePunchCards.REGISTRATE
            .blockEntity("createpunchcards_generator", CreatePunchCardsGeneratorBlockEntity::new)
            .visual(() -> ShaftVisual::new)
            .validBlock(AllBlocks.EXAMPLE_GENERATOR_BLOCK)
            .renderer(() -> CreatePunchCardsShaftRenderer::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
