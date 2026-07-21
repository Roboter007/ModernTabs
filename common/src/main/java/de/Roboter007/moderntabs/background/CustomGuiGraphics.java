package de.Roboter007.moderntabs.background;

import net.minecraft.resources.ResourceLocation;

public interface CustomGuiGraphics {
    void moderntabs$blitSprite(ResourceLocation sprite, ResourceLocation defaultSprite, int x, int y, int width, int height);
    void moderntabs$blitSprite(ResourceLocation sprite, ResourceLocation defaultSprite, int x, int y, int blitOffset, int width, int height);
}
