package de.Roboter007.moderntabs.neoforge.events;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.section.Sections;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = ModernTabs.MOD_ID, value = Dist.CLIENT)
public final class ModernTabsClientEvents {

    @SubscribeEvent
    public static void onRegisterReloadListeners(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(Sections.RELOAD_LISTENER);
    }
}
