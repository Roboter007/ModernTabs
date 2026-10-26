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

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel<T extends CustomTabTitel<?>> extends TabElement<T> {

    private static final MapCodec<CustomTabTitel<?>> BASE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset").forGetter(TabElement::optionalOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation").forGetter(CustomTabTitel::optionalTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(CustomTabTitel::optionalColor),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(CustomTabTitel::optionalFontLocation),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(CustomTabTitel::optionalColor),
            Codec.BOOL.optionalFieldOf("dropShadow").forGetter(CustomTabTitel::optionalDropShadow)
    ).apply(instance, CustomTabTitel::new));

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
    public CustomTabTitel(Optional<Vector2i> offset, Optional<ElementOrientation> tabTextOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> fontLocation, Optional<SimpleColor> color, Optional<Boolean> dropShadow) {
        this(offset.orElseGet(Vector2i::new), tabTextOrientation.orElse(ElementOrientation.LEFT), backgroundColor.orElse(null), fontLocation.orElse(null), color.orElse(null), dropShadow.orElse(false));
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


    public Optional<ElementOrientation> optionalTextOrientation() {
        return Optional.ofNullable(tabTextOrientation);
    }

    public Optional<Vector2i> optionalOffset() {
        return Optional.ofNullable(getOffset());
    }

    public Optional<SimpleColor> optionalBackgroundColor() {
        return Optional.ofNullable(backgroundColor);
    }

    public Optional<ResourceLocation> optionalFontLocation() {
        return Optional.ofNullable(fontLocation);
    }

    public Optional<SimpleColor> optionalColor() {
        return Optional.ofNullable(color);
    }

    public Optional<Boolean> optionalDropShadow() {
        return Optional.ofNullable(dropShadow);
    }


    public CustomTabTitel<?> copy() {
        return new CustomTabTitel<>(this.offset, this.tabTextOrientation, this.backgroundColor, this.fontLocation, this.color, this.dropShadow);
    }
}
