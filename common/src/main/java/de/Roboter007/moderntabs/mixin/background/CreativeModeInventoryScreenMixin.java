package de.Roboter007.moderntabs.mixin.background;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.background.CustomGuiGraphics;
import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage;
import de.Roboter007.moderntabs.platform.ModernTabsPlatform;
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
    protected void renderTabButton(GuiGraphics instance, ResourceLocation sprite, int x, int y, int width, int height, @Local(argsOnly = true) CreativeModeTab creativeModeTab) {
        if(ModernTabs.hasCustomTabBackground(creativeModeTab)) {
            TabIconBackgroundImage tabBackgroundImage = ModernTabs.getCustomTabBackground(creativeModeTab).get(creativeModeTab.row(), ModernTabsPlatform.get().fromInteger(creativeModeTab.column()), TabIconBackgroundImage.Selection.fromBoolean(creativeModeTab == selectedTab));
            // uff, just added a completely new system for just a better fail save for this - why? -> I don't know
            CustomGuiGraphics customGuiGraphics = (CustomGuiGraphics) instance;
            customGuiGraphics.moderntabs$blitSprite(tabBackgroundImage.toResourceLocation(), tabBackgroundImage.toDefaultLocation(), x, y, width, height);
        } else {
            instance.blitSprite(sprite, x, y, width, height);
        }
    }

    @Override
    @Shadow
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}
}
