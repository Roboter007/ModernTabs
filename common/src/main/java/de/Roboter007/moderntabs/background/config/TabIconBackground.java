package de.Roboter007.moderntabs.background.config;

import net.minecraft.world.item.CreativeModeTab.Row;
import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column;
import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Selection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TabIconBackground {

    @Nullable
    public final String namespace;
    public TabIconBackgroundImage[] creativeTabImages;

    public TabIconBackground(@Nullable String namespace, String tabIdentifier) {
        this.namespace = namespace;
        this.creativeTabImages = new TabIconBackgroundImage[] {
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.TOP, Column.LEFT, Selection.SELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.TOP, Column.LEFT, Selection.UNSELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.TOP, Column.MIDDLE, Selection.SELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.TOP, Column.MIDDLE, Selection.UNSELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.BOTTOM, Column.LEFT, Selection.SELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.BOTTOM, Column.LEFT, Selection.UNSELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.BOTTOM, Column.MIDDLE, Selection.SELECTED),
                new TabIconBackgroundImage(namespace, tabIdentifier, Row.BOTTOM, Column.MIDDLE, Selection.UNSELECTED)
        };
    }

    @NotNull
    public TabIconBackgroundImage get(Row row, Column column, Selection selection) {
        for(TabIconBackgroundImage iconBackground : creativeTabImages) {
            if(iconBackground.row().equals(row) && iconBackground.column().equals(column) && iconBackground.selection().equals(selection)) {
                return iconBackground;
            }
        }
        throw new NullPointerException();
    }

    public void setCreativeTabImage(Row row, Column column, Selection selection, TabIconBackgroundImage newIconBackground) {
        for(int i = 0; i < creativeTabImages.length - 1; i++) {
            TabIconBackgroundImage iconBackground = creativeTabImages[i];
            if(iconBackground.row().equals(row) && iconBackground.column().equals(column) && iconBackground.selection().equals(selection)) {
                creativeTabImages[i] = newIconBackground;
            }
        }
    }
}
