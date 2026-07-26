package com.example.createpunchcards.content.kinetics;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

/** Spins the drum's own block model around its AXIS — no shaft. */
public class DrumRenderer extends KineticBlockEntityRenderer<DrumBlockEntity> {

    public DrumRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
}
