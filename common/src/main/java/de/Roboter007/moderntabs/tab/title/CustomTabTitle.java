package de.Roboter007.moderntabs.tab.title;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.tab.TabElement;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Objects;
import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

// uses the vanilla Minecraft text style that can be configured
//ToDo: Rework again, as the component already supports
public class CustomTabTitle<T extends CustomTabTitle<?>> extends TabElement<T> {

    private static final MapCodec<CustomTabTitle<?>> BASE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("text").forGetter(nullable(CustomTabTitle::getText)),
            CodecUtil.CODEC.optionalFieldOf("offset", new Vector2i()).forGetter(TabElement::getOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation", ElementOrientation.LEFT).forGetter(CustomTabTitle::getTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(nullable(CustomTabTitle::getColor)),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(nullable(CustomTabTitle::getFontLocation)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(CustomTabTitle::getColor)),
            Codec.BOOL.optionalFieldOf("dropShadow").forGetter(nullable(CustomTabTitle::isDroppingShadow))
    ).apply(instance, CustomTabTitle::codec));

    public static final Codec<CustomTabTitle<?>> CODEC = Codec.STRING.dispatch("textStyle", CustomTabTitle::getTextStyle,
            type -> switch (type) {
                case "custom" -> BASE_CODEC;
                case "sprite" -> SpriteTabTitle.CODEC;
                case "aura" -> AuraTabTitle.CODEC;
                default -> throw new IllegalArgumentException("Unknown tab title type: " + type);
            });

    protected Component text = null;

    protected SimpleColor backgroundColor = null;
    protected SimpleColor color = null;

    protected ElementOrientation tabTextOrientation = null;
    protected ResourceLocation fontLocation = null;
    protected Boolean dropShadow = null;

    public CustomTabTitle(@Nullable Component text, @Nullable Vector2i offset, @Nullable ElementOrientation tabTextOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation fontLocation, @Nullable SimpleColor color, @Nullable Boolean dropShadow) {
        super(offset);
        this.text = text;
        this.tabTextOrientation = tabTextOrientation;
        this.backgroundColor = backgroundColor;
        this.fontLocation = fontLocation;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static CustomTabTitle<?> codec(Optional<Component> text, Vector2i offset, ElementOrientation tabTextOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> fontLocation, Optional<SimpleColor> color, Optional<Boolean> dropShadow) {
        return new CustomTabTitle<>(text.orElse(null), offset, tabTextOrientation, backgroundColor.orElse(null), fontLocation.orElse(null), color.orElse(null), dropShadow.orElse(null));
    }

    public CustomTabTitle() {
        super(new Vector2i());
    }

    public T text(Component text) {
        this.text = text;
        return this.self();
    }

    public T textOrientation(ElementOrientation textOrientation) {
        this.tabTextOrientation = textOrientation;
        return this.self();
    }

    public T backgroundColor(SimpleColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this.self();
    }

    public T font(String fontId) {
        return this.font(ResourceLocation.parse(fontId));
    }

    public T font(ResourceLocation fontLocation) {
        this.fontLocation = fontLocation;
        return this.self();
    }

    public T color(SimpleColor color) {
        this.color = color;
        return this.self();
    }

    public T dropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
        return this.self();
    }


    public void draw(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int bgOffsetX, int bgOffsetY, int width, int additionalBgWidth, int bgHeight, int color, boolean dropShadow) {
        Component title = Objects.requireNonNullElse(this.text, text);
        int textWidth = font.width(title);

        if(this.tabTextOrientation == ElementOrientation.CENTERED) {
            x += (width - textWidth) / 2;
        } else if(this.tabTextOrientation == ElementOrientation.RIGHT) {
            x += width - textWidth;
        }

        x += this.offset.x();
        y += this.offset.y();

        drawBg(guiGraphics, x + bgOffsetX, y + bgOffsetY, textWidth + additionalBgWidth, bgHeight);

        ResourceLocation titelFontLocation = Objects.requireNonNullElse(this.fontLocation, Minecraft.DEFAULT_FONT);
        SimpleColor titelColor = Objects.requireNonNullElse(this.color, new SimpleColor(color));
        boolean titelDropShadow =  Objects.requireNonNullElse(this.dropShadow, dropShadow);

        guiGraphics.drawString(font, title.copy().withStyle(style -> style.withFont(titelFontLocation)), x, y, titelColor.argbColor(), titelDropShadow);
    }

    public void drawBg(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        if(this.backgroundColor != null) {
            guiGraphics.fill(x, y, x + width, y + height, backgroundColor.argbColor());
        }
    }


    public Component getText() {
        return text;
    }

    public ElementOrientation getTextOrientation() {
        return tabTextOrientation;
    }

    public SimpleColor getBackgroundColor() {
        return backgroundColor;
    }

    public ResourceLocation getFontLocation() {
        return fontLocation;
    }

    public SimpleColor getColor() {
        return color;
    }

    public Boolean isDroppingShadow() {
        return dropShadow;
    }

    public String getTextStyle() {
        return "custom";
    }


    public CustomTabTitle<?> copy() {
        return new CustomTabTitle<>(this.text, this.offset, this.tabTextOrientation, this.backgroundColor, this.fontLocation, this.color, this.dropShadow);
    }
}
