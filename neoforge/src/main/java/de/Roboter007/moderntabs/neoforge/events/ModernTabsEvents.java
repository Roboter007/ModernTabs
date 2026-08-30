package de.Roboter007.moderntabs.neoforge.events;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.section.item.SectionedItems;
import net.minecraft.core.RegistryAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber(modid = ModernTabs.MOD_ID)
public class ModernTabsEvents {

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        RegistryAccess registries = event.getRegistryAccess();
        SectionedItems.resolveItemTags(registries);
    }
}
