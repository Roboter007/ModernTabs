package de.Roboter007.moderntabs.tab.section.decoration;

import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.AnimationMode;
import net.minecraft.resources.ResourceLocation;

public interface Decoration {

    ResourceLocation getSprite();
    AnimationMode getAnimationMode();
    SimpleColor getColor();
}
