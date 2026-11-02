package de.Roboter007.moderntabs.tab.title;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

// uses a sprite as a tab titel
public class SpriteTabTitle extends CustomTabTitle<SpriteTabTitle> {

    public static final MapCodec<SpriteTabTitle> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset", new Vector2i()).forGetter(SpriteTabTitle::getOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation", ElementOrientation.LEFT).forGetter(SpriteTabTitle::getTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(nullable(SpriteTabTitle::getBackgroundColor)),
            ResourceLocation.CODEC.optionalFieldOf("spriteLocation").forGetter(nullable(SpriteTabTitle::getSpriteTitelLocation)),
            Codec.INT.optionalFieldOf("width", 0).forGetter(SpriteTabTitle::getWidth),
            Codec.INT.optionalFieldOf("height", 0).forGetter(SpriteTabTitle::getHeight)
    ).apply(instance, SpriteTabTitle::codec));

    private ResourceLocation spriteTitelLocation;
    private int width;
    private int height;

    public SpriteTabTitle(@Nullable Vector2i offset, @Nullable ElementOrientation textOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation spriteTitelLocation, int width, int height) {
        super(null, offset, textOrientation, backgroundColor, null, null, null);
        this.spriteTitelLocation = spriteTitelLocation;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static SpriteTabTitle codec(Vector2i offset, ElementOrientation textOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> spriteTitelLocation, int width, int height) {
        return new SpriteTabTitle(offset, textOrientation, backgroundColor.orElse(null), spriteTitelLocation.orElse(null), width, height);
    }

    public SpriteTabTitle() {
        this.spriteTitelLocation = null;
        this.width = 0;
        this.height = 0;
    }

    public SpriteTabTitle spriteTitelLocation(String spriteId) {
        this.spriteTitelLocation = ResourceLocation.parse(spriteId);
        return this;
    }

    public SpriteTabTitle spriteTitelLocation(ResourceLocation spriteTitelLocation) {
        this.spriteTitelLocation = spriteTitelLocation;
        return this;
    }

    public SpriteTabTitle width(int width) {
        this.width = width;
        return this;
    }

    public SpriteTabTitle height(int height) {
        this.height = height;
        return this;
    }


    @Override
    public void draw(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int bgOffsetX, int bgOffsetY, int width, int additionalBgWidth, int bgHeight, int color, boolean dropShadow) {
        if(this.spriteTitelLocation != null) {
            if(this.tabTextOrientation == ElementOrientation.CENTERED) {
                x += (160 - this.width) / 2;
            } else if(this.tabTextOrientation == ElementOrientation.RIGHT) {
                x += 160 - this.width;
            }

            x += this.offset.x();
            y += this.offset.y();

            this.drawBg(guiGraphics, x, y, this.width, this.height);
            guiGraphics.blitSprite(this.spriteTitelLocation, x, y - 1, this.width, this.height);
        } else {
            super.draw(guiGraphics, font, text, x, y, bgOffsetX, bgOffsetY, width, additionalBgWidth, bgHeight, color, dropShadow);
        }
    }

    public ResourceLocation getSpriteTitelLocation() {
        return spriteTitelLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    @Override
    public String getTextStyle() {
        return "sprite";
    }
}
