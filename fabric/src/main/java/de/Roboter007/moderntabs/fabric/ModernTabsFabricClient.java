package de.Roboter007.moderntabs.fabric;

import de.Roboter007.moderntabs.fabric.section.SectionReloadListenerFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public final class ModernTabsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(SectionReloadListenerFabric.INSTANCE);
    }
}
