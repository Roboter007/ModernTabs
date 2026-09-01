package de.Roboter007.moderntabs.example;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
import de.Roboter007.moderntabs.section.item.SectionedItems;
import de.Roboter007.moderntabs.titel.AuraTabTitel;
import de.Roboter007.moderntabs.titel.SpriteTabTitel;
import de.Roboter007.moderntabs.titel.TextOrientation;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public final class ExampleTab {

    public static final ResourceLocation TAB_ID = ModernTabs.path("example_tab");

    private static final ResourceLocation SECTION_BUILDING_BLOCKS = ModernTabs.path("example_building_blocks");
    private static final ResourceLocation SECTION_TOOLS_AND_WEAPONS = ModernTabs.path("example_tools_and_weapons");
    private static final ResourceLocation SECTION_FOOD = ModernTabs.path("example_food");

    public static final List<Item> BUILDING_BLOCKS = List.of(
            Items.DIRT, Items.GRASS_BLOCK, Items.STONE, Items.COBBLESTONE,
            Items.OAK_LOG, Items.OAK_PLANKS, Items.GLASS, Items.BRICKS,
            Items.SAND, Items.SANDSTONE, Items.TERRACOTTA
    );

    public static final List<Item> TOOLS_AND_WEAPONS = List.of(
            Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL,
            Items.DIAMOND_HOE, Items.BOW, Items.CROSSBOW, Items.ARROW,
            Items.SHIELD, Items.TRIDENT, Items.FISHING_ROD
    );

    public static final List<Item> FOOD = List.of(
            Items.APPLE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.BREAD,
            Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.CAKE, Items.COOKIE,
            Items.CARROT, Items.GOLDEN_CARROT, Items.MELON_SLICE, Items.PUMPKIN_PIE
    );

    public static final CreativeModeTab TAB = ModernTabsPlatform.get().creativeBuilder()
            .title(Component.translatable("itemGroup.moderntabs.example"))
            .displayItems((parameters, output) -> {
                BUILDING_BLOCKS.forEach(output::accept);
                TOOLS_AND_WEAPONS.forEach(output::accept);
                FOOD.forEach(output::accept);
            })
            .build();


    private ExampleTab() {
    }

    public static void init() {
        ModernColor example_color = new ModernColor("#36454F");
        SpriteTabTitel spriteTabTitel = new SpriteTabTitel(TextOrientation.CENTERED, example_color.copy().lighten(0.6f), ModernTabs.path("container/creative_inventory/titel"), 64, 10);
        /*AuraTabTitel auraTabTitel = (AuraTabTitel) new AuraTabTitel()
                .color2(new ModernColor("#36454F").darken(0.4f))
                .color(new ModernColor("#36454F"))
                .textOrientation(TextOrientation.RIGHT)
                .backgroundColor(new ModernColor("#36454F").darken(0.75f)); */



        SectionedItems.addItemList(SECTION_BUILDING_BLOCKS, BUILDING_BLOCKS);
        SectionedItems.addItemList(SECTION_TOOLS_AND_WEAPONS, TOOLS_AND_WEAPONS);
        SectionedItems.addItemList(SECTION_FOOD, FOOD);

        ModernTabs.TabDesign tabDesign = new ModernTabs.TabDesign()
                .sectionsEnabled(true)
                .tabIconLocation(ModernTabs.path("container/creative_inventory/icon"))
                .color(example_color)
                .customTabTitel(spriteTabTitel);

        ModernTabs.configureTab(TAB, tabDesign);
    }
}
