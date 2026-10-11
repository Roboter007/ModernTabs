package de.Roboter007.moderntabs.mixin.tab.banner;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.extensions.SectionLayoutExtension;
import de.Roboter007.moderntabs.tab.section.renderer.SectionRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin implements SectionLayoutExtension {

    @Shadow
    private Collection<ItemStack> displayItems;
    @Shadow
    private Set<ItemStack> displayItemsSearchTab;

    @Unique
    private Collection<ItemStack> moderntabs$sourceDisplayItems = null;

    @WrapMethod(method = "buildContents")
    private void moderntabs$buildContents(final CreativeModeTab.ItemDisplayParameters parameters, final Operation<Void> original) {
        original.call(parameters);

        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) this;

        if (tabExtension.moderntabs$hasCustomSections()) {
            this.moderntabs$sourceDisplayItems = this.displayItems;
            Collection<ItemStack> content = ItemStackLinkedSet.createTypeAndComponentsSet();
            Set<ItemStack> searchable = ItemStackLinkedSet.createTypeAndComponentsSet();

            SectionRenderer.processItems((CreativeModeTab) (Object) this, this.moderntabs$sourceDisplayItems, stack -> {}, content::add, searchable::add);

            this.displayItems = content;
            this.displayItemsSearchTab = searchable;
        } else {
            this.moderntabs$sourceDisplayItems = null;
        }
    }

    @Override
    public Collection<ItemStack> moderntabs$layoutSections() {
        if (this.moderntabs$sourceDisplayItems == null) {
            return this.displayItems;
        } else {
            List<ItemStack> layout = new ArrayList<>();
            SectionRenderer.processItems((CreativeModeTab) (Object) this, this.moderntabs$sourceDisplayItems, layout::add, stack -> {}, stack -> {});
            return layout;
        }
    }
}