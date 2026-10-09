package de.Roboter007.moderntabs.tab.titel;

import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.FontUtil;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel {

    private ModernColor backgroundColor;
    private ModernColor color;

    private ElementOrientation tabTextOrientation;
    private ResourceLocation fontLocation;
    private Boolean dropShadow;

    public CustomTabTitel(@Nullable ElementOrientation tabTextOrientation, @Nullable ModernColor backgroundColor, @Nullable ResourceLocation fontLocation, @Nullable ModernColor color, @Nullable Boolean dropShadow) {
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.fontLocation = fontLocation;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    public CustomTabTitel() {
        this(null, null, null, null, false);
    }

    public CustomTabTitel textOrientation(ElementOrientation textOrientation) {
        this.tabTextOrientation = textOrientation;
        return this;
    }

    public CustomTabTitel backgroundColor(ModernColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public CustomTabTitel font(String fontId) {
        return this.font(ResourceLocation.parse(fontId));
    }

    public CustomTabTitel font(ResourceLocation fontLocation) {
        this.fontLocation = fontLocation;
        return this;
    }

    public CustomTabTitel color(ModernColor color) {
        this.color = color;
        return this;
    }

    public CustomTabTitel dropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
        return this;
    }


    public ElementOrientation getTextOrientation() {
        return tabTextOrientation;
    }

    public ModernColor getBackgroundColor() {
        return backgroundColor;
    }

    public ResourceLocation getFontLocation() {
        return fontLocation;
    }

    public ModernColor getColor() {
        return color;
    }

    public Boolean isDroppingShadow() {
        return dropShadow;
    }


    public CustomTabTitel copy() {
        return new CustomTabTitel(this.tabTextOrientation, this.backgroundColor, this.fontLocation, this.color, this.dropShadow);
    }
}
