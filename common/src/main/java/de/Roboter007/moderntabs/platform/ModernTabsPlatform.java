package de.Roboter007.moderntabs.platform;

import net.minecraft.world.item.CreativeModeTab;

import java.util.Objects;

public abstract class ModernTabsPlatform {

    private static ModernTabsPlatform INSTANCE;

    public static void init(ModernTabsPlatform impl) {
        INSTANCE = impl;
    }

    public static ModernTabsPlatform get() {
        return Objects.requireNonNull(INSTANCE, "ModernTabsPlatform not initialized");
    }

    public static boolean isInitialized() {
        return INSTANCE != null;
    }

    public ModernTabsPlatform() {}

    public abstract CreativeModeTab.Builder creativeBuilder();
}
