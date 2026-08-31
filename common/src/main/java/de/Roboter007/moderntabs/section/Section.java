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

public record Section(int priority, Title title, Banner banner, Optional<Overlay> overlay) implements Comparable<Section> {

    public static final Codec<Section> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("priority").orElse(0).forGetter(Section::priority),
            Title.CODEC.fieldOf("title").forGetter(Section::title),
            Banner.CODEC.fieldOf("banner").forGetter(Section::banner),
            Overlay.CODEC.optionalFieldOf("overlay").forGetter(Section::overlay)
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

    public record Overlay(ResourceLocation sprite, BannerAnimationMode animationMode, Optional<Integer> color) implements Decoration {
        private static final ResourceLocation DEFAULT_OVERLAY = ModernTabs.path("overlay/default_banner_overlay");

        public static final Codec<Overlay> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_OVERLAY).forGetter(Overlay::sprite),
                StringRepresentable.fromEnum(BannerAnimationMode::values).fieldOf("animation_mode").orElse(BannerAnimationMode.NOT_ANIMATED).forGetter(Overlay::animationMode),
                ColorUtil.ARGB_CODEC.optionalFieldOf("color").orElse(Optional.of(0xFFFFFFFF)).forGetter(Overlay::color)
                ).apply(instance, Overlay::new));
    }

    public record Banner(Optional<ResourceLocation> optionalSprite, BannerAnimationMode animationMode, Optional<Integer> color) implements Decoration {
        public static final ResourceLocation MISSING_BANNER = ModernTabs.path("missing_banner");
        public static final ResourceLocation COLORED_BANNER = ModernTabs.path("colored_banner");

        public static final Codec<Banner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("sprite").orElse(Optional.empty()).forGetter(Banner::optionalSprite),
                StringRepresentable.fromEnum(BannerAnimationMode::values).fieldOf("animation_mode").orElse(BannerAnimationMode.NOT_ANIMATED).forGetter(Banner::animationMode),
                ColorUtil.ARGB_CODEC.optionalFieldOf("color").orElse(Optional.of(0xFFFFFFFF)).forGetter(Banner::color)
        ).apply(instance, Banner::new));

        @Override
        public ResourceLocation sprite() {
            if(optionalSprite.isEmpty()) {
                if(color.isPresent()) {
                    return COLORED_BANNER;
                } else {
                    return MISSING_BANNER;
                }
            } else {
                return this.optionalSprite.get();
            }
        }
    }

    public interface Decoration {
        ResourceLocation sprite();
        BannerAnimationMode animationMode();
        Optional<Integer> color();
    }
}
