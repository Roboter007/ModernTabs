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
        final int a = alpha();
        final int r = clamp(Math.round(red() * (1 - amount)));
        final int g = clamp(Math.round(green() * (1 - amount)));
        final int b = clamp(Math.round(blue() * (1 - amount)));
        this.argbColor = (a << 24) | (r << 16) | (g << 8) | b;
        return this;
    }

    public ModernColor lighten(final float amount) {
        final int a = alpha();
        final int r = clamp(Math.round(red() * (1 + amount)));
        final int g = clamp(Math.round(green() * (1 + amount)));
        final int b = clamp(Math.round(blue() * (1 + amount)));
        this.argbColor = (a << 24) | (r << 16) | (g << 8) | b;
        return this;
    }

    public int alpha() {
        return (this.argbColor >>> 24) & 0xFF;
    }

    public int red() {
        return (this.argbColor >>> 16) & 0xFF;
    }

    public int green() {
        return (this.argbColor >>> 8) & 0xFF;
    }

    public int blue() {
        return this.argbColor & 0xFF;
    }

    public float normalizedAlpha() {
        return alpha() / 255f;
    }

    public float normalizedRed() {
        return red() / 255f;
    }

    public float normalizedGreen() {
        return green() / 255f;
    }

    public float normalizedBlue() {
        return blue() / 255f;
    }

    public void setAlpha(int alpha) {
        this.argbColor = (alpha << 24) | (red() << 16) | (green() << 8) | blue();
    }

    public void setRed(int red) {
        this.argbColor = (alpha() << 24) | (red << 16) | (green() << 8) | blue();
    }

    public void setGreen(int green) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green << 8) | blue();
    }

    public void setBlue(int blue) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green() << 8) | blue;
    }

    public void set(int red, int green, int blue, int alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public int color() {
        return argbColor;
    }
}
