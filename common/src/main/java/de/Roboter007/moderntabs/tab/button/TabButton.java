package de.Roboter007.moderntabs.tab.button;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.ModernTabs;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.icon.TabIcon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TabButton {

    public static final Codec<TabButton> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("spritesLocation").forGetter(TabButton::optionalSpritesLocation),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(TabButton::optionalColor),
            TabIcon.CODEC.optionalFieldOf("icon").forGetter(TabButton::optionalIcon)
    ).apply(instance, TabButton::new));

    private ResourceLocation spritesLocation = null;
    private SimpleColor color = null;
    private TabIcon icon = null;

    private TabButtonSprite[] tabButtonSprites = null;

    public TabButton(ResourceLocation spritesLocation, SimpleColor color, TabIcon icon) {
        this.spritesLocation = spritesLocation;
        this.color = color;
        this.icon = icon;
        updateTabButtonStates();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public TabButton(Optional<ResourceLocation> spritesLocation, Optional<SimpleColor> color, Optional<TabIcon> icon) {
        this(spritesLocation.orElse(null), color.orElse(null), icon.orElse(null));
    }

    public TabButton() {
        updateTabButtonStates();
    }

    public TabButton tabButtonSpritesLocation(ResourceLocation spritesLocation) {
        this.spritesLocation = spritesLocation;
        updateTabButtonStates();
        return this;
    }

    public TabButton color(SimpleColor color) {
        this.color = color;
        return this;
    }

    public TabButton icon(TabIcon icon) {
        this.icon = icon;
        return this;
    }

    public ResourceLocation getSpritesLocation() {
        return spritesLocation;
    }

    public SimpleColor getColor() {
        return color;
    }

    public TabIcon getIcon() {
        return icon;
    }

    public boolean hasCustomIcon() {
        return this.icon != null;
    }

    public boolean hasCustomTabButtonSprite() {
        return this.spritesLocation != null;
    }


    public Optional<ResourceLocation> optionalSpritesLocation() {
        return Optional.ofNullable(spritesLocation);
    }

    public Optional<SimpleColor> optionalColor() {
        return Optional.ofNullable(color);
    }

    public Optional<TabIcon> optionalIcon() {
        return Optional.ofNullable(icon);
    }

    public void updateTabButtonStates() {
        this.tabButtonSprites = new TabButtonSprite[]{
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.TOP, TabButtonSprite.Column.LEFT, TabButtonSprite.Selection.SELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.TOP, TabButtonSprite.Column.LEFT, TabButtonSprite.Selection.UNSELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.TOP, TabButtonSprite.Column.MIDDLE, TabButtonSprite.Selection.SELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.TOP, TabButtonSprite.Column.MIDDLE, TabButtonSprite.Selection.UNSELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.BOTTOM, TabButtonSprite.Column.LEFT, TabButtonSprite.Selection.SELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.BOTTOM, TabButtonSprite.Column.LEFT, TabButtonSprite.Selection.UNSELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.BOTTOM, TabButtonSprite.Column.MIDDLE, TabButtonSprite.Selection.SELECTED),
                new TabButtonSprite(spritesLocation, CreativeModeTab.Row.BOTTOM, TabButtonSprite.Column.MIDDLE, TabButtonSprite.Selection.UNSELECTED)
        };
    }

    @NotNull
    public TabButtonSprite get(CreativeModeTab.Row row, TabButtonSprite.Column column, TabButtonSprite.Selection selection) {
        if(tabButtonSprites != null) {
            for (TabButtonSprite iconBackground : tabButtonSprites) {
                if (iconBackground.row().equals(row) && iconBackground.column().equals(column) && iconBackground.selection().equals(selection)) {
                    return iconBackground;
                }
            }
        }
        ModernTabs.logWarning("Failed to look up the tab button sprite - row: " + row.name() + ", column: " + column.name() + ", selectionState: " + selection.name());
        return new TabButtonSprite(row, column, selection);
    }
}
