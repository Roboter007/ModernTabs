package de.Roboter007.moderntabs.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.joml.Vector2i;

import java.util.Optional;
import java.util.function.Function;

public class CodecUtil {

    public static final Codec<Vector2i> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("x").forGetter(vec -> vec.x),
            Codec.INT.fieldOf("y").forGetter(vec -> vec.y)
    ).apply(instance, Vector2i::new));

    public static <T, R> Function<T, Optional<R>> nullable(Function<T, R> getter) {
        return getter.andThen(Optional::ofNullable);
    }

}
