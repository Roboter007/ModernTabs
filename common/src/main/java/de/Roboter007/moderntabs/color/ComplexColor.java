package de.Roboter007.moderntabs.color;

import org.jetbrains.annotations.NotNull;

import static de.Roboter007.moderntabs.util.ColorUtil.clamp;

public class ComplexColor extends SimpleColor {

    public ComplexColor(String htmlColor) {
        super(htmlColor);
    }

    public ComplexColor(int argbColor) {
        super(argbColor);
    }

    public ComplexColor darken(final float amount) {
        final int a = alpha();
        final int r = clamp(Math.round(red() * (1 - amount)));
        final int g = clamp(Math.round(green() * (1 - amount)));
        final int b = clamp(Math.round(blue() * (1 - amount)));
        this.argbColor = (a << 24) | (r << 16) | (g << 8) | b;
        return this;
    }

    public ComplexColor lighten(final float amount) {
        final int a = alpha();
        final int r = clamp(Math.round(red() * (1 + amount)));
        final int g = clamp(Math.round(green() * (1 + amount)));
        final int b = clamp(Math.round(blue() * (1 + amount)));
        this.argbColor = (a << 24) | (r << 16) | (g << 8) | b;
        return this;
    }

    public ComplexColor withAlpha(int alpha) {
        this.argbColor = (alpha << 24) | (red() << 16) | (green() << 8) | blue();
        return this;
    }

    public ComplexColor withRed(int red) {
        this.argbColor = (alpha() << 24) | (red << 16) | (green() << 8) | blue();
        return this;
    }

    public ComplexColor withGreen(int green) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green << 8) | blue();
        return this;
    }

    public ComplexColor withBlue(int blue) {
        this.argbColor = (alpha() << 24) | (red() << 16) | (green() << 8) | blue;
        return this;
    }

    public ComplexColor with(int red, int green, int blue, int alpha) {
        withRed(red);
        withGreen(green);
        withBlue(blue);
        withAlpha(alpha);
        return this;
    }

    public ComplexColor with(int argbColor) {
        this.argbColor = argbColor;
        return this;
    }

    public ComplexColor mix(@NotNull SimpleColor other, float amount) {
        float t = Math.max(0f, Math.min(1f, amount));

        float a1 = normalizedAlpha();
        float a2 = other.normalizedAlpha();
        float a = lerp(a1, a2, t);

        float r = lerp(argbToLinear(normalizedRed()) * a1, argbToLinear(other.normalizedRed()) * a2, t);
        float g = lerp(argbToLinear(normalizedGreen()) * a1, argbToLinear(other.normalizedGreen()) * a2, t);
        float b = lerp(argbToLinear(normalizedBlue()) * a1, argbToLinear(other.normalizedBlue()) * a2, t);

        if (a > 0f) {
            r = linearToArgb(r / a);
            g = linearToArgb(g / a);
            b = linearToArgb(b / a);
        } else {
            r = g = b = 0f;
        }

        int alpha = toByte(a);
        int red = toByte(r);
        int green = toByte(g);
        int blue = toByte(b);

        return new ComplexColor((alpha << 24) | (red << 16) | (green << 8) | blue);
    }

    private static float lerp(float start, float end, float amount) {
        return start + (end - start) * amount;
    }

    private static float argbToLinear(float value) {
        return value <= 0.04045f ? value / 12.92f : (float) Math.pow((value + 0.055f) / 1.055f, 2.4);
    }

    private static float linearToArgb(float value) {
        return value <= 0.0031308f ? value * 12.92f : 1.055f * (float) Math.pow(value, 1f / 2.4f) - 0.055f;
    }

    private static int toByte(float value) {
        return Math.round(Math.max(0f, Math.min(1f, value)) * 255f);
    }

    public SimpleColor toSimple() {
        return new SimpleColor(this.argbColor);
    }

    public ComplexColor copy() {
        return new ComplexColor(this.argbColor);
    }
}
