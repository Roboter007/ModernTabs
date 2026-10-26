package de.Roboter007.moderntabs.mixin.tab.background;

import de.Roboter007.moderntabs.tab.background.TabBackground;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.color.SimpleColor;
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

import java.util.Objects;

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
        if(extension.moderntabs$hasCustomBackground()) {
            TabBackground tabBackground = extension.moderntabs$getBackground();

            ResourceLocation backgroundLocation = tabBackground.getBackgroundLocation();
            SimpleColor backgroundColor = tabBackground.getColor();

            if(tabBackground.hasCustomColor()) {
                // set color
                guiGraphics.setColor(backgroundColor.normalizedRed(), backgroundColor.normalizedGreen(), backgroundColor.normalizedBlue(), backgroundColor.normalizedAlpha());
            }

            // render
            guiGraphics.blit(Objects.requireNonNullElse(backgroundLocation, atlasLocation), x, y, uOffset, vOffset, uWidth, vHeight);

            if(tabBackground.hasCustomColor()) {
                // reset color
                guiGraphics.flush();
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        } else {
            guiGraphics.blit(atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight);
        }
    }
}
