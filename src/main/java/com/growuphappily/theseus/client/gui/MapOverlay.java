package com.growuphappily.theseus.client.gui;

import com.growuphappily.theseus.Theseus;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Theseus.MOD_ID)
public class MapOverlay extends AbstractGui {
    public int centerX = 200;
    public int centerY = 200;
    public MatrixStack stack;
    public void render(){
        int viewDistance = Minecraft.getInstance().options.renderDistance;
        int currentChunkX = Minecraft.getInstance().player.xChunk;
        int currentChunkZ = Minecraft.getInstance().player.zChunk;
        stack.pushPose();
        //stack.scale(0.2f, 0.2f, 0.2f);
        stack.translate(centerX, centerY, 0);
        for (int i = - viewDistance; i < viewDistance; i++) {
            for (int j = - viewDistance; j < viewDistance; j++) {
                Chunk chunk = Minecraft.getInstance().level.getChunk(currentChunkX + i, currentChunkZ + j);
                if(!chunk.isEmpty()){
                    renderChunkCentered(chunk, i * 16, j * 16);
                }
            }
        }
        stack.popPose();
    }

    public void renderChunkCentered(Chunk chunk, int x, int y){
        //Chunk chunk = (Chunk) Minecraft.getInstance().level.getChunk(Minecraft.getInstance().player.blockPosition());
        BlockPos chunkMin = chunk.getPos().getWorldPosition();
        int seaLevel = chunk.getLevel().getSeaLevel();
        DynamicTexture texture = new DynamicTexture(16, 16, true);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int height = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j);
                BlockPos currentPos = new BlockPos(i + chunkMin.getX(), height,j + chunkMin.getZ());
                BlockState state = chunk.getBlockState(currentPos);
                int color = state.getMaterial().getColor().calculateRGBColor(0);
                if(!state.getFluidState().isEmpty()){
                    color = state.getMaterial().getColor().calculateRGBColor(Math.abs(seaLevel - height) );
                }else {
                    int upHeight;
                    int downHeight;
                    if(j == 0){
                        upHeight = chunk.getLevel().getChunk(chunk.getPos().x, chunk.getPos().z - 1).getHeight(Heightmap.Type.WORLD_SURFACE, i, 15);
                    }else{
                        upHeight = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j - 1);
                    }
                    if(j == 15){
                        downHeight = chunk.getLevel().getChunk(chunk.getPos().x, chunk.getPos().z + 1).getHeight(Heightmap.Type.WORLD_SURFACE, i, 0);
                    }else {
                        downHeight = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, i, j + 1);
                    }


                    if (height < upHeight || height > downHeight) {
                        color = state.getMaterial().getColor().calculateRGBColor(2);
                    }
                    if (height > upHeight || height < downHeight) {
                        color = state.getMaterial().getColor().calculateRGBColor(3);
                    }
                }
                texture.getPixels().setPixelRGBA(i, j, 0xFF000000 + color);

            }
        }
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        texture.upload();
        RenderSystem.enableBlend();
        blit(stack, x - 8, y - 8 ,0f, 0f, 16, 16, 16, 16);
        RenderSystem.disableBlend();
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        MapOverlay overlay = new MapOverlay();
        overlay.stack = event.getMatrixStack();
        overlay.render();
    }
}
