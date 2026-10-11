package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.tab.searchbar.Searchbar;
import net.minecraft.resources.ResourceLocation;

public class KubeJsSearchbar extends Searchbar {

    public Searchbar searchbarLocation(String searchbarId) {
        return this.searchbarLocation(ResourceLocation.parse(searchbarId));
    }

    public Searchbar font(String fontId) {
        return this.font(ResourceLocation.parse(fontId));
    }
}
