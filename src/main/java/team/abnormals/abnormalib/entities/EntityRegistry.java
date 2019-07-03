package team.abnormals.abnormalib.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {
	private static Map<Class<? extends EntityType>, EntityConstructor> entityConstructorMap = new HashMap<>();

	public static <T extends Entity> void register(EntityType<T> entityType, EntityConstructor<T> entityConstructor) {
		entityConstructorMap.put(entityType.getClass(), entityConstructor);
	}

	public static Entity construct(EntityType entityType, World world, double x, double y, double z, int data) {
		EntityConstructor entityConstructor = entityConstructorMap.get(entityType.getClass());
		if(entityConstructor == null) return null;
		return entityConstructor.create(world, x, y, z, data);
	}

	public interface EntityConstructor<T extends Entity> {
		T create(World world, double x, double y, double z, int data);
	}
}
