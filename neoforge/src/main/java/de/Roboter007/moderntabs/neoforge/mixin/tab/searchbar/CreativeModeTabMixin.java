package de.Roboter007.moderntabs.neoforge.mixin.tab.searchbar;

import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtensionPlatform;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.*;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin implements CreativeModeTabExtensionPlatform {

    @Mutable
    @Shadow
    @Final
    private boolean hasSearchBar;

    @Mutable
    @Shadow
    @Final
    private int searchBarWidth;

    @Mutable
    @Shadow
    @Final
    private CreativeModeTab.Type type;

    @Override
    public void moderntabs$setSearchbar(boolean hasSearchbar) {
        this.hasSearchBar = hasSearchbar;
        if(hasSearchbar) {
            type = CreativeModeTab.Type.SEARCH;
        }
    }

    @Override
    public boolean moderntabs$hasSearchbar() {
        return this.hasSearchBar;
    }

    @Override
    public void moderntabs$setSearchbarLength(int searchbarLength) {
        this.searchBarWidth = searchbarLength;
    }

    @Override
    public int moderntabs$getSearchbarLength() {
        return this.searchBarWidth;
    }
}
