package dev.moderntabs.example;

import dev.moderntabs.ModernTabs;
import dev.moderntabs.item.SectionedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public final class ExampleTab {

    public static final ResourceLocation TAB_ID = ModernTabs.path("example_tab");

    private static final ResourceLocation SECTION_BUILDING_BLOCKS = ModernTabs.path("example_building_blocks");
    private static final ResourceLocation SECTION_TOOLS_AND_WEAPONS = ModernTabs.path("example_tools_and_weapons");
    private static final ResourceLocation SECTION_FOOD = ModernTabs.path("example_food");

    private static final List<Item> BUILDING_BLOCKS = List.of(
            Items.DIRT, Items.GRASS_BLOCK, Items.STONE, Items.COBBLESTONE,
            Items.OAK_LOG, Items.OAK_PLANKS, Items.GLASS, Items.BRICKS,
            Items.SAND, Items.SANDSTONE, Items.TERRACOTTA
    );

    private static final List<Item> TOOLS_AND_WEAPONS = List.of(
            Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL,
            Items.DIAMOND_HOE, Items.BOW, Items.CROSSBOW, Items.ARROW,
            Items.SHIELD, Items.TRIDENT, Items.FISHING_ROD
    );

    private static final List<Item> FOOD = List.of(
            Items.APPLE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.BREAD,
            Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.CAKE, Items.COOKIE,
            Items.CARROT, Items.GOLDEN_CARROT, Items.MELON_SLICE, Items.PUMPKIN_PIE
    );

    public static final CreativeModeTab TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 10)
            .title(Component.literal("ModernTabs Example"))
            .icon(() -> new ItemStack(Items.WHITE_BANNER))
            .displayItems((parameters, output) -> {
                BUILDING_BLOCKS.forEach(output::accept);
                TOOLS_AND_WEAPONS.forEach(output::accept);
                FOOD.forEach(output::accept);
            })
            .build();

    private ExampleTab() {
    }

    public static void init() {
        ModernTabs.enableSections(TAB);
        BUILDING_BLOCKS.forEach(item -> SectionedItems.addItem(item, SECTION_BUILDING_BLOCKS));
        TOOLS_AND_WEAPONS.forEach(item -> SectionedItems.addItem(item, SECTION_TOOLS_AND_WEAPONS));
        FOOD.forEach(item -> SectionedItems.addItem(item, SECTION_FOOD));
    }
}
