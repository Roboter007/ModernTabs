package de.Roboter007.moderntabs.tab.section.states;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElementOrientation implements StringRepresentable {
    LEFT,
    CENTERED,
    RIGHT;

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
