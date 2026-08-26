package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.iconBackground.config.ColoredTabIconBackground;
import de.Roboter007.moderntabs.iconBackground.config.TabIconBackground;
import de.Roboter007.moderntabs.section.item.SectionedItems;
import de.Roboter007.moderntabs.titel.AuraTabTitel;
import de.Roboter007.moderntabs.titel.CustomTabTitel;
import de.Roboter007.moderntabs.titel.SpriteTabTitel;
import de.Roboter007.moderntabs.titel.TextOrientation;
import de.Roboter007.moderntabs.util.ModernColor;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.minecraft.resources.ResourceLocation;

public class ModernTabsKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        // Mc
        bindings.add("ResourceLocation", ResourceLocation.class);

        // ModernTabs
        bindings.add("ModernTabs", ModernTabsKubeJs.class);
        bindings.add("TextOrientation", TextOrientation.class);
        bindings.add("ModernColor", ModernColor.class);
        bindings.add("AuraTabTitel", AuraTabTitel.class);
        bindings.add("SpriteTabTitel", SpriteTabTitel.class);
        bindings.add("CustomTabTitel", CustomTabTitel.class);
        bindings.add("TabIconBackground", TabIconBackground.class);
        bindings.add("ColoredTabIconBackground", ColoredTabIconBackground.class);
        bindings.add("SectionedItems", SectionedItems.class);
    }
}