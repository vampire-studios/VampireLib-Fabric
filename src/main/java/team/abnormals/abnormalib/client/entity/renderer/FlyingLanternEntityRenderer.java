package team.abnormals.abnormalib.client.entity.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import team.abnormals.abnormalib.entity.FlyingLanternEntity;

public class FlyingLanternEntityRenderer extends EntityRenderer<FlyingLanternEntity> {
    public FlyingLanternEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1);
    }

    @Override
    public void render(FlyingLanternEntity flyingLanternEntity, double double_1, double double_2, double double_3, float float_1, float float_2) {
        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) double_1, (float) double_2 + 0.5F, (float) double_3);

        this.bindEntityTexture(flyingLanternEntity);
        GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
        blockRenderManager.renderDynamic(flyingLanternEntity.getBlockState(), 1.0F);
        GlStateManager.translatef(0.0F, 0.0F, 1.0F);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getOutlineColor(flyingLanternEntity));
            blockRenderManager.renderDynamic(flyingLanternEntity.getBlockState(), 1.0F);
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.render(flyingLanternEntity, double_1, double_2, double_3, float_1, float_2);
    }

    @Override
    protected Identifier getTexture(FlyingLanternEntity var1) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEX;
    }
}
