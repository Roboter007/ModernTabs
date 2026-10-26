package de.Roboter007.moderntabs.tab.searchbar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.tab.section.Section;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Objects;
import java.util.Optional;

public class Searchbar extends TabElement<Searchbar> {
    public static final ResourceLocation DEFAULT_SEARCHBAR_LOCATION = ModernTabs.path("searchbar/default_searchbar");

    public static final Codec<Searchbar> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset").forGetter(Searchbar::optionalOffset),
            ResourceLocation.CODEC.optionalFieldOf("searchbarLocation").forGetter(Searchbar::optionalSearchbarLocation),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(Searchbar::optionalColor),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(Searchbar::optionalFontLocation),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation").forGetter(Searchbar::optionalOrientation),
            Codec.INT.optionalFieldOf("width").forGetter(Searchbar::optionalWidth),
            Codec.INT.optionalFieldOf("height").forGetter(Searchbar::optionalHeight),
            Codec.INT.optionalFieldOf("editBoxWidth").forGetter(Searchbar::optionalEditBoxWidth),
            Codec.INT.optionalFieldOf("editBoxHeight").forGetter(Searchbar::optionalEditBoxHeight)
    ).apply(instance, Searchbar::new));

    @Nullable
    private ResourceLocation searchbarLocation = null;
    @Nullable
    private SimpleColor color = null;
    @Nullable
    private ResourceLocation fontLocation = null;
    private ElementOrientation orientation = ElementOrientation.RIGHT;
    private int width = 90;
    private int height = 12;
    private int editBoxWidth = 89;
    private int editBoxHeight = 9;

    public Searchbar(Vector2i offset, @Nullable ResourceLocation searchbarLocation, @Nullable SimpleColor color, @Nullable ResourceLocation fontLocation, ElementOrientation orientation, int width, int height, int editBoxWidth, int editBoxHeight) {
        super(offset);
        this.searchbarLocation = searchbarLocation;
        this.color = color;
        this.fontLocation = fontLocation;
        this.orientation = orientation;
        this.width = width;
        this.height = height;
        this.editBoxWidth = editBoxWidth;
        this.editBoxHeight = editBoxHeight;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public Searchbar(Optional<Vector2i> offset, Optional<ResourceLocation> searchbarLocation, Optional<SimpleColor> color, Optional<ResourceLocation> fontLocation, Optional<ElementOrientation> orientation, Optional<Integer> width, Optional<Integer> height, Optional<Integer> editBoxWidth, Optional<Integer> editBoxHeight) {
        this(offset.orElse(new Vector2i()), searchbarLocation.orElse(DEFAULT_SEARCHBAR_LOCATION), color.orElse(null), fontLocation.orElse(null), orientation.orElse(ElementOrientation.RIGHT), width.orElse(90), height.orElse(12), editBoxWidth.orElse(89), editBoxHeight.orElse(9));
    }

    public Searchbar() {
        super(new Vector2i());
    }

    public Searchbar searchbarLocation(@Nullable ResourceLocation searchbarLocation) {
        this.searchbarLocation = searchbarLocation;
        return this;
    }

    public Searchbar color(@Nullable SimpleColor color) {
        this.color = color;
        return this;
    }

    public Searchbar orientation(ElementOrientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public Searchbar font(@Nullable ResourceLocation fontLocation) {
        this.fontLocation = fontLocation;
        return this;
    }

    public Searchbar width(int width) {
        this.width = width;
        return this;
    }

    public Searchbar height(int height) {
        this.height = height;
        return this;
    }

    public Searchbar editBoxWidth(int editBoxWidth) {
        this.editBoxWidth = editBoxWidth;
        return this;
    }

    public Searchbar editBoxHeight(int editBoxHeight) {
        this.editBoxHeight = editBoxHeight;
        return this;
    }

    public ResourceLocation getSearchbarLocation() {
        return Objects.requireNonNullElse(searchbarLocation, DEFAULT_SEARCHBAR_LOCATION);
    }

    public @Nullable SimpleColor getColor() {
        return color;
    }

    public ElementOrientation getOrientation() {
        return orientation;
    }

    @Nullable
    public ResourceLocation getFontLocation() {
        return fontLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEditBoxWidth() {
        return editBoxWidth;
    }

    public int getEditBoxHeight() {
        return editBoxHeight;
    }


    public Optional<ResourceLocation> optionalSearchbarLocation() {
        return Optional.ofNullable(this.searchbarLocation);
    }

    public Optional<SimpleColor> optionalColor() {
        return Optional.ofNullable(color);
    }

    public Optional<ElementOrientation> optionalOrientation() {
        return Optional.ofNullable(orientation);
    }

    public Optional<ResourceLocation> optionalFontLocation() {
        return Optional.ofNullable(fontLocation);
    }

    public Optional<Integer> optionalWidth() {
        return Optional.of(width);
    }

    public Optional<Integer> optionalHeight() {
        return Optional.of(height);
    }

    public Optional<Integer> optionalEditBoxWidth() {
        return Optional.of(editBoxWidth);
    }

    public Optional<Integer> optionalEditBoxHeight() {
        return Optional.of(editBoxHeight);
    }
}