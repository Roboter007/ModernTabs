package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// uses the vanilla Minecraft text style that can be configured
public class CustomTabTitel {

    private final ModernColor backgroundColor;
    private final ModernColor color;

    private final TextOrientation tabTextOrientation;
    private final Font font;
    private final Boolean dropShadow;

    public CustomTabTitel(@Nullable TextOrientation tabTextOrientation, @Nullable ModernColor backgroundColor, @Nullable Font font, @Nullable ModernColor color, @Nullable Boolean dropShadow) {
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.font = font;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    public CustomTabTitel(@Nullable ModernColor backgroundColor, @Nullable Font font, @Nullable ModernColor color, @Nullable Boolean dropShadow) {
        this(TextOrientation.LEFT, backgroundColor, font, color, dropShadow);
    }

    public CustomTabTitel(@Nullable TextOrientation tabTextOrientation, @Nullable Font font, @NotNull ModernColor color, @Nullable Boolean dropShadow) {
        this(tabTextOrientation, null, font, color, dropShadow);
    }

    public TextOrientation textOrientation() {
        return tabTextOrientation;
    }

    public ModernColor backgroundColor() {
        return backgroundColor;
    }

    public Font font() {
        return font;
    }

    public ModernColor color() {
        return color;
    }

    public Boolean dropShadow() {
        return dropShadow;
    }
}
