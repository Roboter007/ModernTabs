package de.Roboter007.moderntabs.tab.button;

import de.Roboter007.moderntabs.color.SimpleColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// just a dynamic colored variant of the tab button textures
public class ColoredTabButtonStates extends TabButtonStates {

    private final SimpleColor color;

    public ColoredTabButtonStates(@NotNull SimpleColor color, @Nullable String namespace, String tabIdentifier) {
        super(namespace, tabIdentifier);
        this.color = color;
    }

    public ColoredTabButtonStates(@NotNull SimpleColor color) {
        super(null, null);
        this.color = color;
    }

    public SimpleColor color() {
        return color;
    }
}
