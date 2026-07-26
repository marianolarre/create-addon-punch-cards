package com.example.createpunchcards;

import com.example.createpunchcards.content.kinetics.ComputerBlock;
import com.example.createpunchcards.content.kinetics.CreatePunchCardsKineticBlock;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.ModelFile;

/**
 * Block registration.
 */
public class AllBlocks {

    public static final BlockEntry<ComputerBlock> COMPUTER_BLOCK = CreatePunchCards.REGISTRATE
            .block("computer_block", ComputerBlock::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .properties(p -> p.noOcclusion())
            .blockstate((ctx, prov) -> {
                ModelFile model = prov.models()
                    .getExistingFile(ResourceLocation.fromNamespaceAndPath("createpunchcards", "block/computer_block"));
                prov.horizontalBlock(ctx.get(), model);
            })
            // here is where you can adjust how much stress your block scales with (for example rpm x 128 for this block is the amount of su)
            .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
            .item()
            .build()
            .register();

    public static final BlockEntry<CreatePunchCardsKineticBlock> DRUM_BLOCK = CreatePunchCards.REGISTRATE
            .block("drum_block", CreatePunchCardsKineticBlock::new)
            .initialProperties(() -> Blocks.ANDESITE)
            .properties(p -> p.noOcclusion())
            .blockstate((ctx, prov) -> {
                ModelFile model = prov.models()
                    .getExistingFile(ResourceLocation.fromNamespaceAndPath("createpunchcards", "block/drum_block"));
                BlockStateGen.axisBlock(ctx, prov, state -> model);
            })
            // here is where you can adjust how much stress your block scales with (for example rpm x 128 for this block is the amount of su)
            .onRegister(b -> BlockStressValues.IMPACTS.register(b, () -> 128))
            .item()
            .build()
            .register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
