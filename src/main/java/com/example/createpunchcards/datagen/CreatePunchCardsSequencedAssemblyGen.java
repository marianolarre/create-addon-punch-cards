package com.example.createpunchcards.datagen;

import java.util.concurrent.CompletableFuture;

import com.example.createpunchcards.AllItems;
import com.example.createpunchcards.CreatePunchCards;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider.GeneratedRecipe;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

/**
 * Sequenced assembly recipe generator. Shows the multi-step form: a transitional item
 * carried between steps, a loop count, and per-step sub-recipes (a deployer
 * application followed by a press).
 */
public class CreatePunchCardsSequencedAssemblyGen extends SequencedAssemblyRecipeGen {

    GeneratedRecipe EXAMPLE = create("createpunchcards_result", b -> b
            .require(com.simibubi.create.AllItems.CARDBOARD)
            .transitionTo(AllItems.INCOMPLETE_PUNCH_CARD.get())
            .addOutput(AllItems.PUNCH_CARD.get(), 1f)
            .loops(6)
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(com.simibubi.create.AllItems.CARDBOARD))
            .addStep(PressingRecipe::new, rb -> rb));

    public CreatePunchCardsSequencedAssemblyGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreatePunchCards.ID);
    }
}
