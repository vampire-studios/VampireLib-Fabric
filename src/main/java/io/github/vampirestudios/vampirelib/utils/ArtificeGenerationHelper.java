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

package io.github.vampirestudios.vampirelib.utils;

@Deprecated
public class ArtificeGenerationHelper {
	/*public static void generateBasicBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder().variant("", new Variant().model(
				Utils.prependToPath(name, "block/"))));
	}

	public static void generateBasicBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation modelId) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder().variant("", new Variant().model(
				Utils.prependToPath(modelId, "block/"))));
	}

	public static void generateLanternBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder()
				.variant("hanging=false", new Variant().model(Utils.prependToPath(name, "block/")))
				.variant("hanging=true", new Variant().model(Utils.appendAndPrependToPath(name, "block/", "_hanging")))
		);
	}

	public static void generateLanternBlockModels(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation parent, Map<String, ResourceLocation> textures, ResourceLocation parentHanging, Map<String, ResourceLocation> texturesHanging) {
		ModelBuilder model = new ModelBuilder().parent(parent);
		if (textures != null) textures.forEach(model::texture);
		clientResourcePackBuilder.addBlockModel(name, model);
		model = new ModelBuilder().parent(parentHanging);
		if (textures != null) textures.forEach(model::texture);
		clientResourcePackBuilder.addBlockModel(Utils.appendToPath(name, "_hanging"), model);
	}

	public static void generatePillarBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		generatePillarBlockState(clientResourcePackBuilder, name, name);
	}

	public static void generatePillarBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation modelId) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder()
				.variant("axis=y", new Variant().model(Utils.prependToPath(modelId, "block/")))
				.variant("axis=x", new Variant().model(Utils.prependToPath(modelId, "block/")).rotationX(90).rotationY(90))
				.variant("axis=z", new Variant().model(Utils.prependToPath(modelId, "block/")).rotationX(90))
		);
	}

	public static void generateHorizontalFacingBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder()
				.variant("facing=north", new Variant().model(Utils.prependToPath(name, "block/")))
				.variant("facing=south", new Variant().model(Utils.prependToPath(name, "block/")).rotationY(180))
				.variant("facing=east", new Variant().model(Utils.prependToPath(name, "block/")).rotationY(90))
				.variant("facing=west", new Variant().model(Utils.prependToPath(name, "block/")).rotationY(270))
		);
	}

	public static void generateFacingBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockState(name, new BlockStateBuilder()
				.variant("facing=north", new Variant().model(Utils.prependToPath(name, "block/")).rotationX(90))
				.variant("facing=south",
						new Variant().model(Utils.prependToPath(name, "block/")).rotationY(180).rotationX(90))
				.variant("facing=east",
						new Variant().model(Utils.prependToPath(name, "block/")).rotationY(90).rotationX(90))
				.variant("facing=west",
						new Variant().model(Utils.prependToPath(name, "block/")).rotationY(270).rotationX(90))
				.variant("facing=up", new Variant().model(Utils.prependToPath(name, "block/")))
				.variant("facing=down", new Variant().model(Utils.prependToPath(name, "block/")).rotationY(180))
		);
	}

	public static void generateAllBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cube_all"))
				.texture("all", Utils.prependToPath(name, "block/"))
		);
	}

	public static void generateAllBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation texture) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cube_all"))
				.texture("all", Utils.prependToPath(texture, "block/"))
		);
	}

	public static void generateCrossBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cross"))
				.texture("cross", Utils.prependToPath(name, "block/"))
		);
	}

	public static void generateCrossBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation texture) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cross"))
				.texture("cross", Utils.prependToPath(texture, "block/"))
		);
	}

	public static void generateColumnBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation endTexture, ResourceLocation sideTexture) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cube_column"))
				.texture("end", Utils.prependToPath(endTexture, "block/"))
				.texture("side", Utils.prependToPath(sideTexture, "block/"))
		);
	}

	public static void generateTopBottomBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation topTexture, ResourceLocation bottomTexture, ResourceLocation sideTexture) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/cube_top_bottom"))
				.texture("top", topTexture)
				.texture("bottom", bottomTexture)
				.texture("side", sideTexture)
		);
	}

	public static void generateLadderBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/ladder"))
				.texture("texture", Utils.prependToPath(name, "block/"))
		);
	}

	public static void generateBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation parent, Map<String, ResourceLocation> textures) {
		ModelBuilder modelBuilder = new ModelBuilder()
				.parent(parent);
		if (textures != null) textures.forEach(modelBuilder::texture);
		clientResourcePackBuilder.addBlockModel(name, modelBuilder);
	}

	public static void generateBlockItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addItemModel(name, new ModelBuilder().parent(Utils.prependToPath(name, "block/")));
	}

	public static void generateBlockItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation modelId) {
		clientResourcePackBuilder.addItemModel(name, new ModelBuilder().parent(Utils.prependToPath(modelId, "block/")));
	}

	public static void generateSimpleItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		clientResourcePackBuilder.addItemModel(name, new ModelBuilder().parent(new ResourceLocation("item/generated"))
				.texture("layer0",
						Utils.prependToPath(name, "item/")));
	}

	public static void generateSimpleItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name, ResourceLocation texture) {
		clientResourcePackBuilder.addItemModel(name, new ModelBuilder().parent(new ResourceLocation("item/generated"))
				.texture("layer0", texture));
	}

	public static void generateStairsBlockState(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name) {
		String JSON = JsonTemplates.STAIRS_BLOCKSTATE
				.replace("%MOD_ID%", name.getNamespace())
				.replace("%BLOCK_ID%", name.getPath());
		pack.add(Utils.appendAndPrependToPath(name, "blockstates/", ".json"), new StringResource(JSON));
	}

	public static void generateStairsBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation textures) {
		pack.addBlockModel(Utils.appendToPath(name, "_inner"), new ModelBuilder()
				.parent(new ResourceLocation("block/inner_stairs"))
				.texture("particle", textures)
				.texture("side", textures)
				.texture("top", textures)
				.texture("bottom", textures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_outer"), new ModelBuilder()
				.parent(new ResourceLocation("block/outer_stairs"))
				.texture("particle", textures)
				.texture("side", textures)
				.texture("top", textures)
				.texture("bottom", textures)
		);
		pack.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/stairs"))
				.texture("particle", textures)
				.texture("side", textures)
				.texture("top", textures)
				.texture("bottom", textures)
		);
	}

	public static void generateWallBlockState(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name) {
		String JSON = JsonTemplates.WALL_BLOCKSTATE
				.replace("%MOD_ID%", name.getNamespace())
				.replace("%BLOCK_ID%", name.getPath());
		pack.add(Utils.appendAndPrependToPath(name, "blockstates/", ".json"), new StringResource(JSON));
	}

	public static void generateWallBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation textures) {
		pack.addBlockModel(Utils.appendToPath(name, "_inventory"), new ModelBuilder()
				.parent(new ResourceLocation("block/wall_inventory"))
				.texture("wall", textures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_post"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_wall_post"))
				.texture("wall", textures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_side"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_wall_side"))
				.texture("wall", textures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_side_tall"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_wall_side_tall"))
				.texture("wall", textures)
		);
	}

	public static void generateSlabBlockState(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation doubleBlockName) {
		BlockStateBuilder state = new BlockStateBuilder();
		Variant variant = new Variant();
		for (SlabType t : SlabType.values()) {
			switch (t) {
				case BOTTOM -> variant.model(Utils.prependToPath(name, "block/"));
				case TOP -> variant.model(Utils.appendAndPrependToPath(name, "block/", "_top"));
				case DOUBLE -> variant.model(Utils.prependToPath(doubleBlockName, "block/"));
			}
			state.variant("type=" + t.getSerializedName(), variant);
		}
		pack.addBlockState(name, state);
	}

	public static void generateSlabBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation textures) {
		pack.addBlockModel(Utils.appendToPath(name, "_top"), new ModelBuilder()
				.parent(new ResourceLocation("block/slab_top"))
				.texture("side", textures)
				.texture("top", textures)
				.texture("bottom", textures)
		);
		pack.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/slab"))
				.texture("side", textures)
				.texture("top", textures)
				.texture("bottom", textures)
		);
	}

	public static void generateFenceBlockState(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name) {
		BlockStateBuilder state = new BlockStateBuilder();
		state.multipartCase(new BlockStateBuilder.Case().apply(
				new Variant().model(Utils.appendAndPrependToPath(name, "block/", "_post"))));
		for (Direction d : Direction.values()) {
			if (d != Direction.UP && d != Direction.DOWN) {
				Variant variant = new Variant()
						.model(Utils.appendAndPrependToPath(name, "block/", "_side"))
						.uvlock(true);
				switch (d) {
					case EAST -> variant.rotationY(90);
					case WEST -> variant.rotationY(270);
					case SOUTH -> variant.rotationY(180);
				}
				state.multipartCase(new BlockStateBuilder.Case()
						.when(d.getSerializedName(), "true")
						.apply(variant)
				);
			}
		}
		pack.addBlockState(name, state);
	}

	public static void generateFenceBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation texture) {
		pack.addBlockModel(Utils.appendToPath(name, "_inventory"), new ModelBuilder()
				.parent(new ResourceLocation("block/fence_inventory"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_post"), new ModelBuilder()
				.parent(new ResourceLocation("block/fence_post"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_side"), new ModelBuilder()
				.parent(new ResourceLocation("block/fence_side"))
				.texture("texture", texture)
		);
	}

	public static void generateFenceGateBlockState(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name) {
//		pack.addBlockState(name, state -> {
//			for (Direction d : Direction.values()) {
//				if (d != Direction.UP && d != Direction.DOWN) {
//					state.variant("facing=" + d.getSerializedName() + ",in_wall=false,open=false", var -> {
//						var.model(Utils.prependToPath(name, "block/"));
//						var.uvlock(true);
//						switch (d) {
//							case NORTH -> var.rotationY(180);
//							case WEST -> var.rotationY(90);
//							case EAST -> var.rotationY(270);
//						}
//					});
//					state.variant("facing=" + d.getSerializedName() + ",in_wall=true,open=false", var -> {
//						var.model(Utils.appendAndPrependToPath(name, "block/", "_wall"));
//						var.uvlock(true);
//						switch (d) {
//							case NORTH -> var.rotationY(180);
//							case WEST -> var.rotationY(90);
//							case EAST -> var.rotationY(270);
//						}
//					});
//					state.variant("facing=" + d.getSerializedName() + ",in_wall=false,open=true", var -> {
//						var.model(Utils.appendAndPrependToPath(name, "block/", "_open"));
//						var.uvlock(true);
//						switch (d) {
//							case NORTH -> var.rotationY(180);
//							case WEST -> var.rotationY(90);
//							case EAST -> var.rotationY(270);
//						}
//					});
//					state.variant("facing=" + d.getSerializedName() + ",in_wall=true,open=true", var -> {
//						var.model(Utils.appendAndPrependToPath(name, "block/", "_wall_open"));
//						var.uvlock(true);
//						switch (d) {
//							case NORTH -> var.rotationY(180);
//							case WEST -> var.rotationY(90);
//							case EAST -> var.rotationY(270);
//						}
//					});
//				}
//			}
//		});
	}

	public static void generateFenceGateBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation texture) {
		pack.addBlockModel(Utils.appendToPath(name, "_open"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_fence_gate_open"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_wall"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_fence_gate_wall"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_wall_open"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_fence_gate_wall_open"))
				.texture("texture", texture)
		);
		pack.addBlockModel(name, new ModelBuilder()
				.parent(new ResourceLocation("block/template_fence_gate"))
				.texture("texture", texture)
		);
	}

	public static void generateDoorBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		String JSON = JsonTemplates.DOOR_BLOCKSTATE
				.replace("%MOD_ID%", name.getNamespace())
				.replace("%BLOCK_ID%", name.getPath());
		clientResourcePackBuilder.add(Utils.appendAndPrependToPath(name, "blockstates/", ".json"),
				new StringResource(JSON));
	}

	public static void generateDoorBlockModels(
			ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name,
			ResourceLocation topTextures, ResourceLocation bottomTextures
	) {
		pack.addBlockModel(Utils.appendToPath(name, "_bottom"), new ModelBuilder()
				.parent(new ResourceLocation("block/door_bottom"))
				.texture("texture", bottomTextures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_bottom_hinge"), new ModelBuilder()
				.parent(new ResourceLocation("block/door_bottom_rh"))
				.texture("texture", bottomTextures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_top"), new ModelBuilder()
				.parent(new ResourceLocation("block/door_top"))
				.texture("texture", topTextures)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_top_hinge"), new ModelBuilder()
				.parent(new ResourceLocation("block/door_top_rh"))
				.texture("texture", topTextures)
		);
	}

	public static void generateTrapdoorBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, ResourceLocation name) {
		String JSON = JsonTemplates.TRAPDOOR_BLOCKSTATE
				.replace("%MOD_ID%", name.getNamespace())
				.replace("%BLOCK_ID%", name.getPath());
		clientResourcePackBuilder.add(Utils.appendAndPrependToPath(name, "blockstates/", ".json"),
				new StringResource(JSON));
	}

	public static void generateTrapdoorBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, ResourceLocation name, ResourceLocation texture) {
		pack.addBlockModel(Utils.appendToPath(name, "_bottom"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_orientable_trapdoor_bottom"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_open"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_orientable_trapdoor_open"))
				.texture("texture", texture)
		);
		pack.addBlockModel(Utils.appendToPath(name, "_top"), new ModelBuilder()
				.parent(new ResourceLocation("block/template_orientable_trapdoor_top"))
				.texture("texture", texture)
		);
	}*/

}
