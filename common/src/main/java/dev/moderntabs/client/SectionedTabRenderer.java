package dev.moderntabs.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.moderntabs.item.SectionedItems;
import dev.moderntabs.item.TabItemTransforms;
import dev.moderntabs.mixin.AbstractContainerScreenAccessor;
import dev.moderntabs.interfaces.SpriteContentsExtension;
import dev.moderntabs.interfaces.TickerExtension;
import dev.moderntabs.section.Section;
import dev.moderntabs.section.Sections;
import dev.moderntabs.util.ColorUtil;
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
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class SectionedTabRenderer {

    public static int CURRENT_ROW = 0;

    private static final Map<CreativeModeTab, Object2IntOpenHashMap<ResourceLocation>> SECTION_Y_VALUES = new IdentityHashMap<>();

    private SectionedTabRenderer() {
    }

    public static void renderBanners(final CreativeModeTab tab, final CreativeModeInventoryScreen screen, final GuiGraphics graphics, final int mouseX, final int mouseY) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = SECTION_Y_VALUES.get(tab);
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
            final ResourceLocation id = Sections.getId(section);
            if (!yValues.containsKey(id)) {
                continue;
            }

            final int yValue = yValues.getInt(id);
            final int sectionRow = yValue - CURRENT_ROW;
            if (sectionRow < 0 || sectionRow > 4) {
                continue;
            }

            final Font font = Minecraft.getInstance().font;
            final int x = 0;
            final int y = sectionRow * 18;
            final int w = 162;
            final int h = 18;

            final ResourceLocation bannerTexture = section.sprite();

            if (section.animateOnHover()) {
                final boolean isHovering = mouseX >= left + x && mouseX <= left + x + w
                        && mouseY >= top + y && mouseY <= top + y + h;
                setPlaying(bannerTexture, isHovering);
            }

            graphics.blitSprite(bannerTexture, x, y, w, h);

            final Component text = section.title().text();
            final int textWidth = font.width(text);

            final int background = section.title().background();
            graphics.fill(x + 2, y + 2, x + textWidth + 8, y + h - 2, background);

            final int light = section.title().color();
            final int dark = section.title().secondaryColor().orElseGet(() -> ColorUtil.darken(light, 0.2f));
            drawAuraText(graphics, text, dark, light, x + 5, y + 5);
        }

        ps.popPose();
        RenderSystem.disableDepthTest();
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

    public static void processItems(final CreativeModeTab tab, final Collection<ItemStack> originalDisplayItems,
                                     final Consumer<ItemStack> displayItems, final Consumer<ItemStack> searchItems) {
        final Object2IntOpenHashMap<ResourceLocation> yValues = new Object2IntOpenHashMap<>();
        SECTION_Y_VALUES.put(tab, yValues);

        final Map<Section, List<ItemStack>> sectionMap = new LinkedHashMap<>();
        final List<ItemStack> unassigned = new ArrayList<>();

        for (final ItemStack stack : originalDisplayItems) {
            final ResourceLocation sectionId = SectionedItems.sectionOf(stack.getItem());
            final Section section = sectionId == null ? null : Sections.get(sectionId);
            if (section == null) {
                unassigned.add(stack);
            } else {
                sectionMap.computeIfAbsent(section, s -> new LinkedList<>()).add(stack);
            }
        }

        int y = 0;

        if (!unassigned.isEmpty()) {
            final int count = emitItems(unassigned, displayItems, searchItems);
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
        for (final Section key : sectionKeys) {
            final int itemCount = emitItems(sectionMap.get(key), displayItems, searchItems);

            final ResourceLocation id = Sections.getId(key);
            yValues.put(id, y);
            final int rowCount = (int) Math.ceil(itemCount / 9.0f);
            y += rowCount + 1;

            if (key.equals(sectionKeys.getLast())) {
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
    }

    private static int emitItems(final List<ItemStack> stacks, final Consumer<ItemStack> displayItems, final Consumer<ItemStack> searchItems) {
        int count = 0;
        for (final ItemStack stack : stacks) {
            final ItemStack transformed = TabItemTransforms.applyTransform(stack);
            if (TabItemTransforms.VisibilityType.SEARCH_ONLY.has(transformed.getItem())) {
                searchItems.accept(transformed);
            } else if (!TabItemTransforms.VisibilityType.INVISIBLE.has(transformed.getItem())) {
                displayItems.accept(transformed);
                searchItems.accept(transformed);
                count++;
            }
        }
        return count;
    }

    public static void setPlaying(final ResourceLocation resourceLocation, final boolean playing) {
        final TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(resourceLocation);
        final SpriteContents.Ticker ticker = ((SpriteContentsExtension) sprite.contents()).moderntabs$getTicker();
        if (ticker instanceof TickerExtension extension) {
            extension.moderntabs$setPlaying(playing);
        }
    }
}
