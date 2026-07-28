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
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;

/**
 * Spins the drum and visual-only slim cogs from the BE's persisted angle. The drum model is
 * authored along +X (EAST); slim cogs are authored Y-up on the bottom face.
 */
public class DrumVisual extends KineticBlockEntityVisual<DrumBlockEntity> implements SimpleDynamicVisual {

    /** Natural cylinder axis of {@code drum_block.json}. */
    private static final Direction MODEL_AXIS = Direction.EAST;

    private final RotatingInstance rotatingModel;
    private final RotatingInstance slimCogPositive;
    private final RotatingInstance slimCogNegative;

    public DrumVisual(VisualizationContext context, DrumBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        rotatingModel = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.DRUM))
                .createInstance();
        slimCogPositive = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SLIM_COGWHEEL))
                .createInstance();
        slimCogNegative = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SLIM_COGWHEEL))
                .createInstance();
        setupInstances(partialTick);
    }

    private void setupInstances(float partialTick) {
        Direction.Axis axis = rotationAxis();
        Direction[] ends = Iterate.directionsInAxis(axis);
        float angle = blockEntity.getAngle(partialTick);

        rotatingModel.rotation.identity();
        rotatingModel.rotateToFace(MODEL_AXIS, rotationAxis())
                .setRotationAxis(axis)
                .setRotationalSpeed(0)
                .setRotationOffset(angle)
                .setPosition(getVisualPosition())
                .setChanged();

        orientSlimCog(slimCogPositive, ends[0], axis, angle);
        orientSlimCog(slimCogNegative, ends[1], axis, angle);
    }

    private void orientSlimCog(RotatingInstance instance, Direction face, Direction.Axis axis, float angle) {
        instance.rotation.identity();
        instance.rotateToFace(Direction.UP, face)
                .setRotationAxis(axis)
                .setRotationalSpeed(0)
                .setRotationOffset(angle)
                .setPosition(getVisualPosition())
                .setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        float angle = blockEntity.getAngle(ctx.partialTick());
        rotatingModel.setRotationalSpeed(0)
                .setRotationOffset(angle)
                .setChanged();
        slimCogPositive.setRotationalSpeed(0)
                .setRotationOffset(angle)
                .setChanged();
        slimCogNegative.setRotationalSpeed(0)
                .setRotationOffset(angle)
                .setChanged();
    }

    @Override
    public void update(float pt) {
        setupInstances(pt);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(rotatingModel, slimCogPositive, slimCogNegative);
    }

    @Override
    protected void _delete() {
        rotatingModel.delete();
        slimCogPositive.delete();
        slimCogNegative.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(rotatingModel);
        consumer.accept(slimCogPositive);
        consumer.accept(slimCogNegative);
    }
}
