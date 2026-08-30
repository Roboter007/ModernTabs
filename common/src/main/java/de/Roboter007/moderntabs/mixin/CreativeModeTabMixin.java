package de.Roboter007.moderntabs.mixin;

import de.Roboter007.moderntabs.iconBackground.config.TabIconBackground;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.List;
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
    public CustomTabTitel moderntabs$customTabTitel = null;
    @Unique
    public TabIconBackground moderntabs$tabIconBackground = null;
    @Unique
    public ResourceLocation moderntabs$tabIcon = null;
    @Unique
    public ResourceLocation moderntabs$scroller = null;
    @Unique
    public ModernColor moderntabs$backgroundColor = null;


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
    public void moderntabs$setCustomTabTitel(CustomTabTitel customTabTitel) {
        this.moderntabs$customTabTitel = customTabTitel;
    }

    @Override
    public CustomTabTitel moderntabs$getCustomTabTitel() {
        return moderntabs$customTabTitel;
    }

    @Override
    public void moderntabs$setCustomTabIconBackground(TabIconBackground tabIconBackground) {
        this.moderntabs$tabIconBackground = tabIconBackground;
    }

    @Override
    public TabIconBackground moderntabs$getCustomTabIconBackground() {
        return moderntabs$tabIconBackground;
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
