/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package io.github.vampirestudios.vampirelib.api;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeDictionary {
	private static final boolean DEBUG = false;
	private static final Logger LOGGER = LoggerFactory.getLogger(BiomeDictionary.class);

	public static final class Type {
		private static final Map<String, Type> byName = new TreeMap<>();
		private static final Collection<Type> allTypes = Collections.unmodifiableCollection(byName.values());

		/*Temperature-based tags. Specifying neither implies a biome is temperate*/
		public static final Type HOT = new Type("HOT");
		public static final Type COLD = new Type("COLD");

		//Tags specifying the amount of vegetation a biome has. Specifying neither implies a biome to have moderate amounts*/
		public static final Type SPARSE = new Type("SPARSE");
		public static final Type DENSE = new Type("DENSE");

		//Tags specifying how moist a biome is. Specifying neither implies the biome as having moderate humidity*/
		public static final Type WET = new Type("WET");
		public static final Type DRY = new Type("DRY");

		/*Tree-based tags, SAVANNA refers to dry, desert-like trees (Such as Acacia), CONIFEROUS refers to snowy trees (Such as Spruce) and JUNGLE refers to jungle trees.
		 * Specifying no tag implies a biome has temperate trees (Such as Oak)*/
		public static final Type SAVANNA = new Type("SAVANNA");
		public static final Type CONIFEROUS = new Type("CONIFEROUS");
		public static final Type JUNGLE = new Type("JUNGLE");

		/*Tags specifying the nature of a biome*/
		public static final Type SPOOKY = new Type("SPOOKY");
		public static final Type DEAD = new Type("DEAD");
		public static final Type LUSH = new Type("LUSH");
		public static final Type MUSHROOM = new Type("MUSHROOM");
		public static final Type MAGICAL = new Type("MAGICAL");
		public static final Type RARE = new Type("RARE");
		public static final Type PLATEAU = new Type("PLATEAU");
		public static final Type MODIFIED = new Type("MODIFIED");

		public static final Type OCEAN = new Type("OCEAN");
		public static final Type RIVER = new Type("RIVER");
		/**
		 * A general tag for all water-based biomes. Shown as present if OCEAN or RIVER are.
		 **/
		public static final Type WATER = new Type("WATER", OCEAN, RIVER);

		/*Generic types which a biome can be*/
		public static final Type MESA = new Type("MESA");
		public static final Type FOREST = new Type("FOREST");
		public static final Type PLAINS = new Type("PLAINS");
		public static final Type HILLS = new Type("HILLS");
		public static final Type SWAMP = new Type("SWAMP");
		public static final Type SANDY = new Type("SANDY");
		public static final Type SNOWY = new Type("SNOWY");
		public static final Type WASTELAND = new Type("WASTELAND");
		public static final Type BEACH = new Type("BEACH");
		public static final Type VOID = new Type("VOID");
		public static final Type UNDERGROUND = new Type("UNDERGROUND");

		/*Mountain related tags*/
		public static final Type PEAK = new Type("PEAK");
		public static final Type SLOPE = new Type("SLOPE");
		public static final Type MOUNTAIN = new Type("MOUNTAIN", PEAK, SLOPE);

		/*Tags specifying the dimension a biome generates in. Specifying none implies a biome that generates in a modded dimension*/
		public static final Type OVERWORLD = new Type("OVERWORLD");
		public static final Type NETHER = new Type("NETHER");
		public static final Type END = new Type("END");

		private final String name;
		private final List<Type> subTypes;
		private final Set<ResourceKey<Biome>> biomes = new HashSet<>();
		private final Set<ResourceKey<Biome>> biomesUn = Collections.unmodifiableSet(biomes);

		private Type(String name, Type... subTypes) {
			this.name = name;
			this.subTypes = ImmutableList.copyOf(subTypes);

			byName.put(name, this);
		}

		/**
		 * Gets the name for this type.
		 */
		public String getName() {
			return name;
		}

		public String toString() {
			return name;
		}

		/**
		 * Retrieves a Type instance by name,
		 * if one does not exist already it creates one.
		 * This can be used as intermediate measure for modders to
		 * add their own Biome types.
		 * <p>
		 * There are <i>no</i> naming conventions besides:
		 * <ul><li><b>Must</b> be all upper case (enforced by name.toUpper())</li>
		 * <li><b>No</b> Special characters. {Unenforced, just don't be a pain, if it becomes a issue I WILL
		 * make this RTE with no worry about backwards compatibility}</li></ul>
		 * <p>
		 * Note: For performance sake, the return value of this function SHOULD be cached.
		 * Two calls with the same name SHOULD return the same value.
		 *
		 * @param name The name of this Type
		 * @return An instance of Type for this name.
		 */
		public static Type getType(String name, Type... subTypes) {
			name = name.toUpperCase();
			Type t = byName.get(name);
			if (t == null) {
				t = new Type(name, subTypes);
			}
			return t;
		}

		/**
		 * Checks if a type instance exists for a given name. Does not have any side effects if a type does not already exist.
		 * This can be used for checking if a user-defined type is valid, for example, in a codec which accepts biome dictionary names.
		 *
		 * @param name The name.
		 * @return {@code true} if a type exists with this name.
		 * @see #getType(String, Type...) #getType for type naming conventions.
		 */
		public static boolean hasType(String name) {
			return byName.containsKey(name.toUpperCase());
		}

		/**
		 * @return An unmodifiable collection of all current biome types.
		 */
		public static Collection<Type> getAll() {
			return allTypes;
		}

		@Nullable
		public static Type fromVanilla(Biome.BiomeCategory category) {
			if (category == Biome.BiomeCategory.NONE)
				return null;
			if (category == Biome.BiomeCategory.THEEND)
				return VOID;
			return getType(category.name());
		}
	}

	private static final Map<ResourceKey<Biome>, BiomeInfo> biomeInfoMap = new HashMap<>();

	private static class BiomeInfo {
		private final Set<Type> types = new HashSet<>();
		private final Set<Type> typesUn = Collections.unmodifiableSet(this.types);
	}

	public static void init() {}

	static {
		registerVanillaBiomes();
	}

	/**
	 * Adds the given types to the biome.
	 */
	public static void addTypes(ResourceKey<Biome> biome, Type... types) {
		Collection<Type> supertypes = listSupertypes(types);
		Collections.addAll(supertypes, types);

		for (Type type : supertypes) {
			type.biomes.add(biome);
		}

		BiomeInfo biomeInfo = getBiomeInfo(biome);
		Collections.addAll(biomeInfo.types, types);
		biomeInfo.types.addAll(supertypes);
	}

	/**
	 * Gets the set of biomes that have the given type.
	 */
	public static Set<ResourceKey<Biome>> getBiomes(Type type) {
		return type.biomesUn;
	}

	/**
	 * Gets the set of types that have been added to the given biome.
	 */
	public static Set<Type> getTypes(ResourceKey<Biome> biome) {
		return getBiomeInfo(biome).typesUn;
	}

	/**
	 * Checks if the two given biomes have types in common.
	 *
	 * @return returns true if a common type is found, false otherwise
	 */
	public static boolean areSimilar(ResourceKey<Biome> biomeA, ResourceKey<Biome> biomeB) {
		Set<Type> typesA = getTypes(biomeA);
		Set<Type> typesB = getTypes(biomeB);
		return typesA.stream().anyMatch(typesB::contains);
	}

	/**
	 * Checks if the given type has been added to the given biome.
	 */
	public static boolean hasType(ResourceKey<Biome> biome, Type type) {
		return getTypes(biome).contains(type);
	}

	/**
	 * Checks if any type has been added to the given biome.
	 */
	public static boolean hasAnyType(ResourceKey<Biome> biome) {
		return !getBiomeInfo(biome).types.isEmpty();
	}

	//Internal implementation
	private static BiomeInfo getBiomeInfo(ResourceKey<Biome> biome) {
		return biomeInfoMap.computeIfAbsent(biome, k -> new BiomeInfo());
	}

	private static Collection<Type> listSupertypes(Type... types) {
		Set<Type> supertypes = new HashSet<Type>();
		Deque<Type> next = new ArrayDeque<Type>();
		Collections.addAll(next, types);

		while (!next.isEmpty()) {
			Type type = next.remove();

			for (Type sType : Type.byName.values()) {
				if (sType.subTypes.contains(type) && supertypes.add(sType))
					next.add(sType);
			}
		}

		return supertypes;
	}

	private static void registerVanillaBiomes() {
		addTypes(Biomes.OCEAN, Type.OCEAN, Type.OVERWORLD);
		addTypes(Biomes.PLAINS, Type.PLAINS, Type.OVERWORLD);
		addTypes(Biomes.DESERT, Type.HOT, Type.DRY, Type.SANDY, Type.OVERWORLD);
		addTypes(Biomes.WINDSWEPT_HILLS, Type.HILLS, Type.OVERWORLD);
		addTypes(Biomes.FOREST, Type.FOREST, Type.OVERWORLD);
		addTypes(Biomes.TAIGA, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.OVERWORLD);
		addTypes(Biomes.SWAMP, Type.WET, Type.SWAMP, Type.OVERWORLD);
		addTypes(Biomes.RIVER, Type.RIVER, Type.OVERWORLD);
		addTypes(Biomes.NETHER_WASTES, Type.HOT, Type.DRY, Type.NETHER);
		addTypes(Biomes.THE_END, Type.COLD, Type.DRY, Type.END);
		addTypes(Biomes.FROZEN_OCEAN, Type.COLD, Type.OCEAN, Type.SNOWY, Type.OVERWORLD);
		addTypes(Biomes.FROZEN_RIVER, Type.COLD, Type.RIVER, Type.SNOWY, Type.OVERWORLD);
		addTypes(Biomes.SNOWY_PLAINS, Type.COLD, Type.SNOWY, Type.WASTELAND, Type.OVERWORLD);
		addTypes(Biomes.MUSHROOM_FIELDS, Type.MUSHROOM, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.BEACH, Type.BEACH, Type.OVERWORLD);
		addTypes(Biomes.JUNGLE, Type.HOT, Type.WET, Type.DENSE, Type.JUNGLE, Type.OVERWORLD);
		addTypes(Biomes.SPARSE_JUNGLE, Type.HOT, Type.WET, Type.JUNGLE, Type.FOREST, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.DEEP_OCEAN, Type.OCEAN, Type.OVERWORLD);
		addTypes(Biomes.STONY_SHORE, Type.BEACH, Type.OVERWORLD);
		addTypes(Biomes.SNOWY_BEACH, Type.COLD, Type.BEACH, Type.SNOWY, Type.OVERWORLD);
		addTypes(Biomes.BIRCH_FOREST, Type.FOREST, Type.OVERWORLD);
		addTypes(Biomes.DARK_FOREST, Type.SPOOKY, Type.DENSE, Type.FOREST, Type.OVERWORLD);
		addTypes(Biomes.SNOWY_TAIGA, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.SNOWY, Type.OVERWORLD);
		addTypes(Biomes.OLD_GROWTH_PINE_TAIGA, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.OVERWORLD);
		addTypes(Biomes.WINDSWEPT_FOREST, Type.HILLS, Type.FOREST, Type.SPARSE, Type.OVERWORLD);
		addTypes(Biomes.SAVANNA, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.OVERWORLD);
		addTypes(Biomes.SAVANNA_PLATEAU, Type.HOT, Type.SAVANNA, Type.PLAINS, Type.SPARSE, Type.RARE, Type.OVERWORLD, Type.SLOPE, Type.PLATEAU);
		addTypes(Biomes.BADLANDS, Type.MESA, Type.SANDY, Type.DRY, Type.OVERWORLD);
		addTypes(Biomes.WOODED_BADLANDS, Type.MESA, Type.SANDY, Type.DRY, Type.SPARSE, Type.OVERWORLD, Type.SLOPE, Type.PLATEAU);
		addTypes(Biomes.MEADOW, Type.PLAINS, Type.PLATEAU, Type.SLOPE, Type.OVERWORLD);
		addTypes(Biomes.GROVE, Type.COLD, Type.CONIFEROUS, Type.FOREST, Type.SNOWY, Type.SLOPE, Type.OVERWORLD);
		addTypes(Biomes.SNOWY_SLOPES, Type.COLD, Type.SPARSE, Type.SNOWY, Type.SLOPE, Type.OVERWORLD);
		addTypes(Biomes.JAGGED_PEAKS, Type.COLD, Type.SPARSE, Type.SNOWY, Type.PEAK, Type.OVERWORLD);
		addTypes(Biomes.FROZEN_PEAKS, Type.COLD, Type.SPARSE, Type.SNOWY, Type.PEAK, Type.OVERWORLD);
		addTypes(Biomes.STONY_PEAKS, Type.HOT, Type.PEAK, Type.OVERWORLD);
		addTypes(Biomes.SMALL_END_ISLANDS, Type.END);
		addTypes(Biomes.END_MIDLANDS, Type.END);
		addTypes(Biomes.END_HIGHLANDS, Type.END);
		addTypes(Biomes.END_BARRENS, Type.END);
		addTypes(Biomes.WARM_OCEAN, Type.OCEAN, Type.HOT, Type.OVERWORLD);
		addTypes(Biomes.LUKEWARM_OCEAN, Type.OCEAN, Type.OVERWORLD);
		addTypes(Biomes.COLD_OCEAN, Type.OCEAN, Type.COLD, Type.OVERWORLD);
		addTypes(Biomes.DEEP_LUKEWARM_OCEAN, Type.OCEAN, Type.OVERWORLD);
		addTypes(Biomes.DEEP_COLD_OCEAN, Type.OCEAN, Type.COLD, Type.OVERWORLD);
		addTypes(Biomes.DEEP_FROZEN_OCEAN, Type.OCEAN, Type.COLD, Type.OVERWORLD);
		addTypes(Biomes.THE_VOID, Type.VOID);
		addTypes(Biomes.SUNFLOWER_PLAINS, Type.PLAINS, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.WINDSWEPT_GRAVELLY_HILLS, Type.HILLS, Type.SPARSE, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.FLOWER_FOREST, Type.FOREST, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.ICE_SPIKES, Type.COLD, Type.SNOWY, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.OLD_GROWTH_BIRCH_FOREST, Type.FOREST, Type.DENSE, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.OLD_GROWTH_SPRUCE_TAIGA, Type.DENSE, Type.FOREST, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.WINDSWEPT_SAVANNA, Type.HOT, Type.DRY, Type.SPARSE, Type.SAVANNA, Type.HILLS, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.ERODED_BADLANDS, Type.MESA, Type.HOT, Type.DRY, Type.SPARSE, Type.RARE, Type.OVERWORLD);
		addTypes(Biomes.BAMBOO_JUNGLE, Type.HOT, Type.WET, Type.RARE, Type.JUNGLE, Type.OVERWORLD);
		addTypes(Biomes.LUSH_CAVES, Type.UNDERGROUND, Type.LUSH, Type.WET, Type.OVERWORLD);
		addTypes(Biomes.DRIPSTONE_CAVES, Type.UNDERGROUND, Type.SPARSE, Type.OVERWORLD);
		addTypes(Biomes.SOUL_SAND_VALLEY, Type.HOT, Type.DRY, Type.NETHER);
		addTypes(Biomes.CRIMSON_FOREST, Type.HOT, Type.DRY, Type.NETHER, Type.FOREST);
		addTypes(Biomes.WARPED_FOREST, Type.HOT, Type.DRY, Type.NETHER, Type.FOREST);
		addTypes(Biomes.BASALT_DELTAS, Type.HOT, Type.DRY, Type.NETHER);

		if (DEBUG) {
			StringBuilder buf = new StringBuilder();
			buf.append("BiomeDictionary:\n");
			Type.byName.forEach((name, type) ->
				buf.append("    ").append(type.name).append(": ")
					.append(type.biomes.stream()
						.map(ResourceKey::location)
						.sorted(ResourceLocation::compareTo)
						.map(Object::toString)
						.collect(Collectors.joining(", "))
					)
					.append('\n')
			);

			boolean missing = false;
			List<ResourceKey<Biome>> all = StreamSupport.stream(BuiltinRegistries.BIOME.spliterator(), false)
				.map(b -> ResourceKey.create(Registry.BIOME_REGISTRY, BuiltinRegistries.BIOME.getKey(b)))
				.sorted().toList();

			for (ResourceKey<Biome> key : all) {
				if (!biomeInfoMap.containsKey(key)) {
					if (!missing) {
						buf.append("Missing:\n");
						missing = true;
					}
					buf.append("    ").append(key.location()).append('\n');
				}
			}
			LOGGER.debug(buf.toString());
		}
	}
}
