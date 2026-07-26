package com.example.createpunchcards;

import com.example.createpunchcards.content.display.CreatePunchCardsDisplaySource;
import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.tterrag.registrate.util.entry.RegistryEntry;

/**
 * Display source registration. Attach an entry to a block in AllBlocks with
 * transform(DisplaySource.displaySource(entry)).
 */
public class AllDisplaySources {

    public static final RegistryEntry<DisplaySource, CreatePunchCardsDisplaySource> EXAMPLE_SOURCE = CreatePunchCards.REGISTRATE
            .displaySource("createpunchcards_source", CreatePunchCardsDisplaySource::new)
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
