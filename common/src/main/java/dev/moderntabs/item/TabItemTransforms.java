package dev.moderntabs.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class TabItemTransforms {

    private static final Map<Item, VisibilityType> ITEM_VISIBILITY = new HashMap<>();
    private static final Map<Item, Function<Item, ItemStack>> STACK_TRANSFORM = new HashMap<>();

    private TabItemTransforms() {
    }

    public static void setVisibility(final ItemLike item, final VisibilityType type) {
        ITEM_VISIBILITY.put(item.asItem(), type);
    }

    public static void setStackTransform(final ItemLike item, final Function<Item, ItemStack> transform) {
        STACK_TRANSFORM.put(item.asItem(), transform);
    }

    public static ItemStack applyTransform(final ItemStack stack) {
        final Function<Item, ItemStack> transform = STACK_TRANSFORM.get(stack.getItem());
        return transform == null ? stack : transform.apply(stack.getItem());
    }

    public enum VisibilityType {
        INVISIBLE,
        SEARCH_ONLY;

        public boolean has(final Item item) {
            return this == ITEM_VISIBILITY.get(item);
        }
    }
}
