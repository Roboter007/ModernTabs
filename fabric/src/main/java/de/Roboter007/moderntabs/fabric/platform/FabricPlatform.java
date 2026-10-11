package de.Roboter007.moderntabs.fabric.platform;

import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.List;

public class FabricPlatform extends ModernTabsPlatform {


    @Override
    public List<String> getLoadedMods() {
        return FabricLoader.getInstance().getAllMods().stream().map(mod -> mod.getMetadata().getId()).toList();
    }

    @Override
    public CreativeModeTab.Builder creativeBuilder() {
        return FabricItemGroup.builder();
    }

    @Override
    public boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

}