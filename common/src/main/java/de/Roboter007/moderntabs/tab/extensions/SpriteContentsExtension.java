package de.Roboter007.moderntabs.tab.extensions;

import net.minecraft.client.renderer.texture.SpriteContents;

public interface SpriteContentsExtension {
    SpriteContents.Ticker moderntabs$getTicker();

    void moderntabs$setTicker(SpriteContents.Ticker ticker);
}
