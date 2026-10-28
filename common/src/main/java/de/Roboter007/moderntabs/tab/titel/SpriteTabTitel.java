package de.Roboter007.moderntabs.tab.titel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.ComplexColor;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

// uses a sprite as a tab titel
public class SpriteTabTitel extends CustomTabTitel<SpriteTabTitel> {

    public static final MapCodec<SpriteTabTitel> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.lenientOptionalFieldOf("offset", new Vector2i()).forGetter(SpriteTabTitel::getOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).lenientOptionalFieldOf("orientation", ElementOrientation.LEFT).forGetter(SpriteTabTitel::getTextOrientation),
            SimpleColor.CODEC.lenientOptionalFieldOf("backgroundColor").forGetter(nullable(SpriteTabTitel::getBackgroundColor)),
            ResourceLocation.CODEC.lenientOptionalFieldOf("spriteLocation").forGetter(nullable(SpriteTabTitel::getSpriteTitelLocation)),
            Codec.INT.lenientOptionalFieldOf("width", 0).forGetter(SpriteTabTitel::getWidth),
            Codec.INT.lenientOptionalFieldOf("height", 0).forGetter(SpriteTabTitel::getHeight)
    ).apply(instance, SpriteTabTitel::codec));

    private ResourceLocation spriteTitelLocation;
    private int width;
    private int height;

    public SpriteTabTitel(@Nullable Vector2i offset, @Nullable ElementOrientation textOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation spriteTitelLocation, int width, int height) {
        super(offset, textOrientation, backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static SpriteTabTitel codec(Vector2i offset, ElementOrientation textOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> spriteTitelLocation, int width, int height) {
        return new SpriteTabTitel(offset, textOrientation, backgroundColor.orElse(null), spriteTitelLocation.orElse(null), width, height);
    }

    public SpriteTabTitel() {
        this.spriteTitelLocation = null;
        this.width = 0;
        this.height = 0;
    }

    public SpriteTabTitel spriteTitelLocation(String spriteId) {
        this.spriteTitelLocation = ResourceLocation.parse(spriteId);
        return this;
    }

    public SpriteTabTitel spriteTitelLocation(ResourceLocation spriteTitelLocation) {
        this.spriteTitelLocation = spriteTitelLocation;
        return this;
    }

    public SpriteTabTitel width(int width) {
        this.width = width;
        return this;
    }

    public SpriteTabTitel height(int height) {
        this.height = height;
        return this;
    }


    public ResourceLocation getSpriteTitelLocation() {
        return spriteTitelLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    @Override
    public String getTextStyle() {
        return "sprite";
    }
}
