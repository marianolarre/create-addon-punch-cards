package com.example.createpunchcards;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AllPartialModels {

    /** Y-up drum mesh for BER / Flywheel (block itself is ENTITYBLOCK_ANIMATED). */
    public static final PartialModel DRUM = PartialModel.of(CreatePunchCards.asResource("block/drum_block"));

    public static void register() {
        // Force class loading so PartialModel.of runs during client init.
    }
}
