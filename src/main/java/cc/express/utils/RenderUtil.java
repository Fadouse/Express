package cc.express.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cc.express.module.Module.mc;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    //Color
    public static void color(int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
    }

    //Draw Rect
    public static void drawRect(double x1, double y1, double x2, double y2, int color) {
        GL11.glPushMatrix();
        glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        GL11.glPushMatrix();
        color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void drawBorderedRect(double x, double y, double x2, double d, float l1,  int col1, int col2) {
        RenderUtil.drawRect(x, y, x2, d, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x2, d);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x2, d);
        GL11.glEnd();
        GL11.glPopMatrix();
        glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    //Texture Rect
    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width,
                                                           float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) x, (double) (y + height), 0.0D)
                .tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D)
                .tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D)
                .tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedRect(float x, float y, float width, float height, ResourceLocation image) {
        glPushMatrix();
        final boolean enableBlend = glIsEnabled(GL_BLEND);
        final boolean disableAlpha = !glIsEnabled(GL_ALPHA_TEST);
        if (!enableBlend) glEnable(GL_BLEND);
        if (!disableAlpha) glDisable(GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(image);
        GlStateManager.color(1F, 1F, 1F, 1F);
        RenderUtil.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        if (!enableBlend) glDisable(GL_BLEND);
        if (!disableAlpha) glEnable(GL_ALPHA_TEST);
        glPopMatrix();
    }

    //Draw Circle

    public static void drawArc(float n, float n2, double n3, final int n4, final int n5, final double n6,
                               final int n7) {
        n3 *= 2.0;
        n *= 2.0f;
        n2 *= 2.0f;
        final float n8 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n4 & 0xFF) / 255.0f;
        GL11.glDisable(2929);
        glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth((float) n7);
        glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glBegin(3);
        int n12 = n5;
        while (n12 <= n6) {
            GL11.glVertex2d(n + Math.sin(n12 * 3.141592653589793 / 180.0) * n3,
                    n2 + Math.cos(n12 * 3.141592653589793 / 180.0) * n3);
            ++n12;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        glEnable(3553);
        GL11.glDisable(3042);
        glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);

    }

    public static void drawCircle(double x, double y, double radius, int c) {
        float alpha = (float) (c >> 24 & 255) / 255.0f;
        float red = (float) (c >> 16 & 255) / 255.0f;
        float green = (float) (c >> 8 & 255) / 255.0f;
        float blue = (float) (c & 255) / 255.0f;
        boolean blend = GL11.glIsEnabled((int) 3042);
        boolean line = GL11.glIsEnabled((int) 2848);
        boolean texture = GL11.glIsEnabled((int) 3553);
        if (!blend) {
            glEnable((int) 3042);
        }
        if (!line) {
            glEnable((int) 2848);
        }
        if (texture) {
            GL11.glDisable((int) 3553);
        }
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
        GL11.glBegin((int) 9);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(
                    (double) ((double) x + Math.sin((double) ((double) i * 3.141526 / 180.0)) * (double) radius),
                    (double) ((double) y + Math.cos((double) ((double) i * 3.141526 / 180.0)) * (double) radius));
            ++i;
        }
        GL11.glEnd();
        if (texture) {
            glEnable((int) 3553);
        }
        if (!line) {
            GL11.glDisable((int) 2848);
        }
        if (!blend) {
            GL11.glDisable((int) 3042);
        }
    }

    public static void drawFullCircle(int x, int y, double r, int segments, float lineWidth, int part, int color) {
        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        r *= 2.0;
        x *= 2;
        y *= 2;
        glEnable((int) 3042);
        GL11.glLineWidth((float) lineWidth);
        GL11.glDisable((int) 3553);
        glEnable((int) 2848);
        GL11.glBlendFunc((int) 770, (int) 771);
        color(color);
        GL11.glBegin((int) 3);
        int i = segments - part;
        while (i <= segments) {
            double cx = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
            double cy = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d((double) ((double) x + cx), (double) ((double) y + cy));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int) 2848);
        glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    //Draw Triangle

    public static void drawTriangle(double x, double y, double x1, double y1, double x2, double y2, int color){
        GL11.glPushMatrix();
        glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        GL11.glPushMatrix();
        color(color);

        GL11.glBegin(GL_TRIANGLES);

        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x2, y2);

        GL11.glEnd();

        GL11.glPopMatrix();
        glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    //Draw Shadow
    public static void drawShadow(float x, float y, float x2, float y2) {
        drawTexturedRect(x - 9, y - 9, 9, 9, new ResourceLocation("shaders/" + "paneltopleft.png"));
        drawTexturedRect(x - 9, y2, 9, 9, new ResourceLocation("shaders/" + "panelbottomleft.png"));
        drawTexturedRect(x2, y2, 9, 9, new ResourceLocation("shaders/" + "panelbottomright.png"));
        drawTexturedRect(x2, y - 9, 9, 9, new ResourceLocation("shaders/" + "paneltopright.png"));
        drawTexturedRect(x - 9, y, 9, y2-y, new ResourceLocation("shaders/" + "panelleft.png"));
        drawTexturedRect(x2, y, 9, y2-y, new ResourceLocation("shaders/" + "panelright.png"));
        drawTexturedRect(x, y - 9, x2-x, 9, new ResourceLocation("shaders/" + "paneltop.png"));
        drawTexturedRect(x, y2, x2-x, 9, new ResourceLocation("shaders/" + "panelbottom.png"));
    }

    //draw Image
    public static void drawImage(final ResourceLocation image, final int x, final int y, final int width, final int height) {
        drawImage(image, x, y, width, height, 1.0f);
    }

    public static void drawImage(final ResourceLocation image, final int x, final int y, final int width, final int height, final float alpha) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    //setGl
    public static void setGlState(final int cap, final boolean state) {
        if (state) {
            GL11.glEnable(cap);
        }
        else {
            GL11.glDisable(cap);
        }
    }
}
