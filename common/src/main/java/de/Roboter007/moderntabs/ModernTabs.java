package de.Roboter007.moderntabs;

import de.Roboter007.moderntabs.iconBackground.config.ColoredTabIconBackground;
import de.Roboter007.moderntabs.iconBackground.config.TabIconBackground;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModernTabs {

    public static final String MOD_ID = "moderntabs";
    public static final String MOD_NAME = "ModernTabs";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);

    // Disabled by default
    private static boolean EXAMPLE_TAB_ENABLED = ModernTabsPlatform.get().isDevEnvironment();
    private static final HashMap<ResourceLocation, TabDesign> UNAPPLIED_TAB_DESIGN_MAP = new HashMap<>();


    // Example Tab -> has to be enabled to work
    public static boolean isExampleTabEnabled() {
        return EXAMPLE_TAB_ENABLED;
    }

    public static void setExampleTabEnabled(boolean exampleTab) {
        ModernTabs.EXAMPLE_TAB_ENABLED = exampleTab;
    }

    // no need for look up in Registry
    public static void configureTab(CreativeModeTab tab, TabDesign tabDesign) {
        tabDesign.apply(tab);
    }

    // needs to look up the instance for the CreativeModeTab in the registry
    public static void configureTab(ResourceLocation tabLocation, TabDesign tabDesign) {
        UNAPPLIED_TAB_DESIGN_MAP.put(tabLocation, tabDesign);
    }

    public static void configureTab(String tabId, TabDesign tabDesign) {
        UNAPPLIED_TAB_DESIGN_MAP.put(ResourceLocation.parse(tabId), tabDesign);
    }

    public static void applyTabDesign() {
        for(Map.Entry<ResourceLocation, TabDesign> entry : UNAPPLIED_TAB_DESIGN_MAP.entrySet()) {
            CreativeModeTab tab = BuiltInRegistries.CREATIVE_MODE_TAB.get(entry.getKey());
            TabDesign tabDesign = entry.getValue();
            tabDesign.apply(tab);
        }
    }

    // Utility
    public static ResourceLocation path(final String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class TabDesign {
        private boolean sectionsEnabled;
        private TabIconBackground tabIconBackground;
        private ResourceLocation tabIconLocation;
        private ResourceLocation tabScrollerLocation;
        private CustomTabTitel customTabTitel;
        private ModernColor backgroundColor;

        public TabDesign(boolean sectionsEnabled, @Nullable TabIconBackground tabIconBackground, @Nullable ResourceLocation tabIconLocation, @Nullable ResourceLocation tabScrollerLocation, @Nullable CustomTabTitel customTabTitel, @Nullable ModernColor backgroundColor) {
            this.sectionsEnabled = sectionsEnabled;
            this.tabIconBackground = tabIconBackground;
            this.tabIconLocation = tabIconLocation;
            this.tabScrollerLocation = tabScrollerLocation;
            this.customTabTitel = customTabTitel;
            this.backgroundColor = backgroundColor;
        }

        public TabDesign() {
            this(false, null, null, null, null, null);
        }

        public TabDesign sectionsEnabled(boolean sectionsEnabled) {
            this.sectionsEnabled = sectionsEnabled;
            return this;
        }

        public TabDesign tabIconBackground(TabIconBackground tabIconBackground) {
            this.tabIconBackground = tabIconBackground;
            return this;
        }

        public TabDesign tabIconLocation(ResourceLocation tabIconLocation) {
            this.tabIconLocation = tabIconLocation;
            return this;
        }

        public TabDesign tabScrollerLocation(ResourceLocation tabScrollerLocation) {
            this.tabScrollerLocation = tabScrollerLocation;
            return this;
        }

        public TabDesign customTabTitel(CustomTabTitel customTabTitel) {
            this.customTabTitel = customTabTitel;
            return this;
        }

        public TabDesign backgroundColor(ModernColor backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public TabDesign color(ModernColor color) {
            this.backgroundColor = color;
            this.tabIconBackground = new ColoredTabIconBackground(color);
            return this;
        }

        public TabDesign color(ModernColor color, String namespace, String tabIdentifier) {
            this.backgroundColor = color;
            this.tabIconBackground = new ColoredTabIconBackground(color, namespace, tabIdentifier);
            return this;
        }

        public void apply(CreativeModeTab tab) {
            if(tab == null) {
                throw new NullPointerException(ModernTabs.MOD_NAME +  "ModernTabs - couldn't find tab in the registry!");
            }
            CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;

            tabExtension.moderntabs$setSectionsEnabled(this.sectionsEnabled);
            tabExtension.moderntabs$setCustomTabIconBackground(this.tabIconBackground);
            tabExtension.moderntabs$setCustomTabIcon(this.tabIconLocation);
            tabExtension.moderntabs$setCustomScroller(this.tabScrollerLocation);
            tabExtension.moderntabs$setCustomTabTitel(this.customTabTitel);
            tabExtension.moderntabs$setBackgroundColor(this.backgroundColor);
        }


        public boolean areSectionsEnabled() {
            return this.sectionsEnabled;
        }

        public Optional<TabIconBackground> getTabIconBackground() {
            return Optional.of(this.tabIconBackground);
        }

        public Optional<ResourceLocation> getTabIconLocation() {
            return Optional.of(this.tabIconLocation);
        }

        public Optional<CustomTabTitel> getCustomTabTitel() {
            return Optional.of(this.customTabTitel);
        }

        public Optional<ModernColor> getBackgroundColor() {
            return Optional.of(this.backgroundColor);
        }

    }
}
