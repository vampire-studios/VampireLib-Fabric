/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Team Abnormals
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package team.abnormals.abnormalib.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.client.render.entity.model.LargeChestEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import team.abnormals.abnormalib.blocks.entity.ChestBaseBlockEntity;

public class ChestBaseBlockEntityRenderer extends BlockEntityRenderer<ChestBaseBlockEntity> {

    private final ChestEntityModel modelSingleChest = new ChestEntityModel();
    private final ChestEntityModel modelDoubleChest = new LargeChestEntityModel();

    @Override
    public void render(ChestBaseBlockEntity chestBaseBlockEntity, double var2, double var4, double var6, float var8, int var9) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        BlockState blockState = chestBaseBlockEntity.hasWorld() ? chestBaseBlockEntity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        if (chestType != ChestType.LEFT) {
            boolean var12 = chestType != ChestType.SINGLE;
            ChestEntityModel chestEntityModel = this.getTexture(chestBaseBlockEntity, var9, var12);
            if (var9 >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(var12 ? 8.0F : 4.0F, 4.0F, 1.0F);
                GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            } else {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translatef((float) var2, (float) var4 + 1.0F, (float) var6 + 1.0F);
            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            float rotation = blockState.get(ChestBlock.FACING).asRotation();
            if ((double) Math.abs(rotation) > 1.0E-5D) {
                GlStateManager.translatef(0.5F, 0.5F, 0.5F);
                GlStateManager.rotatef(rotation, 0.0F, 1.0F, 0.0F);
                GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            }

            this.animate(chestBaseBlockEntity, var8, chestEntityModel);
            chestEntityModel.method_2799();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (var9 >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }

        }
    }

    private ChestEntityModel getTexture(ChestBaseBlockEntity var1, int var2, boolean var3) {
        Identifier texture;
        if (var2 >= 0) {
            texture = DESTROY_STAGE_TEXTURES[var2];
        } else {
            texture = var3 ? var1.baseBlock.getDoubleModelTexture() : var1.baseBlock.getModelTexture();
        }
        this.bindTexture(texture);
        return var3 ? this.modelDoubleChest : this.modelSingleChest;
    }

    private void animate(ChestBaseBlockEntity chestBaseBlockEntity, float var2, ChestEntityModel chestEntityModel) {
        float animationProgress = ((ChestAnimationProgress) chestBaseBlockEntity).getAnimationProgress(var2);
        animationProgress = 1.0F - animationProgress;
        animationProgress = 1.0F - animationProgress * animationProgress * animationProgress;
        chestEntityModel.method_2798().pitch = -(animationProgress * 1.5707964F);
    }
}