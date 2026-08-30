package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

// uses a sprite as a tab titel
public class SpriteTabTitel extends CustomTabTitel {

    private ResourceLocation spriteTitelLocation;
    private int width;
    private int height;

    public SpriteTabTitel(@Nullable TextOrientation textOrientation, @Nullable ModernColor backgroundColor, @NotNull ResourceLocation spriteTitelLocation, int width, int height) {
        super(textOrientation, backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    public SpriteTabTitel() {
        this.spriteTitelLocation = null;
        this.width = 0;
        this.height = 0;
    }

    public SpriteTabTitel spriteTitelLocation(ResourceLocation spriteTitelLocation) {
        this.spriteTitelLocation = spriteTitelLocation;
        return this;
    }

    public SpriteTabTitel width(int width) {
        this.width = width;
        return this;
    }

    public SpriteTabTitel height(int height) {
        this.height = height;
        return this;
    }


    public ResourceLocation getSpriteTitelLocation() {
        return spriteTitelLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
