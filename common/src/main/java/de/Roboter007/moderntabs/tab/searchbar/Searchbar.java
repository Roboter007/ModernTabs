package de.Roboter007.moderntabs.tab.searchbar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Objects;
import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

public class Searchbar extends TabElement<Searchbar> {
    public static final ResourceLocation DEFAULT_SEARCHBAR_LOCATION = ModernTabs.path("searchbar/default_searchbar");

    public static final Codec<Searchbar> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset", new Vector2i()).forGetter(Searchbar::getOffset),
            ResourceLocation.CODEC.optionalFieldOf("searchbarLocation").forGetter(nullable(Searchbar::getSearchbarLocation)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(Searchbar::getColor)),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(nullable(Searchbar::getFontLocation)),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation", ElementOrientation.RIGHT).forGetter(Searchbar::getOrientation),
            Codec.INT.optionalFieldOf("width", 90).forGetter(Searchbar::getWidth),
            Codec.INT.optionalFieldOf("height", 12).forGetter(Searchbar::getHeight),
            Codec.INT.optionalFieldOf("editBoxWidth", 89).forGetter(Searchbar::getEditBoxWidth),
            Codec.INT.optionalFieldOf("editBoxHeight", 9).forGetter(Searchbar::getEditBoxWidth)
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
    public Searchbar(Vector2i offset, Optional<ResourceLocation> searchbarLocation, Optional<SimpleColor> color, Optional<ResourceLocation> fontLocation, ElementOrientation orientation, Integer width, Integer height, Integer editBoxWidth, Integer editBoxHeight) {
        this(offset, searchbarLocation.orElse(DEFAULT_SEARCHBAR_LOCATION), color.orElse(null), fontLocation.orElse(null), orientation, width, height, editBoxWidth, editBoxHeight);
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
}