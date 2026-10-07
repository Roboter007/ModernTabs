package de.Roboter007.moderntabs.tab.section.renderer;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.Roboter007.moderntabs.ModernTabs;
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
import de.Roboter007.moderntabs.util.ModernColor;
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
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.Consumer;

public final class SectionRenderer {

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

    private SectionRenderer() {
    }

    public static void renderBanners(final CreativeModeTab tab, final CreativeModeInventoryScreen screen, final GuiGraphics graphics, final int mouseX, final int mouseY) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        if (yValues == null || yValues.isEmpty()) {
            return;
        }

        final PoseStack ps = graphics.pose();
        ps.pushPose();

        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        final int left = ((AbstractContainerScreenAccessor) screen).getLeftPos() + 8;
        final int top = ((AbstractContainerScreenAccessor) screen).getTopPos() + 17;
        ps.translate(left, top, 0);

        for (final Section section : Sections.sortedEntries()) {
            renderSection(tab, section, graphics, yValues, left, top, mouseX, mouseY);
        }
        renderEmptyRows(tab, graphics);

        ps.popPose();
        RenderSystem.disableDepthTest();
    }

    public static void renderSection(final CreativeModeTab tab, Section section, GuiGraphics graphics, final Object2IntOpenHashMap<ResourceLocation> yValues, final int left, final int top, final int mouseX, final int mouseY) {
        CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;
        final ResourceLocation id = Sections.getId(section);
        if (!yValues.containsKey(id)) {
            return;
        }

        final int yValue = yValues.getInt(id);
        final int sectionRow = yValue - CURRENT_ROW;
        if (sectionRow < 0 || sectionRow > 4) {
            return;
        }

        final int x = 0;
        final int y = sectionRow * 18;

        final boolean isHovering = mouseX >= left + x && mouseX <= left + x + BANNER_WIDTH && mouseY >= top + y && mouseY <= top + y + BANNER_HEIGHT;

        // render main banner sprite
        renderSectionDecoration(extension, graphics, isHovering, section.banner(), x, y, BANNER_WIDTH, BANNER_HEIGHT);

        // render banner overlay sprite
        if(section.overlay().isPresent()) {
            renderSectionDecoration(extension, graphics, isHovering, section.overlay().get(), x, y, BANNER_WIDTH, BANNER_HEIGHT);
        }

        // render text
        final Component text = section.title().text();
        final Font font = Minecraft.getInstance().font;
        final int textWidth = font.width(text);

        final int orientatedX;
        final int backgroundMinX;
        final int backgroundMaxX;
        final int textX;

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

        final int background = section.title().background();
        graphics.fill(backgroundMinX, y + 2, backgroundMaxX, y + BANNER_HEIGHT - 2, background);

        final int light = section.title().color();
        final int dark = section.title().secondaryColor().orElseGet(() -> ColorUtil.darken(light, 0.2f));

        drawAuraText(graphics, text, dark, light, textX, y + 5);

        if(canToggle(tab, section)) {
            renderSectionToggle(extension, graphics, section, x, y, BANNER_WIDTH, BANNER_HEIGHT, isHovering);
        }
    }

    private static void renderEmptyRows(CreativeModeTab tab, GuiGraphics graphics) {
        CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;

        if(extension.moderntabs$hasFiller()) {
            final int totalRows = TOTAL_ROWS.getOrDefault(tab, 0);
            final int firstEmptyRow = Math.max(totalRows - CURRENT_ROW, 0);

            if (extension.moderntabs$hasCustomBackgroundColor()) {
                ModernColor color = extension.moderntabs$getBackgroundColor();
                graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            for (int row = firstEmptyRow; row < VISIBLE_ROWS; row++) {
                graphics.blitSprite(extension.moderntabs$getFiller(), 0, row * BANNER_HEIGHT, BANNER_WIDTH, BANNER_HEIGHT);
            }

            if (extension.moderntabs$hasCustomBackgroundColor()) {
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
            ModernColor color = new ModernColor(decoration.color().get());
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        } else if (extension.moderntabs$hasCustomBackgroundColor() && decoration instanceof Section.Overlay) {
            ModernColor color = extension.moderntabs$getBackgroundColor();
            graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
        }

        customGraphics.moderntabs$blitSprite(bannerSprite, Section.Banner.MISSING_BANNER, x, y, w, h);

        if (decoration.color().isPresent()) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (extension.moderntabs$hasCustomBackgroundColor() && decoration instanceof Section.Overlay) {
            graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void drawAuraText(final GuiGraphics graphics, final Component text, final int color1, final int color2, final int x, final int y) {
        final Font font = Minecraft.getInstance().font;
        final Window window = Minecraft.getInstance().getWindow();
        final float scale = (float) window.getGuiScale();

        graphics.drawString(font, text, x, y, color1, true);

        final PoseStack ps = graphics.pose();
        ps.pushPose();
        ps.translate(0, 0, 1);
        final Matrix4f pose = ps.last().copy().pose();
        final Vector3f position = pose.transformPosition(new Vector3f(x, y, 0));
        final Vector3f corner = pose.transformPosition(new Vector3f(x + font.width(text), y + font.lineHeight / 1.8f, 0));

        position.mul(scale);
        corner.mul(scale);
        final int height = (int) (corner.y - position.y);
        final int width = (int) (corner.x - position.x);
        RenderSystem.enableScissor(
                (int) position.x,
                window.getHeight() - (int) position.y - height,
                width,
                height
        );

        graphics.drawString(font, text, x, y, color2, false);

        RenderSystem.disableScissor();
        ps.popPose();
    }

    public static void renderSectionToggle(CreativeModeTabExtension extension, GuiGraphics graphics, Section section, int sectionX, int sectionY, int sectionW, int sectionH, boolean isHovering) {
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

            boolean collapsed = Sections.sectionCollapsed(section);
            ResourceLocation toggleButtonLocation;
            if(isHovering) {
                toggleButtonLocation = collapsed ? EXTEND_BUTTON_SELECTED : COLLAPSE_BUTTON_SELECTED;
            } else {
                toggleButtonLocation = collapsed ? EXTEND_BUTTON : COLLAPSE_BUTTON;
            }

            if (extension.moderntabs$hasCustomBackgroundColor()) {
                ModernColor color = extension.moderntabs$getBackgroundColor();
                graphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            graphics.blitSprite(toggleButtonLocation, orientatedX, sectionY + (sectionH / 2) - (height / 2), width, height);

            if (extension.moderntabs$hasCustomBackgroundColor()) {
                graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    private static int itemRowsOf(final CreativeModeTab tab, final ResourceLocation id) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        final int sectionRow = yValues.getInt(id);

        int nextBannerRow = TOTAL_ROWS.getOrDefault(tab, 0);
        for (final Object2IntMap.Entry<ResourceLocation> entry : yValues.object2IntEntrySet()) {
            final int row = entry.getIntValue();
            if (row > sectionRow && row < nextBannerRow) {
                nextBannerRow = row;
            }
        }
        return nextBannerRow - sectionRow - 1;
    }

    public static boolean canToggle(final CreativeModeTab tab, final Section section) {
        if (Sections.isCollapsible(section)) {
            final CreativeModeTabExtension extension = (CreativeModeTabExtension) tab;
            if (!Sections.sectionCollapsed(section) && !extension.moderntabs$doesAllowLessVisibleRows()) {
                final ResourceLocation id = Sections.getId(section);
                final Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);

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

    public static void processItems(final CreativeModeTab tab, final Collection<ItemStack> originalDisplayItems, final Consumer<ItemStack> displayItems, final Consumer<ItemStack> searchItems) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = new Object2IntOpenHashMap<>();
        SECTIONS.put(tab, yValues);

        final Map<Section, List<ItemStack>> sectionMap = new LinkedHashMap<>();
        final List<ItemStack> unassigned = new ArrayList<>();

        for (final ItemStack stack : originalDisplayItems) {
            final ResourceLocation sectionId = SectionUtil.sectionOf(stack.getItem());
            final Section section = sectionId == null ? null : Sections.get(sectionId);
            if (section == null) {
                unassigned.add(stack);
            } else {
                sectionMap.computeIfAbsent(section, s -> new LinkedList<>()).add(stack);
            }
        }

        int y = 0;

        if (!unassigned.isEmpty()) {
            final int count = countLeftoverItems(true, unassigned, displayItems, searchItems);
            y = (int) Math.ceil(count / 9.0f);
            final int remainder = count % 9;
            if (remainder != 0) {
                for (int i = 0; i < 9 - remainder; i++) {
                    displayItems.accept(ItemStack.EMPTY);
                }
            }
        }

        if (sectionMap.isEmpty()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            displayItems.accept(ItemStack.EMPTY);
        }

        final List<Section> sectionKeys = sectionMap.keySet().stream().sorted().toList();
        for (final Section section : sectionKeys) {
            final int itemCount = countLeftoverItems(!Sections.sectionCollapsed(section), sectionMap.get(section), displayItems, searchItems);

            final ResourceLocation id = Sections.getId(section);
            yValues.put(id, y);
            final int rowCount = (int) Math.ceil(itemCount / 9.0f);
            y += rowCount + 1;

            if (section.equals(sectionKeys.getLast())) {
                break;
            }

            int padding = 9 - itemCount % 9;
            if (padding < 9) {
                padding += 9;
            }
            for (int i = 0; i < padding; i++) {
                displayItems.accept(ItemStack.EMPTY);
            }
        }
        TOTAL_ROWS.put(tab, y);
    }

    private static int countLeftoverItems(boolean extended, final List<ItemStack> stacks, final Consumer<ItemStack> displayItems, final Consumer<ItemStack> searchItems) {
        int count = 0;
        for (final ItemStack stack : stacks) {
            final ItemStack transformed = SectionUtil.applyTransform(stack);
            if (SectionUtil.ItemVisibility.SEARCH_ONLY.has(transformed.getItem())) {
                searchItems.accept(transformed);
            } else if (!SectionUtil.ItemVisibility.INVISIBLE.has(transformed.getItem())) {
                searchItems.accept(transformed);
                if(extended) {
                    displayItems.accept(transformed);
                    count++;
                }
            }
        }
        return count;
    }

    @Nullable
    public static Section findSectionAt(final CreativeModeTab tab, final int left, final int top, final double mouseX, final double mouseY) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = SECTIONS.get(tab);
        final int visibleRow = (int) ((mouseY - top) / BANNER_HEIGHT);

        if (!(yValues == null || yValues.isEmpty()) && !(mouseX < left || mouseX >= left + BANNER_WIDTH || mouseY < top) && visibleRow < VISIBLE_ROWS) {
            for (final Object2IntMap.Entry<ResourceLocation> entry : yValues.object2IntEntrySet()) {
                if (entry.getIntValue() - CURRENT_ROW == visibleRow) {
                    return Sections.get(entry.getKey());
                }
            }
        }
        return null;
    }


    public static void setPlaying(final ResourceLocation resourceLocation, final boolean playing) {
        final TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(resourceLocation);
        final SpriteContents.Ticker ticker = ((SpriteContentsExtension) sprite.contents()).moderntabs$getTicker();
        if (ticker instanceof TickerExtension extension) {
            extension.moderntabs$setPlaying(playing);
        }
    }
}
