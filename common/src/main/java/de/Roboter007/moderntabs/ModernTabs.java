package de.Roboter007.moderntabs;

import de.Roboter007.moderntabs.background.config.TabIconBackground;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Set;

public class ModernTabs {

    public static final String MOD_ID = "moderntabs";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);
    private static boolean exampleTab = true;

    private static final Set<CreativeModeTab> SECTIONED_TABS = Collections.newSetFromMap(new IdentityHashMap<>());
    private static final HashMap<CreativeModeTab, TabIconBackground> TABS_WITH_CUSTOM_ICON_BACKGROUND = new HashMap<>();


    private ModernTabs() {}

    // Sections for Creative Tab Banners -> have to be enabled to work
    public static void enableSections(final CreativeModeTab tab) {
        SECTIONED_TABS.add(tab);
    }

    public static boolean hasSections(final CreativeModeTab tab) {
        return tab != null && SECTIONED_TABS.contains(tab);
    }

    // Custom Tab Icon Background -> have to be enabled to work
    public static void enableCustomIconBackground(CreativeModeTab creativeModeTab, TabIconBackground creativeTabBackgrounds) {
        TABS_WITH_CUSTOM_ICON_BACKGROUND.put(creativeModeTab, creativeTabBackgrounds);
    }

    public static boolean hasCustomTabBackground(CreativeModeTab creativeModeTab) {
        return TABS_WITH_CUSTOM_ICON_BACKGROUND.containsKey(creativeModeTab) && TABS_WITH_CUSTOM_ICON_BACKGROUND.get(creativeModeTab) != null;
    }

    public static TabIconBackground getCustomTabBackground(CreativeModeTab creativeModeTab) {
        return TABS_WITH_CUSTOM_ICON_BACKGROUND.get(creativeModeTab);
    }

    // Example Tab
    public static boolean isExampleTabEnabled() {
        return exampleTab;
    }

    public static void setExampleTabEnabled(boolean exampleTab) {
        ModernTabs.exampleTab = exampleTab;
    }

    // Utility
    public static ResourceLocation path(final String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
