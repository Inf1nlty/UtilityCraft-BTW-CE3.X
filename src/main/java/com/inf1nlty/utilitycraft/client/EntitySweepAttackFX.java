package com.inf1nlty.utilitycraft.client;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class EntitySweepAttackFX extends EntityFX {

    private static final ResourceLocation[] SWEEP_TEXTURES = {
            new ResourceLocation("utilitycraft:textures/particle/sweep_0.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_1.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_2.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_3.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_4.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_5.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_6.png"),
            new ResourceLocation("utilitycraft:textures/particle/sweep_7.png")
    };

    private int life;
    private final int maxLife = 6;
    private final int baseFrame;

    public EntitySweepAttackFX(World world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);
        this.life = 0;
        this.particleMaxAge = maxLife;
        this.noClip = true;

        int totalFrames = 8;
        this.baseFrame = this.rand.nextInt(totalFrames);

        float colorVariation = 0.9F + this.rand.nextFloat() * 0.1F;
        this.particleRed = colorVariation;
        this.particleGreen = colorVariation * 0.95F;
        this.particleBlue = colorVariation * 0.9F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.life++;
        if (this.life >= this.maxLife) this.setDead();
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTicks, float rx, float rz, float ryz, float rxy, float rxz) {

        Minecraft.getMinecraft().renderEngine.bindTexture(SWEEP_TEXTURES[this.baseFrame]);

        float ageProgress = ((float)this.life + partialTicks) / (float)this.maxLife;
        float alpha = 1.0F - ageProgress * ageProgress;
        float sizeMultiplier = 1.0F + (float)Math.sin(ageProgress * Math.PI) * 0.2F;
        float width = 2.0F * sizeMultiplier;
        float height = 0.8F * sizeMultiplier;
        float halfW = width / 2, halfH = height / 2;

        float px = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float py = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);

        GL11.glTranslatef(px, py, pz);

        Entity renderEntity = Minecraft.getMinecraft().renderViewEntity;

        if (renderEntity != null) {
            float cameraYaw = renderEntity.rotationYaw;
            float cameraPitch = renderEntity.rotationPitch;
            GL11.glRotatef(-cameraYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(cameraPitch, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0, 0, 1.25F);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(this.particleRed * 1.7F, this.particleGreen * 1.7F, this.particleBlue * 1.7F, alpha);

        tess.startDrawingQuads();
        tess.setBrightness(15728880);

        tess.addVertexWithUV(-halfW, -halfH, 0, 0, 1);
        tess.addVertexWithUV(-halfW, +halfH, 0, 0, 0);
        tess.addVertexWithUV(+halfW, +halfH, 0, 1, 0);
        tess.addVertexWithUV(+halfW, -halfH, 0, 1, 1);

        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}