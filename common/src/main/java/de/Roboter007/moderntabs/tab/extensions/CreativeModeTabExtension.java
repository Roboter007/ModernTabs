package de.Roboter007.moderntabs.tab.extensions;

import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface CreativeModeTabExtension {

    // experimental
    void modernTabs$addItem(ItemStack stack, boolean searchable);

    // boolean options
    void moderntabs$setSectionsEnabled(boolean sectionsEnabled);
    boolean moderntabs$hasCustomSections();

    void moderntabs$setAllowLessVisibleRows(boolean allowLessVisibleRows);
    boolean moderntabs$doesAllowLessVisibleRows();

    // searchbar
    @NotNull
    ResourceLocation moderntabs$getSearchbarLocation();
    void moderntabs$setSearchbarLocation(ResourceLocation fontLocation);

    ResourceLocation moderntabs$getSearchbarFontLocation();
    void moderntabs$setSearchbarFontLocation(ResourceLocation fontLocation);
    default boolean moderntabs$searchbarHasCustomFont() {
        return moderntabs$getSearchbarFontLocation() != null;
    }

    ElementOrientation moderntabs$getSearchbarOrientation();
    void moderntabs$setSearchbarOrientation(ElementOrientation orientation);

    int moderntabs$getSearchbarWidth();
    void moderntabs$setSearchbarWidth(int searchbarWidth);

    int moderntabs$getSearchbarHeight();
    void moderntabs$setSearchbarHeight(int searchbarHeight);

    int moderntabs$getSearchbarEditBoxHeight();
    void moderntabs$setSearchbarEditBoxHeight(int searchbarEditBoxHeight);

    // tab titles
    void moderntabs$setCustomTabTitel(CustomTabTitel customTabTitel);
    CustomTabTitel moderntabs$getCustomTabTitel();
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
    ModernColor moderntabs$getBackgroundColor();
    void moderntabs$setBackgroundColor(ModernColor backgroundColor);
    default boolean moderntabs$hasCustomBackgroundColor() {
        return moderntabs$getBackgroundColor() != null;
    }

}
