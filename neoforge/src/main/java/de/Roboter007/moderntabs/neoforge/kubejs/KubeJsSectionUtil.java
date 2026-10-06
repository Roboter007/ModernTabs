package de.Roboter007.moderntabs.neoforge.kubejs;

import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.util.SectionUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class KubeJsSectionUtil {

    public static void addItem(String sectionId, String itemId) {
        SectionUtil.addItemByLocation(ResourceLocation.parse(itemId), ResourceLocation.parse(sectionId));
    }

    public static void addItems(String sectionId, String... itemIds) {
        for(String itemId : itemIds) {
            addItem(sectionId, itemId);
        }
    }

    public static void addItemList(String sectionId, List<String> itemIds) {
        for(String itemId : itemIds) {
            addItem(sectionId, itemId);
        }
    }

    public static void addItemsByTag(String sectionId, String tagId) {
        if(tagId.charAt(0) == '#') {
            ResourceLocation tagLocation = ResourceLocation.parse(sectionId.substring(1));
            SectionUtil.addItemTagByLoc(tagLocation, ResourceLocation.parse(sectionId));
        } else {
            ModernTabs.LOGGER.error("tried to the tag: {} to section: {}", tagId, sectionId);
        }
    }

    public static void setVisibility(String itemId, final SectionUtil.ItemVisibility type) {
        SectionUtil.setVisibility(ResourceLocation.parse(itemId), type);
    }

}
