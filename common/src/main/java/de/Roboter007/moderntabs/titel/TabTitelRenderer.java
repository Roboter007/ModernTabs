package de.Roboter007.moderntabs.titel;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Objects;

public class TabTitelRenderer {

    public static void drawAuraText(final GuiGraphics graphics, final Component text, Font font1, Font font2, final int color1, final int color2, boolean dropShadow1, boolean dropShadow2, final int x, final int y) {
        final Font defaultFont = Minecraft.getInstance().font;
        final Window window = Minecraft.getInstance().getWindow();
        final float scale = (float) window.getGuiScale();

        graphics.drawString(Objects.requireNonNullElse(font1, defaultFont), text, x, y, color1, dropShadow1);

        final PoseStack ps = graphics.pose();
        ps.pushPose();
        ps.translate(0, 0, 1);
        final Matrix4f pose = ps.last().copy().pose();
        final Vector3f position = pose.transformPosition(new Vector3f(x, y, 0));
        final Vector3f corner = pose.transformPosition(new Vector3f(x + Objects.requireNonNullElse(font1, defaultFont).width(text), y + Objects.requireNonNullElse(font1, defaultFont).lineHeight / 1.8f, 0));

        position.mul(scale);
        corner.mul(scale);
        final int height = (int) (corner.y - position.y);
        final int width = (int) (corner.x - position.x);
        RenderSystem.enableScissor(
                (int) position.x,
                window.getHeight() - (int) position.y - height,
                width,
                height
        );

        graphics.drawString(Objects.requireNonNullElse(font2, defaultFont), text, x, y, color2, dropShadow2);

        RenderSystem.disableScissor();
        ps.popPose();
    }
}
