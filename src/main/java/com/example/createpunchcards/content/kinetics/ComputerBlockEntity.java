package com.example.createpunchcards.content.kinetics;

import java.util.List;

import com.example.createpunchcards.Lang;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

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

    /** Drum directly above, if any. Used only for visual slim-cog sync. */
    @Nullable
    public DrumBlockEntity getDrumAbove() {
        if (level == null)
            return null;
        return level.getBlockEntity(worldPosition.above()) instanceof DrumBlockEntity drum ? drum : null;
    }

    /** Half-tooth phase so the reversed computer cog meshes with the drum cog (45° tooth pitch). */
    private static final float SLIM_COG_MESH_OFFSET = 22.5f;

    /**
     * Slim cog angle in degrees. Tracks the drum above when present (reversed + mesh offset);
     * otherwise 0. Does not use computer shaft speed (visual-only, drum-driven).
     */
    public float getSlimCogAngle(float partialTicks) {
        DrumBlockEntity drum = getDrumAbove();
        return drum == null ? 0f : -drum.getAngle(partialTicks) + SLIM_COG_MESH_OFFSET;
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
