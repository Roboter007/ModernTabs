package de.Roboter007.moderntabs.fabric.mixin.tab.searchbar;

import de.Roboter007.moderntabs.fabric.tab.searchbar.SessionSearchTreesExtension;
import de.Roboter007.moderntabs.fabric.tab.section.CreativeModeTabSearchRegistry;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtensionPlatform;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

    @Shadow
    private EditBox searchBox;

    @Shadow
    private static CreativeModeTab selectedTab;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;refreshSearchResults()V", shift = At.Shift.BEFORE))
    private void selectTab(CreativeModeTab tab, CallbackInfo ci) {
        CreativeModeTabExtensionPlatform extension = (CreativeModeTabExtensionPlatform) tab;

        if(extension.moderntabs$hasSearchbar()) {
            this.searchBox.setWidth(extension.moderntabs$getSearchbarEditBoxWidth());
        }
    }

    @Inject(method = "tryRebuildTabContents", at = @At(value = "RETURN"))
    private void tryRebuildTabContents(SessionSearchTrees searchTrees, FeatureFlagSet enabledFeatures, boolean hasPermissions, HolderLookup.Provider registries, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && searchTrees != null) {
            SessionSearchTreesExtension extension = (SessionSearchTreesExtension) searchTrees;

            for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
                // search tab is already handled -> ignore it here
                if (tab != CreativeModeTabs.searchTab() && ((CreativeModeTabExtensionPlatform) tab).moderntabs$hasSearchbar()) {
                    List<ItemStack> list = List.copyOf(tab.getDisplayItems());
                    extension.moderntabs$updateCreativeTooltips(registries, list, CreativeModeTabSearchRegistry.getNameSearchKey(tab));
                    extension.moderntabs$updateCreativeTags(list, CreativeModeTabSearchRegistry.getTagSearchKey(tab));
                }
            }
        }
    }

    @Redirect(method = "refreshSearchResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/SessionSearchTrees;creativeNameSearch()Lnet/minecraft/client/searchtree/SearchTree;"))
    private SearchTree<ItemStack> moderntabs$creativeNameSearch(SessionSearchTrees searchTrees) {
        SessionSearchTrees.Key key = CreativeModeTabSearchRegistry.getNameSearchKey(selectedTab);
        if (key == null) {
            key = SessionSearchTrees.CREATIVE_NAMES;
        }
        return ((SessionSearchTreesExtension) searchTrees).moderntabs$creativeNameSearch(key);
    }

    @Redirect(method = "refreshSearchResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/SessionSearchTrees;creativeTagSearch()Lnet/minecraft/client/searchtree/SearchTree;"))
    private SearchTree<ItemStack> moderntabs$creativeTagSearch(SessionSearchTrees searchTrees) {
        SessionSearchTrees.Key key = CreativeModeTabSearchRegistry.getTagSearchKey(selectedTab);
        if (key == null) {
            key = SessionSearchTrees.CREATIVE_TAGS;
        }
        return ((SessionSearchTreesExtension) searchTrees).moderntabs$creativeTagSearch(key);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    }
}
