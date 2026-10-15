package de.Roboter007.moderntabs.mixin.tab.titel;

import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.section.renderer.TextRenderer;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.tab.titel.AuraTabTitel;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.tab.titel.SpriteTabTitel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    @Shadow
    private static CreativeModeTab selectedTab;

    @Override
    @Shadow
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}

    public CreativeModeInventoryMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Redirect(method = "renderLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"))
    protected int renderLabels(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int color, boolean dropShadow) {
        CreativeModeTabExtension creativeModeTabExtension = (CreativeModeTabExtension) selectedTab;
        if(creativeModeTabExtension.moderntabs$hasCustomTabTitelRendering()) {
            CustomTabTitel<?> customTabTitel = creativeModeTabExtension.moderntabs$getCustomTabTitel();

            final int textWidth;
            if(customTabTitel instanceof SpriteTabTitel spriteTabTitel) {
                textWidth = spriteTabTitel.getWidth();
            } else {
                textWidth = font.width(text);
            }

            if(customTabTitel.getTextOrientation() == ElementOrientation.CENTERED) {
                x += (160 - textWidth) / 2;
            } else if(customTabTitel.getTextOrientation() == ElementOrientation.RIGHT) {
                x += 160 - textWidth;
            }

            if(customTabTitel.getBackgroundColor() != null) {
                final int height = 10;
                guiGraphics.fill(x, y - 2, x + textWidth + 2, y + height, customTabTitel.getBackgroundColor().color());
                x++;
                if(customTabTitel instanceof SpriteTabTitel) {
                    y--;
                }
            }

            ResourceLocation titelFontLocation;
            if(customTabTitel.getFontLocation() != null) {
                titelFontLocation = customTabTitel.getFontLocation();
            } else {
                titelFontLocation = Minecraft.DEFAULT_FONT;
            }

            SimpleColor titelColor = new SimpleColor(color);
            if(customTabTitel.getColor() != null) {
                titelColor = customTabTitel.getColor();
            }

            boolean titelDropShadow = dropShadow;
            if(customTabTitel.isDroppingShadow() != null) {
                titelDropShadow = customTabTitel.isDroppingShadow();
            }

            x = x + customTabTitel.getOffset().x();
            y = y + customTabTitel.getOffset().y();

            if(customTabTitel instanceof AuraTabTitel auraTabTitel) {
                ResourceLocation titelFont2Location = Minecraft.DEFAULT_FONT;
                if(auraTabTitel.getFont2Location() != null) {
                    titelFont2Location = auraTabTitel.getFont2Location();
                }

                SimpleColor titelColor2 = new SimpleColor(color);
                if(auraTabTitel.getColor2() != null) {
                    titelColor2 = auraTabTitel.getColor2();
                }

                boolean titelDropShadow2 = dropShadow;
                if(auraTabTitel.isDroppingShadow2() != null) {
                    titelDropShadow2 = auraTabTitel.isDroppingShadow2();
                }

                TextRenderer.drawAuraText(guiGraphics, text, titelFontLocation, titelFont2Location, titelColor.color(), titelColor2.color(), titelDropShadow, titelDropShadow2, x, y);
            } else if(customTabTitel instanceof SpriteTabTitel spriteTabTitel) {
                guiGraphics.blitSprite(spriteTabTitel.getSpriteTitelLocation(), x, y, spriteTabTitel.getWidth(), spriteTabTitel.getHeight());
            } else {
                guiGraphics.drawString(font, text.copy().withStyle(style -> style.withFont(titelFontLocation)), x, y, titelColor.color(), titelDropShadow);
            }
        } else {
            guiGraphics.drawString(this.font, text, x, y, color, dropShadow);
        }

        return x;
    }
}
