package com.example.createpunchcards.content.kinetics;

import java.util.List;

import com.example.createpunchcards.Lang;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Block entity for the computer kinetic block. Extending KineticBlockEntity ties it
 * into the kinetic network as a consumer.
 */
public class ComputerBlockEntity extends KineticBlockEntity {

    /** Stress Units this block draws per RPM (goggle tooltip readout). */
    public static final float STRESS_IMPACT = 8.0f;

    public ComputerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
        if (!(other instanceof DrumBlockEntity))
            return false;
        if (!other.getBlockPos().equals(worldPosition.above()))
            return false;
        return DrumComputerKinetics.axesAligned(
                otherState.getValue(DrumBlock.AXIS).getSerializedName(),
                ComputerBlock.getShaftFacing(state).getAxis().getSerializedName());
    }

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff,
            boolean connectedViaAxes, boolean connectedViaCogs) {
        if (!(target instanceof DrumBlockEntity drum))
            return 0;
        if (diff.getX() != 0 || diff.getY() != 1 || diff.getZ() != 0)
            return 0;
        if (!DrumComputerKinetics.axesAligned(
                stateTo.getValue(DrumBlock.AXIS).getSerializedName(),
                ComputerBlock.getShaftFacing(stateFrom).getAxis().getSerializedName()))
            return 0;
        return DrumComputerKinetics.speedRatioFromComputer(getTheoreticalSpeed(), drum.computeTargetSpeed());
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        int draw = (int) (Math.abs(getSpeed()) * STRESS_IMPACT);
        tooltip.add(Component.literal("    ")
                .append(Lang.translate(Lang.KINETIC_CONSUMING).withStyle(ChatFormatting.GRAY)));
        tooltip.add(Component.literal("        ")
                .append(Lang.translate(Lang.SU, draw).withStyle(ChatFormatting.AQUA)));
        return true;
    }
}
