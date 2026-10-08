package de.Roboter007.moderntabs.fabric.tab.searchbar;

import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface SessionSearchTreesExtension {

    void moderntabs$updateCreativeTooltips(HolderLookup.Provider registries, List<ItemStack> list, SessionSearchTrees.Key key);
    SearchTree<ItemStack> moderntabs$creativeNameSearch(SessionSearchTrees.Key key);

    void moderntabs$updateCreativeTags(List<ItemStack> list, SessionSearchTrees.Key key);
    SearchTree<ItemStack> moderntabs$creativeTagSearch(SessionSearchTrees.Key key);
}
