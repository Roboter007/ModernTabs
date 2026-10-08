package de.Roboter007.moderntabs.fabric.mixin.tab.searchbar;

import de.Roboter007.moderntabs.fabric.tab.searchbar.SessionSearchTreesExtension;
import de.Roboter007.moderntabs.fabric.tab.section.CreativeModeTabSearchRegistry;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.client.searchtree.FullTextSearchTree;
import net.minecraft.client.searchtree.IdSearchTree;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Mixin(SessionSearchTrees.class)
public abstract class SessionSearchTreesMixin implements SessionSearchTreesExtension {


    @Shadow
    protected abstract void register(SessionSearchTrees.Key key, Runnable reloader);

    @Shadow
    private static Stream<String> getTooltipLines(Stream<ItemStack> items, Item.TooltipContext context, TooltipFlag tooltipFlag) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "updateCreativeTooltips", at = @At("HEAD"), cancellable = true)
    public void updateCreativeTooltips(HolderLookup.Provider registries, List<ItemStack> items, CallbackInfo ci) {
        this.moderntabs$updateCreativeTooltips(registries, items, SessionSearchTrees.CREATIVE_NAMES);
        ci.cancel();
    }

    @Inject(method = "updateCreativeTags", at = @At("HEAD"), cancellable = true)
    public void updateCreativeTooltips(List<ItemStack> items, CallbackInfo ci) {
        this.moderntabs$updateCreativeTags(items, SessionSearchTrees.CREATIVE_TAGS);
        ci.cancel();
    }

    @Override
    public void moderntabs$updateCreativeTooltips(HolderLookup.Provider registries, List<ItemStack> list, SessionSearchTrees.Key key) {
        this.register(key, () -> {
            Item.TooltipContext item$tooltipcontext = Item.TooltipContext.of(registries);
            TooltipFlag tooltipflag = TooltipFlag.Default.NORMAL.asCreative();
            CompletableFuture<?> completablefuture = CreativeModeTabSearchRegistry.getNameSearchTree(key);
            CreativeModeTabSearchRegistry.putNameSearchTree(key, CompletableFuture.supplyAsync(() -> new FullTextSearchTree<>((arg) -> getTooltipLines(Stream.of(arg), item$tooltipcontext, tooltipflag), (arg) -> arg.getItemHolder().unwrapKey().map(ResourceKey::location).stream(), list), Util.backgroundExecutor()));
            completablefuture.cancel(true);
        });
    }

    @Override
    public void moderntabs$updateCreativeTags(List<ItemStack> list, SessionSearchTrees.Key key) {
        this.register(key, () -> {
            CompletableFuture<?> completablefuture = CreativeModeTabSearchRegistry.getTagSearchTree(key);
            CreativeModeTabSearchRegistry.putTagSearchTree(key, CompletableFuture.supplyAsync(() -> new IdSearchTree<>((arg) -> arg.getTags().map(TagKey::location), list), Util.backgroundExecutor()));
            completablefuture.cancel(true);
        });
    }

    @Inject(method = "creativeTagSearch", at = @At("RETURN"), cancellable = true)
    public void creativeTagSearch(CallbackInfoReturnable<SearchTree<ItemStack>> cir) {
        cir.setReturnValue(this.moderntabs$creativeTagSearch(SessionSearchTrees.CREATIVE_TAGS));
    }

    @Override
    public SearchTree<ItemStack> moderntabs$creativeTagSearch(SessionSearchTrees.Key key) {
        return CreativeModeTabSearchRegistry.getTagSearchTree(key).join();
    }

    @Inject(method = "creativeNameSearch", at = @At("RETURN"), cancellable = true)
    public void creativeNameSearch(CallbackInfoReturnable<SearchTree<ItemStack>> cir) {
        cir.setReturnValue(this.moderntabs$creativeNameSearch(SessionSearchTrees.CREATIVE_NAMES));
    }

    @Override
    public SearchTree<ItemStack> moderntabs$creativeNameSearch(SessionSearchTrees.Key key) {
        return CreativeModeTabSearchRegistry.getNameSearchTree(key).join();
    }
}
