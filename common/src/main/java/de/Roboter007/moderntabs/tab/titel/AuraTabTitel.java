package de.Roboter007.moderntabs.tab.titel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.color.SimpleColor;
import de.Roboter007.moderntabs.tab.section.states.ElementOrientation;
import de.Roboter007.moderntabs.util.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Optional;

// uses the same text style of the tab banners
public class AuraTabTitel extends CustomTabTitel<AuraTabTitel> {

    public static final MapCodec<AuraTabTitel> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CodecUtil.CODEC.optionalFieldOf("offset").forGetter(AuraTabTitel::optionalOffset),
            StringRepresentable.fromEnum(ElementOrientation::values).optionalFieldOf("orientation").forGetter(AuraTabTitel::optionalTextOrientation),
            SimpleColor.CODEC.optionalFieldOf("backgroundColor").forGetter(AuraTabTitel::optionalColor),
            ResourceLocation.CODEC.optionalFieldOf("font1Location").forGetter(AuraTabTitel::optionalFontLocation),
            ResourceLocation.CODEC.optionalFieldOf("font2Location").forGetter(AuraTabTitel::optionalFont2Location),
            SimpleColor.CODEC.optionalFieldOf("color1").forGetter(AuraTabTitel::optionalColor),
            SimpleColor.CODEC.optionalFieldOf("color2").forGetter(AuraTabTitel::optionalColor2),
            Codec.BOOL.optionalFieldOf("dropShadow1").forGetter(AuraTabTitel::optionalDropShadow),
            Codec.BOOL.optionalFieldOf("dropShadow2").forGetter(AuraTabTitel::optionalDropShadow2)
    ).apply(instance, AuraTabTitel::new));

    private ResourceLocation font2Location;
    private SimpleColor color2;
    private Boolean dropShadow2;

    public AuraTabTitel(@Nullable Vector2i offset, @Nullable ElementOrientation textOrientation, @Nullable SimpleColor backgroundColor, @Nullable ResourceLocation font1Location, @Nullable ResourceLocation font2Location, @Nullable SimpleColor color1, @Nullable SimpleColor color2, @Nullable Boolean dropShadow1, @Nullable Boolean dropShadow2) {
        super(offset, textOrientation, backgroundColor, font1Location, color1, dropShadow1);
        this.font2Location = font2Location;
        this.color2 = color2;
        this.dropShadow2 = dropShadow2;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public AuraTabTitel(Optional<Vector2i> offset, Optional<ElementOrientation> textOrientation, Optional<SimpleColor> backgroundColor, Optional<ResourceLocation> font1Location, Optional<ResourceLocation> font2Location, Optional<SimpleColor> color1, Optional<SimpleColor> color2, Optional<Boolean> dropShadow1, Optional<Boolean> dropShadow2) {
        this(offset.orElse(new Vector2i()), textOrientation.orElse(ElementOrientation.LEFT), backgroundColor.orElse(null), font1Location.orElse(null), font2Location.orElse(null), color1.orElse(null), color2.orElse(null), dropShadow1.orElse(false), dropShadow2.orElse(false));
    }

    public AuraTabTitel () {
        this.font2Location = null;
        this.color2 = null;
        this.dropShadow2 = null;
    }

    public AuraTabTitel font2(String font2id) {
        return this.font2(ResourceLocation.parse(font2id));
    }

    public AuraTabTitel font2(ResourceLocation font2Location) {
        this.font2Location = font2Location;
        return this;
    }

    public AuraTabTitel color2(SimpleColor color2) {
        this.color2 = color2;
        return this;
    }

    public AuraTabTitel dropShadow2(boolean dropShadow2) {
        this.dropShadow2 = dropShadow2;
        return this;
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

    public Optional<ResourceLocation> optionalFont2Location() {
        return Optional.ofNullable(font2Location);
    }

    public Optional<SimpleColor> optionalColor2() {
        return Optional.ofNullable(color2);
    }

    public Optional<Boolean> optionalDropShadow2() {
        return Optional.ofNullable(dropShadow2);
    }


    @Override
    public String getTextStyle() {
        return "aura";
    }
}