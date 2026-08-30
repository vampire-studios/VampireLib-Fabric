package io.github.vampirestudios.vampirelib.mixins.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;

@Mixin(ServerboundSetStructureBlockPacket.class)
public abstract class UpdateStructureBlockC2SPacketMixin {

	@ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 48))
	public int readNbt(int old) {
		return 4096;
	}

}
