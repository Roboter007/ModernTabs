package dev.moderntabs.section;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Sections {

    public static final SectionReloadListener RELOAD_LISTENER = new SectionReloadListener();

    private static Map<ResourceLocation, Section> byId = Map.of();
    private static Map<Section, ResourceLocation> idsBySection = Map.of();
    private static List<Section> sorted = List.of();

    private Sections() {
    }

    public static Section get(final ResourceLocation id) {
        return byId.get(id);
    }

    public static ResourceLocation getId(final Section section) {
        return idsBySection.get(section);
    }

    public static List<Section> sortedEntries() {
        return sorted;
    }

    static void reload(final Map<ResourceLocation, Section> newEntries) {
        final Map<ResourceLocation, Section> byIdCopy = new HashMap<>(newEntries);
        final Map<Section, ResourceLocation> idsCopy = new HashMap<>();
        newEntries.forEach((id, section) -> idsCopy.put(section, id));

        byId = Map.copyOf(byIdCopy);
        idsBySection = Map.copyOf(idsCopy);
        sorted = newEntries.values().stream().sorted().toList();
    }
}
