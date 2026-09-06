package de.Roboter007.moderntabs.fabric;

import de.Roboter007.moderntabs.fabric.platform.FabricPlatform;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class ModernTabsPreLaunch implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {
        ModernTabsPlatform.init(new FabricPlatform());
    }
}
