package de.Roboter007.moderntabs.fabric;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.example.ExampleTab;
import de.Roboter007.moderntabs.fabric.platform.FabricPlatform;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ModernTabsFabric implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);


    @Override
    public void onInitialize() {
        ModernTabsPlatform.init(new FabricPlatform());
        if(ModernTabs.isExampleTabEnabled()) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ExampleTab.TAB_ID, ExampleTab.TAB);

            ExampleTab.init();
        }
    }
}
