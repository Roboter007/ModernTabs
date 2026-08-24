package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.iconBackground.config.ColoredTabIconBackground;
import de.Roboter007.moderntabs.iconBackground.config.TabIconBackground;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernTabsKubeJs {

    public ModernTabsKubeJs() {}


    public static boolean isExampleTabEnabled() {
        return ModernTabs.isExampleTabEnabled();
    }

    public static void setExampleTabEnabled(boolean exampleTab) {
        ModernTabs.setExampleTabEnabled(exampleTab);
    }

    public static Builder builder(String tabId) {
        return new Builder(BuiltInRegistries.CREATIVE_MODE_TAB.get(ResourceLocation.parse(tabId)));
    }

    public static class Builder {

        private final CreativeModeTab creativeModeTab;
        private final CreativeModeTabExtension tabExtension;

        private Builder(CreativeModeTab creativeModeTab) {
            this.creativeModeTab = creativeModeTab;
            this.tabExtension = (CreativeModeTabExtension) creativeModeTab;
        }

        public Builder withEnabledSections(boolean sectionsEnabled) {
            this.tabExtension.moderntabs$setSectionsEnabled(sectionsEnabled);
            return this;
        }


        public Builder withCustomTabIconBackground(TabIconBackground tabIconBackground) {
            this.tabExtension.moderntabs$setCustomTabIconBackground(tabIconBackground);
            return this;
        }


        public Builder withCustomTabIcon(String tabIconLocation) {
            ResourceLocation resourceLocation = ResourceLocation.parse(tabIconLocation);
            this.tabExtension.moderntabs$setCustomTabIcon(resourceLocation);
            return this;
        }

        public Builder withCustomScroller(String tabIconLocation) {
            ResourceLocation resourceLocation = ResourceLocation.parse(tabIconLocation);
            this.tabExtension.moderntabs$setCustomScroller(resourceLocation);
            return this;
        }

        public Builder withCustomTitelRendering(CustomTabTitel tabTitelRendererConfig) {
            this.tabExtension.moderntabs$setCustomTabTitel(tabTitelRendererConfig);
            return this;
        }

        public Builder withCustomBackgroundColor(String backgroundColor) {
            ModernColor modernColor = new ModernColor(backgroundColor);
            this.tabExtension.moderntabs$setBackgroundColor(modernColor);
            return this;
        }

        public Builder withCustomColor(String color) {
            ModernColor modernColor = new ModernColor(color);
            this.tabExtension.moderntabs$setBackgroundColor(modernColor);
            this.tabExtension.moderntabs$setCustomTabIconBackground(new ColoredTabIconBackground(modernColor));
            return this;
        }

        public CreativeModeTab creativeModeTab() {
            return creativeModeTab;
        }
    }
}
