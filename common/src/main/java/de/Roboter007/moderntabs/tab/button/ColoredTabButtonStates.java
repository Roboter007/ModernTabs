package de.Roboter007.moderntabs.tab.button;

import de.Roboter007.moderntabs.util.ModernColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// just a dynamic colored variant of the tab button textures
public class ColoredTabButtonStates extends TabButtonStates {

    private final ModernColor color;

    public ColoredTabButtonStates(@NotNull ModernColor color, @Nullable String namespace, String tabIdentifier) {
        super(namespace, tabIdentifier);
        this.color = color;
    }

    public ColoredTabButtonStates(@NotNull ModernColor color) {
        super(null, null);
        this.color = color;
    }

    public ModernColor color() {
        return color;
    }
}
