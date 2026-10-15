package de.Roboter007.moderntabs.tab.titel;

import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.SimpleColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel<T extends CustomTabTitel<?>> extends TabElement<T> {

    private SimpleColor backgroundColor;
    private SimpleColor color;

    private ElementOrientation tabTextOrientation;
    private ResourceLocation fontLocation;
    private Boolean dropShadow;

    public CustomTabTitel(@Nullable ElementOrientation tabTextOrientation, @Nullable Vector2i offset, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation fontLocation, @Nullable SimpleColor color, @Nullable Boolean dropShadow) {
        super(offset);
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.fontLocation = fontLocation;
        this.color = color;
        this.dropShadow = dropShadow;
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


    public CustomTabTitel<?> copy() {
        return new CustomTabTitel<>(this.tabTextOrientation, this.offset, this.backgroundColor, this.fontLocation, this.color, this.dropShadow);
    }
}
