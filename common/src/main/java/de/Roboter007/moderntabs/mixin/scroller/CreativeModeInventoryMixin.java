package de.Roboter007.moderntabs.mixin.scroller;

import de.Roboter007.moderntabs.iconBackground.CustomGuiGraphics;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryMixin {

    @Shadow
    private static CreativeModeTab selectedTab;

    @Redirect(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    public void renderScroller(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, int width, int height) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;
        // set color
        if(tabExtension.moderntabs$hasCustomBackgroundColor()) {
            ModernColor scrollerColor = tabExtension.moderntabs$getBackgroundColor();
            guiGraphics.setColor(scrollerColor.normalizedRed(), scrollerColor.normalizedGreen(), scrollerColor.normalizedBlue(), scrollerColor.normalizedAlpha());
        }

        // render
        if(tabExtension.moderntabs$hasCustomScroller()) {
            CustomGuiGraphics customGuiGraphics = (CustomGuiGraphics) guiGraphics;
            customGuiGraphics.moderntabs$blitSprite(tabExtension.moderntabs$getCustomScroller(), sprite, x, y, width, height);
        } else {
            guiGraphics.blitSprite(sprite, x, y, width, height);
        }

        // reset color
        if(tabExtension.moderntabs$hasCustomBackgroundColor()) {
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
