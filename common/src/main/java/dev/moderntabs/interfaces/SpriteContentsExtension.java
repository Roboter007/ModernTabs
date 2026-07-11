package dev.moderntabs.interfaces;

import net.minecraft.client.renderer.texture.SpriteContents;

public interface SpriteContentsExtension {
    SpriteContents.Ticker moderntabs$getTicker();

    void moderntabs$setTicker(SpriteContents.Ticker ticker);
}
