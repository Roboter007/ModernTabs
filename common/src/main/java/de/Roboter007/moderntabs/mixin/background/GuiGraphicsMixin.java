package de.Roboter007.moderntabs.mixin.background;

import de.Roboter007.moderntabs.background.CustomGuiGraphics;
import de.Roboter007.moderntabs.background.CustomGuiSpriteManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements CustomGuiGraphics {

    @Shadow
    @Final
    private GuiSpriteManager sprites;

    @Shadow
    protected abstract void blitTiledSprite(TextureAtlasSprite sprite, int x, int y, int blitOffset, int width, int height, int uPosition, int vPosition, int spriteWidth, int spriteHeight, int nineSliceWidth, int nineSliceHeight);

    @Shadow
    protected abstract void blitNineSlicedSprite(TextureAtlasSprite sprite, GuiSpriteScaling.NineSlice nineSlice, int x, int y, int blitOffset, int width, int height);

    @Shadow
    protected abstract void blitSprite(TextureAtlasSprite sprite, int x, int y, int blitOffset, int width, int height);

    @Unique
    public void moderntabs$blitSprite(ResourceLocation sprite, ResourceLocation defaultSprite, int x, int y, int width, int height) {
        this.moderntabs$blitSprite((ResourceLocation)sprite, defaultSprite, x, y, 0, width, height);
    }

    @Unique
    public void moderntabs$blitSprite(ResourceLocation sprite, ResourceLocation defaultSprite, int x, int y, int blitOffset, int width, int height) {
        CustomGuiSpriteManager customGuiSpriteManager = (CustomGuiSpriteManager) sprites;
        TextureAtlasSprite textureAtlasSprite = customGuiSpriteManager.moderntabs$getSpriteWithDefault(sprite, defaultSprite);
        GuiSpriteScaling guiSpriteScaling = this.sprites.getSpriteScaling(textureAtlasSprite);
        if (guiSpriteScaling instanceof GuiSpriteScaling.Stretch) {
            this.blitSprite(textureAtlasSprite, x, y, blitOffset, width, height);

        } else if (guiSpriteScaling instanceof GuiSpriteScaling.Tile(int width1, int height1)) {
            this.blitTiledSprite(textureAtlasSprite, x, y, blitOffset, width, height, 0, 0, width1, height1, width1, height1);

        } else if (guiSpriteScaling instanceof GuiSpriteScaling.NineSlice nineSlice) {
            this.blitNineSlicedSprite(textureAtlasSprite, nineSlice, x, y, blitOffset, width, height);
        }
    }
}
