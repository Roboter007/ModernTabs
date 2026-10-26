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

// uses a sprite as a tab titel
public class SpriteTabTitel extends CustomTabTitel<SpriteTabTitel> {

    public static final MapCodec<SpriteTabTitel> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset").forGetter(SpriteTabTitel::optionalOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation").forGetter(SpriteTabTitel::optionalTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(SpriteTabTitel::optionalBackgroundColor),
            ResourceLocation.CODEC.optionalFieldOf("spriteLocation").forGetter(SpriteTabTitel::optionalSpriteTitelLocation),
            Codec.INT.optionalFieldOf("width").forGetter(SpriteTabTitel::optionalWidth),
            Codec.INT.optionalFieldOf("height").forGetter(SpriteTabTitel::optionalHeight)
    ).apply(instance, SpriteTabTitel::new));

    private ResourceLocation spriteTitelLocation;
    private int width;
    private int height;

    public SpriteTabTitel(@Nullable Vector2i offset, @Nullable ElementOrientation textOrientation, @Nullable SimpleColor backgroundColor, @NotNull ResourceLocation spriteTitelLocation, int width, int height) {
        super(offset, textOrientation, backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public SpriteTabTitel(Optional<Vector2i> offset, Optional<ElementOrientation> textOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> spriteTitelLocation, Optional<Integer> width, Optional<Integer> height) {
        this(offset.orElse(new Vector2i()), textOrientation.orElse(ElementOrientation.LEFT), backgroundColor.orElse(null), spriteTitelLocation.orElse(null), width.orElse(0), height.orElse(0));
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


    public Optional<ResourceLocation> optionalSpriteTitelLocation() {
        return Optional.ofNullable(spriteTitelLocation);
    }

    public Optional<Integer> optionalWidth() {
        return Optional.of(width);
    }

    public Optional<Integer> optionalHeight() {
        return Optional.of(height);
    }

    @Override
    public String getTextStyle() {
        return "sprite";
    }
}
