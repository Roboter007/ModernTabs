package de.Roboter007.moderntabs.util;

import static de.Roboter007.moderntabs.util.ColorUtil.clamp;

public class ModernColor {
    
    private int argbColor;

    public ModernColor(String htmlColor) {
        this.argbColor = ColorUtil.parse(htmlColor).getOrThrow();
    }
    
    public ModernColor(int argbColor) {
        this.argbColor = argbColor;
    }

    public ModernColor darken(final float amount) {
        final int a = (this.argbColor >>> 24) & 0xFF;
        final int r = clamp(Math.round(((this.argbColor >>> 16) & 0xFF) * (1 - amount)));
        final int g = clamp(Math.round(((this.argbColor >>> 8) & 0xFF) * (1 - amount)));
        final int b = clamp(Math.round((this.argbColor & 0xFF) * (1 - amount)));
        this.argbColor = (a << 24) | (r << 16) | (g << 8) | b;
        return this;
    }

    public int color() {
        return argbColor;
    }
}
