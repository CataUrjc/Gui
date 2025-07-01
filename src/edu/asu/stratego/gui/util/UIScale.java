package edu.asu.stratego.gui.util;

import java.awt.*;

public class UIScale {
    private static final double UNIT;
    private static int SIDE = 0;

    // Static block to calculate UI dimensions based on screen size
    static {
        int side1;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculate SIDE to be a multiple of 12 for consistent unit size
        side1 = (int) (0.85 * screenSize.getHeight());
        SIDE = (side1 / 12) * 12;
        UNIT = SIDE / 12.0;
    }
    public static double getUnit() {
        return UNIT;
    }

    public static int getSide() {
        return SIDE;
    }
}
