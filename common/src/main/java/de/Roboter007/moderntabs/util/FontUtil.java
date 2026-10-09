package de.Roboter007.moderntabs.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;

public class FontUtil {

    public static Font fromLocation(ResourceLocation fontLocation) {
        return fromLocation(fontLocation, true);
    }

    public static Font fromLocation(ResourceLocation fontLocation, boolean filterFishyGlyphs) {
        return new Font((rl) -> new FontSet(Minecraft.getInstance().getTextureManager(), fontLocation), filterFishyGlyphs);
    }
}
