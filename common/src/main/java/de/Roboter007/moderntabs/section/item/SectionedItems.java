package de.Roboter007.moderntabs.section.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SectionedItems {

    private static final Map<ResourceLocation, ResourceLocation> ITEM_TO_SECTION = new ConcurrentHashMap<>();

    private SectionedItems() {
    }

    public static void addItem(final ItemLike item, final ResourceLocation sectionId) {
        ITEM_TO_SECTION.put(BuiltInRegistries.ITEM.getKey(item.asItem()), sectionId);
    }

    public static ResourceLocation sectionOf(final ItemLike item) {
        return ITEM_TO_SECTION.get(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }
}
