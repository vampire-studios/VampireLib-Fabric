package team.abnormals.abnormalib.utils.registry;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistryBuilder<E extends Entity> {

    private static Identifier name;

    private EntityType.EntityFactory<E> entityFactory;

    private EntityCategory category;

    private int trackingDistance;
    private int updateIntervalTicks;
    private boolean alwaysUpdateVelocity;

    private int primaryColor;
    private int secondaryColor;
    private boolean hasEgg;

    private EntityDimensions size;

    public static <E extends Entity> EntityRegistryBuilder<E> createBuilder(Identifier nameIn) {
        name = nameIn;
        return new EntityRegistryBuilder<>();
    }

    public EntityRegistryBuilder<E> entity(EntityType.EntityFactory<E> entityFactory) {
        this.entityFactory = entityFactory;
        return this;
    }

    public EntityRegistryBuilder<E> category(EntityCategory category) {
        this.category = category;
        return this;
    }

    public EntityRegistryBuilder<E> tracker(int trackingDistance, int updateIntervalTicks, boolean alwaysUpdateVelocity) {
        this.trackingDistance = trackingDistance;
        this.updateIntervalTicks = updateIntervalTicks;
        this.alwaysUpdateVelocity = alwaysUpdateVelocity;
        return this;
    }

    public EntityRegistryBuilder<E> egg(int primaryColor, int secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        return this;
    }

    public EntityRegistryBuilder<E> hasEgg(boolean hasEgg) {
        this.hasEgg = hasEgg;
        return this;
    }

    public EntityRegistryBuilder<E> size(EntityDimensions size) {
        this.size = size;
        return this;
    }

    public EntityType<E> build() {
        EntityType<E> entityBuilder = FabricEntityTypeBuilder.create(category, entityFactory).size(size).disableSaving().build();
        EntityType<E> entityType = Registry.register(Registry.ENTITY_TYPE, name, entityBuilder);
        if ((this.alwaysUpdateVelocity)) {
            if (this.updateIntervalTicks != 0 & this.trackingDistance != 0)
                FabricEntityTypeBuilder.create(category, entityFactory).size(size).trackable(trackingDistance, updateIntervalTicks, alwaysUpdateVelocity).disableSaving().build();
        }
        if (hasEgg) {
            RegistryUtils.registerItem(new SpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Settings().group(ItemGroup.MISC)), new Identifier(name.getNamespace(), String.format("%s_spawn_egg", name.getPath())));
        }
        return entityType;
    }

}