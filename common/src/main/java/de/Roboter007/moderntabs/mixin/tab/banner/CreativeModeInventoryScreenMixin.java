package de.Roboter007.moderntabs.mixin.tab.banner;

import com.llamalad7.mixinextras.sugar.Local;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.section.Section;
import de.Roboter007.moderntabs.tab.section.renderer.SectionRenderer;
import de.Roboter007.moderntabs.tab.section.Sections;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Shadow
    private static CreativeModeTab selectedTab;

    @Shadow
    protected abstract void refreshCurrentTabContents(Collection<ItemStack> items);

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void moderntabs$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        final CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;
        if (button == 0 && tabExtension.moderntabs$hasCustomSections()) {
            final AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor) this;
            final Section section = SectionRenderer.findSectionAt(selectedTab, accessor.getLeftPos() + 8, accessor.getTopPos() + 17, mouseX, mouseY);
            if (section != null && SectionRenderer.canToggle(selectedTab, section) && Sections.toggleCollapsedState(section)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.refreshCurrentTabContents(selectedTab.getDisplayItems());
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void moderntabs$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;
        if (tabExtension.moderntabs$hasCustomSections()) {
            SectionRenderer.renderBanners(selectedTab, (CreativeModeInventoryScreen) (Object) this, guiGraphics, mouseX, mouseY);
        }
    }

    @Inject(method = "getTooltipFromContainerItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTabs;tabs()Ljava/util/List;"))
    private void moderntabs$getTooltipFromContainerItem(ItemStack stack, CallbackInfoReturnable<List<Component>> cir, @Local(ordinal = 1) List<Component> list1, @Local int i) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;
        if(tabExtension.moderntabs$hasCustomTabContent()) {
            for(ResourceLocation sectionLocation : tabExtension.moderntabs$getCustomTabContent().getSections()) {
                Section section = Sections.get(sectionLocation);
                if(section != null) {
                    if (section.getContent().contains(stack) && section.getTitle().getText() != null) {
                        list1.add(i, section.getTitle().getText().copy().withStyle(ChatFormatting.BLUE));
                    }
                } else {
                    ModernTabs.logWarning("Section: " + sectionLocation + " - does not exist!");
                }
            }
        }
    }
}
