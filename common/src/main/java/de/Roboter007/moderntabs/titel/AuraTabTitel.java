package de.Roboter007.moderntabs.titel;

import de.Roboter007.moderntabs.util.ModernColor;
import net.minecraft.client.gui.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuraTabTitel extends CustomTabTitel {

    private final Font font2;
    private final ModernColor color2;
    private final Boolean dropShadow2;

    public AuraTabTitel(@Nullable ModernColor backgroundColor, @Nullable Font font1, @Nullable Font font2, @Nullable ModernColor color1, @Nullable ModernColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(backgroundColor, font1, color1, dropShadow1);
        this.font2 = font2;
        this.color2 = color2;
        this.dropShadow2 = dropShadow2;
    }

    public AuraTabTitel(@Nullable ModernColor backgroundColor, @Nullable Font font1, @Nullable Font font2, @Nullable ModernColor color1, @Nullable ModernColor color2) {
        this(backgroundColor, font1, font2, color1, color2, true, false);
    }

    public AuraTabTitel(@Nullable ModernColor backgroundColor, @Nullable ModernColor color1, @Nullable ModernColor color2) {
        this(backgroundColor,null,null, color1, color2, true, false);
    }

    public AuraTabTitel(@Nullable ModernColor backgroundColor, @Nullable Font font1, @Nullable Font font2, @NotNull ModernColor color, float darknessAmount) {
        this(backgroundColor, font1, font2, color, color.darken(darknessAmount), true, false);
    }

    public AuraTabTitel(@Nullable ModernColor backgroundColor, @NotNull ModernColor color, float darknessAmount) {
        this(backgroundColor,null,null, color, color.darken(darknessAmount), true, false);
    }

    public AuraTabTitel(@Nullable Font font1, @Nullable Font font2, @Nullable ModernColor color1, @Nullable ModernColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        this(null, font1, font2, color1, color2, dropShadow1, dropShadow2);
    }

    public AuraTabTitel(@Nullable Font font1, @Nullable Font font2, @Nullable ModernColor color1, @Nullable ModernColor color2) {
        this(null, font1, font2, color1, color2, true, false);
    }

    public AuraTabTitel(@Nullable ModernColor color1, @Nullable ModernColor color2) {
        this(null,null,null, color1, color2, true, false);
    }

    public AuraTabTitel(@Nullable Font font1, @Nullable Font font2, @NotNull ModernColor color, float darknessAmount) {
        this(null, font1, font2, color, color.darken(darknessAmount), true, false);
    }

    public AuraTabTitel(@NotNull ModernColor color, float darknessAmount) {
        this(null,null,null, color, color.darken(darknessAmount), true, false);
    }

    public AuraTabTitel(@NotNull ModernColor color) {
        this(null,null,null, color, color.darken(0.2f), true, false);
    }


    public Font font2() {
        return font2;
    }

    public ModernColor color2() {
        return color2;
    }

    public Boolean dropShadow2() {
        return dropShadow2;
    }
}
