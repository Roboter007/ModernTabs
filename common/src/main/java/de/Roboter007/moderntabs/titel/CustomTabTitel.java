package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomTabTitel {

    private final ModernColor backgroundColor;

    private final Font font;
    private final ModernColor color;
    private final Boolean dropShadow;

    public CustomTabTitel(@Nullable ModernColor backgroundColor, @Nullable Font font, @Nullable ModernColor color, @Nullable Boolean dropShadow) {
        this.backgroundColor = backgroundColor;
        this.font = font;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    public CustomTabTitel(@Nullable Font font, @NotNull ModernColor color, @Nullable Boolean dropShadow) {
        this(null, font, color, dropShadow);
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
