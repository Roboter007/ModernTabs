package de.Roboter007.moderntabs.mixin.icon;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.extensions.CreativeModeTabExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryMixin {

    @Inject(method = "renderTabButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getIconItem()Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    public void renderTabButton(GuiGraphics guiGraphics, CreativeModeTab creativeModeTab, CallbackInfo ci, @Local(name = "k") int k, @Local(name = "j") int j) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) creativeModeTab;
        if(tabExtension.moderntabs$hasCustomTabIcon()) {
            guiGraphics.blitSprite(tabExtension.moderntabs$getCustomTabIcon(), j, k, 16, 16);
            guiGraphics.pose().popPose();
            ci.cancel();
        }
    }
}
