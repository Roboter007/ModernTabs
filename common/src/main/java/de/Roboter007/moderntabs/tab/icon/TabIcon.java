package de.Roboter007.moderntabs.tab.icon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2i;

import java.util.Optional;

public class TabIcon extends TabElement<TabIcon> {

    public static final Codec<TabIcon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset").forGetter(TabIcon::optionalOffset),
            ResourceLocation.CODEC.optionalFieldOf("location").forGetter(TabIcon::optionalLocation),
            ItemStack.CODEC.optionalFieldOf("itemStack").forGetter(TabIcon::optionalItemStack),
            Codec.INT.optionalFieldOf("width").forGetter(TabIcon::optionalWidth),
            Codec.INT.optionalFieldOf("height").forGetter(TabIcon::optionalHeight)
    ).apply(instance, TabIcon::new));

    private ResourceLocation location = null;
    private ItemStack itemStack = null;
    private int width = 16;
    private int height = 16;

    public TabIcon(Vector2i offset, ResourceLocation location, ItemStack itemStack, int width, int height) {
        super(offset);
        this.location = location;
        this.itemStack = itemStack;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public TabIcon(Optional<Vector2i> offset, Optional<ResourceLocation> location, Optional<ItemStack> itemStack, Optional<Integer> width, Optional<Integer> height) {
        this(offset.orElse(new Vector2i()), location.orElse(null), itemStack.orElse(null), width.orElse(16), height.orElse(16));
    }

    public TabIcon() {
        super(new Vector2i());
    }

    public TabIcon location(ResourceLocation iconLocation) {
        this.location = iconLocation;
        return this;
    }

    public TabIcon itemStack(ItemStack iconStack) {
        this.itemStack = iconStack;
        return this;
    }

    public TabIcon width(int width) {
        this.width = width;
        return this;
    }

    public TabIcon height(int height) {
        this.height = height;
        return this;
    }


    public ResourceLocation getLocation() {
        return location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Optional<ResourceLocation> optionalLocation() {
        return Optional.ofNullable(location);
    }

    public Optional<ItemStack> optionalItemStack() {
        return Optional.ofNullable(itemStack);
    }

    public Optional<Integer> optionalWidth() {
        return Optional.of(width);
    }

    public Optional<Integer> optionalHeight() {
        return Optional.of(height);
    }
}
