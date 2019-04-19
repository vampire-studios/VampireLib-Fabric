package team.hollow.abnormalib.utils.registry;

import net.fabricmc.fabric.api.entity.EntityTrackingRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistryBuilder<E extends Entity> {

    private static Identifier name;

    private EntityType.EntityFactory<E> class_4049;

    private EntityCategory category;

    private int trackingDistance;
    private int updateIntervalTicks;
    private boolean alwaysUpdateVelocity;

    private int primaryColor;
    private int secondaryColor;

    private EntitySize size;

    public static <E extends Entity> EntityRegistryBuilder<E> createBuilder(Identifier nameIn) {
        name = nameIn;
        return new EntityRegistryBuilder<E>();
    }

    public EntityRegistryBuilder<E> entity(EntityType.EntityFactory<E> class_4049) {
        this.class_4049 = class_4049;
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

    public EntityRegistryBuilder<E> size(EntitySize size) {
        this.size = size;
        return this;
    }

    public EntityType<E> build() {
        EntityType<E> entityBuilder = EntityType.Builder.create(class_4049, category).setSize(size.width, size.height).disableSaving().build(name.getPath());
        EntityType<E> entityType = Registry.register(Registry.ENTITY_TYPE, name, entityBuilder);
        if ((this.alwaysUpdateVelocity)) {
            if (this.updateIntervalTicks != 0 & this.trackingDistance != 0)
                EntityTrackingRegistry.INSTANCE.register(entityType, trackingDistance, updateIntervalTicks, alwaysUpdateVelocity);
        }
        RegistryUtils.registerItem(new SpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Settings().itemGroup(ItemGroup.MISC)), new Identifier(name.getNamespace(), String.format("%s_spawn_egg", name.getPath())));
        return entityType;
    }

}