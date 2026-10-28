package de.Roboter007.moderntabs.tab.section.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.graphics.CustomGuiGraphics;
import de.Roboter007.moderntabs.tab.section.states.AnimationMode;
import de.Roboter007.moderntabs.mixin.tab.banner.AbstractContainerScreenAccessor;
import de.Roboter007.moderntabs.tab.section.Section;
import de.Roboter007.moderntabs.tab.extensions.SpriteContentsExtension;
import de.Roboter007.moderntabs.tab.extensions.TickerExtension;
import de.Roboter007.moderntabs.tab.section.Sections;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.ColorUtil;
import de.Roboter007.moderntabs.util.SectionUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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

            for (Section section : Sections.sortedEntries()) {
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
                int x = 0;
                int y = sectionRow * 18;

                boolean isHovering = mouseX >= left + x && mouseX <= left + x + BANNER_WIDTH && mouseY >= top + y && mouseY <= top + y + BANNER_HEIGHT;

                // render main banner sprite
                renderSectionDecoration(extension, graphics, isHovering, section.banner(), x, y, BANNER_WIDTH, BANNER_HEIGHT);

                // render banner overlay sprite
                if(section.overlay().isPresent()) {
                    renderSectionDecoration(extension, graphics, isHovering, section.overlay().get(), x, y, BANNER_WIDTH, BANNER_HEIGHT);
                }

                // render text
                Component text = section.title().text();
                Font font = Minecraft.getInstance().font;
                int textWidth = font.width(text);

                int orientatedX;
                int backgroundMinX;
                int backgroundMaxX;
                int textX;

                if(section.title().orientation() == ElementOrientation.CENTERED) {
                    orientatedX = (BANNER_WIDTH - textWidth) / 2;
                    backgroundMinX = orientatedX - 2;
                    backgroundMaxX = orientatedX + textWidth + 2;
                    textX = orientatedX;
                } else if (section.title().orientation() == ElementOrientation.RIGHT) {
                    orientatedX = BANNER_WIDTH - textWidth;
                    backgroundMinX = orientatedX - 8;
                    backgroundMaxX = orientatedX + textWidth - 2;
                    textX = orientatedX - 5;
                } else {
                    orientatedX = x;
                    backgroundMinX = orientatedX + 2;
                    backgroundMaxX = orientatedX + textWidth + 8;
                    textX = orientatedX + 5;
                }

                int background = section.title().background();
                graphics.fill(backgroundMinX, y + 2, backgroundMaxX, y + BANNER_HEIGHT - 2, background);

                int light = section.title().color();
                int dark = section.title().secondaryColor().orElseGet(() -> ColorUtil.darken(light, 0.2f));

                TextRenderer.drawAuraText(graphics, text, dark, light, textX, y + 5);

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

    public static void renderSectionDecoration(CreativeModeTabExtension extension, GuiGraphics graphics, boolean isHovering, Section.Decoration decoration, int x, int y, int w, int h) {
        ResourceLocation bannerSprite = decoration.sprite();
        CustomGuiGraphics customGraphics = (CustomGuiGraphics) graphics;

        if (decoration.animationMode() == AnimationMode.PLAY_ON_HOVER) {
            setPlaying(bannerSprite, isHovering);
        } else if (decoration.animationMode() == AnimationMode.PLAY_CONTINUOUSLY) {
            setPlaying(bannerSprite, true);
        } else if (decoration.animationMode() == AnimationMode.NOT_ANIMATED) {
            setPlaying(bannerSprite, false);
        }

        if (decoration.color().isPresent()) {
            SimpleColor color = new SimpleColor(decoration.color().get());
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        } else if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor() && decoration instanceof Section.Overlay) {
            SimpleColor color = extension.moderntabs$getBackground().getColor();
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        }

        customGraphics.moderntabs$blitSprite(bannerSprite, Section.Banner.MISSING_BANNER, x, y, w, h);

        if (decoration.color().isPresent()) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (extension.moderntabs$hasCustomBackground() && extension.moderntabs$getBackground().hasCustomColor() && decoration instanceof Section.Overlay) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void renderSectionToggle(CreativeModeTabExtension extension, GuiGraphics graphics, Section section, boolean collapsed, int sectionX, int sectionY, int sectionW, int sectionH, boolean isHovering) {
        int width = 11;
        int height = 8;

        if(Sections.isCollapsible(section)) {
            int orientatedX;
            if(section.sectionToggle().get().orientation() == ElementOrientation.CENTERED) {
                orientatedX = sectionX + ((sectionW - width) / 2);
            } else if (section.sectionToggle().get().orientation() == ElementOrientation.RIGHT) {
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
                graphics.flush();
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
        Object2IntOpenHashMap<ResourceLocation> yValues = new Object2IntOpenHashMap<>();
        SECTIONS.put(tab, yValues);
        SEARCH_COLLAPSED.remove(tab);

        Map<Section, List<ItemStack>> sectionMap = new LinkedHashMap<>();
        List<ItemStack> unassigned = new ArrayList<>();

        for (ItemStack stack : originalDisplayItems) {
            ResourceLocation sectionId = SectionUtil.sectionOf(stack.getItem());
            Section section = sectionId == null ? null : Sections.get(sectionId);

            if (section == null) {
                unassigned.add(stack);
            } else {
                sectionMap.computeIfAbsent(section, s -> new LinkedList<>()).add(stack);
            }
        }

        int y = 0;

        if (!unassigned.isEmpty()) {
            int count = countLeftoverItems(true, unassigned, layoutItems, contentItems, searchItems);
            y = (count + 8) / 9;
            padToFullRow(count, layoutItems);
        }

        if (!sectionMap.isEmpty()) {
            List<Section> sectionKeys = sectionMap.keySet().stream().sorted().toList();
            TAB_SECTIONS.put(tab, sectionKeys);

            int totalRows = layoutSections(sectionKeys, y, yValues, layoutItems, section -> countLeftoverItems(!Sections.sectionCollapsed(section), sectionMap.get(section), layoutItems, contentItems, searchItems));
            TOTAL_ROWS.put(tab, totalRows);
        } else {
            TAB_SECTIONS.put(tab, List.of());
        }
    }

    @Nullable
    public static List<ItemStack> layoutSearchResults(CreativeModeTab tab, List<ItemStack> results) {
        List<Section> tabSections = TAB_SECTIONS.get(tab);
        if (tabSections != null && !tabSections.isEmpty()) {
            Set<ResourceLocation> tabSectionIds = new HashSet<>();
            for (Section section : tabSections) {
                tabSectionIds.add(Sections.getId(section));
            }

            Map<ResourceLocation, List<ItemStack>> matches = new HashMap<>();
            List<ItemStack> unassigned = new ArrayList<>();

            for (ItemStack stack : results) {
                ResourceLocation sectionId = SectionUtil.sectionOf(stack.getItem());
                if (sectionId != null && tabSectionIds.contains(sectionId)) {
                    matches.computeIfAbsent(sectionId, id -> new ArrayList<>()).add(stack);
                } else {
                    unassigned.add(stack);
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

            int totalRows = layoutSections(tabSections, y, yValues, layout::add, section -> {
                ResourceLocation id = Sections.getId(section);
                List<ItemStack> sectionMatches = matches.getOrDefault(id, List.of());

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
            return null;
        }
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