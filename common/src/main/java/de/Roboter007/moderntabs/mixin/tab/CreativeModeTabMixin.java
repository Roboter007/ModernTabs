package de.Roboter007.moderntabs.mixin.tab;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.SearchbarPlatform;
import de.Roboter007.moderntabs.tab.searchbar.Searchbar;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.color.ModernColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin implements CreativeModeTabExtension {

    @Shadow
    private Collection<ItemStack> displayItems;
    @Shadow
    private Set<ItemStack> displayItemsSearchTab;
    @Unique
    public boolean moderntabs$sectionsEnabled = false;
    @Unique
    public boolean moderntabs$allowLessVisibleRows = false;
    @Unique
    public CustomTabTitel<?> moderntabs$customTabTitel = null;
    @Unique
    public TabButtonStates moderntabs$tabButtonStates = null;
    @Unique
    public ResourceLocation moderntabs$filler = null;
    @Unique
    public ResourceLocation moderntabs$tabIcon = null;
    @Unique
    public ResourceLocation moderntabs$scroller = null;
    @Unique
    public ModernColor moderntabs$backgroundColor = null;

    @Unique
    public Searchbar moderntabs$searchbar = null;


    @Override
    public void modernTabs$addItem(ItemStack stack, boolean searchable) {
        this.displayItems.add(stack);
        if(searchable) {
            this.displayItemsSearchTab.add(stack);
        }
    }

    @Override
    public void moderntabs$setSectionsEnabled(boolean sectionsEnabled) {
        this.moderntabs$sectionsEnabled = sectionsEnabled;
    }

    @Override
    public boolean moderntabs$hasCustomSections() {
        return moderntabs$sectionsEnabled;
    }

    @Override
    public void moderntabs$setAllowLessVisibleRows(boolean allowLessVisibleRows) {
        this.moderntabs$allowLessVisibleRows = allowLessVisibleRows;
    }

    @Override
    public boolean moderntabs$doesAllowLessVisibleRows() {
        return moderntabs$allowLessVisibleRows;
    }

    @Override
    public Searchbar moderntabs$getCustomSearchbar() {
        SearchbarPlatform searchbarPlatform = (SearchbarPlatform) this;
        Searchbar searchbar = this.moderntabs$searchbar;

        if(searchbar != null) {
            return searchbar.editBoxWidth(searchbarPlatform.moderntabs$getSearchbarEditBoxWidth());
        } else {
            return null;
        }
    }

    @Override
    public void moderntabs$setCustomSearchbar(@Nullable Searchbar searchbar) {
        SearchbarPlatform searchbarPlatform = (SearchbarPlatform) this;
        searchbarPlatform.moderntabs$setSearchbarEnabled(searchbar != null);
        if(searchbar != null) {
            searchbarPlatform.moderntabs$setSearchbarEditBoxWidth(searchbar.getEditBoxWidth());
        }
        this.moderntabs$searchbar = searchbar;
    }

    @Override
    public boolean moderntabs$hasCustomSearchbar() {
        CreativeModeTab tab = (CreativeModeTab) (Object) this;
        SearchbarPlatform searchbarPlatform = (SearchbarPlatform) tab;
        return searchbarPlatform.moderntabs$hasSearchbar();
    }

    @Override
    public void moderntabs$setCustomTabTitel(CustomTabTitel<?> customTabTitel) {
        this.moderntabs$customTabTitel = customTabTitel;
    }

    @Override
    public CustomTabTitel<?> moderntabs$getCustomTabTitel() {
        return moderntabs$customTabTitel;
    }

    @Override
    public void moderntabs$setCustomTabButtonStates(TabButtonStates tabIconBackground) {
        this.moderntabs$tabButtonStates = tabIconBackground;
    }

    @Override
    public TabButtonStates moderntabs$getCustomTabButtonStates() {
        return moderntabs$tabButtonStates;
    }

    @Override
    public ResourceLocation moderntabs$getFiller() {
        return moderntabs$filler;
    }

    @Override
    public void moderntabs$setFiller(ResourceLocation fillerLocation) {
        this.moderntabs$filler = fillerLocation;
    }

    @Override
    public ResourceLocation moderntabs$getCustomTabIcon() {
        return moderntabs$tabIcon;
    }

    @Override
    public void moderntabs$setCustomTabIcon(ResourceLocation tabIconLocation) {
        this.moderntabs$tabIcon = tabIconLocation;
    }

    @Override
    public ResourceLocation moderntabs$getCustomScroller() {
        return moderntabs$scroller;
    }

    @Override
    public void moderntabs$setCustomScroller(ResourceLocation scrollerLocation) {
        this.moderntabs$scroller = scrollerLocation;
    }

    @Override
    public ModernColor moderntabs$getBackgroundColor() {
        return moderntabs$backgroundColor;
    }

    @Override
    public void moderntabs$setBackgroundColor(ModernColor backgroundColor) {
        this.moderntabs$backgroundColor = backgroundColor;
    }

}
