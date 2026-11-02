package de.Roboter007.moderntabs.tab.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

public class TabContent {

    public static final Codec<TabContent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("overwriteDefaultContent", false).forGetter(TabContent::isOverwritingDefaultContent),
            ItemStack.CODEC.listOf().optionalFieldOf("items").forGetter(nullable(TabContent::getTabContent)),
            ResourceLocation.CODEC.listOf().optionalFieldOf("sections", new ArrayList<>()).forGetter(TabContent::getSections)
    ).apply(instance, TabContent::codec));

    private boolean overwriteDefaultContent = false;
    private List<ItemStack> tabContent = null;
    private List<ResourceLocation> sections = new ArrayList<>();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static TabContent codec(boolean overwriteDefaultContent, Optional<List<ItemStack>> tabContent, List<ResourceLocation> resourceLocations) {
        return new TabContent(overwriteDefaultContent, tabContent.orElse(null), resourceLocations);
    }

    public TabContent(boolean overwriteDefaultContent, @Nullable List<ItemStack> tabContent, List<ResourceLocation> sections) {
        this.overwriteDefaultContent = overwriteDefaultContent;
        this.tabContent = tabContent;
        this.sections = sections;
    }

    public TabContent() {}

    public TabContent overwriteDefaultContent(boolean overwriteDefaultContent) {
        this.overwriteDefaultContent = overwriteDefaultContent;
        return this;
    }

    public TabContent content(List<ItemStack> tabContent) {
        this.tabContent = tabContent;
        return this;
    }

    public TabContent addContent(ItemStack stack) {
        this.tabContent.add(stack);
        return this;
    }

    public TabContent removeContent(ItemStack stack) {
        this.tabContent.remove(stack);
        return this;
    }


    public TabContent sections(List<ResourceLocation> sections) {
        this.sections = sections;
        return this;
    }

    public TabContent addSection(ResourceLocation section) {
        this.sections.add(section);
        return this;
    }

    public TabContent removeSection(ResourceLocation section) {
        this.sections.remove(section);
        return this;
    }

    @Nullable
    public List<ItemStack> getTabContent() {
        return tabContent;
    }

    public List<ResourceLocation> getSections() {
        return sections;
    }

    public boolean isOverwritingDefaultContent() {
        return overwriteDefaultContent;
    }
}
