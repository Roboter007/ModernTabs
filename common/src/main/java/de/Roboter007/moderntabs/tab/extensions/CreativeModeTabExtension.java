package de.Roboter007.moderntabs.tab.extensions;

import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.searchbar.Searchbar;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface CreativeModeTabExtension {

    // experimental
    void modernTabs$addItem(ItemStack stack, boolean searchable);

    // boolean options
    void moderntabs$setSectionsEnabled(boolean sectionsEnabled);
    boolean moderntabs$hasCustomSections();

    void moderntabs$setAllowLessVisibleRows(boolean allowLessVisibleRows);
    boolean moderntabs$doesAllowLessVisibleRows();

    // searchbar

    Searchbar moderntabs$getCustomSearchbar();
    void moderntabs$setCustomSearchbar(Searchbar searchbar);
    boolean moderntabs$hasCustomSearchbar();

    // tab titles
    void moderntabs$setCustomTabTitel(CustomTabTitel<?> customTabTitel);
    CustomTabTitel<?> moderntabs$getCustomTabTitel();
    default boolean moderntabs$hasCustomTabTitelRendering() {
        return moderntabs$getCustomTabTitel() != null;
    }

    // tab button states
    void moderntabs$setCustomTabButtonStates(TabButtonStates tabButtonStates);
    TabButtonStates moderntabs$getCustomTabButtonStates();
    default boolean moderntabs$hasCustomTabButtonStates() {
        return moderntabs$getCustomTabButtonStates() != null;
    }

    // filler
    ResourceLocation moderntabs$getFiller();
    void moderntabs$setFiller(ResourceLocation tabIconLocation);
    default boolean moderntabs$hasFiller() {
        return moderntabs$getFiller() != null;
    }

    // tab icon
    ResourceLocation moderntabs$getCustomTabIcon();
    void moderntabs$setCustomTabIcon(ResourceLocation tabIconLocation);
    default boolean moderntabs$hasCustomTabIcon() {
        return moderntabs$getCustomTabIcon() != null;
    }

    // scrollbar
    ResourceLocation moderntabs$getCustomScroller();
    void moderntabs$setCustomScroller(ResourceLocation scrollerLocation);
    default boolean moderntabs$hasCustomScroller() {
        return moderntabs$getCustomScroller() != null;
    }

    // tab background color
    SimpleColor moderntabs$getBackgroundColor();
    void moderntabs$setBackgroundColor(SimpleColor backgroundColor);
    default boolean moderntabs$hasCustomBackgroundColor() {
        return moderntabs$getBackgroundColor() != null;
    }

}
