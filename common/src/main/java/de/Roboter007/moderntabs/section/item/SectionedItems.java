package de.Roboter007.moderntabs.section.item;

import de.Roboter007.moderntabs.ModernTabs;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class SectionedItems {

    public static final Map<ResourceLocation, ResourceLocation> ITEM_TAG_TO_SECTION = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> ITEM_TO_SECTION = new ConcurrentHashMap<>();

    private SectionedItems() {}

    public static void addItem(ResourceLocation sectionLocation, ItemLike item) {
        ITEM_TO_SECTION.put(BuiltInRegistries.ITEM.getKey(item.asItem()), sectionLocation);
    }

    public static void addItem(String sectionId, ItemLike item) {
        ITEM_TO_SECTION.put(BuiltInRegistries.ITEM.getKey(item.asItem()), ResourceLocation.parse(sectionId));
    }

    public static void addItemById(String sectionId, String itemId) {
        ITEM_TO_SECTION.put(ResourceLocation.parse(itemId), ResourceLocation.parse(sectionId));
    }

    public static void addItemByTag(String sectionId, String tagId) {
        if(tagId.charAt(0) == '#') {
            ResourceLocation tagLocation = ResourceLocation.parse(sectionId.substring(1));
            ITEM_TAG_TO_SECTION.put(tagLocation, ResourceLocation.parse(sectionId));
        } else {
            ModernTabs.LOGGER.error("tried to the tag: {} to section: {}", tagId, sectionId);
        }
    }

    public static void resolveItemTags(RegistryAccess registryAccess) {
        for(ResourceLocation tagLocation : ITEM_TAG_TO_SECTION.keySet()) {
            List<Item> itemsFromTag = registryAccess.lookupOrThrow(Registries.ITEM).get(TagKey.create(Registries.ITEM, tagLocation))
                    .map(contents -> contents.stream().map(Holder::value).toList()).orElse(List.of());
            if(!itemsFromTag.isEmpty()) {
                ResourceLocation sectionLocation = ITEM_TAG_TO_SECTION.get(tagLocation);
                addItemList(sectionLocation, itemsFromTag);
            }
        }
    }

    public static void addItems(String sectionId, String... itemIds) {
        for(String itemId : itemIds) {
            addItemById(sectionId, itemId);
        }
    }

    public static void addItems(String sectionId, ItemLike... items) {
        for(ItemLike item : items) {
            addItem(sectionId, item);
        }
    }

    public static void addItemListById(String sectionId, List<String> itemIds) {
        for(String itemId : itemIds) {
            addItemById(sectionId, itemId);
        }
    }

    public static void addItemList(String sectionId, List<ItemLike> items) {
        for(ItemLike item : items) {
            addItem(sectionId, item);
        }
    }

    public static void addItemList(ResourceLocation sectionLocation, List<Item> items) {
        for(ItemLike item : items) {
            addItem(sectionLocation, item);
        }
    }


    public static ResourceLocation sectionOf(final ItemLike item) {
        return ITEM_TO_SECTION.get(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }
}
