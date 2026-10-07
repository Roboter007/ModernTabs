package de.Roboter007.moderntabs.platform;

import de.Roboter007.moderntabs.tab.button.TabButtonTexture;
import net.minecraft.world.item.CreativeModeTab;

public interface CreativeModeInventoryScreenPlatform {

    TabButtonTexture.Column moderntabs$column(CreativeModeTab creativeModeTab);
    CreativeModeTab.Row moderntabs$row(CreativeModeTab creativeModeTab);

}
