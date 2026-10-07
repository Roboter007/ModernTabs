package de.Roboter007.moderntabs.tab.button;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

// includes one possible position where the tab button can be placed and the texture the tab button has in this position
public record TabButtonTexture(String namespace, String tabIdentifier, CreativeModeTab.Row row, Column column, Selection selection) {

    public TabButtonTexture(CreativeModeTab.Row row, Column column, Selection selection) {
        this(null, null, row, column, selection);
    }

    @NotNull
    public ResourceLocation toResourceLocation() {
        if (namespace != null) {
            return ResourceLocation.fromNamespaceAndPath(namespace, "container/creative_inventory/tab_" + tabIdentifier +  "_" + row.toString().toLowerCase() + "_" + column.toString().toLowerCase() + "_" + selection.toString().toLowerCase());
        } else {
            return toDefaultLocation();
        }
    }

    @NotNull
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
