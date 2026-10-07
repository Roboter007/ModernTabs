package de.Roboter007.moderntabs.mixin.iconBackground;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.graphics.CustomGuiGraphics;
import de.Roboter007.moderntabs.tab.button.ColoredTabButtonStates;
import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.button.TabButtonTexture;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.CreativeModeInventoryScreenPlatform;
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

    @Redirect(method = "renderTabButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    protected void renderTabButton(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, int width, int height, @Local(argsOnly = true) CreativeModeTab creativeModeTab) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) creativeModeTab;

        if(tabExtension.moderntabs$hasCustomTabButtonStates()) {
            CreativeModeInventoryScreenPlatform platform = (CreativeModeInventoryScreenPlatform) this;

            TabButtonStates tabIconBackground = tabExtension.moderntabs$getCustomTabButtonStates();
            TabButtonTexture tabBackgroundImage = tabIconBackground.get(platform.moderntabs$row(creativeModeTab), platform.moderntabs$column(creativeModeTab), TabButtonTexture.Selection.fromBoolean(creativeModeTab == selectedTab));

            if(tabIconBackground instanceof ColoredTabButtonStates coloredTabIconBackground) {
                // set color
                ModernColor color = coloredTabIconBackground.color();
                guiGraphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());

                // render
                CustomGuiGraphics customGuiGraphics = (CustomGuiGraphics) guiGraphics;
                customGuiGraphics.moderntabs$blitSprite(tabBackgroundImage.toResourceLocation(), tabBackgroundImage.toDefaultLocation(), x, y, width, height);

                // reset color
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            } else {

                // uff, just added a completely new system for just a better fail save for this - why? -> I don't know
                CustomGuiGraphics customGuiGraphics = (CustomGuiGraphics) guiGraphics;
                customGuiGraphics.moderntabs$blitSprite(tabBackgroundImage.toResourceLocation(), tabBackgroundImage.toDefaultLocation(), x, y, width, height);
            }
        } else {
            guiGraphics.blitSprite(sprite, x, y, width, height);
        }
    }

    @Override
    @Shadow
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}
}
