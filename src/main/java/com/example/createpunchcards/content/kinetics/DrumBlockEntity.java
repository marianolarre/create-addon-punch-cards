package com.example.createpunchcards.content.kinetics;

import java.util.List;

import com.example.createpunchcards.Lang;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Block entity for the drum. Extending KineticBlockEntity ties it into the kinetic
 * network as a consumer. Stress impact is registered in AllBlocks; goggle tooltip
 * shows SU draw at current speed. Angle is accumulated and saved so the drum resumes
 * from the same orientation after stop/start and world reload.
 */
public class DrumBlockEntity extends KineticBlockEntity {

    /** Stress Units this block draws per RPM. Registered as its impact in AllBlocks. */
    public static final float STRESS_IMPACT = 8.0f;

    /** Absolute drum rotation in degrees. */
    private float angle;

    public DrumBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public float computeTargetSpeed() {
        return DrumComputerKinetics.STUB_TARGET_RPM;
    }

    /** Degrees, including partial-tick interpolation while spinning. */
    public float getAngle(float partialTicks) {
        return angle + getAngularSpeed() * partialTicks;
    }

    private float getAngularSpeed() {
        return convertToAngular(getSpeed());
    }

    @Override
    public void tick() {
        super.tick();
        float angular = getAngularSpeed();
        if (angular == 0)
            return;
        angle = (angle + angular) % 360f;
        if (angle < 0)
            angle += 360f;
    }

    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);
        // Keep client/server angle aligned when the drum stops or restarts.
        if ((previousSpeed == 0) != (getSpeed() == 0))
            sendData();
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putFloat("Angle", angle);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        angle = compound.getFloat("Angle");
        super.read(compound, registries, clientPacket);
    }

    @Override
    public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
        if (!(other instanceof ComputerBlockEntity))
            return false;
        if (!other.getBlockPos().equals(worldPosition.below()))
            return false;
        return DrumComputerKinetics.axesAligned(
                state.getValue(DrumBlock.AXIS).getSerializedName(),
                ComputerBlock.getShaftFacing(otherState).getAxis().getSerializedName());
    }

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff,
            boolean connectedViaAxes, boolean connectedViaCogs) {
        // Computer is the source; do not push speed downward from the drum.
        return 0;
    }

    /**
     * Adds custom lines to the Engineer's Goggles overlay. Calling super keeps the base
     * kinetic stress readout, then we append the SU this block is drawing at its current
     * speed. Returning true signals that the overlay has content to show.
     */
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
