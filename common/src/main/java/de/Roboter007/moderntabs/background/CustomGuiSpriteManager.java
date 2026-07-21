package de.Roboter007.moderntabs.background;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public interface CustomGuiSpriteManager {
    TextureAtlasSprite moderntabs$getSpriteWithDefault(ResourceLocation location, ResourceLocation defaultLocation);
}
