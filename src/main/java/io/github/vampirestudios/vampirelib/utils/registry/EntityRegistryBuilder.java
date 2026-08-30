package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.Objects;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;

public final class EntityRegistryBuilder<E extends Entity> {

	private final Identifier name;

	private EntityType.EntityFactory<E> entityFactory;
	private MobCategory category = MobCategory.MISC;

	private int trackingDistance;
	private int updateIntervalTicks;
	private boolean alwaysUpdateVelocity;

	private int primaryColor;
	private int secondaryColor;
	private boolean hasEgg;
	private boolean fireImmune;

	private EntityDimensions dimensions;

	private EntityRegistryBuilder(Identifier name) {
		this.name = Objects.requireNonNull(name, "name");
	}

	public static <E extends Entity> EntityRegistryBuilder<E> createBuilder(Identifier name) {
		return new EntityRegistryBuilder<>(name);
	}

	public EntityRegistryBuilder<E> entity(EntityType.EntityFactory<E> entityFactory) {
		this.entityFactory = Objects.requireNonNull(entityFactory, "entityFactory");
		return this;
	}

	public EntityRegistryBuilder<E> category(MobCategory category) {
		this.category = Objects.requireNonNull(category, "category");
		return this;
	}

	/**
	 * @deprecated Use {@link #category(MobCategory)}.
	 */
	@Deprecated
	public EntityRegistryBuilder<E> group(MobCategory category) {
		return category(category);
	}

	/**
	 * Sets the entity tracking distance in blocks.
	 */
	public EntityRegistryBuilder<E> trackingDistance(int trackingDistance) {
		if (trackingDistance < 1) {
			throw new IllegalArgumentException("Tracking distance must be greater than 0");
		}

		this.trackingDistance = trackingDistance;
		return this;
	}

	public EntityRegistryBuilder<E> updateIntervalTicks(int updateIntervalTicks) {
		if (updateIntervalTicks < 1) {
			throw new IllegalArgumentException("Update interval must be greater than 0");
		}

		this.updateIntervalTicks = updateIntervalTicks;
		return this;
	}

	public EntityRegistryBuilder<E> alwaysUpdateVelocity(boolean alwaysUpdateVelocity) {
		this.alwaysUpdateVelocity = alwaysUpdateVelocity;
		return this;
	}

	/**
	 * @deprecated Configure tracking options individually instead.
	 */
	@Deprecated
	public EntityRegistryBuilder<E> tracker(
		int trackingDistance,
		int updateIntervalTicks,
		boolean alwaysUpdateVelocity
	) {
		return trackingDistance(trackingDistance)
			.updateIntervalTicks(updateIntervalTicks)
			.alwaysUpdateVelocity(alwaysUpdateVelocity);
	}

	public EntityRegistryBuilder<E> egg(int primaryColor, int secondaryColor) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.hasEgg = true;
		return this;
	}

	public EntityRegistryBuilder<E> hasEgg(boolean hasEgg) {
		this.hasEgg = hasEgg;
		return this;
	}

	public EntityRegistryBuilder<E> makeFireImmune() {
		this.fireImmune = true;
		return this;
	}

	public EntityRegistryBuilder<E> dimensions(EntityDimensions dimensions) {
		this.dimensions = Objects.requireNonNull(dimensions, "dimensions");
		return this;
	}

	public EntityRegistryBuilder<E> dimensions(float width, float height) {
		this.dimensions = EntityDimensions.scalable(width, height);
		return this;
	}

	public EntityType<E> build() {
		Objects.requireNonNull(entityFactory, "Entity factory must be configured before building");
		Objects.requireNonNull(dimensions, "Entity dimensions must be configured before building");

		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, name);

		EntityType.Builder<E> entityBuilder = EntityType.Builder.of(entityFactory, category)
			.sized(dimensions.width(), dimensions.height())
			.eyeHeight(dimensions.eyeHeight());

		if (fireImmune) {
			entityBuilder.fireImmune();
		}

		if (trackingDistance > 0) {
			entityBuilder.clientTrackingRange(blocksToChunks(trackingDistance));
		}

		if (updateIntervalTicks > 0) {
			entityBuilder.updateInterval(updateIntervalTicks);
		}

		if (alwaysUpdateVelocity) {
			fabricBuilder(entityBuilder).alwaysUpdateVelocity(true);
		}

		EntityType<E> entityType = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			name,
			entityBuilder.build(key)
		);

		if (hasEgg) {
			registerSpawnEgg(entityType);
		}

		return entityType;
	}

	@SuppressWarnings("unchecked")
	private static <E extends Entity> FabricEntityType.Builder<E> fabricBuilder(EntityType.Builder<E> builder) {
		return builder;
	}

	@SuppressWarnings("unchecked")
	private void registerSpawnEgg(EntityType<E> entityType) {
		RegistryHelper.createRegistryHelper(name.getNamespace()).items().registerSpawnEgg(
			name.getPath(),
			(EntityType<? extends Mob>) entityType,
			primaryColor,
			secondaryColor
		);
	}

	private static int blocksToChunks(int blocks) {
		return (blocks + 15) / 16;
	}

}
