package de.Roboter007.moderntabs.platform;

import net.minecraft.world.item.CreativeModeTab;

import java.util.List;
import java.util.Objects;

public abstract class ModernTabsPlatform {

    private static ModernTabsPlatform INSTANCE;

    public static void init(ModernTabsPlatform impl) {
        INSTANCE = impl;
    }

    public static ModernTabsPlatform get() {
        return Objects.requireNonNull(INSTANCE, "ModernTabsPlatform not initialized");
    }

    public ModernTabsPlatform() {}

    public abstract List<String> getLoadedMods();
    public abstract CreativeModeTab.Builder creativeBuilder();
    public abstract boolean isDevEnvironment();
}
