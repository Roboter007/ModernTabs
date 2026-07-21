package de.Roboter007.moderntabs.neoforge.platform;

import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;

import static de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column.*;

public class NeoForgePlatform extends ModernTabsPlatform {

    @Override
    public Column fromInteger(int column) {
        if(column % 5 == 0) {
            return LEFT;
        } else {
            return MIDDLE;
        }
    }
}