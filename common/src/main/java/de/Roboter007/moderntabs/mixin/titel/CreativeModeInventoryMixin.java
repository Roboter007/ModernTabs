package de.Roboter007.moderntabs.mixin.titel;

import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.titel.*;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
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
            CustomTabTitel customTabTitel = creativeModeTabExtension.moderntabs$getCustomTabTitel();

            final int textWidth;
            if(customTabTitel instanceof SpriteTabTitel spriteTabTitel) {
                textWidth = spriteTabTitel.getWidth();
            } else {
                textWidth = font.width(text);
            }

            if(customTabTitel.getTextOrientation() == TextOrientation.CENTERED) {
                x += (160 - textWidth) / 2;
            } else if(customTabTitel.getTextOrientation() == TextOrientation.RIGHT) {
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

            Font titelFont = font;
            if(customTabTitel.getFont() != null) {
                titelFont = customTabTitel.getFont();
            }

            ModernColor titelColor = new ModernColor(color);
            if(customTabTitel.getColor() != null) {
                titelColor = customTabTitel.getColor();
            }

            boolean titelDropShadow = dropShadow;
            if(customTabTitel.isDroppingShadow() != null) {
                titelDropShadow = customTabTitel.isDroppingShadow();
            }

            if(customTabTitel instanceof AuraTabTitel auraTabTitel) {
                Font titelFont2 = font;
                if(auraTabTitel.getFont2() != null) {
                    titelFont2 = auraTabTitel.getFont2();
                }

                ModernColor titelColor2 = new ModernColor(color);
                if(auraTabTitel.getColor2() != null) {
                    titelColor2 = auraTabTitel.getColor2();
                }

                boolean titelDropShadow2 = dropShadow;
                if(auraTabTitel.isDroppingShadow2() != null) {
                    titelDropShadow2 = auraTabTitel.isDroppingShadow2();
                }

                TabTitelRenderer.drawAuraText(guiGraphics, text, titelFont, titelFont2, titelColor.color(), titelColor2.color(), titelDropShadow, titelDropShadow2, x, y);
            } else if(customTabTitel instanceof SpriteTabTitel spriteTabTitel) {
                guiGraphics.blitSprite(spriteTabTitel.getSpriteTitelLocation(), x, y, spriteTabTitel.getWidth(), spriteTabTitel.getHeight());
            } else {
                guiGraphics.drawString(titelFont, text, x, y, titelColor.color(), titelDropShadow);
            }
        } else {
            guiGraphics.drawString(this.font, text, x, y, color, dropShadow);
        }

        return x;
    }
}
