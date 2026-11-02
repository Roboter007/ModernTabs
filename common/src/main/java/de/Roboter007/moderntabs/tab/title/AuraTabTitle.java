package de.Roboter007.moderntabs.tab.title;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.renderer.TextRenderer;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
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

// uses the same text style of the tab banners
public class AuraTabTitle extends CustomTabTitle<AuraTabTitle> {

    public static final MapCodec<AuraTabTitle> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("text").forGetter(nullable(CustomTabTitle::getText)),
            CodecUtil.CODEC.optionalFieldOf("offset", new Vector2i()).forGetter(AuraTabTitle::getOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation", ElementOrientation.LEFT).forGetter(AuraTabTitle::getTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(nullable(AuraTabTitle::getBackgroundColor)),
            ResourceLocation.CODEC.optionalFieldOf("fontLocation").forGetter(nullable(AuraTabTitle::getFontLocation)),
            ResourceLocation.CODEC.optionalFieldOf("font2Location").forGetter(nullable(AuraTabTitle::getFont2Location)),
            SimpleColor.CODEC.optionalFieldOf("color").forGetter(nullable(AuraTabTitle::getColor)),
            SimpleColor.CODEC.optionalFieldOf("color2").forGetter(nullable(AuraTabTitle::getColor2)),
            Codec.BOOL.optionalFieldOf("dropShadow").forGetter(nullable(AuraTabTitle::isDroppingShadow)),
            Codec.BOOL.optionalFieldOf("dropShadow2").forGetter(nullable(AuraTabTitle::isDroppingShadow2))
    ).apply(instance, AuraTabTitle::codec));

    private ResourceLocation font2Location;
    private SimpleColor color2;
    private Boolean dropShadow2;

    public AuraTabTitle(@Nullable Component text, @Nullable Vector2i offset, @Nullable ElementOrientation textOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation font1Location, @Nullable ResourceLocation font2Location, @Nullable SimpleColor color1, @Nullable SimpleColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(text, offset, textOrientation, backgroundColor, font1Location, color1, dropShadow1);
        this.font2Location = font2Location;
        this.color2 = color2;
        this.dropShadow2 = dropShadow2;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static AuraTabTitle codec(Optional<Component> text, Vector2i offset, ElementOrientation textOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> font1Location, Optional<ResourceLocation> font2Location, Optional<SimpleColor> color1, Optional<SimpleColor> color2, Optional<Boolean> dropShadow1, Optional<Boolean> dropShadow2) {
        return new AuraTabTitle(text.orElse(null), offset, textOrientation, backgroundColor.orElse(null), font1Location.orElse(null), font2Location.orElse(null), color1.orElse(null), color2.orElse(null), dropShadow1.orElse(null), dropShadow2.orElse(null));
    }

    public AuraTabTitle() {
        this.font2Location = null;
        this.color2 = null;
        this.dropShadow2 = null;
    }

    public AuraTabTitle font2(String font2id) {
        return this.font2(ResourceLocation.parse(font2id));
    }

    public AuraTabTitle font2(ResourceLocation font2Location) {
        this.font2Location = font2Location;
        return this;
    }

    public AuraTabTitle color2(SimpleColor color2) {
        this.color2 = color2;
        return this;
    }

    public AuraTabTitle dropShadow2(boolean dropShadow2) {
        this.dropShadow2 = dropShadow2;
        return this;
    }


    @Override
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
        ResourceLocation titelFont2Location = Objects.requireNonNullElse(this.font2Location, Minecraft.DEFAULT_FONT);
        SimpleColor titelColor = Objects.requireNonNullElse(this.color, new SimpleColor(color));
        SimpleColor titelColor2 = Objects.requireNonNullElse(this.color2, new SimpleColor(color).toComplex().darken(0.2f));
        boolean titelDropShadow =  Objects.requireNonNullElse(this.dropShadow, dropShadow);
        boolean titelDropShadow2 = Objects.requireNonNullElse(this.dropShadow2, dropShadow);

        TextRenderer.drawAuraText(guiGraphics, title, titelFontLocation, titelFont2Location, titelColor.argbColor(), titelColor2.argbColor(), titelDropShadow, titelDropShadow2, x, y);
    }

    public ResourceLocation getFont2Location() {
        return font2Location;
    }

    public SimpleColor getColor2() {
        return color2;
    }

    public Boolean isDroppingShadow2() {
        return dropShadow2;
    }

    @Override
    public String getTextStyle() {
        return "aura";
    }
}