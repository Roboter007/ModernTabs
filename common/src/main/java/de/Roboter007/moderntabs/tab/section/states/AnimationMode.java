package de.Roboter007.moderntabs.tab.section.states;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AnimationMode implements StringRepresentable {
    PLAY_ON_HOVER(),
    PLAY_CONTINUOUSLY(),
    NOT_ANIMATED();


    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
