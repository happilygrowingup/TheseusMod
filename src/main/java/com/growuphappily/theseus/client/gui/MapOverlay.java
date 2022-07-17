package com.growuphappily.theseus.client.gui;

import com.growuphappily.theseus.Theseus;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class MapOverlay extends AbstractGui {

    public void render(MatrixStack stack){
        Chunk chunk = (Chunk) Minecraft.getInstance().level.getChunk(Minecraft.getInstance().player.blockPosition());
        BlockPos chunkMin = chunk.getPos().getWorldPosition();
        DynamicTexture texture = new DynamicTexture(16, 16, true);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int height = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j);
                texture.getPixels().setPixelRGBA(i, j, 0xFF000000 + chunk.getBlockState(new BlockPos(i + chunkMin.getX(), height,j + chunkMin.getZ())).getMaterial().getColor().col);
            }
        }
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        texture.upload();
        stack.pushPose();
        stack.mulPose(Vector3f.YP.rotation(Minecraft.getInstance().player.getYHeadRot()));
        RenderSystem.enableBlend();
        blit(stack, 20, 20 ,0f, 0f, 16, 16, 16, 16);
        RenderSystem.disableBlend();
        stack.popPose();
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        new MapOverlay().render(event.getMatrixStack());
    }
}
