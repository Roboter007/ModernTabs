package dev.moderntabs.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

// Notice: replaces some methods that are included in the Veil mod
public final class ColorUtil {

    public static final Codec<Integer> ARGB_CODEC = Codec.STRING.comapFlatMap(
            ColorUtil::parse,
            ColorUtil::toHexString
    );

    private ColorUtil() {
    }

    private static DataResult<Integer> parse(final String value) {
        final String hex = value.startsWith("#") ? value.substring(1) : value;
        try {
            if (hex.length() == 6) {
                return DataResult.success(0xFF000000 | (int) Long.parseLong(hex, 16));
            }
            if (hex.length() == 8) {
                return DataResult.success((int) Long.parseLong(hex, 16));
            }
        } catch (final NumberFormatException ignored) {
        }
        return DataResult.error(() -> "Expected a '#RRGGBB' or '#AARRGGBB' color, got '" + value + "'");
    }

    private static String toHexString(final int argb) {
        return String.format("#%08X", argb);
    }

    public static int darken(final int argb, final float amount) {
        final int a = (argb >>> 24) & 0xFF;
        final int r = clamp(Math.round(((argb >>> 16) & 0xFF) * (1 - amount)));
        final int g = clamp(Math.round(((argb >>> 8) & 0xFF) * (1 - amount)));
        final int b = clamp(Math.round((argb & 0xFF) * (1 - amount)));
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static int clamp(final int value) {
        return Math.max(0, Math.min(255, value));
    }
}
