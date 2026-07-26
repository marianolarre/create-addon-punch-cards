package com.example.createpunchcards.content.ponder;

import com.example.createpunchcards.AllBlocks;
import com.example.createpunchcards.CreatePunchCards;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

/**
 * Ponder plugin for the addon, registered client-side in CreatePunchCards. registerScenes
 * associates a storyboard with one or more items. Each scene has two parts: a schematic
 * saved as an nbt file under assets/createpunchcards/ponder, whose name matches the id passed
 * to addStoryBoard, and the storyboard code in CreatePunchCardsPonderScenes.
 */
public class CreatePunchCardsPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return CreatePunchCards.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(AllBlocks.EXAMPLE_KINETIC_BLOCK.getId())
                .addStoryBoard("createpunchcards_ponder", CreatePunchCardsPonderScenes::examplePonder);
    }
}
