package de.Roboter007.moderntabs.tab.designs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.ModernTabs.TabDesign;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TabDesignReloadListener extends SimpleJsonResourceReloadListener {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();

    public TabDesignReloadListener() {
        super(GSON, "moderntabs/designs");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<ResourceLocation, TabDesign> parsed = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            DataResult<TabDesign> result = TabDesign.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
            if (result.isSuccess()) {
                parsed.put(entry.getKey(), result.getOrThrow());
            } else {
                LOGGER.error("ModernTabs: couldn't parse tab design '{}': {}", entry.getKey(), result);
            }
        }
        ModernTabs.reload(parsed);
    }
}
