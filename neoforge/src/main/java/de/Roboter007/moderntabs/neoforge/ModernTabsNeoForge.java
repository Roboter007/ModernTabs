package de.Roboter007.moderntabs.neoforge;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.example.ExampleTab;
import de.Roboter007.moderntabs.neoforge.platform.NeoForgePlatform;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ModernTabs.MOD_ID)
public final class ModernTabsNeoForge {

    public static final Logger LOGGER = LoggerFactory.getLogger(ModernTabs.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> EXAMPLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModernTabs.MOD_ID);
    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = EXAMPLE_TABS.register("example_tab", () -> ExampleTab.TAB);

    public ModernTabsNeoForge(final IEventBus modEventBus, final ModContainer modContainer) {
        ModernTabsPlatform.init(new NeoForgePlatform());
        if(ModernTabs.isExampleTabEnabled()) {
            DeferredRegister<CreativeModeTab> EXAMPLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModernTabs.MOD_ID);
            EXAMPLE_TABS.register("example_tab", () -> ExampleTab.TAB);

            EXAMPLE_TABS.register(modEventBus);
            ExampleTab.init();
        }
    }

}
