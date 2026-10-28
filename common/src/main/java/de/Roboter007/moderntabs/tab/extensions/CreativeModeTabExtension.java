package de.Roboter007.moderntabs.tab.extensions;

import de.Roboter007.moderntabs.tab.background.TabBackground;
import de.Roboter007.moderntabs.tab.button.TabButton;
import de.Roboter007.moderntabs.tab.content.TabContent;
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

    // tab background color
    TabBackground moderntabs$getBackground();
    void moderntabs$setBackground(TabBackground background);
    default boolean moderntabs$hasCustomBackground() {
        return moderntabs$getBackground() != null;
    }

    // tab button
    void moderntabs$setCustomTabButton(TabButton tabButton);
    TabButton moderntabs$getCustomTabButton();
    default boolean moderntabs$hasCustomTabButton() {
        return moderntabs$getCustomTabButton() != null;
    }

    // tab titles
    void moderntabs$setCustomTabTitel(CustomTabTitel<?> customTabTitel);
    CustomTabTitel<?> moderntabs$getCustomTabTitel();
    default boolean moderntabs$hasCustomTabTitelRendering() {
        return moderntabs$getCustomTabTitel() != null;
    }

    // searchbar
    Searchbar moderntabs$getCustomSearchbar();
    void moderntabs$setCustomSearchbar(Searchbar searchbar);
    boolean moderntabs$hasCustomSearchbar();

    // filler
    ResourceLocation moderntabs$getFiller();
    void moderntabs$setFiller(ResourceLocation fillerLocation);
    default boolean moderntabs$hasFiller() {
        return moderntabs$getFiller() != null;
    }

    // scrollbar
    ResourceLocation moderntabs$getCustomScroller();
    void moderntabs$setCustomScroller(ResourceLocation scrollerLocation);
    default boolean moderntabs$hasCustomScroller() {
        return moderntabs$getCustomScroller() != null;
    }

    // tab content
    TabContent moderntabs$getCustomTabContent();
    void moderntabs$setCustomTabContent(TabContent tabContent);
    default boolean moderntabs$hasCustomTabContent() {
        return moderntabs$getCustomTabContent() != null;
    }

}
