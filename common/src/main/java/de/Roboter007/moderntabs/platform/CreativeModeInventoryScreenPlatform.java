package de.Roboter007.moderntabs.platform;

import de.Roboter007.moderntabs.tab.iconBackground.TabIconBackgroundImage;
import net.minecraft.world.item.CreativeModeTab;

public interface CreativeModeInventoryScreenPlatform {

    TabIconBackgroundImage.Column moderntabs$column(CreativeModeTab creativeModeTab);
    CreativeModeTab.Row moderntabs$row(CreativeModeTab creativeModeTab);

}
