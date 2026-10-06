package de.Roboter007.moderntabs.util;

import de.Roboter007.moderntabs.ModernTabs;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class SectionUtil {
    public static final Map<ResourceLocation, ResourceLocation> ITEM_TAG_TO_SECTION = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> ITEM_TO_SECTION = new ConcurrentHashMap<>();

    private static final Map<ResourceLocation, ItemVisibility> ITEM_VISIBILITY = new HashMap<>();
    private static final Map<Item, Function<Item, ItemStack>> STACK_TRANSFORM = new HashMap<>();

    public static void addItemByLocation(ResourceLocation sectionLocation, ResourceLocation itemLocation) {
        ITEM_TO_SECTION.put(sectionLocation, itemLocation);
    }

    public static void addItemTagByLoc(ResourceLocation sectionLocation, ResourceLocation tagLocation) {
        ITEM_TAG_TO_SECTION.put(sectionLocation, tagLocation);
    }

    public static void addItem(ResourceLocation sectionLocation, ItemLike item) {
        ITEM_TO_SECTION.put(BuiltInRegistries.ITEM.getKey(item.asItem()), sectionLocation);
    }

    public static void addItem(String sectionId, ItemLike item) {
        ITEM_TO_SECTION.put(BuiltInRegistries.ITEM.getKey(item.asItem()), ResourceLocation.parse(sectionId));
    }

    public static void addItemsByTag(String sectionId, String tagId) {
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

    public static void addItems(String sectionId, ItemLike... items) {
        for(ItemLike item : items) {
            addItem(sectionId, item);
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

    public static void setVisibility(final ItemLike item, final ItemVisibility type) {
        ITEM_VISIBILITY.put(BuiltInRegistries.ITEM.getKey(item.asItem()), type);
    }

    public static void setVisibility(final ResourceLocation item, final ItemVisibility type) {
        ITEM_VISIBILITY.put(item, type);
    }

    public static void setStackTransform(final ItemLike item, final Function<Item, ItemStack> transform) {
        STACK_TRANSFORM.put(item.asItem(), transform);
    }

    public static ItemStack applyTransform(final ItemStack stack) {
        final Function<Item, ItemStack> transform = STACK_TRANSFORM.get(stack.getItem());
        return transform == null ? stack : transform.apply(stack.getItem());
    }

    public enum ItemVisibility {
        INVISIBLE,
        SEARCH_ONLY;

        public boolean has(final Item item) {
            return this == ITEM_VISIBILITY.get(BuiltInRegistries.ITEM.getKey(item));
        }
    }
}
