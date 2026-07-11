package dev.moderntabs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class ModernTabs {

    public static final String MOD_ID = "moderntabs";
    private static boolean exampleTab = true;

    private static final Set<CreativeModeTab> SECTIONED_TABS = Collections.newSetFromMap(new IdentityHashMap<>());

    private ModernTabs() {}

    public static void enableSections(final CreativeModeTab tab) {
        SECTIONED_TABS.add(tab);
    }

    public static boolean hasSections(final CreativeModeTab tab) {
        return tab != null && SECTIONED_TABS.contains(tab);
    }

    public static ResourceLocation path(final String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static boolean isExampleTabEnabled() {
        return exampleTab;
    }

    public static void setExampleTabEnabled(boolean exampleTab) {
        ModernTabs.exampleTab = exampleTab;
    }
}
