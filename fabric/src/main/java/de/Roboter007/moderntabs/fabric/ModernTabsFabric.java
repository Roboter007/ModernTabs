package de.Roboter007.moderntabs.fabric;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.example.ExampleTab;
import de.Roboter007.moderntabs.util.SectionUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ModernTabsFabric implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> ModernTabs.applyTabDesign());
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> SectionUtil.resolveItemTags(registries));

        if(ModernTabs.isExampleTabEnabled()) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ExampleTab.TAB_ID, ExampleTab.TAB);
            ExampleTab.init();
        }
    }
}
