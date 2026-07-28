package com.example.createpunchcards.content.kinetics;

import com.example.createpunchcards.AllPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Half-shaft on the left face; visual-only slim cog on the right, synced to a drum above.
 */
public class ComputerRenderer extends KineticBlockEntityRenderer<ComputerBlockEntity> {

    public ComputerRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(ComputerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
            int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState state = be.getBlockState();
        Direction shaftFacing = ComputerBlock.getShaftFacing(state);
        SuperByteBuffer shaft = CachedBuffers.partialFacing(com.simibubi.create.AllPartialModels.SHAFT_HALF, state,
                shaftFacing);
        standardKineticRotationTransform(shaft, be, light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));

        Direction cogFacing = ComputerBlock.getSlimCogFacing(state);
        SuperByteBuffer cog = CachedBuffers.partialFacingVertical(AllPartialModels.SLIM_COGWHEEL, state, cogFacing);
        kineticRotationTransform(cog, be, shaftFacing.getAxis(), AngleHelper.rad(be.getSlimCogAngle(partialTicks)),
                light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ComputerBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(com.simibubi.create.AllPartialModels.SHAFT_HALF, state,
                ComputerBlock.getShaftFacing(state));
    }
}
