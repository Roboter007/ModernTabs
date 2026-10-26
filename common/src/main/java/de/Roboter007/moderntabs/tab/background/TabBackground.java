package de.Roboter007.moderntabs.tab.background;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class TabBackground {

    public static final Codec<TabBackground> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(TabBackground::optionalColor),
            ResourceLocation.CODEC.optionalFieldOf("backgroundLocation").forGetter(TabBackground::optionalBackgroundLocation)
    ).apply(instance, TabBackground::new));

    private SimpleColor color = null;
    private ResourceLocation backgroundLocation = null;

    public TabBackground(SimpleColor color, ResourceLocation backgroundLocation) {
        this.color = color;
        this.backgroundLocation = backgroundLocation;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public TabBackground(Optional<SimpleColor> color, Optional<ResourceLocation> backgroundLocation) {
        this(color.orElse(null), backgroundLocation.orElse(null));
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


    public Optional<SimpleColor> optionalColor() {
        return Optional.ofNullable(color);
    }

    public Optional<ResourceLocation> optionalBackgroundLocation() {
        return Optional.ofNullable(backgroundLocation);
    }
}
