package de.Roboter007.moderntabs.mixin.tab.button;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.graphics.CustomGuiGraphics;
import de.Roboter007.moderntabs.tab.button.TabButton;
import de.Roboter007.moderntabs.tab.button.TabButtonSprite;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.platform.CreativeModeInventoryScreenPlatform;
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

import java.util.Optional;

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

        if(tabExtension.moderntabs$hasCustomTabButton()) {
            CreativeModeInventoryScreenPlatform platform = (CreativeModeInventoryScreenPlatform) this;

            TabButton tabButton = tabExtension.moderntabs$getCustomTabButton();
            TabButtonSprite tabBackgroundImage = tabButton.get(platform.moderntabs$row(creativeModeTab), platform.moderntabs$column(creativeModeTab), TabButtonSprite.Selection.fromBoolean(creativeModeTab == selectedTab));
            SimpleColor color = Optional.ofNullable(tabButton.getColor()).orElse(tabExtension.moderntabs$getBackground().getColor());

            if(color != null) {
                // set color
                guiGraphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            // render
            // uff, just added a completely new system for just a better fail save for this - why? -> I don't know
            CustomGuiGraphics customGuiGraphics = (CustomGuiGraphics) guiGraphics;
            customGuiGraphics.moderntabs$blitSprite(tabButton.hasCustomTabButtonSprite() ? tabBackgroundImage.toResourceLocation() : sprite, tabBackgroundImage.toDefaultLocation(), x, y, width, height);

            if(color != null) {
                // reset color
                guiGraphics.flush();
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        } else {
            guiGraphics.blitSprite(sprite, x, y, width, height);
        }
    }

    @Override
    @Shadow
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}
}
