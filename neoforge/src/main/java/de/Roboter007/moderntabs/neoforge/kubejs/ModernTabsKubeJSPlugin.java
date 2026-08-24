package de.Roboter007.moderntabs.neoforge.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class ModernTabsKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("ModernTabs", ModernTabsKubeJs.class);
    }
}