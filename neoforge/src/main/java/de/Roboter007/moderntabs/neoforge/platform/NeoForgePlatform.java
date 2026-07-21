package de.Roboter007.moderntabs.neoforge.platform;

import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.minecraft.world.item.CreativeModeTab;

import static de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column.*;

public class NeoForgePlatform extends ModernTabsPlatform {

    /*@Override
    public Column fromInteger(int column) {
        if(column % 5 == 0) {
            return LEFT;
        } else {
            return MIDDLE;
        }
    } */

    /*@Override
    public Column fromInteger(int column) {
        if(column == 0) {
            return LEFT;
        } else {
            return MIDDLE;
        }
    } */

    @Override
    public CreativeModeTab.Builder creativeBuilder() {
        return CreativeModeTab.builder();
    }
}