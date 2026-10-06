package de.Roboter007.moderntabs.tab.section;

import de.Roboter007.moderntabs.tab.section.states.ToggleState;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Sections {

    public static final SectionReloadListener RELOAD_LISTENER = new SectionReloadListener();

    private static Map<ResourceLocation, Section> SECTION_BY_ID = Map.of();
    private static Map<Section, ResourceLocation> IDS_BY_SECTION = Map.of();
    private static List<Section> SORTED = new ArrayList<>();

    private static Set<ResourceLocation> COLLAPSED_IDS = new HashSet<>();

    private Sections() {
    }

    public static boolean isCollapsible(Section section) {
        return section.sectionToggle().isPresent();
    }

    public static boolean isCollapsedByDefault(Section section) {
        return isCollapsible(section) && section.sectionToggle().get().defaultState() == ToggleState.COLLAPSED;
    }

    public static boolean sectionCollapsed(Section section) {
        if(isCollapsible(section)) {
            ResourceLocation location = IDS_BY_SECTION.get(section);
            return location != null && COLLAPSED_IDS.contains(location);
        }
        return false;
    }

    public static boolean toggleCollapsedState(Section section) {
        if(isCollapsible(section)) {
            ResourceLocation location = getId(section);

            if (location != null) {
                final Set<ResourceLocation> updatedIds = new HashSet<>(COLLAPSED_IDS);
                if (!updatedIds.remove(location)) {
                    updatedIds.add(location);
                }
                COLLAPSED_IDS = Set.copyOf(updatedIds);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static Set<ResourceLocation> collapsedState() {
        return COLLAPSED_IDS;
    }

    public static Section get(final ResourceLocation id) {
        return SECTION_BY_ID.get(id);
    }

    public static ResourceLocation getId(final Section section) {
        return IDS_BY_SECTION.get(section);
    }

    public static List<Section> sortedEntries() {
        return SORTED;
    }

    public static void reload(final Map<ResourceLocation, Section> newEntries) {
        final Map<ResourceLocation, Section> byIdCopy = new HashMap<>(newEntries);
        final Map<Section, ResourceLocation> idsCopy = new HashMap<>();
        newEntries.forEach((id, section) -> idsCopy.put(section, id));

        SECTION_BY_ID = Map.copyOf(byIdCopy);
        IDS_BY_SECTION = Map.copyOf(idsCopy);
        SORTED = newEntries.values().stream().sorted().toList();

        final Set<ResourceLocation> collapsed = new HashSet<>();

        newEntries.forEach((id, section) -> {
            if (isCollapsedByDefault(section)) {
                collapsed.add(id);
            }
        });
        COLLAPSED_IDS = Set.copyOf(collapsed);
    }
}
