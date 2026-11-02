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

public class Overlay implements Decoration {

    public static final ResourceLocation DEFAULT_OVERLAY = ModernTabs.path("overlay/default_banner_overlay");

    public static final Codec<Overlay> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("sprite").forGetter(Overlay::getSprite),
            StringRepresentable.fromEnum(AnimationMode::values).optionalFieldOf("animation_mode").forGetter(nullable(Overlay::getAnimationMode)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(Overlay::getColor))
    ).apply(instance, Overlay::codec));

    private ResourceLocation sprite = DEFAULT_OVERLAY;
    private AnimationMode animationMode = AnimationMode.NOT_ANIMATED;
    private SimpleColor color = null;

    public Overlay(ResourceLocation sprite, AnimationMode animationMode, SimpleColor color) {
        this.sprite = sprite;
        this.animationMode = animationMode;
        this.color = color;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Overlay codec(ResourceLocation sprite, Optional<AnimationMode> animationMode, Optional<SimpleColor> color) {
        return new Overlay(sprite, animationMode.orElse(null), color.orElse(null));
    }

    public Overlay() {}

    public Overlay sprite(ResourceLocation sprite) {
        this.sprite = sprite;
        return this;
    }

    public Overlay animationMode(AnimationMode animationMode) {
        this.animationMode = animationMode;
        return this;
    }

    public Overlay color(SimpleColor color) {
        this.color = color;
        return this;
    }


    @Override
    public ResourceLocation getSprite() {
        return this.sprite;
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
