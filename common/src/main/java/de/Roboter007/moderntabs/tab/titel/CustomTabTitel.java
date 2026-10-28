package de.Roboter007.moderntabs.tab.titel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel<T extends CustomTabTitel<?>> extends TabElement<T> {

    private static final MapCodec<CustomTabTitel<?>> BASE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset", new Vector2i()).forGetter(TabElement::getOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation", ElementOrientation.LEFT).forGetter(CustomTabTitel::getTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(nullable(CustomTabTitel::getColor)),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(nullable(CustomTabTitel::getFontLocation)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(CustomTabTitel::getColor)),
            Codec.BOOL.optionalFieldOf("dropShadow", false).forGetter(CustomTabTitel::isDroppingShadow)
    ).apply(instance, CustomTabTitel::codec));

    public static final Codec<CustomTabTitel<?>> CODEC = Codec.STRING.dispatch("textStyle", CustomTabTitel::getTextStyle,
            type -> switch (type) {
                case "custom" -> BASE_CODEC;
                case "sprite" -> SpriteTabTitel.CODEC;
                case "aura" -> AuraTabTitel.CODEC;
                default -> throw new IllegalArgumentException("Unknown tab title type: " + type);
            });

    private SimpleColor backgroundColor;
    private SimpleColor color;

    private ElementOrientation tabTextOrientation;
    private ResourceLocation fontLocation;
    private Boolean dropShadow;

    public CustomTabTitel(@Nullable Vector2i offset, @Nullable ElementOrientation tabTextOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation fontLocation, @Nullable SimpleColor color, @Nullable Boolean dropShadow) {
        super(offset);
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.fontLocation = fontLocation;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static CustomTabTitel<?> codec(Vector2i offset, ElementOrientation tabTextOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> fontLocation, Optional<SimpleColor> color, boolean dropShadow) {
        return new CustomTabTitel<>(offset, tabTextOrientation, backgroundColor.orElse(null), fontLocation.orElse(null), color.orElse(null), dropShadow);
    }

    public CustomTabTitel() {
        this(null, null, null, null, null, false);
    }

    public T textOrientation(ElementOrientation textOrientation) {
        this.tabTextOrientation = textOrientation;
        return this.self();
    }

    public T backgroundColor(SimpleColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this.self();
    }

    public T font(String fontId) {
        return this.font(ResourceLocation.parse(fontId));
    }

    public T font(ResourceLocation fontLocation) {
        this.fontLocation = fontLocation;
        return this.self();
    }

    public T color(SimpleColor color) {
        this.color = color;
        return this.self();
    }

    public T dropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
        return this.self();
    }


    public ElementOrientation getTextOrientation() {
        return tabTextOrientation;
    }

    public SimpleColor getBackgroundColor() {
        return backgroundColor;
    }

    public ResourceLocation getFontLocation() {
        return fontLocation;
    }

    public SimpleColor getColor() {
        return color;
    }

    public Boolean isDroppingShadow() {
        return dropShadow;
    }

    public String getTextStyle() {
        return "custom";
    }


    public CustomTabTitel<?> copy() {
        return new CustomTabTitel<>(this.offset, this.tabTextOrientation, this.backgroundColor, this.fontLocation, this.color, this.dropShadow);
    }
}
