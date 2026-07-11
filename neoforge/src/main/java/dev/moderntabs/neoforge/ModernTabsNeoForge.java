package dev.moderntabs.neoforge;

import dev.moderntabs.ModernTabs;
import dev.moderntabs.example.ExampleTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ModernTabs.MOD_ID)
public final class ModernTabsNeoForge {

    private static final DeferredRegister<CreativeModeTab> EXAMPLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModernTabs.MOD_ID);
    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = EXAMPLE_TABS.register("example_tab", () -> ExampleTab.TAB);

    public ModernTabsNeoForge(final IEventBus modEventBus, final ModContainer modContainer) {
        if(ModernTabs.isExampleTabEnabled()) {
            DeferredRegister<CreativeModeTab> EXAMPLE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModernTabs.MOD_ID);
            DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = EXAMPLE_TABS.register("example_tab", () -> ExampleTab.TAB);

            EXAMPLE_TABS.register(modEventBus);
            ExampleTab.init();
        }
    }
}
