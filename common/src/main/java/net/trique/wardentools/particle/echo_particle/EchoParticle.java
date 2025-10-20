package net.trique.wardentools.particle.echo_particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EchoParticle extends TextureSheetParticle {
    private final float xRot;
    private final float yRot;

    public EchoParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet,
                        double xSpeed, double ySpeed, double zSpeed, float xRot,float yRot) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        this.xRot = xRot;
        this.yRot = yRot;
        xd = yd = zd = 0;
        this.quadSize *= 1f;
        this.lifetime = 200;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
       // this.alpha = 1.0F - Mth.clamp(((float) this.age + partialTicks) / (float) this.lifetime, 0.0F, 1.0F);
        Quaternionf quaternionf = new Quaternionf();

        quaternionf.rotateY((float) (Math.PI- yRot * Math.PI/180));
        quaternionf.rotateX(-(float) (xRot * Math.PI/180));

        this.renderRotatedQuad(buffer, camera, quaternionf, partialTicks);

        //fixme backside problem
        Quaternionf quaternionf1 = new Quaternionf();

        quaternionf1.rotateY((float) (Math.PI-yRot * Math.PI/180));
        quaternionf1.rotateX(-(float) (xRot * Math.PI/180));


        this.renderRotatedQuad(buffer, camera, quaternionf1, partialTicks);
    }

    private void fadeOut() {
        this.alpha = (-(1 / (float) lifetime) * age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Factory(SpriteSet sprites) implements ParticleProvider<EchoParticleOption> {

        @Override
        public Particle createParticle(EchoParticleOption echoParticleOption, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float scale = echoParticleOption.scale();
            EchoParticle particle = new EchoParticle(clientLevel, x, y, z, sprites, xSpeed, ySpeed, zSpeed,echoParticleOption.rotX(),echoParticleOption.rotY());
            return particle.scale(scale);
        }
    }
}
