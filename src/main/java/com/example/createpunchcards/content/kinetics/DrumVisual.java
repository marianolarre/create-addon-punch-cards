package com.example.createpunchcards.content.kinetics;

import java.util.function.Consumer;

import com.example.createpunchcards.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction;

/**
 * Spins the drum from the BE's persisted angle. The drum model is authored along +X
 * (EAST), so orientation starts from EAST rather than UP.
 */
public class DrumVisual extends KineticBlockEntityVisual<DrumBlockEntity> implements SimpleDynamicVisual {

    /** Natural cylinder axis of {@code drum_block.json}. */
    private static final Direction MODEL_AXIS = Direction.EAST;

    private final RotatingInstance rotatingModel;

    public DrumVisual(VisualizationContext context, DrumBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        rotatingModel = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.DRUM))
                .createInstance()
                .rotateToFace(MODEL_AXIS, rotationAxis())
                .setRotationAxis(rotationAxis())
                .setRotationalSpeed(0)
                .setRotationOffset(blockEntity.getAngle(partialTick))
                .setPosition(getVisualPosition());
        rotatingModel.setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        rotatingModel.setRotationalSpeed(0)
                .setRotationOffset(blockEntity.getAngle(ctx.partialTick()))
                .setChanged();
    }

    @Override
    public void update(float pt) {
        rotatingModel.rotation.identity();
        rotatingModel.rotateToFace(MODEL_AXIS, rotationAxis())
                .setRotationAxis(rotationAxis())
                .setRotationalSpeed(0)
                .setRotationOffset(blockEntity.getAngle(pt))
                .setPosition(getVisualPosition())
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
    }
}
