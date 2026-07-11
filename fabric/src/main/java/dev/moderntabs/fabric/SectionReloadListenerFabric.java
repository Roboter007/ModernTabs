package dev.moderntabs.fabric;

import dev.moderntabs.ModernTabs;
import dev.moderntabs.section.SectionReloadListener;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public final class SectionReloadListenerFabric extends SectionReloadListener implements IdentifiableResourceReloadListener {

    public static final SectionReloadListenerFabric INSTANCE = new SectionReloadListenerFabric();

    private static final ResourceLocation ID = ModernTabs.path("sections");

    private SectionReloadListenerFabric() {
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}
