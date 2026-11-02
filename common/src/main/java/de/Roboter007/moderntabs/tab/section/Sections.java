package de.Roboter007.moderntabs.tab.section;

import de.Roboter007.moderntabs.tab.content.TabContent;
import de.Roboter007.moderntabs.tab.extensions.CreativeModeTabExtension;
import de.Roboter007.moderntabs.tab.section.states.ToggleState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public final class Sections {

    public static final SectionReloadListener RELOAD_LISTENER = new SectionReloadListener();

    private static Map<ResourceLocation, Section> SECTION_BY_ID = Map.of();
    private static Map<Section, ResourceLocation> IDS_BY_SECTION = Map.of();

    private static Set<ResourceLocation> COLLAPSED_IDS = new HashSet<>();

    private Sections() {}

    public static boolean isCollapsible(Section section) {
        return section.getSectionToggle() != null;
    }

    public static boolean isCollapsedByDefault(Section section) {
        return isCollapsible(section) && section.getSectionToggle().getDefaultState() == ToggleState.COLLAPSED;
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

    public static Section get(ResourceLocation id) {
        return SECTION_BY_ID.get(id);
    }

    public static ResourceLocation getId(Section section) {
        return IDS_BY_SECTION.get(section);
    }

    public static List<Section> getSectionsFromTab(CreativeModeTab tab) {
        CreativeModeTabExtension tabExtension = (CreativeModeTabExtension) tab;
        if(tabExtension.moderntabs$hasCustomTabContent()) {
            TabContent tabContent = tabExtension.moderntabs$getCustomTabContent();
            return tabContent.getSections().stream().map(SECTION_BY_ID::get).filter(Objects::nonNull).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public static List<ItemStack> getContentForSection(ResourceLocation resourceLocation) {
        Section section = SECTION_BY_ID.get(resourceLocation);
        if(section != null) {
            return section.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public static void reload(Map<ResourceLocation, Section> newEntries) {
        Map<ResourceLocation, Section> byIdCopy = new HashMap<>(newEntries);
        Map<Section, ResourceLocation> idsCopy = new HashMap<>();
        newEntries.forEach((id, section) -> idsCopy.put(section, id));

        SECTION_BY_ID = Map.copyOf(byIdCopy);
        IDS_BY_SECTION = Map.copyOf(idsCopy);

        Set<ResourceLocation> collapsed = new HashSet<>();

        newEntries.forEach((id, section) -> {
            if (isCollapsedByDefault(section)) {
                collapsed.add(id);
            }
        });
        COLLAPSED_IDS = Set.copyOf(collapsed);
    }
}
