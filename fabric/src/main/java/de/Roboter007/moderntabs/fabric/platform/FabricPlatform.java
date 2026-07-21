package de.Roboter007.moderntabs.fabric.platform;

import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;

import static de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column.*;

public class FabricPlatform extends ModernTabsPlatform {

    @Override
    public Column fromInteger(int column) {
        if(column == 0) {
            return LEFT;
        } else {
            return MIDDLE;
        }
    }
}