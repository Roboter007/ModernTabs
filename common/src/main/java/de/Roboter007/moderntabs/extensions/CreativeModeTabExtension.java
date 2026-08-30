package de.Roboter007.moderntabs.extensions;

import de.Roboter007.moderntabs.iconBackground.config.TabIconBackground;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface CreativeModeTabExtension {

    void modernTabs$addItem(ItemStack stack, boolean searchable);

    void moderntabs$setSectionsEnabled(boolean sectionsEnabled);
    boolean moderntabs$hasCustomSections();

    void moderntabs$setCustomTabTitel(CustomTabTitel customTabTitel);
    CustomTabTitel moderntabs$getCustomTabTitel();
    default boolean moderntabs$hasCustomTabTitelRendering() {
        return moderntabs$getCustomTabTitel() != null;
    }

    void moderntabs$setCustomTabIconBackground(TabIconBackground tabIconBackground);
    TabIconBackground moderntabs$getCustomTabIconBackground();
    default boolean moderntabs$hasCustomTabIconBackground() {
        return moderntabs$getCustomTabIconBackground() != null;
    }

    ResourceLocation moderntabs$getCustomTabIcon();
    void moderntabs$setCustomTabIcon(ResourceLocation tabIconLocation);
    default boolean moderntabs$hasCustomTabIcon() {
        return moderntabs$getCustomTabIcon() != null;
    }

    ResourceLocation moderntabs$getCustomScroller();
    void moderntabs$setCustomScroller(ResourceLocation scrollerLocation);
    default boolean moderntabs$hasCustomScroller() {
        return moderntabs$getCustomScroller() != null;
    }

    ModernColor moderntabs$getBackgroundColor();
    void moderntabs$setBackgroundColor(ModernColor backgroundColor);
    default boolean moderntabs$hasCustomBackgroundColor() {
        return moderntabs$getBackgroundColor() != null;
    }

}
