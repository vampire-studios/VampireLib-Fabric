package team.abnormals.abnormalib.mixins.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import team.abnormals.abnormalib.entities.EntityRegistry;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
	@Shadow private ClientWorld world;
	private static double abnormaLib$x;
    private static double abnormaLib$y;
    private static double abnormaLib$z;
    private static EntityType abnormaLib$entityType;
    private static int abnormaLib$entityData;

	@Inject(method = "onEntitySpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/network/packet/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo callbackInfo, double x, double y, double z, EntityType entityType) {
	    abnormaLib$x = x;
	    abnormaLib$y = y;
	    abnormaLib$z = z;
	    abnormaLib$entityType = entityType;
	    abnormaLib$entityData = packet.getEntityData();
    }

    @ModifyVariable(
        method = "onEntitySpawn",
        at = @At(value = "JUMP", opcode = Opcodes.IFNULL, ordinal = 0, shift = At.Shift.BEFORE),
		index = 8
    )
    private Entity setSpawnEntity(Entity old) {
		if(old == null)
        	return EntityRegistry.construct(abnormaLib$entityType, world, abnormaLib$x, abnormaLib$y, abnormaLib$z, abnormaLib$entityData);
		return old;
    }
}
