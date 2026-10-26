package de.Roboter007.moderntabs.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.util.ColorUtil;

public class SimpleColor {

    public static final Codec<SimpleColor> CODEC = ColorUtil.ARGB_CODEC.xmap(SimpleColor::new, SimpleColor::argbColor);

    protected int argbColor;

    public SimpleColor(String htmlColor) {
        this(ColorUtil.parse(htmlColor).getOrThrow());
    }

    public SimpleColor(int argbColor) {
        this.argbColor = argbColor;
    }

    public int argbColor() {
        return this.argbColor;
    }

    public String color() {
        return ColorUtil.toHexString(this.argbColor);
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
