package de.Roboter007.moderntabs.fabric.tab.designs;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.tab.designs.TabDesignReloadListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public final class TabDesignReloadListenerFabric extends TabDesignReloadListener implements IdentifiableResourceReloadListener {

    public static final TabDesignReloadListenerFabric INSTANCE = new TabDesignReloadListenerFabric();

    private static final ResourceLocation FABRIC_ID = ModernTabs.path("designs");

    private TabDesignReloadListenerFabric() {
    }

    @Override
    public ResourceLocation getFabricId() {
        return FABRIC_ID;
    }
}
