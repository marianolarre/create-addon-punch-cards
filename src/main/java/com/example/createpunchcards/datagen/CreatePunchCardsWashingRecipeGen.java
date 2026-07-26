package com.example.createpunchcards.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.createpunchcards.AllItems;
import com.example.createpunchcards.CreatePunchCards;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.WashingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Splashing recipe generator.
 */
public class CreatePunchCardsWashingRecipeGen extends WashingRecipeGen {

    GeneratedRecipe EXAMPLE = create("createpunchcards_washing",
            b -> b.require(Items.DIRT).output(AllItems.EXAMPLE_ITEM.get()));

    public CreatePunchCardsWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreatePunchCards.ID);
    }
}
