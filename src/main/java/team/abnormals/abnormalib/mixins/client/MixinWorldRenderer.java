package team.abnormals.abnormalib.mixins.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlBuffer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
	private Vector4f abnormaLib$originColor = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
	private Vector4f abnormaLib$targetDelta = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
	private float abnormaLib$progress = 0.0F;

	@Shadow private ClientWorld world;

	@Shadow @Final private MinecraftClient client;

	@Shadow private boolean vertexBufferObjectsEnabled;

	@Shadow private GlBuffer field_4087;

	@Shadow private int field_4117;

	@Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderEndSky()V", shift = At.Shift.AFTER))
	private void onEndSkyRendered(float delta, CallbackInfo callbackInfo) {
		Vec3d skyColor = client.world.getSkyColor(client.gameRenderer.getCamera().getBlockPos(), 0.0F);
		if (skyColor.x != abnormaLib$originColor.getX() + abnormaLib$targetDelta.getX() || skyColor.y != abnormaLib$originColor.getY() + abnormaLib$targetDelta.getY() || skyColor.z != abnormaLib$originColor.getZ() + abnormaLib$targetDelta.getZ()) {
			// Set origin color to current color
			abnormaLib$originColor.set(abnormaLib$originColor.getX() + abnormaLib$targetDelta.getX() * abnormaLib$progress, abnormaLib$originColor.getY() + abnormaLib$targetDelta.getY() * abnormaLib$progress, abnormaLib$originColor.getZ() + abnormaLib$targetDelta.getZ() * abnormaLib$progress, abnormaLib$originColor.getW() + abnormaLib$targetDelta.getW() * abnormaLib$progress);
			abnormaLib$progress = 0.0F;
			if (skyColor.x == 0 && skyColor.y == 0 && skyColor.z == 0) {
				abnormaLib$targetDelta.set(-abnormaLib$originColor.getX(), -abnormaLib$originColor.getY(), -abnormaLib$originColor.getZ(), -abnormaLib$originColor.getW());
			} else {
				abnormaLib$targetDelta.set((float) skyColor.x - abnormaLib$originColor.getX(), (float) skyColor.y - abnormaLib$originColor.getY(), (float) skyColor.z - abnormaLib$originColor.getZ(), 1.0F - abnormaLib$originColor.getW());
			}
		}

		abnormaLib$progress = Math.min(1.0F, abnormaLib$progress + delta * 0.04F);

		if(!MathHelper.equalsApproximate(abnormaLib$originColor.getW() + abnormaLib$targetDelta.getW() * abnormaLib$progress, 0.0F)) {
			GlStateManager.disableTexture();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();
			GlStateManager.color4f(abnormaLib$originColor.getX() + abnormaLib$targetDelta.getX() * abnormaLib$progress, abnormaLib$originColor.getY() + abnormaLib$targetDelta.getY() * abnormaLib$progress, abnormaLib$originColor.getZ() + abnormaLib$targetDelta.getZ() * abnormaLib$progress, abnormaLib$originColor.getW() + abnormaLib$targetDelta.getW() * abnormaLib$progress);
			if(vertexBufferObjectsEnabled) {
				field_4087.bind();
				GlStateManager.enableClientState(32884);
				GlStateManager.vertexPointer(3, 5126, 12, 0);
				field_4087.draw(7);
				GlBuffer.unbind();
				GlStateManager.disableClientState(32884);
			} else {
				GlStateManager.callList(field_4117);
			}
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture();
			GlStateManager.depthMask(true);
			GlStateManager.disableFog();
		}
	}
}
