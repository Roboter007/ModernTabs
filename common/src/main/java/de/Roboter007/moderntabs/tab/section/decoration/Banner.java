package de.Roboter007.moderntabs.tab.section.decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.AnimationMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

public class Banner implements Decoration{
    public static final ResourceLocation MISSING_BANNER = ModernTabs.path("banner/missing_banner");
    public static final ResourceLocation COLORED_BANNER = ModernTabs.path("banner/colored_banner");

    public static final Codec<Banner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("sprite").forGetter(nullable(Banner::getSprite)),
            StringRepresentable.fromEnum(AnimationMode::values).optionalFieldOf("animation_mode").forGetter(nullable(Banner::getAnimationMode)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(Banner::getColor))
    ).apply(instance, Banner::codec));

    private ResourceLocation sprite = null;
    private AnimationMode animationMode = AnimationMode.NOT_ANIMATED;
    private SimpleColor color = null;

    public Banner(ResourceLocation sprite, AnimationMode animationMode, SimpleColor color) {
        this.sprite = sprite;
        this.animationMode = animationMode;
        this.color = color;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Banner codec(Optional<ResourceLocation> sprite, Optional<AnimationMode> animationMode, Optional<SimpleColor> color) {
        return new Banner(sprite.orElse(null), animationMode.orElse(null), color.orElse(null));
    }

    public Banner() {}

    public Banner sprite(ResourceLocation sprite) {
        this.sprite = sprite;
        return this;
    }

    public Banner animationMode(AnimationMode animationMode) {
        this.animationMode = animationMode;
        return this;
    }

    public Banner color(SimpleColor color) {
        this.color = color;
        return this;
    }


    @Override
    public ResourceLocation getSprite() {
        if(sprite == null) {
            if(color != null) {
                return COLORED_BANNER;
            } else {
                return MISSING_BANNER;
            }
        } else {
            return this.sprite;
        }
    }

    @Override
    public AnimationMode getAnimationMode() {
        return this.animationMode;
    }

    @Override
    public SimpleColor getColor() {
        return this.color;
    }
}
