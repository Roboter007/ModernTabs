package de.Roboter007.moderntabs.mixin.background;

import de.Roboter007.moderntabs.background.CustomGuiSpriteManager;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@Mixin(GuiSpriteManager.class)
public class GuiSpriteManagerMixin extends TextureAtlasHolder implements CustomGuiSpriteManager {
    @Shadow
    @Final
    private static Set<MetadataSectionSerializer<?>> METADATA_SECTIONS;

    protected GuiSpriteManagerMixin(TextureManager textureManager) {
        super(textureManager, ResourceLocation.withDefaultNamespace("textures/atlas/gui.png"), ResourceLocation.withDefaultNamespace("gui"), METADATA_SECTIONS);
    }

    @Unique
    public TextureAtlasSprite moderntabs$getSpriteWithDefault(ResourceLocation location, ResourceLocation defaultLocation) {
        TextureAtlasSprite textureatlassprite = this.textureAtlas.texturesByName.getOrDefault(location, this.textureAtlas.texturesByName.get(defaultLocation));

        if (textureatlassprite == null) {
            throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
        } else {
            return textureatlassprite;
        }
    }
}
