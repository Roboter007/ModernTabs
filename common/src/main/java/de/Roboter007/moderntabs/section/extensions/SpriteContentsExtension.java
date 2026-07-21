package de.Roboter007.moderntabs.section.extensions;

import net.minecraft.client.renderer.texture.SpriteContents;

public interface SpriteContentsExtension {
    SpriteContents.Ticker moderntabs$getTicker();

    void moderntabs$setTicker(SpriteContents.Ticker ticker);
}
