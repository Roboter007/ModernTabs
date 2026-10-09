package de.Roboter007.moderntabs.mixin.tab.searchbar;

import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtensionPlatform;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.FontUtil;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    @Shadow
    private static CreativeModeTab selectedTab;

    @Shadow
    private EditBox searchBox;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER))
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;
        CreativeModeTabExtensionPlatform tabExtensionPlatform = (CreativeModeTabExtensionPlatform) selectedTab;

        if (tabExtensionPlatform.moderntabs$hasSearchbar()) {
            ResourceLocation searchbarLocation = tabExtension.moderntabs$getSearchbarLocation();

            if (tabExtension.moderntabs$hasCustomBackgroundColor()) {
                ModernColor color = tabExtension.moderntabs$getBackgroundColor();
                guiGraphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            int x;
            ElementOrientation orientation = tabExtension.moderntabs$getSearchbarOrientation();
            if (orientation == ElementOrientation.LEFT) {
                x = this.leftPos + 8;
            } else if (orientation == ElementOrientation.CENTERED) {
                x = this.leftPos + 8 + 162 / 2 - tabExtensionPlatform.moderntabs$getSearchbarEditBoxWidth() / 2;
            } else {
                x = this.leftPos + 80;
            }

            guiGraphics.blitSprite(searchbarLocation, x, this.topPos + 4, tabExtension.moderntabs$getSearchbarWidth(), tabExtension.moderntabs$getSearchbarHeight());

            if (tabExtension.moderntabs$hasCustomBackgroundColor()) {
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Inject(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;refreshSearchResults()V", shift = At.Shift.BEFORE))
    private void selectTab$ifSelected(CreativeModeTab tab, CallbackInfo ci) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;
        CreativeModeTabExtensionPlatform tabExtensionPlatform = (CreativeModeTabExtensionPlatform) tab;

        if (tabExtensionPlatform.moderntabs$hasSearchbar()) {
            this.searchBox.setHeight(tabExtension.moderntabs$getSearchbarEditBoxHeight());

            int x;
            ElementOrientation orientation = tabExtension.moderntabs$getSearchbarOrientation();
            if (orientation == ElementOrientation.LEFT) {
                x = this.leftPos + 10;
            } else if (orientation == ElementOrientation.CENTERED) {
                x = this.leftPos + 10 + 162 / 2 - tabExtensionPlatform.moderntabs$getSearchbarEditBoxWidth() / 2;
            } else {
                x = this.leftPos + 82;
            }

            this.searchBox.setX(x);
            if (tabExtension.moderntabs$searchbarHasCustomFont()) {
                this.searchBox.setFormatter((text, start) -> FormattedCharSequence.forward(text, Style.EMPTY.withFont(tabExtension.moderntabs$getSearchbarFontLocation())));
            }
        }
    }

    @Inject(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setValue(Ljava/lang/String;)V", ordinal = 1))
    private void selectTab$IfNotSelected(CreativeModeTab tab, CallbackInfo ci) {
        this.searchBox.setX(this.leftPos + 82);
        this.searchBox.setFormatter((text, start) -> FormattedCharSequence.forward(text, Style.EMPTY.withFont(Minecraft.DEFAULT_FONT)));
    }
}
