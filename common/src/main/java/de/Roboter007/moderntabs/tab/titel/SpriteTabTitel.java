package de.Roboter007.moderntabs.tab.titel;

import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.ModernColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

// uses a sprite as a tab titel
public class SpriteTabTitel extends CustomTabTitel<SpriteTabTitel> {

    private ResourceLocation spriteTitelLocation;
    private int width;
    private int height;

    public SpriteTabTitel(@Nullable ElementOrientation textOrientation, @Nullable Vector2i offset, @Nullable ModernColor backgroundColor, @NotNull ResourceLocation spriteTitelLocation, int width, int height) {
        super(textOrientation, offset, backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    public SpriteTabTitel() {
        this.spriteTitelLocation = null;
        this.width = 0;
        this.height = 0;
    }

    public SpriteTabTitel spriteTitelLocation(String spriteId) {
        this.spriteTitelLocation = ResourceLocation.parse(spriteId);
        return this;
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
