package de.Roboter007.moderntabs.tab.extensions;

import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface CreativeModeTabExtension {

    void modernTabs$addItem(ItemStack stack, boolean searchable);

    void moderntabs$setSectionsEnabled(boolean sectionsEnabled);
    boolean moderntabs$hasCustomSections();

    void moderntabs$setAllowLessVisibleRows(boolean allowLessVisibleRows);
    boolean moderntabs$doesAllowLessVisibleRows();

    void moderntabs$setCustomTabTitel(CustomTabTitel customTabTitel);
    CustomTabTitel moderntabs$getCustomTabTitel();
    default boolean moderntabs$hasCustomTabTitelRendering() {
        return moderntabs$getCustomTabTitel() != null;
    }

    void moderntabs$setCustomTabButtonStates(TabButtonStates tabButtonStates);
    TabButtonStates moderntabs$getCustomTabButtonStates();
    default boolean moderntabs$hasCustomTabButtonStates() {
        return moderntabs$getCustomTabButtonStates() != null;
    }

    ResourceLocation moderntabs$getFiller();
    void moderntabs$setFiller(ResourceLocation tabIconLocation);
    default boolean moderntabs$hasFiller() {
        return moderntabs$getFiller() != null;
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
