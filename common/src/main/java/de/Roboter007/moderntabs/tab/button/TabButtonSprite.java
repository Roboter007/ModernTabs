package de.Roboter007.moderntabs.tab.button;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

// includes one possible position where the tab button can be placed and the texture the tab button has in this position
public record TabButtonSprite(ResourceLocation tabLocation, CreativeModeTab.Row row, Column column, Selection selection) {

    public TabButtonSprite(String tabLocationId, CreativeModeTab.Row row, Column column, Selection selection) {
        this(ResourceLocation.parse(tabLocationId), row, column, selection);
    }

    public TabButtonSprite(String namespace, String tabPath, CreativeModeTab.Row row, Column column, Selection selection) {
        this(ResourceLocation.fromNamespaceAndPath(namespace, tabPath), row, column, selection);
    }

    public TabButtonSprite(CreativeModeTab.Row row, Column column, Selection selection) {
        this(null, null, row, column, selection);
    }

    @NotNull
    public ResourceLocation toResourceLocation() {
        if (tabLocation != null) {
            return ResourceLocation.fromNamespaceAndPath(tabLocation.getNamespace(), "container/creative_inventory/tab_" + tabLocation.getPath() +  "_" + row.toString().toLowerCase() + "_" + column.toString().toLowerCase() + "_" + selection.toString().toLowerCase());
        } else {
            return toDefaultLocation();
        }
    }

    @NotNull
    public ResourceLocation toDefaultLocation() {
        return ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_" + row.toString().toLowerCase() + "_" + selection.toString().toLowerCase() + "_" + column.getColumnId());
    }

    public enum Column {
        LEFT(1),
        MIDDLE(2);

        private final int mcColumn;

        Column(int mcColumn) {
            this.mcColumn = mcColumn;
        }

        public int getColumnId() {
            return mcColumn;
        }
    }

    public enum Selection {
        SELECTED,
        UNSELECTED;

        public static Selection fromBoolean(boolean selected) {
            if(selected) {
                return SELECTED;
            } else {
                return UNSELECTED;
            }
        }
    }
}
