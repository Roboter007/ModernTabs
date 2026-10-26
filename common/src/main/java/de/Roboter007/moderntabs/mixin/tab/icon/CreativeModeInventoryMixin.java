package de.Roboter007.moderntabs.mixin.tab.icon;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.icon.TabIcon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    public CreativeModeInventoryMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Shadow
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {}

    @Inject(method = "renderTabButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getIconItem()Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    public void renderTabButton(GuiGraphics guiGraphics, CreativeModeTab creativeModeTab, CallbackInfo ci, @Local(name = "k") int k, @Local(name = "j") int j) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) creativeModeTab;
        if(tabExtension.moderntabs$hasCustomTabButton() && tabExtension.moderntabs$getCustomTabButton().hasCustomIcon()) {
            TabIcon tabIcon = tabExtension.moderntabs$getCustomTabButton().getIcon();

            int x = j + tabIcon.getOffset().x();
            int y = k + tabIcon.getOffset().y();

            int width = tabIcon.getWidth();
            int height = tabIcon.getHeight();

            ItemStack iconStack = tabIcon.getItemStack();

            if(iconStack != null) {
                guiGraphics.renderItem(iconStack, width, height);
                guiGraphics.renderItemDecorations(this.font, iconStack, width, height);
            }

            if(tabIcon.getLocation() != null) {
                guiGraphics.blitSprite(tabIcon.getLocation(), x, y, width, height);
            }

            guiGraphics.pose().popPose();
            ci.cancel();
        }
    }
}
