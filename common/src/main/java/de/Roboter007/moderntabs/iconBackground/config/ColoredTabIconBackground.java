package de.Roboter007.moderntabs.iconBackground.config;

import de.Roboter007.moderntabs.util.ModernColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColoredTabIconBackground extends TabIconBackground {

    private final ModernColor color;

    public ColoredTabIconBackground(@NotNull ModernColor color, @Nullable String namespace, String tabIdentifier) {
        super(namespace, tabIdentifier);
        this.color = color;
    }

    public ColoredTabIconBackground(@NotNull ModernColor color) {
        super(null, null);
        this.color = color;
    }

    public ModernColor color() {
        return color;
    }
}
