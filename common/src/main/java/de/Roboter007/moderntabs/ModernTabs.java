package de.Roboter007.moderntabs;

import de.Roboter007.moderntabs.background.config.TabIconBackground;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//ToDo: add way for fabric to add a scroller custom texture
public class ModernTabs {

    public static final String MOD_ID = "moderntabs";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);
    // Disabled by default
    private static boolean exampleTab = ModernTabsPlatform.get().isDevEnvironment();


    private ModernTabs() {}


    // Example Tab -> has to be enabled to work
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

    public static Builder builder(CreativeModeTab creativeModeTab) {
        return new Builder(creativeModeTab);
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


        public Builder withCustomTabIcon(ResourceLocation tabIconLocation) {
            this.tabExtension.moderntabs$setCustomTabIcon(tabIconLocation);
            return this;
        }

        public Builder withCustomScroller(ResourceLocation tabIconLocation) {
            this.tabExtension.moderntabs$setCustomScroller(tabIconLocation);
            return this;
        }

        public Builder withCustomTitelRendering(CustomTabTitel tabTitelRendererConfig) {
            this.tabExtension.moderntabs$setCustomTabTitel(tabTitelRendererConfig);
            return this;
        }

        public CreativeModeTab creativeModeTab() {
            return creativeModeTab;
        }
    }
}
