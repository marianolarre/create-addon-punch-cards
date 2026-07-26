package com.example.createpunchcards.content.kinetics;

import net.minecraft.util.Mth;

public final class DrumComputerKinetics {

    /** Placeholder RPM magnitude until advanced computeTargetSpeed exists. */
    public static final float STUB_TARGET_RPM = 16f;

    private DrumComputerKinetics() {}

    public static boolean axesAligned(String drumAxis, String computerAxis) {
        return drumAxis.equals(computerAxis);
    }

    /**
     * Create {@code propagateRotationTo} factor: {@code computerSpeed * ratio == signedTarget}.
     */
    public static float speedRatioFromComputer(float computerSpeed, float targetRpmMagnitude) {
        if (Mth.equal(computerSpeed, 0))
            return 0f;
        float signedTarget = Math.copySign(Math.abs(targetRpmMagnitude), computerSpeed);
        return signedTarget / computerSpeed;
    }
}
