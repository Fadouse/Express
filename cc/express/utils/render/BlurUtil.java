package cc.express.utils.render;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class BlurUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static ResourceLocation resourceLocation = new ResourceLocation("express/shader/blur.json");
    private static ShaderGroup shaderGroup;
    private static Framebuffer framebuffer;

    private static int lastFactor;
    private static int lastWidth;
    private static int lastHeight;

    public BlurUtil() {
        this.resourceLocation = new ResourceLocation("express/shader/blur.json");
    }



    public static void init() {
        try {
            shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourceLocation);
            shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            framebuffer = shaderGroup.mainFramebuffer;
        } catch (final JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void setValues(final int strength) {
        shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
        shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
        shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
        shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);
    }

    public static final void blur(final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        lastFactor = scaleFactor;
        lastWidth = width;
        lastHeight = height;

        setValues(blurStrength);
        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableAlpha();
    }

    public static final void blur(final double x, final double y, final double areaWidth, final double areaHeight, final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();

        if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
            init();
        }

        lastFactor = scaleFactor;
        lastWidth = width;
        lastHeight = height;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissor(x, y, areaWidth, areaHeight);
        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(mc.timer.renderPartialTicks);
        setValues(blurStrength);
        mc.getFramebuffer().bindFramebuffer(false);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private static boolean sizeHasChanged(final int scaleFactor, final int width, final int height) {
        return (lastFactor != scaleFactor || lastWidth != width || lastHeight != height);
    }

}