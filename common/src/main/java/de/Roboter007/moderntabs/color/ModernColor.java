package de.Roboter007.moderntabs.color;

import de.Roboter007.moderntabs.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

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

    public ModernColor withAlpha(int alpha) {
        this.argbColor = (alpha << 24) | (red() << 16) | (green() << 8) | blue();
        return this;
    }

    public ModernColor withRed(int red) {
        this.argbColor = (alpha() << 24) | (red << 16) | (green() << 8) | blue();
        return this;
    }

    public ModernColor withGreen(int green) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green << 8) | blue();
        return this;
    }

    public ModernColor withBlue(int blue) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green() << 8) | blue;
        return this;
    }

    public ModernColor with(int red, int green, int blue, int alpha) {
        withRed(red);
        withGreen(green);
        withBlue(blue);
        withAlpha(alpha);
        return this;
    }

    public ModernColor mix(@NotNull ModernColor other, float amount) {
        float t = Math.max(0f, Math.min(1f, amount));

        float a1 = normalizedAlpha();
        float a2 = other.normalizedAlpha();
        float a = lerp(a1, a2, t);

        float r = lerp(srgbToLinear(normalizedRed()) * a1, srgbToLinear(other.normalizedRed()) * a2, t);
        float g = lerp(srgbToLinear(normalizedGreen()) * a1, srgbToLinear(other.normalizedGreen()) * a2, t);
        float b = lerp(srgbToLinear(normalizedBlue()) * a1, srgbToLinear(other.normalizedBlue()) * a2, t);

        if (a > 0f) {
            r = linearToSrgb(r / a);
            g = linearToSrgb(g / a);
            b = linearToSrgb(b / a);
        } else {
            r = g = b = 0f;
        }

        int alpha = toByte(a);
        int red = toByte(r);
        int green = toByte(g);
        int blue = toByte(b);

        return new ModernColor((alpha << 24) | (red << 16) | (green << 8) | blue);
    }

    private static float lerp(float start, float end, float amount) {
        return start + (end - start) * amount;
    }

    private static float srgbToLinear(float value) {
        return value <= 0.04045f ? value / 12.92f : (float) Math.pow((value + 0.055f) / 1.055f, 2.4);
    }

    private static float linearToSrgb(float value) {
        return value <= 0.0031308f ? value * 12.92f : 1.055f * (float) Math.pow(value, 1f / 2.4f) - 0.055f;
    }

    private static int toByte(float value) {
        return Math.round(Math.max(0f, Math.min(1f, value)) * 255f);
    }

    public int color() {
        return argbColor;
    }

    public ModernColor copy() {
        return new ModernColor(this.argbColor);
    }
}
