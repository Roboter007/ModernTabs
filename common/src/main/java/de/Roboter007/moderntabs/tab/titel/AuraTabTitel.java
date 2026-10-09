package de.Roboter007.moderntabs.tab.titel;

import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.FontUtil;
import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

// uses the same text style of the tab banners
public class AuraTabTitel extends CustomTabTitel {

    private ResourceLocation font2Location;
    private ModernColor color2;
    private Boolean dropShadow2;

    public AuraTabTitel(@Nullable ElementOrientation textOrientation, @Nullable ModernColor backgroundColor, @Nullable ResourceLocation font1Location, @Nullable ResourceLocation font2Location, @Nullable ModernColor color1, @Nullable ModernColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(textOrientation, backgroundColor, font1Location, color1, dropShadow1);
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

    public AuraTabTitel color2(ModernColor color2) {
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

    public ModernColor getColor2() {
        return color2;
    }

    public Boolean isDroppingShadow2() {
        return dropShadow2;
    }
}
