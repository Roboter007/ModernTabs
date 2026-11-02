package de.Roboter007.moderntabs.tab.section.toggle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.tab.section.states.ToggleState;
import net.minecraft.util.StringRepresentable;

public class SectionToggle {

    public static final Codec<SectionToggle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            StringRepresentable.fromEnum(ToggleState::values).fieldOf("defaultState").forGetter(SectionToggle::getDefaultState),
            StringRepresentable.fromEnum(ElementOrientation::values).fieldOf("orientation").forGetter(SectionToggle::getOrientation)
    ).apply(instance, SectionToggle::new));

    private ToggleState defaultState = ToggleState.EXPANDED;
    private ElementOrientation orientation = ElementOrientation.RIGHT;

    public SectionToggle(ToggleState defaultState, ElementOrientation orientation) {
        this.defaultState = defaultState;
        this.orientation = orientation;
    }

    public SectionToggle() {}

    public SectionToggle defaultState(ToggleState defaultState) {
        this.defaultState = defaultState;
        return this;
    }

    public SectionToggle orientation(ElementOrientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public ToggleState getDefaultState() {
        return defaultState;
    }

    public ElementOrientation getOrientation() {
        return orientation;
    }
}
