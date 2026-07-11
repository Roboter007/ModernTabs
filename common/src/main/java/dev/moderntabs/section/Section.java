package dev.moderntabs.section;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.moderntabs.ModernTabs;
import dev.moderntabs.util.ColorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record Section(int priority, Title title, ResourceLocation sprite, boolean animateOnHover) implements Comparable<Section> {

    private static final ResourceLocation DEFAULT_BANNER = ModernTabs.path("default_banner");

    public static final Codec<Section> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("priority").orElse(0).forGetter(Section::priority),
            Title.CODEC.fieldOf("title").forGetter(Section::title),
            ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_BANNER).forGetter(Section::sprite),
            Codec.BOOL.fieldOf("only_animate_on_hover").orElse(false).forGetter(Section::animateOnHover)
    ).apply(instance, Section::new));

    @Override
    public int compareTo(@NotNull final Section other) {
        return Integer.compare(this.priority, other.priority);
    }

    public record Title(Component text, int color, Optional<Integer> secondaryColor, int background) {
        public static final Codec<Title> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.fieldOf("text").forGetter(Title::text),
                ColorUtil.ARGB_CODEC.fieldOf("color").orElse(0xFFFFFFFF).forGetter(Title::color),
                ColorUtil.ARGB_CODEC.optionalFieldOf("secondary_color").forGetter(Title::secondaryColor),
                ColorUtil.ARGB_CODEC.fieldOf("background").orElse(0xAA000000).forGetter(Title::background)
        ).apply(instance, Title::new));
    }
}
