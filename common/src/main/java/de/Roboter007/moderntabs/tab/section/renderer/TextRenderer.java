package de.Roboter007.moderntabs.tab.section.renderer;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TextRenderer {

    public static void drawAuraText(GuiGraphics graphics, Component text, ResourceLocation font1Location, ResourceLocation font2Location, int color1, int color2, boolean dropShadow1, boolean dropShadow2, int x, int y) {
        Font defaultFont = Minecraft.getInstance().font;
        Window window = Minecraft.getInstance().getWindow();
        float scale = (float) window.getGuiScale();

        MutableComponent text1 = text.copy();
        if(font1Location != null) {
            text1 = text1.withStyle(style -> style.withFont(font1Location));
        }

        MutableComponent text2 = text.copy();
        if(font2Location != null) {
            text2 = text2.withStyle(style -> style.withFont(font2Location));
        }

        graphics.drawString(defaultFont, text1, x, y, color1, dropShadow1);

        final PoseStack ps = graphics.pose();
        ps.pushPose();
        ps.translate(0, 0, 1);
        Matrix4f pose = ps.last().copy().pose();
        Vector3f position = pose.transformPosition(new Vector3f(x, y, 0));
        Vector3f corner = pose.transformPosition(new Vector3f(x + defaultFont.width(text1), y + defaultFont.lineHeight / 1.8f, 0));

        position.mul(scale);
        corner.mul(scale);
        int height = (int) (corner.y - position.y);
        int width = (int) (corner.x - position.x);

        RenderSystem.enableScissor((int) position.x, window.getHeight() - (int) position.y - height, width, height);

        graphics.drawString(defaultFont, text2, x, y, color2, dropShadow2);

        RenderSystem.disableScissor();
        ps.popPose();
    }

    public static void drawAuraText(GuiGraphics graphics, Component text, int color1, int color2, int x, int y) {
        drawAuraText(graphics, text, null, null, color1, color2, true, false, x, y);
    }
}
