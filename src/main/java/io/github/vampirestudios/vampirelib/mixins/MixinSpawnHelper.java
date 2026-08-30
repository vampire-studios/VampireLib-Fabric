package io.github.vampirestudios.vampirelib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;

import io.github.vampirestudios.vampirelib.utils.EntitySpawnImpl;

/**
 * @author Valoeghese
 */
@Mixin(NaturalSpawner.class)
public class MixinSpawnHelper {
	@Redirect(
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"),
			method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V"
	)
	private static void entitySpawnEventNatural(ServerLevel serverLevel, Entity entity) {
		EntitySpawnImpl.spawnEntityV(serverLevel, entity);
	}

	@Redirect(
			at = @At(value = "INVOKE",
					 target = "Lnet/minecraft/world/level/ServerLevelAccessor;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"),
			method = "spawnMobsForChunkGeneration"
	)
	private static void entitySpawnEventChunk(ServerLevelAccessor serverLevelAccessor, Entity entity) {
		EntitySpawnImpl.spawnEntityV(serverLevelAccessor, entity);
	}

}
