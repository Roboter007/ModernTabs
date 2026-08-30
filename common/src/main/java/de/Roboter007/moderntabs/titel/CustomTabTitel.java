package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel {

    private ModernColor backgroundColor;
    private ModernColor color;

    private TextOrientation tabTextOrientation;
    private Font font;
    private Boolean dropShadow;

    public CustomTabTitel(@Nullable TextOrientation tabTextOrientation, @Nullable ModernColor backgroundColor, @Nullable Font font, @Nullable ModernColor color, @Nullable Boolean dropShadow) {
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.font = font;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    public CustomTabTitel() {
        this(null, null, null, null, false);
    }

    public CustomTabTitel textOrientation(TextOrientation textOrientation) {
        this.tabTextOrientation = textOrientation;
        return this;
    }

    public CustomTabTitel backgroundColor(ModernColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public CustomTabTitel font(Font font) {
        this.font = font;
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


    public TextOrientation getTextOrientation() {
        return tabTextOrientation;
    }

    public ModernColor getBackgroundColor() {
        return backgroundColor;
    }

    public Font getFont() {
        return font;
    }

    public ModernColor getColor() {
        return color;
    }

    public Boolean isDroppingShadow() {
        return dropShadow;
    }


    public CustomTabTitel copy() {
        return new CustomTabTitel(this.tabTextOrientation, this.backgroundColor, this.font, this.color, this.dropShadow);
    }
}
