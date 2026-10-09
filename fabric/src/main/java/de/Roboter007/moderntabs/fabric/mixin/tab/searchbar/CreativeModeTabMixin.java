package de.Roboter007.moderntabs.fabric.mixin.tab.searchbar;

import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtensionPlatform;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.*;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin implements CreativeModeTabExtensionPlatform {

    @Mutable
    @Shadow
    @Final
    private CreativeModeTab.Type type;
    @Unique
    public int moderntabs$searchbarLength = 89;
    @Override
    public void moderntabs$setSearchbarEnabled(boolean hasSearchbar) {
        if (hasSearchbar) {
            if (this.type == CreativeModeTab.Type.CATEGORY) {
                this.type = CreativeModeTab.Type.SEARCH;
            }
        } else {
            this.type = CreativeModeTab.Type.CATEGORY;
        }
    }

    @Override
    public boolean moderntabs$hasSearchbar() {
        return this.type == CreativeModeTab.Type.SEARCH;
    }

    @Override
    public void moderntabs$setSearchbarEditBoxWidth(int searchbarLength) {
        this.moderntabs$searchbarLength = searchbarLength;
    }

    @Override
    public int moderntabs$getSearchbarEditBoxWidth() {
        return this.moderntabs$searchbarLength;
    }


}
