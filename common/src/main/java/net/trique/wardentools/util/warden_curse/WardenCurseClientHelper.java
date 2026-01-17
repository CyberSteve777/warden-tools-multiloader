package net.trique.wardentools.util.warden_curse;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class WardenCurseClientHelper {
    private static final HashMap<Integer, Ticker> ENTITIES_TO_RENDER_GLOWING = new HashMap<>();
    private static final HashMap<BlockPos, Ticker> BLOCKS_TO_OUTLINE = new HashMap<>();



    public static void tickClientGlowingEntities() {
        Iterator<Integer> iterator = ENTITIES_TO_RENDER_GLOWING.keySet().iterator();
        try {
            while(iterator.hasNext()) {
                int entityId = iterator.next();
                Ticker ticker = ENTITIES_TO_RENDER_GLOWING.get(entityId);
                if (!ticker.tick()) {
                    iterator.remove();
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public static void tickOutlinedBlocks() {
        Iterator<BlockPos> iterator = BLOCKS_TO_OUTLINE.keySet().iterator();
        try {
            while(iterator.hasNext()) {
                BlockPos entityId = iterator.next();
                Ticker ticker = BLOCKS_TO_OUTLINE.get(entityId);
                if (!ticker.tick()) {
                    iterator.remove();
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public static Set<Integer> getEntitiesToRenderGlowing() {
        return ENTITIES_TO_RENDER_GLOWING.keySet();
    }

    public static void addEntity(int id, int ticks) {
        if (id != -1) ENTITIES_TO_RENDER_GLOWING.put(id, new Ticker(ticks));
    }

    public static void addBlockPos(BlockPos pos, int ticks) {
        BLOCKS_TO_OUTLINE.put(pos, new Ticker(ticks));
    }

    public static void renderOutlinedBlocks(PoseStack stack) {
        if (stack != null && !BLOCKS_TO_OUTLINE.isEmpty()) {
            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
            RenderSystem.disableDepthTest();
            RenderSystem.disableCull();
            Matrix4fStack posestack = RenderSystem.getModelViewStack();
            posestack.pushMatrix();
            posestack.mul(stack.last().pose());
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
            RenderSystem.lineWidth(5f);
            for (BlockPos pos : BLOCKS_TO_OUTLINE.keySet()) {
                Vector3f cameraPos = cam.getPosition().toVector3f();
                float posx = pos.getX() - cameraPos.x();
                float posy = pos.getY() - cameraPos.y();
                float posz = pos.getZ() - cameraPos.z();
                renderBlock(bufferBuilder, posx, posy, posz);
            }
            MeshData data = bufferBuilder.build();
            if (data != null) {
                BufferUploader.drawWithShader(data);
            }
            posestack.popMatrix();
            RenderSystem.applyModelViewMatrix();
        }
    }

    private static void renderBlock(BufferBuilder builder, float x, float y, float z) {
        int [][] offsets = {
                {0, 0, 0}, {0, 1, 0}, {0, 0, 0}, {1, 0, 0},
                {0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1},
                {0, 1, 0}, {1, 1, 0}, {1, 1, 1}, {1, 1, 0},
                {1, 1, 1}, {0, 1, 1}, {1, 1, 1}, {1, 0, 1},
                {1, 0, 1}, {0, 0, 1}, {1, 0, 1}, {1, 0, 0},
                {0, 0, 1}, {0, 1, 1}, {1, 0, 0}, {1, 1, 0}
        };

        int [][] normals = {
                {0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0},
                {0, 0, 1}, {0, 0, -1}, {0, 0, 1}, {0, 0, -1},
                {1, 0, 0}, {-1, 0, 0}, {0, 0, -1}, {0, 0, 1},
                {-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0},
                {-1, 0, 0}, {1, 0, 0}, {0, 0, -1}, {0, 0, 1},
                {0, 1, 0}, {0, -1, 0}, {0, 1, 0}, {0, -1, 0}
        };

        for (int i = 0; i < offsets.length; i++) {
            builder.addVertex(x + offsets[i][0], y + offsets[i][1], z + offsets[i][2])
                    .setColor(41, 223, 235, 255)
                    .setNormal(normals[i][0], normals[i][1], normals[i][2]);
        }
    }
}
