package dev.moderntabs.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.moderntabs.ModernTabs;
import dev.moderntabs.client.SectionedTabRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {

    @Shadow
    private Collection<ItemStack> displayItems;

    @Shadow
    private Set<ItemStack> displayItemsSearchTab;

    @WrapMethod(method = "buildContents")
    private void moderntabs$buildContents(final CreativeModeTab.ItemDisplayParameters parameters, final Operation<Void> original) {
        final CreativeModeTab self = (CreativeModeTab) (Object) this;

        original.call(parameters);

        if (!ModernTabs.hasSections(self)) {
            return;
        }

        final List<ItemStack> newDisplayItems = new LinkedList<>();
        final Set<ItemStack> newSearchItems = new LinkedHashSet<>();
        SectionedTabRenderer.processItems(self, this.displayItems, newDisplayItems::add, newSearchItems::add);
        this.displayItems = newDisplayItems;
        this.displayItemsSearchTab = newSearchItems;
    }
}
