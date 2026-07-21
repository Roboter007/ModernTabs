package de.Roboter007.moderntabs.mixin.banner;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.section.client.SectionedTabRenderer;
import de.Roboter007.moderntabs.section.item.SectionedItems;
import de.Roboter007.moderntabs.section.Section;
import de.Roboter007.moderntabs.section.Sections;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {

    @Shadow
    private static CreativeModeTab selectedTab;

    @Inject(method = "render", at = @At("TAIL"))
    private void moderntabs$render(final GuiGraphics guiGraphics, final int mouseX, final int mouseY, final float partialTick, final CallbackInfo ci) {
        if (ModernTabs.hasSections(selectedTab)) {
            SectionedTabRenderer.renderBanners(selectedTab, (CreativeModeInventoryScreen) (Object) this, guiGraphics, mouseX, mouseY);
        }
    }

    @Inject(method = "getTooltipFromContainerItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTabs;tabs()Ljava/util/List;"))
    private void moderntabs$getTooltipFromContainerItem(final ItemStack stack, final CallbackInfoReturnable<List<Component>> cir, @Local(ordinal = 1) final List<Component> list1, @Local final int i) {
        final ResourceLocation sectionId = SectionedItems.sectionOf(stack.getItem());
        if (sectionId == null) {
            return;
        }
        final Section section = Sections.get(sectionId);
        if (section != null) {
            list1.add(i, section.title().text().copy().withStyle(ChatFormatting.BLUE));
        }
    }
}
