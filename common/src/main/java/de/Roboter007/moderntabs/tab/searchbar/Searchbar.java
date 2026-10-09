package de.Roboter007.moderntabs.tab.searchbar;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Searchbar {

    public static final ResourceLocation DEFAULT_SEARCHBAR_LOCATION = ModernTabs.path("searchbar/default_searchbar");

    @Nullable
    private ResourceLocation searchbarLocation;
    @Nullable
    private ResourceLocation fontLocation;
    private ElementOrientation orientation;
    private int width;
    private int height;
    private int editBoxWidth;
    private int editBoxHeight;

    public Searchbar(@Nullable ResourceLocation searchbarLocation, @Nullable ResourceLocation fontLocation, ElementOrientation orientation, int width, int height, int editBoxWidth, int editBoxHeight) {
        this.searchbarLocation = searchbarLocation;
        this.fontLocation = fontLocation;
        this.orientation = orientation;
        this.width = width;
        this.height = height;
        this.editBoxWidth = editBoxWidth;
        this.editBoxHeight = editBoxHeight;
    }

    public Searchbar(ElementOrientation orientation, int width, int height, int editBoxWidth, int editBoxHeight) {
        this(null, null, orientation, width, height, editBoxWidth, editBoxHeight);
    }

    public Searchbar() {
        this(ElementOrientation.RIGHT, 90, 12, 89, 9);
    }

    public Searchbar searchbarLocation(@Nullable ResourceLocation searchbarLocation) {
        this.searchbarLocation = searchbarLocation;
        return this;
    }

    public Searchbar orientation(ElementOrientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public Searchbar font(String fontId) {
        return this.font(ResourceLocation.parse(fontId));
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