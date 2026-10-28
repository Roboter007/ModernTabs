package de.Roboter007.moderntabs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.background.TabBackground;
import de.Roboter007.moderntabs.tab.button.TabButton;
import de.Roboter007.moderntabs.tab.content.TabContent;
import de.Roboter007.moderntabs.tab.designs.TabDesignReloadListener;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.tab.icon.TabIcon;
import de.Roboter007.moderntabs.tab.searchbar.Searchbar;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.*;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

// ToDo: merge TabTitle stuff, with banner text rendering stuff for the same text logic (targeted version: v1.8.0)
//  add full Resource Pack, KubeJs and code compatibility, so no separate stuff has to be created, but should be cross-compatible (targeted version: v1.8.0)
//  add support for fully customizing the content of the tabs (targeted version: v1.9.0)
//  creative mode tab editor (targeted version: v2.0.0)
//  update to newer versions than 1.21.1 (targeted version: after v2.0.0)
//  expand the banner system to EMI/JEI (targeted version: after v2.0.0)
public class ModernTabs {

    public static final String MOD_ID = "moderntabs";
    public static final String MOD_NAME = "ModernTabs";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);

    // Disabled by default
    private static boolean EXAMPLE_TAB_ENABLED = ModernTabsPlatform.get().isDevEnvironment();
    public static final TabDesignReloadListener TAB_DESIGN_RELOAD_LISTENER = new TabDesignReloadListener();
    private static final HashMap<ResourceLocation, TabDesign> UNAPPLIED_TAB_DESIGN_MAP = new HashMap<>();

    public static void logWarning(String warning) {
        LOGGER.warn("[" + MOD_NAME + "]  {}", "Please report this bug to the mod author via Github - https://github.com/Roboter007/ModernTabs/issues");
        LOGGER.warn("[" + MOD_NAME + "]  {}", warning);
    }

    public static void reload(Map<ResourceLocation, TabDesign> newEntries) {
        for(Map.Entry<ResourceLocation, TabDesign> entry : newEntries.entrySet()) {
            CreativeModeTab tab = BuiltInRegistries.CREATIVE_MODE_TAB.get(entry.getKey());
            if(tab != null) {
                TabDesign tabDesign = entry.getValue();
                tabDesign.apply(tab);
            }
        }
    }

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

        public static final Codec<TabDesign> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("sectionsEnabled", false).forGetter(TabDesign::areSectionsEnabled),
                Codec.BOOL.optionalFieldOf("allowLessVisibleRows", true).forGetter(TabDesign::doesAllowLessVisibleRows),
                TabBackground.CODEC.optionalFieldOf("background").forGetter(nullable(TabDesign::getBackground)),
                TabButton.CODEC.optionalFieldOf("button").forGetter(nullable(TabDesign::getTabButton)),
                Searchbar.CODEC.optionalFieldOf("searchbar").forGetter(nullable(TabDesign::getSearchbar)),
                CustomTabTitel.CODEC.optionalFieldOf("title").forGetter(nullable(TabDesign::getCustomTabTitel)),
                ResourceLocation.CODEC.optionalFieldOf("fillerLocation").forGetter(nullable(TabDesign::getFillerLocation)),
                ResourceLocation.CODEC.optionalFieldOf("scrollerLocation").forGetter(nullable(TabDesign::getScrollerLocation)),
                TabContent.CODEC.optionalFieldOf("content").forGetter(nullable(TabDesign::getTabContent))
        ).apply(instance, TabDesign::new));

        private boolean sectionsEnabled = false;
        private boolean allowLessVisibleRows = true;
        private TabBackground background = null;
        private TabButton tabButton = null;
        private Searchbar searchbar = null;
        private CustomTabTitel<?> customTabTitel = null;
        private ResourceLocation fillerLocation = DEFAULT_SPACE_FILLER;
        private ResourceLocation scrollerLocation = null;
        private TabContent tabContent = null;

        public TabDesign(boolean sectionsEnabled, boolean allowLessVisibleRows, @Nullable TabBackground background, @Nullable TabButton tabButton, @Nullable Searchbar searchbar, @Nullable CustomTabTitel<?> customTabTitel , @Nullable ResourceLocation fillerLocation, @Nullable ResourceLocation tabScrollerLocation, @Nullable TabContent tabContent) {
            this.sectionsEnabled = sectionsEnabled;
            this.allowLessVisibleRows = allowLessVisibleRows;
            this.background = background;
            this.tabButton = tabButton;
            this.searchbar = searchbar;
            this.customTabTitel = customTabTitel;
            this.fillerLocation = fillerLocation;
            this.scrollerLocation = tabScrollerLocation;
            this.tabContent = tabContent;
        }

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        public TabDesign(boolean sectionsEnabled, boolean allowLessVisibleRows, Optional<TabBackground> background, Optional<TabButton> tabButton, Optional<Searchbar> searchbar, Optional<CustomTabTitel<?>> customTabTitel , Optional<ResourceLocation> fillerLocation, Optional<ResourceLocation> tabScrollerLocation, Optional<TabContent> tabContent) {
            this(sectionsEnabled, allowLessVisibleRows, background.orElse(null), tabButton.orElse(null), searchbar.orElse(null), customTabTitel.orElse(null), fillerLocation.orElse(null), tabScrollerLocation.orElse(null), tabContent.orElse(null));
        }

        public TabDesign() {}

        public TabDesign withSections() {
            this.sectionsEnabled = true;
            return this;
        }

        public TabDesign sectionsEnabled(boolean sectionsEnabled) {
            this.sectionsEnabled = sectionsEnabled;
            return this;
        }

        public TabDesign withLessVisibleRows() {
            this.allowLessVisibleRows = true;
            return this;
        }

        public TabDesign allowLessVisibleRows(boolean allowLessVisibleRows) {
            this.allowLessVisibleRows = allowLessVisibleRows;
            return this;
        }

        public TabDesign searchbar(Searchbar searchbar) {
            this.searchbar = searchbar;
            return this;
        }

        public TabDesign withSearchbar() {
            this.searchbar = new Searchbar();
            return this;
        }

        public TabDesign tabButton(TabButton tabButton) {
            this.tabButton = tabButton;
            return this;
        }

        public TabDesign fillerLocation(ResourceLocation fillerLocation) {
            this.fillerLocation = fillerLocation;
            return this;
        }

        public TabDesign iconLocation(ResourceLocation tabIconLocation) {
            tabButton(Objects.requireNonNullElseGet(this.tabButton, TabButton::new));
            this.tabButton.icon(Objects.requireNonNullElseGet(this.tabButton.getIcon(), TabIcon::new).location(tabIconLocation));
            return this;
        }

        public TabDesign scrollerLocation(ResourceLocation tabScrollerLocation) {
            this.scrollerLocation = tabScrollerLocation;
            return this;
        }

        public TabDesign customTabTitel(CustomTabTitel<?> customTabTitel) {
            this.customTabTitel = customTabTitel;
            return this;
        }

        public TabDesign background(TabBackground background) {
            this.background = background;
            return this;
        }

        public TabDesign backgroundColor(SimpleColor backgroundColor) {
            return background(Objects.requireNonNullElseGet(this.background, TabBackground::new).color(backgroundColor));
        }

        public TabDesign color(SimpleColor color) {
            this.background = Objects.requireNonNullElseGet(this.background, TabBackground::new).color(color);
            return this;
        }

        public TabDesign color(SimpleColor color, String namespace, String tabIdentifier) {
            this.background = Objects.requireNonNullElseGet(this.background, TabBackground::new).color(color);
            return this;
        }

        public TabDesign tabContent(TabContent tabContent) {
            this.tabContent = tabContent;
            return this;
        }

        public void apply(CreativeModeTab tab) {
            if(tab == null) {
                throw new NullPointerException(ModernTabs.MOD_NAME +  "ModernTabs - couldn't find tab in the registry!");
            }
            ResourceLocation tabLocation = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab);
            CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;

            tabExtension.moderntabs$setSectionsEnabled(this.sectionsEnabled);
            tabExtension.moderntabs$setAllowLessVisibleRows(this.allowLessVisibleRows);

            tabExtension.moderntabs$setBackground(this.background);
            tabExtension.moderntabs$setCustomTabButton(this.tabButton != null ? this.tabButton.tabButtonSpritesLocation(tabLocation) : null);
            tabExtension.moderntabs$setCustomSearchbar(this.searchbar);
            tabExtension.moderntabs$setCustomTabTitel(this.customTabTitel);
            tabExtension.moderntabs$setFiller(this.fillerLocation);
            tabExtension.moderntabs$setCustomScroller(this.scrollerLocation);
            tabExtension.moderntabs$setCustomTabContent(this.tabContent);
        }

        public boolean areSectionsEnabled() {
            return sectionsEnabled;
        }

        public boolean doesAllowLessVisibleRows() {
            return allowLessVisibleRows;
        }

        public TabBackground getBackground() {
            return background;
        }

        public TabButton getTabButton() {
            return tabButton;
        }

        public Searchbar getSearchbar() {
            return searchbar;
        }

        public CustomTabTitel<?> getCustomTabTitel() {
            return customTabTitel;
        }

        public ResourceLocation getFillerLocation() {
            return fillerLocation;
        }

        public ResourceLocation getScrollerLocation() {
            return scrollerLocation;
        }

        public TabContent getTabContent() {
            return tabContent;
        }

    }
}
