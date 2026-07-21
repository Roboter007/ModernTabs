package de.Roboter007.moderntabs.section;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SectionReloadListener extends SimpleJsonResourceReloadListener {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new Gson();

    public SectionReloadListener() {
        super(GSON, "moderntabs/sections");
    }

    @Override
    protected void apply(final Map<ResourceLocation, JsonElement> map, final ResourceManager resourceManager, final ProfilerFiller profilerFiller) {
        final Map<ResourceLocation, Section> parsed = new HashMap<>();
        for (final Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            final DataResult<Section> result = Section.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
            if (result.isSuccess()) {
                parsed.put(entry.getKey(), result.getOrThrow());
            } else {
                LOGGER.error("ModernTabs: couldn't parse section '{}': {}", entry.getKey(), result);
            }
        }
        Sections.reload(parsed);
    }
}
