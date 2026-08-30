package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;

import io.github.vampirestudios.vampirelib.utils.EntitySpawnImpl;

/**
 * @author Valoeghese
 */
@Mixin(BaseSpawner.class)
public class MixinMobSpawnerLogic {
	@Redirect(
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/server/level/ServerLevel;tryAddFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)Z"),
			method = "serverTick"
	)
	private boolean entitySpawnEventSpawner(ServerLevel self, Entity entity) {
		return EntitySpawnImpl.spawnEntityZ(self, entity);
	}
}
