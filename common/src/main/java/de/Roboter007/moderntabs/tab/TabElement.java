package de.Roboter007.moderntabs.tab;

import org.joml.Vector2i;

import java.util.Objects;

public abstract class TabElement<T extends TabElement<?>> {

    protected Vector2i offset;

    public TabElement(Vector2i offset) {
        this.offset = Objects.requireNonNullElseGet(offset, Vector2i::new);
    }

    public T offset(Vector2i offset) {
        this.offset = offset;
        return this.self();
    }

    public Vector2i getOffset() {
        return offset;
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
