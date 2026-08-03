package de.Roboter007.moderntabs.fabric.platform;

import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTab;

public class FabricPlatform extends ModernTabsPlatform {

    @Override
    public CreativeModeTab.Builder creativeBuilder() {
        return FabricItemGroup.builder();
    }

    @Override
    public boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

}