package dev.moderntabs.mixin;

import dev.moderntabs.client.SectionedTabRenderer;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class ItemPickerMenuMixin {

    @Shadow
    protected abstract int getRowIndexForScroll(float f);

    @Inject(method = "scrollTo", at = @At("HEAD"))
    private void moderntabs$scrollTo(final float f, final CallbackInfo ci) {
        SectionedTabRenderer.CURRENT_ROW = this.getRowIndexForScroll(f);
    }
}
