package de.Roboter007.moderntabs.background.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public record TabIconBackgroundImage(String namespace, String tabIdentifier, CreativeModeTab.Row row, Column column, Selection selection) {

    public TabIconBackgroundImage(CreativeModeTab.Row row, Column column, Selection selection) {
        this(null, null, row, column, selection);
    }

    public ResourceLocation toResourceLocation() {
        if (namespace != null) {
            return ResourceLocation.fromNamespaceAndPath(namespace, "container/creative_inventory/tab_" + tabIdentifier +  "_" + row.toString().toLowerCase() + "_" + column.toString().toLowerCase() + "_" + selection.toString().toLowerCase());
        } else {
            return toDefaultLocation();
        }
    }

    public ResourceLocation toDefaultLocation() {
        return ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_" + row.toString().toLowerCase() + "_" + selection.toString().toLowerCase() + "_" + column.getMcColumn());
    }

    public enum Column {
        LEFT(1),
        MIDDLE(2);

        private final int mcColumn;

        Column(int mcColumn) {
            this.mcColumn = mcColumn;
        }

        public int getMcColumn() {
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
