package de.Roboter007.moderntabs.tab.button;

import net.minecraft.world.item.CreativeModeTab.Row;
import de.Roboter007.moderntabs.tab.button.TabButtonTexture.Column;
import de.Roboter007.moderntabs.tab.button.TabButtonTexture.Selection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// includes all possible positions for the tab buttons
public class TabButtonStates {

    @Nullable
    public final String namespace;
    public TabButtonTexture[] creativeTabImages;

    public TabButtonStates(@Nullable String namespace, String tabIdentifier) {
        this.namespace = namespace;
        this.creativeTabImages = new TabButtonTexture[] {
                new TabButtonTexture(namespace, tabIdentifier, Row.TOP, Column.LEFT, Selection.SELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.TOP, Column.LEFT, Selection.UNSELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.TOP, Column.MIDDLE, Selection.SELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.TOP, Column.MIDDLE, Selection.UNSELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.BOTTOM, Column.LEFT, Selection.SELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.BOTTOM, Column.LEFT, Selection.UNSELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.BOTTOM, Column.MIDDLE, Selection.SELECTED),
                new TabButtonTexture(namespace, tabIdentifier, Row.BOTTOM, Column.MIDDLE, Selection.UNSELECTED)
        };
    }

    @NotNull
    public TabButtonTexture get(Row row, Column column, Selection selection) {
        for(TabButtonTexture iconBackground : creativeTabImages) {
            if(iconBackground.row().equals(row) && iconBackground.column().equals(column) && iconBackground.selection().equals(selection)) {
                return iconBackground;
            }
        }
        throw new NullPointerException();
    }
}
