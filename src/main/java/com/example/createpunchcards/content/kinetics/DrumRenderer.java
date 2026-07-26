package com.example.createpunchcards.content.kinetics;

import com.example.createpunchcards.AllPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.block.state.BlockState;

/** Spins the drum from the BE's persisted angle. Model is authored along +X. */
public class DrumRenderer extends KineticBlockEntityRenderer<DrumBlockEntity> {

    private static final Direction MODEL_AXIS = Direction.EAST;

    public DrumRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(DrumBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
            int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        Axis axis = getRotationAxisOf(be);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        SuperByteBuffer model = CachedBuffers.partialDirectional(AllPartialModels.DRUM, state, facing, () -> {
            PoseStack stack = new PoseStack();
            TransformStack.of(stack)
                    .center()
                    .rotateTo(MODEL_AXIS, facing)
                    .uncenter();
            return stack;
        });
        kineticRotationTransform(model, be, axis, AngleHelper.rad(be.getAngle(partialTicks)), light)
                .renderInto(ms, buffer.getBuffer(type));
    }
}
