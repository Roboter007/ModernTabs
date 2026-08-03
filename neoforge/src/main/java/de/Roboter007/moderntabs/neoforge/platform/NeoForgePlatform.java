package de.Roboter007.moderntabs.neoforge.platform;

import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.loading.FMLEnvironment;

public class NeoForgePlatform extends ModernTabsPlatform {

    @Override
    public CreativeModeTab.Builder creativeBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }
}