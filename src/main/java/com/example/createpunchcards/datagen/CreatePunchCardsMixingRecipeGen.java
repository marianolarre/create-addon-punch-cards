package com.example.createpunchcards.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.createpunchcards.AllItems;
import com.example.createpunchcards.CreatePunchCards;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Mixing recipe generator. This one requires heat; drop the requiresHeat call for a
 * recipe with no heat requirement.
 */
public class CreatePunchCardsMixingRecipeGen extends MixingRecipeGen {

    GeneratedRecipe EXAMPLE = create("createpunchcards_mixing", b -> b
            .require(Items.STONE)
            .require(Items.CLAY_BALL)
            .output(AllItems.EXAMPLE_RESULT.get())
            .requiresHeat(HeatCondition.HEATED));

    public CreatePunchCardsMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreatePunchCards.ID);
    }
}
