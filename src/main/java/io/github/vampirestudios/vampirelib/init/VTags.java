/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class VTags {

	public static class Blocks {
		public static TagKey<Block> tag(String id) {
			return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("c", id));
		}
	}

	public static class Items {
		public static final TagKey<Item> BUCKETS_WATER = tag("buckets/water");
		public static final TagKey<Item> BUCKETS_LAVA = tag("buckets/lava");
		public static final TagKey<Item> BUCKETS_MILK = tag("buckets/milk");
		public static final TagKey<Item> BUCKETS_POWDER_SNOW = tag("buckets/powder_snow");
		public static final TagKey<Item> BUCKETS = tag("buckets");

		public static TagKey<Item> tag(String id) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", id));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> BOSSES = tag("bosses");

		/**
		 * Mobs that are dragons, draconic, or close enough to being dragons
		 */
		public static final TagKey<EntityType<?>> DRAGONS = tag("dragons");

		public static final TagKey<EntityType<?>> GOLEMS = tag("golems");

		/**
		 * Mobs that are build with blocks and then summoned
		 */
		public static final TagKey<EntityType<?>> BUILDABLE_MOBS = tag("buildable_mobs");

		public static final TagKey<EntityType<?>> BIG_NOSES = tag("big_noses");
		public static final TagKey<EntityType<?>> ILLAGERS = tag("illagers");

		/**
		 * Mobs that are logically composed of an element
		 * If your mob is made of a different element, and you want cross-compatibility between mods, feel free to make a new tag
		 * If your mob is made of a combination of elements, feel free to add all appropriate tags to it
		 */
		public static final TagKey<EntityType<?>> ELEMENTAL = tag("elemental");
		public static final TagKey<EntityType<?>> ELEMENTAL_FIRE = tag("elemental/fire");
		public static final TagKey<EntityType<?>> ELEMENTAL_ICE = tag("elemental/ice");
		public static final TagKey<EntityType<?>> ELEMENTAL_METAL = tag("elemental/metal");

		/**
		 * Attack entities that have logical elemental properties
		 * If your attack entity is made of a different element, and you want cross-compatibility between mods, feel free to make a new tag
		 * If your attack entity is made of a combination of elements, feel free to add all appropriate tags to it
		 */
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS = tag("elemental_attacks");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_ELECTRIC = tag("elemental_attacks/electric");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_FIRE = tag("elemental_attacks/fire");
		public static final TagKey<EntityType<?>> ELEMENTAL_ATTACKS_ICE = tag("elemental_attacks/ice");

		public static final TagKey<EntityType<?>> ARTHROPODS = tag("animals/arthropods");

		public static final TagKey<EntityType<?>> AVIANS = tag("avians");
		public static final TagKey<EntityType<?>> AVIANS_FOWLS = tag("avians/fowls");

		//Mobs which are living in the water
		public static final TagKey<EntityType<?>> AQUATIC = tag("aquatic");
		public static final TagKey<EntityType<?>> FISH = tag("aquatic/fish");
		public static final TagKey<EntityType<?>> CEPHALOPODS = tag("aquatic/cephalopods");
		public static final TagKey<EntityType<?>> GUARDIANS = tag("aquatic/guardians");

		public static final TagKey<EntityType<?>> REPTILES = tag("reptiles");

		public static final TagKey<EntityType<?>> MAMMALS = tag("mammals");
		public static final TagKey<EntityType<?>> MAMMALS_BOVINES = tag("mammals/bovines");
		public static final TagKey<EntityType<?>> MAMMALS_BOVINES_CATTLE = tag("mammals/bovines_cattle");
		public static final TagKey<EntityType<?>> MAMMALS_CAMELIDS = tag("mammals/camelids");
		public static final TagKey<EntityType<?>> MAMMALS_CANIDS = tag("mammals/canids");
		public static final TagKey<EntityType<?>> MAMMALS_CAPRINES = tag("mammals/caprines");
		public static final TagKey<EntityType<?>> MAMMALS_EQUINES = tag("mammals/equines");
		public static final TagKey<EntityType<?>> MAMMALS_FELINES = tag("mammals/felines");
		public static final TagKey<EntityType<?>> MAMMALS_SWINES = tag("mammals/swines");
		public static final TagKey<EntityType<?>> MAMMALS_URSIDS = tag("mammals/ursids");

		public static final TagKey<EntityType<?>> GHOSTS = tag("ghosts");

		public static final TagKey<EntityType<?>> MILKABLE = tag("milkable");
		public static final TagKey<EntityType<?>> MUSHROOM_COWS = tag("mushroom_cows");

		public static final TagKey<EntityType<?>> BLIND_MOBS = tag("blind_mobs");

		public static final TagKey<EntityType<?>> FLYING = tag("flying");
		public static final TagKey<EntityType<?>> LAND = tag("land");

		/**
		 * Mobs that prefer to live in lava
		 */
		public static final TagKey<EntityType<?>> VOLCANIC = tag("volcanic");

		public static final TagKey<EntityType<?>> HELL_MOBS = tag("hell_mobs");

		/**
		 * Mobs that are meant to be antagonistic to the player
		 */
		public static final TagKey<EntityType<?>> ENEMIES = tag("enemies");
		public static final TagKey<EntityType<?>> CREEPERS = tag("creepers");

		public static final TagKey<EntityType<?>> UNDEAD = tag("undead");
		public static final TagKey<EntityType<?>> SKELETONS = tag("undead/skeletons");
		public static final TagKey<EntityType<?>> ZOMBIES = tag("undead/zombies");

		/**
		 * Mobs that simulate an intelligent, independent relationship of some sort with the player
		 */
		public static final TagKey<EntityType<?>> NPC = tag("npc");

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Biomes {
		// Tags specifying aquatic biomes

		// Tags specifying that a biome is a variant of a normal biome
		public static final TagKey<Biome> RARE = tag("rare");
		public static final TagKey<Biome> PLATEAUS = tag("plateaus"); // plateau variants of other biomes
		public static final TagKey<Biome> HILLS = tag("hills"); // hills variants of other biomes

		// Tags specifying aquatic biomes
		public static final TagKey<Biome> OCEANS = tag("water/oceans");
		public static final TagKey<Biome> SHALLOW_OCEANS = tag("water/oceans/shallow");
		public static final TagKey<Biome> DEEP_OCEANS = tag("water/oceans/deep");
		public static final TagKey<Biome> RIVERS = tag("water/rivers");
		public static final TagKey<Biome> WATER = tag("water");

		// Tags specifying generic types of biomes
		public static final TagKey<Biome> BADLANDS = tag("badlands");
		public static final TagKey<Biome> BEACHES = tag("beaches");
		public static final TagKey<Biome> DESERTS = tag("deserts");
		public static final TagKey<Biome> FORESTS = tag("forests");
		public static final TagKey<Biome> BIRCH_FORESTS = tag("forests/birch");
		public static final TagKey<Biome> DARK_FORESTS = tag("forests/dark");
		public static final TagKey<Biome> JUNGLE_FORESTS = tag("forests/jungles");
		public static final TagKey<Biome> BAMBOO_JUNGLE_FORESTS = tag("forests/jungles/bamboo");
		public static final TagKey<Biome> NETHER_FORESTS = tag("forests/nether");
		public static final TagKey<Biome> OAK_FORESTS = tag("forests/oak");
		public static final TagKey<Biome> TAIGA_FORESTS = tag("forests/taigas");
		public static final TagKey<Biome> GRASSLANDS = tag("grasslands"); // plains and savannas
		public static final TagKey<Biome> MUSHROOM = tag("mushroom");
		public static final TagKey<Biome> MOUNTAINS = tag("mountains");
		public static final TagKey<Biome> PEAKS = tag("peaks");
		public static final TagKey<Biome> PLAINS = tag("plains");
		public static final TagKey<Biome> SAVANNAS = tag("savannas");
		public static final TagKey<Biome> SNOWY = tag("snowy"); // indicates that a biome has snow and/or ice
		public static final TagKey<Biome> SLOPES = tag("slopes");
		public static final TagKey<Biome> SWAMPS = tag("swamps");
		public static final TagKey<Biome> VOIDS = tag("voids");

		// Tags specifying that a biome generates in a vanilla dimension. Specifying none of these indicates that the biome only generates in a modded dimension
		public static final TagKey<Biome> OVERWORLD = tag("overworld");
		public static final TagKey<Biome> OVERWORLD_SURFACE = tag("overworld/surface");
		public static final TagKey<Biome> OVERWORLD_UNDERGROUND = tag("overworld/underground");
		public static final TagKey<Biome> NETHER = tag("nether");
		public static final TagKey<Biome> END = tag("end");

		public static final TagKey<Biome> IS_DESERT = tag("is_desert");

		public static final TagKey<Biome> IS_CAVE = tag("is_cave");
		public static final TagKey<Biome> IS_PEAK = tag("is_peak");
		public static final TagKey<Biome> IS_SLOPE = tag("is_slope");
		public static final TagKey<Biome> IS_MOUNTAIN = tag("is_mountain");

		public static final TagKey<Biome> IS_GRASSLAND = tag("is_grassland");
		public static final TagKey<Biome> IS_ICY = tag("is_icy");
		public static final TagKey<Biome> IS_OUTER_END = tag("is_outer_end");

		public static final TagKey<Biome> WITH_DEFAULT_MONSTER_SPAWNS = tag("with_default_monster_spawns");
		public static final TagKey<Biome> WITHOUT_DEFAULT_MONSTER_SPAWNS = tag("without_default_monster_spawns");

		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class NoiseSettings {
		public static final TagKey<NoiseGeneratorSettings> AMPLIFIED = tag("amplified");
		public static final TagKey<NoiseGeneratorSettings> CAVES = tag("caves");
		public static final TagKey<NoiseGeneratorSettings> END = tag("end");
		public static final TagKey<NoiseGeneratorSettings> FLOATING_ISLANDS = tag("floating_islands");
		public static final TagKey<NoiseGeneratorSettings> NETHER = tag("nether");
		public static final TagKey<NoiseGeneratorSettings> OVERWORLD = tag("overworld");

		private static TagKey<NoiseGeneratorSettings> tag(String name) {
			return TagKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class DimensionTypes {
		public static final TagKey<DimensionType> END = tag("end");
		public static final TagKey<DimensionType> NETHER = tag("nether");
		public static final TagKey<DimensionType> OVERWORLD = tag("overworld");
		public static final TagKey<DimensionType> OVERWORLD_CAVES = tag("overworld/caves");

		private static TagKey<DimensionType> tag(String name) {
			return TagKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class MobEffects {
		public static final TagKey<MobEffect> NEGATIVES = tag("negatives");
		public static final TagKey<MobEffect> POSITIVES = tag("positives");

		public static final TagKey<MobEffect> INSTANT = tag("instant");
		public static final TagKey<MobEffect> LASTING = tag("lasting");

		private static TagKey<MobEffect> tag(String name) {
			return TagKey.create(Registry.MOB_EFFECT_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Enchantments {
		public static final TagKey<Enchantment> TOOL_ENCHANTMENTS = tag("tools");
		public static final TagKey<Enchantment> PICKAXE_ENCHANTMENTS = tag("tools/pickaxes");
		public static final TagKey<Enchantment> AXE_ENCHANTMENTS = tag("tools/axes");
		public static final TagKey<Enchantment> HOE_ENCHANTMENTS = tag("tools/hoes");
		public static final TagKey<Enchantment> SHOVEL_ENCHANTMENTS = tag("tools/shovels");

		public static final TagKey<Enchantment> WEAPON_ENCHANTMENTS = tag("weapons");
		public static final TagKey<Enchantment> SWORD_ENCHANTMENTS = tag("weapons/sword");

		public static final TagKey<Enchantment> BOW_ENCHANTMENTS = tag("range/bows");
		public static final TagKey<Enchantment> CROSSBOW_ENCHANTMENTS = tag("range/crossbows");
		public static final TagKey<Enchantment> TRIDENT_ENCHANTMENTS = tag("range/tridents");

		public static final TagKey<Enchantment> CURSES = tag("curses");

		public static final TagKey<Enchantment> ARMOR_ENCHANTMENTS = tag("armors");
		public static final TagKey<Enchantment> HEAD_ENCHANTMENTS = tag("armors/head");
		public static final TagKey<Enchantment> CHEST_ENCHANTMENTS = tag("armors/chest");
		public static final TagKey<Enchantment> LEGS_ENCHANTMENTS = tag("armors/legs");
		public static final TagKey<Enchantment> FEET_ENCHANTMENTS = tag("armors/feet");

		private static TagKey<Enchantment> tag(String name) {
			return TagKey.create(Registry.ENCHANTMENT_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class Potions {
		public static final TagKey<Potion> NEGATIVE_POTIONS = tag("negatives");
		public static final TagKey<Potion> POSITIVE_POTIONS = tag("positives");

		public static final TagKey<Potion> INSTANT_POTIONS = tag("instant");
		public static final TagKey<Potion> LASTING_POTIONS = tag("lasting");

		private static TagKey<Potion> tag(String name) {
			return TagKey.create(Registry.POTION_REGISTRY, new ResourceLocation("c", name));
		}
	}

	public static class PaintingVariants {
		public static final TagKey<PaintingVariant> ONE_X_ONE = tag("1x1");
		public static final TagKey<PaintingVariant> TWO_X_ONE = tag("2x1");
		public static final TagKey<PaintingVariant> ONE_X_TWO = tag("1x2");
		public static final TagKey<PaintingVariant> TWO_X_TWO = tag("2x2");
		public static final TagKey<PaintingVariant> FOUR_X_TWO = tag("4x2");
		public static final TagKey<PaintingVariant> FOUR_X_THREE = tag("4x3");
		public static final TagKey<PaintingVariant> FOUR_X_FOUR = tag("4x4");

		private static TagKey<PaintingVariant> tag(String name) {
			return TagKey.create(Registry.PAINTING_VARIANT_REGISTRY, new ResourceLocation("c", name));
		}
	}

}
