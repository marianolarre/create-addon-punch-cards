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
 * Half-shaft follows computer kinetics; slim cog is visual-only and tracks drum angle when a
 * drum is above (idle at 0 otherwise).
 */
public class ComputerVisual extends KineticBlockEntityVisual<ComputerBlockEntity> implements SimpleDynamicVisual {

    private final RotatingInstance shaft;
    private final RotatingInstance slimCog;

    public ComputerVisual(VisualizationContext context, ComputerBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(com.simibubi.create.AllPartialModels.SHAFT_HALF))
                .createInstance();
        slimCog = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SLIM_COGWHEEL))
                .createInstance();
        setupInstances(partialTick);
    }

    private void setupInstances(float partialTick) {
        Direction shaftFacing = ComputerBlock.getShaftFacing(blockState);
        Direction cogFacing = ComputerBlock.getSlimCogFacing(blockState);

        shaft.rotation.identity();
        shaft.rotateToFace(Direction.SOUTH, shaftFacing)
                .setup(blockEntity)
                .setPosition(getVisualPosition())
                .setChanged();

        slimCog.rotation.identity();
        slimCog.rotateToFace(Direction.UP, cogFacing)
                .setRotationAxis(shaftFacing.getAxis())
                .setRotationalSpeed(0)
                .setRotationOffset(blockEntity.getSlimCogAngle(partialTick))
                .setPosition(getVisualPosition())
                .setChanged();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        slimCog.setRotationalSpeed(0)
                .setRotationOffset(blockEntity.getSlimCogAngle(ctx.partialTick()))
                .setChanged();
    }

    @Override
    public void update(float pt) {
        setupInstances(pt);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(shaft, slimCog);
    }

    @Override
    protected void _delete() {
        shaft.delete();
        slimCog.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(shaft);
        consumer.accept(slimCog);
    }
}
