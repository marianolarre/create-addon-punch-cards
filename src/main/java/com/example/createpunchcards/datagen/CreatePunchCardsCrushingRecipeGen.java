package com.example.createpunchcards.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.createpunchcards.CreatePunchCards;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Crushing recipe generator. Shows a guaranteed output alongside a chance-based
 * secondary output.
 */
public class CreatePunchCardsCrushingRecipeGen extends CrushingRecipeGen {

    GeneratedRecipe EXAMPLE = create("createpunchcards_crushing", b -> b
            .require(Items.COBBLESTONE)
            .output(Items.GRAVEL)
            .output(0.25f, Items.FLINT)
            .duration(150));

    public CreatePunchCardsCrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreatePunchCards.ID);
    }
}
