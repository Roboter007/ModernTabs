package de.Roboter007.moderntabs;

import de.Roboter007.moderntabs.tab.button.ColoredTabButtonStates;
import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
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
        private static final ResourceLocation DEFAULT_SPACE_FILLER = ModernTabs.path("filler/default_space_filler");

        private boolean sectionsEnabled;
        private boolean allowLessVisibleRows;
        private TabButtonStates tabButtonStates;
        private ResourceLocation fillerLocation;
        private ResourceLocation iconLocation;
        private ResourceLocation scrollerLocation;
        private CustomTabTitel customTabTitel;
        private ModernColor backgroundColor;

        public TabDesign(boolean sectionsEnabled, boolean allowLessVisibleRows, @Nullable TabButtonStates tabButtonStates, @Nullable ResourceLocation fillerLocation, @Nullable ResourceLocation tabIconLocation, @Nullable ResourceLocation tabScrollerLocation, @Nullable CustomTabTitel customTabTitel, @Nullable ModernColor backgroundColor) {
            this.sectionsEnabled = sectionsEnabled;
            this.allowLessVisibleRows = allowLessVisibleRows;
            this.tabButtonStates = tabButtonStates;
            this.fillerLocation = fillerLocation;
            this.iconLocation = tabIconLocation;
            this.scrollerLocation = tabScrollerLocation;
            this.customTabTitel = customTabTitel;
            this.backgroundColor = backgroundColor;
        }

        public TabDesign() {
            this(false, true, null, DEFAULT_SPACE_FILLER, null, null, null, null);
        }

        public TabDesign sectionsEnabled(boolean sectionsEnabled) {
            this.sectionsEnabled = sectionsEnabled;
            return this;
        }

        public TabDesign allowLessVisibleRows(boolean allowLessVisibleRows) {
            this.allowLessVisibleRows = allowLessVisibleRows;
            return this;
        }

        public TabDesign tabButtonStates(TabButtonStates tabButtonStates) {
            this.tabButtonStates = tabButtonStates;
            return this;
        }

        public TabDesign fillerLocation(ResourceLocation fillerLocation) {
            this.fillerLocation = fillerLocation;
            return this;
        }

        public TabDesign iconLocation(ResourceLocation tabIconLocation) {
            this.iconLocation = tabIconLocation;
            return this;
        }

        public TabDesign scrollerLocation(ResourceLocation tabScrollerLocation) {
            this.scrollerLocation = tabScrollerLocation;
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
            this.tabButtonStates = new ColoredTabButtonStates(color);
            return this;
        }

        public TabDesign color(ModernColor color, String namespace, String tabIdentifier) {
            this.backgroundColor = color;
            this.tabButtonStates = new ColoredTabButtonStates(color, namespace, tabIdentifier);
            return this;
        }

        public void apply(CreativeModeTab tab) {
            if(tab == null) {
                throw new NullPointerException(ModernTabs.MOD_NAME +  "ModernTabs - couldn't find tab in the registry!");
            }
            CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;

            tabExtension.moderntabs$setSectionsEnabled(this.sectionsEnabled);
            tabExtension.moderntabs$setAllowLessVisibleRows(this.allowLessVisibleRows);
            tabExtension.moderntabs$setCustomTabButtonStates(this.tabButtonStates);
            tabExtension.moderntabs$setFiller(this.fillerLocation);
            tabExtension.moderntabs$setCustomTabIcon(this.iconLocation);
            tabExtension.moderntabs$setCustomScroller(this.scrollerLocation);
            tabExtension.moderntabs$setCustomTabTitel(this.customTabTitel);
            tabExtension.moderntabs$setBackgroundColor(this.backgroundColor);
        }


        public boolean areSectionsEnabled() {
            return this.sectionsEnabled;
        }

        public boolean allowLessVisibleRows() {
            return this.allowLessVisibleRows;
        }

        public Optional<TabButtonStates> getTabButtonStates() {
            return Optional.of(this.tabButtonStates);
        }

        public Optional<ResourceLocation> getFillerLocation() {
            return Optional.of(this.fillerLocation);
        }

        public Optional<ResourceLocation> getIconLocation() {
            return Optional.of(this.iconLocation);
        }

        public Optional<ResourceLocation> getScrollerLocation() {
            return Optional.of(this.scrollerLocation);
        }

        public Optional<CustomTabTitel> getCustomTabTitel() {
            return Optional.of(this.customTabTitel);
        }

        public Optional<ModernColor> getBackgroundColor() {
            return Optional.of(this.backgroundColor);
        }

    }
}
