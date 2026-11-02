package de.Roboter007.moderntabs.tab.section;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.Roboter007.moderntabs.tab.section.decoration.Banner;
import de.Roboter007.moderntabs.tab.section.decoration.Overlay;
import de.Roboter007.moderntabs.tab.section.toggle.SectionToggle;
import de.Roboter007.moderntabs.tab.title.CustomTabTitle;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.Roboter007.moderntabs.util.CodecUtil.nullable;

public class Section {

    public static final Codec<Section> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.listOf().optionalFieldOf("content", new ArrayList<>()).forGetter(Section::getContent),
            CustomTabTitle.CODEC.fieldOf("title").forGetter(Section::getTitle),
            Banner.CODEC.fieldOf("banner").forGetter(Section::getBanner),
            Overlay.CODEC.optionalFieldOf("overlay").forGetter(nullable(Section::getOverlay)),
            SectionToggle.CODEC.optionalFieldOf("sectionToggle").forGetter(nullable(Section::getSectionToggle))
    ).apply(instance, Section::codec));

    private List<ItemStack> content = new ArrayList<>();
    private CustomTabTitle<?> title = null;
    private Banner banner = null;
    private Overlay overlay = null;
    private SectionToggle sectionToggle = null;

    public Section(List<ItemStack> content, CustomTabTitle<?> title, Banner banner, Overlay overlay, SectionToggle sectionToggle) {
        this.content = content;
        this.title = title;
        this.banner = banner;
        this.overlay = overlay;
        this.sectionToggle = sectionToggle;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Section codec(List<ItemStack> content, CustomTabTitle<?> title, Banner banner, Optional<Overlay> overlay, Optional<SectionToggle> sectionToggle) {
        return new Section(content, title, banner, overlay.orElse(null), sectionToggle.orElse(null));
    }

    public Section() {}

    public Section content(List<ItemStack> content) {
        this.content = content;
        return this;
    }

    public Section addContent(ItemStack itemStack) {
        this.content.add(itemStack);
        return this;
    }

    public Section title(CustomTabTitle<?> title) {
        this.title = title;
        return this;
    }

    public Section banner(Banner banner) {
        this.banner = banner;
        return this;
    }

    public Section overlay(Overlay overlay) {
        this.overlay = overlay;
        return this;
    }

    public Section sectionToggle(SectionToggle sectionToggle) {
        this.sectionToggle = sectionToggle;
        return this;
    }


    public List<ItemStack> getContent() {
        return content;
    }

    public CustomTabTitle<?> getTitle() {
        return title;
    }

    public Banner getBanner() {
        return banner;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public SectionToggle getSectionToggle() {
        return sectionToggle;
    }

    /*public record SectionToggle(ToggleState defaultState, ElementOrientation orientation) {
        public static final Codec<SectionToggle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StringRepresentable.fromEnum(ToggleState::values).fieldOf("defaultState").orElse(ToggleState.EXPANDED).forGetter(SectionToggle::defaultState),
                StringRepresentable.fromEnum(ElementOrientation::values).fieldOf("orientation").orElse(ElementOrientation.RIGHT).forGetter(SectionToggle::orientation)
        ).apply(instance, SectionToggle::new));
    }

    //ToDo: replace with the the better title code
    public record Title(Component text, ElementOrientation orientation, int color, Optional<Integer> secondaryColor, int background) {
        public static final Codec<Title> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.fieldOf("text").forGetter(Title::text),
                StringRepresentable.fromEnum(ElementOrientation::values).fieldOf("orientation").orElse(ElementOrientation.LEFT).forGetter(Title::orientation),
                ColorUtil.ARGB_CODEC.fieldOf("color").orElse(0xFFFFFFFF).forGetter(Title::color),
                ColorUtil.ARGB_CODEC.optionalFieldOf("secondary_color").forGetter(Title::secondaryColor),
                ColorUtil.ARGB_CODEC.fieldOf("background").orElse(0xAA000000).forGetter(Title::background)
        ).apply(instance, Title::new));
    }

    public record Overlay(ResourceLocation sprite, AnimationMode animationMode, Optional<Integer> color) implements Decoration {
        private static final ResourceLocation DEFAULT_OVERLAY = ModernTabs.path("overlay/default_banner_overlay");

        public static final Codec<Overlay> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("sprite").orElse(DEFAULT_OVERLAY).forGetter(Overlay::sprite),
                StringRepresentable.fromEnum(AnimationMode::values).fieldOf("animation_mode").orElse(AnimationMode.NOT_ANIMATED).forGetter(Overlay::animationMode),
                ColorUtil.ARGB_CODEC.optionalFieldOf("color").orElse(Optional.of(0xFFFFFFFF)).forGetter(Overlay::color)
        ).apply(instance, Overlay::new));
    }

    public record Banner(Optional<ResourceLocation> optionalSprite, AnimationMode animationMode, Optional<Integer> color) implements Decoration {
        public static final ResourceLocation MISSING_BANNER = ModernTabs.path("banner/missing_banner");
        public static final ResourceLocation COLORED_BANNER = ModernTabs.path("banner/colored_banner");

        public static final Codec<Banner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("sprite").orElse(Optional.empty()).forGetter(Banner::optionalSprite),
                StringRepresentable.fromEnum(AnimationMode::values).fieldOf("animation_mode").orElse(AnimationMode.NOT_ANIMATED).forGetter(Banner::animationMode),
                ColorUtil.ARGB_CODEC.optionalFieldOf("color").orElse(Optional.of(0xFFFFFFFF)).forGetter(Banner::color)
        ).apply(instance, Banner::new));

        @Override
        public ResourceLocation sprite() {
            if(optionalSprite.isEmpty()) {
                if(color.isPresent()) {
                    return COLORED_BANNER;
                } else {
                    return MISSING_BANNER;
                }
            } else {
                return this.optionalSprite.get();
            }
        }
    }

    public interface Decoration {
        ResourceLocation sprite();
        AnimationMode animationMode();
        Optional<Integer> color();
    } */
}
