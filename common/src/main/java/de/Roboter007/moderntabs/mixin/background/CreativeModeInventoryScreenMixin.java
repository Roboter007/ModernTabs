package de.Roboter007.moderntabs.mixin.background;

import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.util.ModernColor;
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
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    @Shadow
    private static CreativeModeTab selectedTab;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Redirect(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    protected void renderBg(GuiGraphics guiGraphics, ResourceLocation atlasLocation, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        CreativeModeTabExtension extension = (CreativeModeTabExtension) selectedTab;
        if(extension.moderntabs$hasCustomBackgroundColor()) {
            // set color
            ModernColor backgroundColor = extension.moderntabs$getBackgroundColor();
            guiGraphics.setColor(backgroundColor.normalizedRed(), backgroundColor.normalizedGreen(), backgroundColor.normalizedBlue(), backgroundColor.normalizedAlpha());

            // render
            guiGraphics.blit(atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight);

            // reset color
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            guiGraphics.blit(atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight);
        }
    }
}
