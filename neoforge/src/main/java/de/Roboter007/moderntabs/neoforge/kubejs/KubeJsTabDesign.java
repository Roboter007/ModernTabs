package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import net.minecraft.resources.ResourceLocation;

public class KubeJsTabDesign extends ModernTabs.TabDesign {

    public ModernTabs.TabDesign fillerLocation(String fillerLocation) {
        return this.fillerLocation(ResourceLocation.parse(fillerLocation));
    }

    public ModernTabs.TabDesign iconLocation(String tabIconId) {
        return this.iconLocation(ResourceLocation.parse(tabIconId));
    }

    public ModernTabs.TabDesign scrollerLocation(String tabScrollerId) {
        return this.scrollerLocation(ResourceLocation.parse(tabScrollerId));
    }

}
