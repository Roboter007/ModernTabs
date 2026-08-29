package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import net.minecraft.resources.ResourceLocation;

public class KubeJsTabDesign extends ModernTabs.TabDesign {

    public ModernTabs.TabDesign tabIconLocation(String tabIconId) {
        return this.tabIconLocation(ResourceLocation.parse(tabIconId));
    }

    public ModernTabs.TabDesign tabScrollerLocation(String tabScrollerId) {
        return this.tabScrollerLocation(ResourceLocation.parse(tabScrollerId));
    }

}
