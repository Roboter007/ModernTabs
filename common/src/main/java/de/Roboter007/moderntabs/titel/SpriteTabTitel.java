package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpriteTabTitel extends CustomTabTitel {

    private final ResourceLocation spriteTitelLocation;
    private final int width;
    private final int height;

    public SpriteTabTitel(@Nullable ModernColor backgroundColor, @NotNull ResourceLocation spriteTitelLocation, int width, int height) {
        super(backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    public SpriteTabTitel(@NotNull ResourceLocation spriteTitelLocation, int width, int height) {
        this(null, spriteTitelLocation, width, height);
    }

    public ResourceLocation spriteTitelLocation() {
        return spriteTitelLocation;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
