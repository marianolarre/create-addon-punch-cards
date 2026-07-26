package com.example.createpunchcards;

import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * Item registration. Each item overrides its model to borrow a vanilla texture, so the
 * template builds with no texture files of its own. Point the model at your own texture,
 * or remove the override and add assets/createpunchcards/textures/item/name.png.
 */
public class AllItems {

    public static final ItemEntry<Item> PUNCH_CARD = CreatePunchCards.REGISTRATE
            .item("punch_card", Item::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.fromNamespaceAndPath("createpunchcards", "item/punch_card")))
            .register();

    public static final ItemEntry<Item> INCOMPLETE_PUNCH_CARD = CreatePunchCards.REGISTRATE
            .item("incomplete_punch_card", Item::new)
            .model((c, p) -> p.generated(c::getEntry, ResourceLocation.fromNamespaceAndPath("createpunchcards", "item/incomplete_punch_card")))
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
