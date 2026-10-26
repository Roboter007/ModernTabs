package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.ColorPresets;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.button.TabButton;
import de.Roboter007.moderntabs.tab.icon.TabIcon;
import de.Roboter007.moderntabs.tab.titel.AuraTabTitel;
import de.Roboter007.moderntabs.tab.titel.CustomTabTitel;
import de.Roboter007.moderntabs.tab.titel.SpriteTabTitel;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.ComplexColor;
import de.Roboter007.moderntabs.util.SectionUtil;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class ModernTabsKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        // ModernTabs bindings in KubeJs
        bindings.add("ModernTabs", ModernTabs.class);
        bindings.add("Builder", KubeJsTabDesign.class);
        bindings.add("ElementOrientation", ElementOrientation.class);
        bindings.add("ComplexColor", ComplexColor.class);
        bindings.add("SimpleColor", SimpleColor.class);
        bindings.add("ColorPresets", ColorPresets.class);
        bindings.add("AuraTabTitel", AuraTabTitel.class);
        bindings.add("SpriteTabTitel", SpriteTabTitel.class);
        bindings.add("CustomTabTitel", CustomTabTitel.class);
        bindings.add("TabButton", TabButton.class);
        bindings.add("TabIcon", TabIcon.class);
        bindings.add("SectionUtil", KubeJsSectionUtil.class);
        bindings.add("Searchbar", KubeJsSearchbar.class);
        bindings.add("ItemVisibility", SectionUtil.ItemVisibility.class);
    }
}