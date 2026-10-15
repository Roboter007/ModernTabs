package de.Roboter007.moderntabs.tab.titel;

import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

// uses the same text style of the tab banners
public class AuraTabTitel extends CustomTabTitel<AuraTabTitel> {

    private ResourceLocation font2Location;
    private SimpleColor color2;
    private Boolean dropShadow2;

    public AuraTabTitel(@Nullable ElementOrientation textOrientation, @Nullable Vector2i offset, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation font1Location, @Nullable ResourceLocation font2Location, @Nullable SimpleColor color1, @Nullable SimpleColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(textOrientation, offset, backgroundColor, font1Location, color1, dropShadow1);
        this.font2Location = font2Location;
        this.color2 = color2;
        this.dropShadow2 = dropShadow2;
    }

    public AuraTabTitel () {
        this.font2Location = null;
        this.color2 = null;
        this.dropShadow2 = null;
    }

    public AuraTabTitel font2(String font2id) {
        return this.font2(ResourceLocation.parse(font2id));
    }

    public AuraTabTitel font2(ResourceLocation font2Location) {
        this.font2Location = font2Location;
        return this;
    }

    public AuraTabTitel color2(SimpleColor color2) {
        this.color2 = color2;
        return this;
    }

    public AuraTabTitel dropShadow2(boolean dropShadow2) {
        this.dropShadow2 = dropShadow2;
        return this;
    }


    public ResourceLocation getFont2Location() {
        return font2Location;
    }

    public SimpleColor getColor2() {
        return color2;
    }

    public Boolean isDroppingShadow2() {
        return dropShadow2;
    }
}