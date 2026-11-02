package de.Roboter007.moderntabs.tab.section.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.content.TabContent;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.graphics.CustomGuiGraphics;
import de.Roboter007.moderntabs.tab.section.decoration.Banner;
import de.Roboter007.moderntabs.tab.section.decoration.Decoration;
import de.Roboter007.moderntabs.tab.section.decoration.Overlay;
import de.Roboter007.moderntabs.tab.section.states.AnimationMode;
import de.Roboter007.moderntabs.mixin.tab.banner.AbstractContainerScreenAccessor;
import de.Roboter007.moderntabs.tab.section.Section;
import de.Roboter007.moderntabs.tab.extensions.SpriteContentsExtension;
import de.Roboter007.moderntabs.tab.extensions.TickerExtension;
import de.Roboter007.moderntabs.tab.section.Sections;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.tab.title.AuraTabTitle;
import de.Roboter007.moderntabs.tab.title.CustomTabTitle;
import de.Roboter007.moderntabs.util.SectionUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public class SectionRenderer {

    private static final int BANNER_WIDTH = 162;
    private static final int BANNER_HEIGHT = 18;
    private static final int VISIBLE_ROWS = 5;

    public static int CURRENT_ROW = 0;

    public static final ResourceLocation COLLAPSE_BUTTON_SELECTED = ModernTabs.path("container/creative_inventory/toggle/collapse_button_selected");
    public static final ResourceLocation COLLAPSE_BUTTON = ModernTabs.path("container/creative_inventory/toggle/collapse_button");
    public static final ResourceLocation EXTEND_BUTTON_SELECTED = ModernTabs.path("container/creative_inventory/toggle/extend_button_selected");
    public static final ResourceLocation EXTEND_BUTTON = ModernTabs.path("container/creative_inventory/toggle/extend_button");

    public static final Map<CreativeModeTab, Object2IntOpenHashMap<ResourceLocation>> SECTIONS = new IdentityHashMap<>();
    public static final Map<CreativeModeTab, Integer> TOTAL_ROWS = new IdentityHashMap<>();
    private static final Map<CreativeModeTab, List<Section>> TAB_SECTIONS = new IdentityHashMap<>();
    private static final Map<CreativeModeTab, Set<ResourceLocation>> SEARCH_COLLAPSED = new IdentityHashMap<>();

    private SectionRenderer() {
    }

    public static void renderBanners(CreativeModeTab tab, CreativeModeInventoryScreen screen, GuiGraphics graphics, int mouseX, int mouseY) {
        Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        if (yValues != null && !yValues.isEmpty()) {
            PoseStack ps = graphics.pose();
            ps.pushPose();

            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1, 1, 1, 1);
            int left = ((AbstractContainerScreenAccessor) screen).getLeftPos() + 8;
            int top = ((AbstractContainerScreenAccessor) screen).getTopPos() + 17;
            ps.translate(left, top, 0);

            for (Section section : Sections.getSectionsFromTab(tab)) {
                renderSection(tab, section, graphics, yValues, left, top, mouseX, mouseY);
            }
            renderEmptyRows(tab, graphics);

            ps.popPose();
            RenderSystem.disableDepthTest();
        }
    }

    public static void renderSection(CreativeModeTab tab, Section section, GuiGraphics graphics, Object2IntOpenHashMap<ResourceLocation> yValues, int left, int top, int mouseX, int mouseY) {
        CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;
        ResourceLocation id = Sections.getId(section);
        if (yValues.containsKey(id)) {
            int yValue = yValues.getInt(id);
            int sectionRow = yValue - CURRENT_ROW;
            if (sectionRow >= 0 && sectionRow <= 4) {
                final int x = 0;
                final int y = sectionRow * 18;

                boolean isHovering = mouseX >= left + x && mouseX <= left + x + BANNER_WIDTH && mouseY >= top + y && mouseY <= top + y + BANNER_HEIGHT;

                // render main banner sprite
                renderSectionDecoration(extension, graphics, isHovering, section.getBanner(), x, y, BANNER_WIDTH, BANNER_HEIGHT);

                // render banner overlay sprite
                if(section.getOverlay() != null) {
                    renderSectionDecoration(extension, graphics, isHovering, section.getOverlay(), x, y, BANNER_WIDTH, BANNER_HEIGHT);
                }


                // render text
                CustomTabTitle<?> customTabTitle = section.getTitle();

                // load default values for section titles
                if(customTabTitle.getBackgroundColor() == null) {
                    customTabTitle.backgroundColor(new SimpleColor(0xAA000000));
                }

                if(customTabTitle.isDroppingShadow() == null) {
                    customTabTitle.dropShadow(true);
                }

                if(customTabTitle instanceof AuraTabTitle auraTabTitle && auraTabTitle.isDroppingShadow2() == null) {
                    auraTabTitle.dropShadow2(true);
                }

                int textX = x;
                int bgX;
                int bgWidth;

                ElementOrientation orientation = customTabTitle.getTextOrientation();
                if(orientation == ElementOrientation.RIGHT) {
                    textX -= 7;
                    bgX = -3;
                    bgWidth = 6;
                } else if(orientation == ElementOrientation.LEFT) {
                    textX += 5;
                    bgX = -3;
                    bgWidth = 6;
                } else {
                    bgX = -2;
                    bgWidth = 4;
                }

                customTabTitle.draw(graphics, Minecraft.getInstance().font, Component.literal("Placeholder"), textX, y + 5, bgX, -3, BANNER_WIDTH + 2, bgWidth, BANNER_HEIGHT - 4, new SimpleColor("FFFFFF").argbColor(), true);

                // render section toggle
                boolean searching = isSearching(tab);
                if(searching ? Sections.isCollapsible(section) : canToggle(tab, section)) {
                    renderSectionToggle(extension, graphics, section, isCollapsed(tab, section), x, y, BANNER_WIDTH, BANNER_HEIGHT, isHovering && !searching);
                }
            }
        }
    }

    private static void renderEmptyRows(CreativeModeTab tab, GuiGraphics graphics) {
        CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;

        if(extension.moderntabs$hasFiller()) {
            int totalRows = TOTAL_ROWS.getOrDefault(tab, 0);
            int firstEmptyRow = Math.max(totalRows - CURRENT_ROW, 0);

            if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor()) {
                SimpleColor color = extension.moderntabs$getBackground().getColor();
                graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            for (int row = firstEmptyRow; row < VISIBLE_ROWS; row++) {
                graphics.blitSprite(extension.moderntabs$getFiller(), 0, row * BANNER_HEIGHT, BANNER_WIDTH, BANNER_HEIGHT);
            }

            if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor()) {
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    public static void renderSectionDecoration(CreativeModeTabExtension extension, GuiGraphics graphics, boolean isHovering, Decoration decoration, int x, int y, int w, int h) {
        ResourceLocation bannerSprite = decoration.getSprite();
        CustomGuiGraphics customGraphics = (CustomGuiGraphics) graphics;

        if (decoration.getAnimationMode() == AnimationMode.PLAY_ON_HOVER) {
            setPlaying(bannerSprite, isHovering);
        } else if (decoration.getAnimationMode() == AnimationMode.PLAY_CONTINUOUSLY) {
            setPlaying(bannerSprite, true);
        } else if (decoration.getAnimationMode() == AnimationMode.NOT_ANIMATED) {
            setPlaying(bannerSprite, false);
        }

        if (decoration.getColor() != null) {
            SimpleColor color = decoration.getColor();
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        } else if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor() && decoration instanceof Overlay) {
            SimpleColor color = extension.moderntabs$getBackground().getColor();
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        }

        customGraphics.moderntabs$blitSprite(bannerSprite, Banner.MISSING_BANNER, x, y, w, h);

        if (decoration.getColor() != null) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor() && decoration instanceof Overlay) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void renderSectionToggle(CreativeModeTabExtension extension, GuiGraphics graphics, Section section, boolean collapsed, int sectionX, int sectionY, int sectionW, int sectionH, boolean isHovering) {
        int width = 11;
        int height = 8;

        if(Sections.isCollapsible(section)) {
            int orientatedX;

            ElementOrientation orientation = section.getSectionToggle().getOrientation();
            if(orientation == ElementOrientation.CENTERED) {
                orientatedX = sectionX + ((sectionW - width) / 2);
            } else if (orientation == ElementOrientation.RIGHT) {
                orientatedX = sectionX + sectionW - width - 2;
            } else {
                orientatedX = sectionX + 2;
            }

            ResourceLocation toggleButtonLocation;
            if(isHovering) {
                toggleButtonLocation = collapsed ? EXTEND_BUTTON_SELECTED : COLLAPSE_BUTTON_SELECTED;
            } else {
                toggleButtonLocation = collapsed ? EXTEND_BUTTON : COLLAPSE_BUTTON;
            }

            if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor()) {
                SimpleColor color = extension.moderntabs$getBackground().getColor();
                graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            graphics.blitSprite(toggleButtonLocation, orientatedX, sectionY + (sectionH / 2) - (height / 2), width, height);

            if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor()) {
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    private static int itemRowsOf(CreativeModeTab tab, ResourceLocation id) {
        Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        int sectionRow = yValues.getInt(id);

        int nextBannerRow = TOTAL_ROWS.getOrDefault(tab, 0);
        for (Object2IntMap.Entry<ResourceLocation> entry : yValues.object2IntEntrySet()) {
            int row = entry.getIntValue();
            if (row > sectionRow && row < nextBannerRow) {
                nextBannerRow = row;
            }
        }
        return nextBannerRow - sectionRow - 1;
    }

    public static boolean canToggle(CreativeModeTab tab, Section section) {
        if (Sections.isCollapsible(section) && !isSearching(tab)) {
            CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;
            if (!Sections.sectionCollapsed(section) && !extension.moderntabs$doesAllowLessVisibleRows()) {
                ResourceLocation id = Sections.getId(section);
                Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);

                if (id != null && yValues != null && yValues.containsKey(id)) {
                    return TOTAL_ROWS.getOrDefault(tab, 0) - itemRowsOf(tab, id) >= VISIBLE_ROWS;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    private static boolean isCollapsed(CreativeModeTab tab, Section section) {
        Set<ResourceLocation> searchCollapsed = SEARCH_COLLAPSED.get(tab);
        return searchCollapsed != null ? searchCollapsed.contains(Sections.getId(section)) :  Sections.sectionCollapsed(section);
    }

    public static void processItems(CreativeModeTab tab, Collection<ItemStack> originalDisplayItems, Consumer<ItemStack> layoutItems, Consumer<ItemStack> contentItems, Consumer<ItemStack> searchItems) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;

        Object2IntOpenHashMap<ResourceLocation> yValues = new Object2IntOpenHashMap<>();
        SECTIONS.put(tab, yValues);
        SEARCH_COLLAPSED.remove(tab);

        if(tabExtension.moderntabs$hasCustomTabContent()) {
            TabContent content = tabExtension.moderntabs$getCustomTabContent();

            List<ItemStack> unassigned = content.getTabContent();
            if(!content.isOverwritingDefaultContent()) {
                List<ItemStack> originalItems = originalDisplayItems.stream().toList();
                if(unassigned == null) {
                    unassigned = originalItems;
                } else {
                    unassigned.addAll(originalItems);
                }
            }

            int y = 0;

            if (unassigned != null && !unassigned.isEmpty()) {
                int count = countLeftoverItems(true, unassigned, layoutItems, contentItems, searchItems);
                y = (count + 8) / 9;
                padToFullRow(count, layoutItems);
            }

            List<Section> sections = Sections.getSectionsFromTab(tab);

            if (!sections.isEmpty()) {
                TAB_SECTIONS.put(tab, sections);

                int totalRows = layoutSections(sections, y, yValues, layoutItems, section -> countLeftoverItems(!Sections.sectionCollapsed(section), section.getContent(), layoutItems, contentItems, searchItems));
                TOTAL_ROWS.put(tab, totalRows);
            } else {
                TAB_SECTIONS.put(tab, List.of());
            }
        }
    }

    //ToDo: fix search for items in section as they don't get filtered - improve logic more && remove old SectionUtil logic & take over item tag logic to the new system
    @Nullable
    public static List<ItemStack> layoutSearchResults(CreativeModeTab tab, List<ItemStack> results) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;
        if(tabExtension.moderntabs$hasCustomTabContent()) {
            TabContent tabContent = tabExtension.moderntabs$getCustomTabContent();
            List<Section> sections = Sections.getSectionsFromTab(tab);
            HashMap<Section, List<ItemStack>> matches = new HashMap<>();

            if(!sections.isEmpty()) {
                for(Section section : sections) {
                    List<ItemStack> contentMatches = new ArrayList<>();
                    for(ItemStack stack : results) {
                        if(section.getContent().contains(stack)) {
                            contentMatches.add(stack);
                        }
                    }
                    matches.put(section, contentMatches);
                }
            }

            List<ItemStack> unassigned = getUnassignedItems(sections, results);
            if(tabContent.getTabContent() != null) {
                for (ItemStack stack : results) {
                    if (tabContent.getTabContent().contains(stack)) {
                        unassigned.add(stack);
                    }
                }
            }

            List<ItemStack> layout = new ArrayList<>();
            Object2IntOpenHashMap<ResourceLocation> yValues = new Object2IntOpenHashMap<>();
            Set<ResourceLocation> collapsed = new HashSet<>();

            int y = 0;
            if (!unassigned.isEmpty()) {
                layout.addAll(unassigned);
                y = (unassigned.size() + 8) / 9;
                padToFullRow(unassigned.size(), layout::add);
            }

            int totalRows = layoutSections(sections, y, yValues, layout::add, section -> {
                ResourceLocation id = Sections.getId(section);
                List<ItemStack> sectionMatches = matches.getOrDefault(section, List.of());

                if (sectionMatches.isEmpty() && Sections.isCollapsible(section)) {
                    collapsed.add(id);
                    return 0;
                } else {
                    layout.addAll(sectionMatches);
                    return sectionMatches.size();
                }
            });

            SECTIONS.put(tab, yValues);
            TOTAL_ROWS.put(tab, totalRows);
            SEARCH_COLLAPSED.put(tab, collapsed);
            return layout;
        } else {
            return results;
        }
    }

    private static List<ItemStack> getUnassignedItems(List<Section> sections, List<ItemStack> results) {
        List<ItemStack> unassigned = new ArrayList<>(results);
        if(!sections.isEmpty()) {
            for (Section section : sections) {
                unassigned.removeIf(stack -> section.getContent().contains(stack));
            }
        }
        return unassigned;
    }

    private static int layoutSections(List<Section> sections, int startRow, Object2IntOpenHashMap<ResourceLocation> yValues, Consumer<ItemStack> layoutItems, ToIntFunction<Section> emitItems) {
        int y = startRow;

        for (int i = 0; i < 9; i++) {
            layoutItems.accept(ItemStack.EMPTY);
        }

        for (int index = 0; index < sections.size(); index++) {
            Section section = sections.get(index);
            int itemCount = emitItems.applyAsInt(section);

            yValues.put(Sections.getId(section), y);
            y += (itemCount + 8) / 9 + 1;

            if (index != sections.size() - 1) {
                int padding = 9 - itemCount % 9;
                if (padding < 9) {
                    padding += 9;
                }
                for (int i = 0; i < padding; i++) {
                    layoutItems.accept(ItemStack.EMPTY);
                }
            }
        }
        return y;
    }

    private static void padToFullRow(int count, Consumer<ItemStack> layoutItems) {
        int remainder = count % 9;
        if (remainder != 0) {
            for (int i = 0; i < 9 - remainder; i++) {
                layoutItems.accept(ItemStack.EMPTY);
            }
        }
    }

    private static int countLeftoverItems(boolean extended, List<ItemStack> stacks, Consumer<ItemStack> layoutItems, Consumer<ItemStack> contentItems, Consumer<ItemStack> searchItems) {
        int count = 0;
        for (ItemStack stack : stacks) {
            ItemStack transformed = SectionUtil.applyTransform(stack);
            if (SectionUtil.ItemVisibility.SEARCH_ONLY.has(transformed.getItem())) {
                searchItems.accept(transformed);
            } else if (!SectionUtil.ItemVisibility.INVISIBLE.has(transformed.getItem())) {
                searchItems.accept(transformed);
                contentItems.accept(transformed);

                if(extended) {
                    layoutItems.accept(transformed);
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isSearching(CreativeModeTab tab) {
        return SEARCH_COLLAPSED.containsKey(tab);
    }

    @Nullable
    public static Section findSectionAt(CreativeModeTab tab, int left, int top, double mouseX, double mouseY) {
        Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        int visibleRow = (int) ((mouseY - top) / BANNER_HEIGHT);

        if (yValues != null && !yValues.isEmpty() && mouseX >= left && mouseX < left + BANNER_WIDTH && mouseY >= top && visibleRow < VISIBLE_ROWS) {
            for (Object2IntMap.Entry<ResourceLocation> entry : yValues.object2IntEntrySet()) {
                if (entry.getIntValue() - CURRENT_ROW == visibleRow) {
                    return Sections.get(entry.getKey());
                }
            }
        }
        return null;
    }

    public static void setPlaying(ResourceLocation resourceLocation, boolean playing) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(resourceLocation);
        SpriteContents.Ticker ticker = ((SpriteContentsExtension) sprite.contents()).moderntabs$getTicker();

        if (ticker instanceof TickerExtension extension) {
            extension.moderntabs$setPlaying(playing);
        }
    }
}