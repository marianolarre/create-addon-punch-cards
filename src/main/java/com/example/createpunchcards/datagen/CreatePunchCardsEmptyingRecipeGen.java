package com.example.createpunchcards.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.createpunchcards.CreatePunchCards;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Emptying recipe generator. Shows a fluid result, declared with withFluidOutputs.
 */
public class CreatePunchCardsEmptyingRecipeGen extends EmptyingRecipeGen {

    GeneratedRecipe EXAMPLE = create("createpunchcards_emptying", b -> b
            .require(Items.WATER_BUCKET)
            .output(Items.BUCKET)
            .withFluidOutputs(new FluidStack(Fluids.WATER, 1000)));

    public CreatePunchCardsEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreatePunchCards.ID);
    }
}
