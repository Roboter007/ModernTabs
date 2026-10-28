package de.Roboter007.moderntabs.mixin.tab.searchbar;

import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.extensions.SectionLayoutExtension;
import de.Roboter007.moderntabs.tab.searchbar.Searchbar;
import de.Roboter007.moderntabs.tab.section.renderer.SectionRenderer;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
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
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Searchbar searchbar = tabExtension.moderntabs$getCustomSearchbar();

        if (tabExtension.moderntabs$hasCustomSearchbar()) {
            ResourceLocation searchbarLocation = searchbar.getSearchbarLocation();
            SimpleColor color = Optional.ofNullable(searchbar.getColor()).orElse(tabExtension.moderntabs$getBackground().getColor());

            if (color != null) {
                guiGraphics.setColor(color.normalizedRed(), color.normalizedGreen(), color.normalizedBlue(), color.normalizedAlpha());
            }

            int x;
            int y = this.topPos + 4;
            ElementOrientation orientation = searchbar.getOrientation();
            if (orientation == ElementOrientation.LEFT) {
                x = this.leftPos + 8;
            } else if (orientation == ElementOrientation.CENTERED) {
                x = this.leftPos + 8 + 162 / 2 - tabExtension.moderntabs$getCustomSearchbar().getEditBoxWidth() / 2;
            } else {
                x = this.leftPos + 80;
            }

            x = x + searchbar.getOffset().x();
            y = y + searchbar.getOffset().y();

            guiGraphics.blitSprite(searchbarLocation, x, y, searchbar.getWidth(), searchbar.getHeight());

            if (color != null) {
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Inject(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;refreshSearchResults()V", shift = At.Shift.BEFORE))
    private void selectTab$ifSelected(CreativeModeTab tab, CallbackInfo ci) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;
        Searchbar searchbar = tabExtension.moderntabs$getCustomSearchbar();

        if (tabExtension.moderntabs$hasCustomSearchbar()) {
            this.searchBox.setHeight(searchbar.getEditBoxHeight());

            int x;
            ElementOrientation orientation = searchbar.getOrientation();
            if (orientation == ElementOrientation.LEFT) {
                x = this.leftPos + 10;
            } else if (orientation == ElementOrientation.CENTERED) {
                x = this.leftPos + 10 + 162 / 2 - searchbar.getEditBoxWidth() / 2;
            } else {
                x = this.leftPos + 82;
            }

            this.searchBox.setX(x);
            if (searchbar.getFontLocation() != null) {
                this.searchBox.setFormatter((text, start) -> FormattedCharSequence.forward(text, Style.EMPTY.withFont(searchbar.getFontLocation())));
            }
        }
    }

    @Inject(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setValue(Ljava/lang/String;)V", ordinal = 1))
    private void selectTab$IfNotSelected(CreativeModeTab tab, CallbackInfo ci) {
        this.searchBox.setX(this.leftPos + 82);
        this.searchBox.setFormatter((text, start) -> FormattedCharSequence.forward(text, Style.EMPTY.withFont(Minecraft.DEFAULT_FONT)));
    }

    @Redirect(method = {"selectTab", "refreshSearchResults", "tryRefreshInvalidatedTabs"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getDisplayItems()Ljava/util/Collection;"))
    private Collection<ItemStack> moderntabs$useSectionLayout(CreativeModeTab tab) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;
        SectionLayoutExtension layoutExtension = (SectionLayoutExtension) tab;
        if (tabExtension.moderntabs$hasCustomSections()) {
            return layoutExtension.moderntabs$layoutSections();
        }
        return tab.getDisplayItems();
    }


    @Inject(method = "refreshSearchResults", at = @At("TAIL"))
    private void moderntabs$layoutSearchResults(CallbackInfo ci) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) selectedTab;

        if(tabExtension.moderntabs$hasCustomSections() && tabExtension.moderntabs$hasCustomSearchbar() && !this.searchBox.getValue().isEmpty()) {
            List<ItemStack> layout = SectionRenderer.layoutSearchResults(selectedTab, List.copyOf(this.menu.items));

            if (layout != null) {
                this.menu.items.clear();
                this.menu.items.addAll(layout);
                this.menu.scrollTo(0.0F);
            }
        }
    }
}
