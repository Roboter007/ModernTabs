package de.Roboter007.moderntabs.color;

import de.Roboter007.moderntabs.util.ColorUtil;

public class SimpleColor {

    protected int argbColor;

    public SimpleColor(String htmlColor) {
        this(ColorUtil.parse(htmlColor).getOrThrow());
    }

    public SimpleColor(int argbColor) {
        this.argbColor = argbColor;
    }

    public int color() {
        return this.argbColor;
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

    public ComplexColor toComplex() {
        return new ComplexColor(this.argbColor);
    }
}
