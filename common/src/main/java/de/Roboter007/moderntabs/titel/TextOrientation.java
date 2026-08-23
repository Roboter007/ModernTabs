package de.Roboter007.moderntabs.titel;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum TextOrientation implements StringRepresentable {
    LEFT,
    CENTERED,
    RIGHT;

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
