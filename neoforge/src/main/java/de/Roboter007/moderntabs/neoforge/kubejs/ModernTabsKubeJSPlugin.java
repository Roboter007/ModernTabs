package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.button.ColoredTabButtonStates;
import de.Roboter007.moderntabs.tab.button.TabButtonStates;
import de.Roboter007.moderntabs.tab.titel.AuraTabTitel;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.tab.titel.SpriteTabTitel;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.ModernColor;
import de.Roboter007.moderntabs.util.SectionUtil;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class ModernTabsKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        // ModernTabs bindings in KubeJs
        bindings.add("ModernTabs", ModernTabs.class);
        bindings.add("TabDesign", KubeJsTabDesign.class);
        bindings.add("ElementOrientation", ElementOrientation.class);
        bindings.add("ModernColor", ModernColor.class);
        bindings.add("AuraTabTitel", AuraTabTitel.class);
        bindings.add("SpriteTabTitel", SpriteTabTitel.class);
        bindings.add("CustomTabTitel", CustomTabTitel.class);
        bindings.add("TabButtonStates", TabButtonStates.class);
        bindings.add("ColoredTabButtonStates", ColoredTabButtonStates.class);
        bindings.add("SectionUtil", KubeJsSectionUtil.class);
        bindings.add("ItemVisibility", SectionUtil.ItemVisibility.class);
    }
}