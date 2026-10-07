package de.Roboter007.moderntabs.mixin.tab.banner;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.section.Sections;
import de.Roboter007.moderntabs.tab.section.renderer.SectionRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    @Unique
    private Collection<ItemStack> moderntabs$sourceDisplayItems = null;
    @Unique
    private Set<ResourceLocation> moderntabs$appliedCollapsedState = null;

    @WrapMethod(method = "buildContents")
    private void moderntabs$buildContents(final CreativeModeTab.ItemDisplayParameters parameters, final Operation<Void> original) {
        original.call(parameters);

        final CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) this;

        if(tabExtension.moderntabs$hasCustomSections()) {
            this.moderntabs$sourceDisplayItems = this.displayItems;
            this.moderntabs$rebuildSections();
        } else {
            this.moderntabs$sourceDisplayItems = null;
        }
    }

    @Unique
    public void moderntabs$rebuildSections() {
        if (this.moderntabs$sourceDisplayItems != null) {
            final CreativeModeTab self = (CreativeModeTab) (Object) this;
            final List<ItemStack> newDisplayItems = new LinkedList<>();
            final Set<ItemStack> newSearchItems = new LinkedHashSet<>();
            SectionRenderer.processItems(self, this.moderntabs$sourceDisplayItems, newDisplayItems::add, newSearchItems::add);

            this.displayItems = newDisplayItems;
            this.displayItemsSearchTab = newSearchItems;
            this.moderntabs$appliedCollapsedState = Sections.collapsedState();
        }
    }

    @Unique
    public void moderntabs$rebuildSectionsIfOutdated() {
        if (this.moderntabs$appliedCollapsedState != Sections.collapsedState()) {
            this.moderntabs$rebuildSections();
        }
    }

    @Inject(method = "getDisplayItems", at = @At("HEAD"))
    private void moderntabs$syncSections(final CallbackInfoReturnable<Collection<ItemStack>> cir) {
        this.moderntabs$rebuildSectionsIfOutdated();
    }
}
