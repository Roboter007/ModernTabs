package de.Roboter007.moderntabs.mixin.titel;

import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.titel.AuraTabTitel;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.titel.SpriteTabTitel;
import de.Roboter007.moderntabs.titel.TabTitelRenderer;
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
            if(customTabTitel.backgroundColor() != null) {
                final int textWidth = font.width(text);
                final int height = 10;
                guiGraphics.fill(x, y - 2, x + textWidth + 2, y + height, customTabTitel.backgroundColor().color());
                x++;
            }

            Font titelFont = font;
            if(customTabTitel.font() != null) {
                titelFont = customTabTitel.font();
            }

            ModernColor titelColor = new ModernColor(color);
            if(customTabTitel.color() != null) {
                titelColor = customTabTitel.color();
            }

            boolean titelDropShadow = dropShadow;
            if(customTabTitel.dropShadow() != null) {
                titelDropShadow = customTabTitel.dropShadow();
            }

            if(customTabTitel instanceof AuraTabTitel auraTabTitel) {
                Font titelFont2 = font;
                if(auraTabTitel.font2() != null) {
                    titelFont2 = auraTabTitel.font2();
                }

                ModernColor titelColor2 = new ModernColor(color);
                if(auraTabTitel.color2() != null) {
                    titelColor2 = auraTabTitel.color2();
                }

                boolean titelDropShadow2 = dropShadow;
                if(auraTabTitel.dropShadow2() != null) {
                    titelDropShadow2 = auraTabTitel.dropShadow2();
                }

                TabTitelRenderer.drawAuraText(guiGraphics, text, titelFont, titelFont2, titelColor.color(), titelColor2.color(), titelDropShadow, titelDropShadow2, x, y);
            } else if(customTabTitel instanceof SpriteTabTitel spriteTabTitel) {
                guiGraphics.blitSprite(spriteTabTitel.spriteTitelLocation(), x, y, width, height);
            } else {
                guiGraphics.drawString(titelFont, text, x, y, titelColor.color(), titelDropShadow);
            }
        } else {
            guiGraphics.drawString(this.font, text, x, y, color, dropShadow);
        }

        return x;
    }
}
