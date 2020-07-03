/*
 * MIT License
 *
 * Copyright (c) 2020 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.utils.registry;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistryBuilder<E extends Entity> {

    private static Identifier name;

    private EntityType.EntityFactory<E> entityFactory;

    private SpawnGroup category;

    private int trackingDistance;
    private int updateIntervalTicks;
    private boolean alwaysUpdateVelocity;

    private int primaryColor;
    private int secondaryColor;
    private boolean hasEgg;

    private EntityDimensions dimensions;

    public static <E extends Entity> EntityRegistryBuilder<E> createBuilder(Identifier nameIn) {
        name = nameIn;
        return new EntityRegistryBuilder<>();
    }

    public EntityRegistryBuilder<E> entity(EntityType.EntityFactory<E> entityFactory) {
        this.entityFactory = entityFactory;
        return this;
    }

    @Deprecated
    public EntityRegistryBuilder<E> category(SpawnGroup category) {
        this.category = category;
        return this;
    }

    public EntityRegistryBuilder<E> group(SpawnGroup category) {
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

    public EntityRegistryBuilder<E> dimensions(EntityDimensions size) {
        this.dimensions = size;
        return this;
    }

    public EntityType<E> build() {
        EntityType<E> entityBuilder = FabricEntityTypeBuilder.create(category, entityFactory).size(dimensions).disableSaving().build();
        EntityType<E> entityType = Registry.register(Registry.ENTITY_TYPE, name, entityBuilder);
        if ((this.alwaysUpdateVelocity)) {
            if (this.updateIntervalTicks != 0 & this.trackingDistance != 0)
                FabricEntityTypeBuilder.create(category, entityFactory).size(dimensions).trackable(trackingDistance, updateIntervalTicks, alwaysUpdateVelocity).disableSaving().build();
        }
        if (hasEgg) {
            RegistryHelper.createRegistryHelper(name.getNamespace()).registerItem(new SpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Settings().group(ItemGroup.MISC)), String.format("%s_spawn_egg", name.getPath()));
        }
        return entityType;
    }

}