package com.example.createpunchcards.content.kinetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DrumComputerKineticsTest {

    @Test
    void axesAligned_sameAxis_true() {
        assertTrue(DrumComputerKinetics.axesAligned("x", "x"));
    }

    @Test
    void axesAligned_differentAxis_false() {
        assertFalse(DrumComputerKinetics.axesAligned("y", "x"));
    }

    @Test
    void speedRatio_zeroComputer_returnsZero() {
        assertEquals(0f, DrumComputerKinetics.speedRatioFromComputer(0f, 16f), 1e-6f);
    }

    @Test
    void speedRatio_mapsComputerToConstantTarget() {
        float ratio = DrumComputerKinetics.speedRatioFromComputer(64f, 16f);
        assertEquals(16f, 64f * ratio, 1e-4f);
    }

    @Test
    void speedRatio_preservesSign() {
        float ratio = DrumComputerKinetics.speedRatioFromComputer(-32f, 16f);
        assertEquals(-16f, -32f * ratio, 1e-4f);
    }
}
