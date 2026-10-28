package de.Roboter007.moderntabs.tab.background;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

public class TabBackground {

    public static final Codec<TabBackground> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(TabBackground::getColor)),
            ResourceLocation.CODEC.optionalFieldOf("backgroundLocation").forGetter(nullable(TabBackground::getBackgroundLocation))
    ).apply(instance, TabBackground::codec));

    private SimpleColor color = null;
    private ResourceLocation backgroundLocation = null;

    public TabBackground(SimpleColor color, ResourceLocation backgroundLocation) {
        this.color = color;
        this.backgroundLocation = backgroundLocation;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static TabBackground codec(Optional<SimpleColor> color, Optional<ResourceLocation> backgroundLocation) {
        return new TabBackground(color.orElse(null), backgroundLocation.orElse(null));
    }

    public TabBackground() {}

    public TabBackground color(SimpleColor color) {
        this.color = color;
        return this;
    }

    public TabBackground backgroundLocation(ResourceLocation backgroundLocation) {
        this.backgroundLocation = backgroundLocation;
        return this;
    }

    public boolean hasCustomColor() {
        return this.color != null;
    }

    public boolean hasCustomBackgroundLocation() {
        return this.backgroundLocation != null;
    }

    public SimpleColor getColor() {
        return color;
    }

    public ResourceLocation getBackgroundLocation() {
        return backgroundLocation;
    }
}
