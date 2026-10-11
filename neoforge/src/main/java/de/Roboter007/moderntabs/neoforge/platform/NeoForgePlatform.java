package de.Roboter007.moderntabs.neoforge.platform;

import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.List;

public class NeoForgePlatform extends ModernTabsPlatform {


    @Override
    public List<String> getLoadedMods() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

    @Override
    public CreativeModeTab.Builder creativeBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }
}