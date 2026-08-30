package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// uses the same text style of the tab banners
public class AuraTabTitel extends CustomTabTitel {

    private Font font2;
    private ModernColor color2;
    private Boolean dropShadow2;

    public AuraTabTitel(@Nullable TextOrientation textOrientation, @Nullable ModernColor backgroundColor, @Nullable Font font1, @Nullable Font font2, @Nullable ModernColor color1, @Nullable ModernColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(textOrientation, backgroundColor, font1, color1, dropShadow1);
        this.font2 = font2;
        this.color2 = color2;
        this.dropShadow2 = dropShadow2;
    }

    public AuraTabTitel () {
        this.font2 = null;
        this.color2 = null;
        this.dropShadow2 = null;
    }

    public AuraTabTitel font2(Font font2) {
        this.font2 = font2;
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


    public Font getFont2() {
        return font2;
    }

    public ModernColor getColor2() {
        return color2;
    }

    public Boolean isDroppingShadow2() {
        return dropShadow2;
    }
}
