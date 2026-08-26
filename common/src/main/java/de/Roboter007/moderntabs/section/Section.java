package de.Roboter007.moderntabs.section;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.titel.TextOrientation;
import de.Roboter007.moderntabs.util.ColorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record Section(int priority, Title title, ResourceLocation sprite, Optional<Overlay> overlay, BannerAnimationMode animationMode) implements Comparable<Section> {

    private static final ResourceLocation DEFAULT_BANNER = ModernTabs.path("default_banner");

    public static final Codec<Section> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("priority").orElse(0).forGetter(Section::priority),
            Title.CODEC.fieldOf("title").forGetter(Section::title),
            ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_BANNER).forGetter(Section::sprite),
            Overlay.CODEC.optionalFieldOf("overlay").forGetter(Section::overlay),
            StringRepresentable.fromEnum(BannerAnimationMode::values).fieldOf("animation_mode").orElse(BannerAnimationMode.NOT_ANIMATED).forGetter(Section::animationMode)
    ).apply(instance, Section::new));

    @Override
    public int compareTo(@NotNull final Section other) {
        return Integer.compare(this.priority, other.priority);
    }

    public record Title(Component text, TextOrientation orientation, int color, Optional<Integer> secondaryColor, int background) {
        public static final Codec<Title> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.fieldOf("text").forGetter(Title::text),
                StringRepresentable.fromEnum(TextOrientation::values).fieldOf("orientation").orElse(TextOrientation.LEFT).forGetter(Title::orientation),
                ColorUtil.ARGB_CODEC.fieldOf("color").orElse(0xFFFFFFFF).forGetter(Title::color),
                ColorUtil.ARGB_CODEC.optionalFieldOf("secondary_color").forGetter(Title::secondaryColor),
                ColorUtil.ARGB_CODEC.fieldOf("background").orElse(0xAA000000).forGetter(Title::background)
        ).apply(instance, Title::new));
    }

    public record Overlay(ResourceLocation location, Optional<Integer> color, BannerAnimationMode animationMode) {
        private static final ResourceLocation DEFAULT_OVERLAY = ModernTabs.path("overlay/default_banner_overlay");

        public static final Codec<Overlay> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_OVERLAY).forGetter(Overlay::location),
                ColorUtil.ARGB_CODEC.optionalFieldOf("color").orElse(Optional.of(0xFFFFFFFF)).forGetter(Overlay::color),
                StringRepresentable.fromEnum(BannerAnimationMode::values).fieldOf("animation_mode").orElse(BannerAnimationMode.NOT_ANIMATED).forGetter(Overlay::animationMode)
        ).apply(instance, Overlay::new));
    }
}
