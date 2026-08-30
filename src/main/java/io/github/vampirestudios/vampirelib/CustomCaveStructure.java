/*
 * Copyright (c) 2024 OliviaTheVampire
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
 *//*


package io.github.vampirestudios.vampirelib;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class CustomCaveStructure implements Feature {

	@Override
	public MapCodec<? extends Feature> codec() {
		return null;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(origin.getX(), origin.getY(), origin.getZ());
		generateCaveSegment(level, mutablePos, random, 10, 0); // Start recursive cave generation
		return true;
	}

    private void generateCaveSegment(WorldGenLevel world, BlockPos pos, RandomSource random, int maxDepth, int depth) {
        if (depth > maxDepth) return;

		// Randomly choose a type of cave segment
		float typeSelector = random.nextFloat();
		if (typeSelector < 0.07) {
			generateThinCave(world, pos, random);
		} else if (typeSelector < 0.14) {
			generateLargeCavern(world, pos, random);
		} else if (typeSelector < 0.2) {
			generateWindingTunnel(world, pos, random);
		} else if (typeSelector < 0.26) {
			generateVerticalShaft(world, pos, random);
		} else if (typeSelector < 0.32) {
			generateSpiralCave(world, pos, random);
		} else if (typeSelector < 0.38) {
			generateChasm(world, pos, random);
		} else if (typeSelector < 0.44) {
			generateBubbleCave(world, pos, random);
		} else if (typeSelector < 0.5) {
			generateWaveTunnel(world, pos, random);
		} else if (typeSelector < 0.56) {
			generateBranchingTunnel(world, pos, random);
		} else if (typeSelector < 0.61) {
			generateColumnedHall(world, pos, random);
		} else if (typeSelector < 0.66) {
			generateTightSqueeze(world, pos, random);
		} else if (typeSelector < 0.7) {
			generateCrossroads(world, pos, random);
		} else if (typeSelector < 0.74) {
			generateSinkhole(world, pos, random);
		} else if (typeSelector < 0.77) {
			generateFissure(world, pos, random);
		} else if (typeSelector < 0.8) {
			generateNaturalArchway(world, pos, random);
		} else if (typeSelector < 0.85) {
			generateMultiLayeredMaze(world, pos, random);
		} else if (typeSelector < 0.89) {
			generateCathedralCavern(world, pos, random);
		} else if (typeSelector < 0.93) {
			generateWaterfallPassage(world, pos, random);
		} else if (typeSelector < 0.96) {
			generateMazeRavine(world, pos, random);
		} else if (typeSelector < 0.98) {
			generateCollapsedMineshaft(world, pos, random);
		} else {
			generateTwistingVinesCavern(world, pos, random);
		}

        // Add decorations and biome variants
        generateBiomeVariant(world, pos, random);

        // Randomly extend to a nearby segment
        int newDepth = depth + 1;
        if (random.nextFloat() < 0.8) {
            BlockPos nextPos = pos.offset((random.nextInt(16) - 8), (random.nextInt(8) - 4), (random.nextInt(16) - 8));
            generateCaveSegment(world, nextPos, random, maxDepth, newDepth);
        }
    }

    // Cave Types

    private void generateThinCave(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int length = random.nextInt(20) + 10;
        for (int i = 0; i < length; i++) {
            world.setBlock(pos.offset(i, 0, 0), Blocks.AIR.defaultBlockState(), 3);
        }
    }

	private void generateLargeCavern(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int radius = random.nextInt(8) + 8;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				for (int y = -radius / 2; y <= radius / 2; y++) {
					if (x * x + y * y + z * z <= radius * radius) {
						world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
		// Add moss patches to the floor of the cavern
		addMossPatch(world, pos, random);
	}

    private void generateWindingTunnel(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int turns = random.nextInt(5) + 5;
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        for (int i = 0; i < turns; i++) {
            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            int dz = random.nextInt(3) - 1;
            int length = random.nextInt(10) + 5;

            for (int j = 0; j < length; j++) {
                mutablePos.move(dx, dy, dz);
                world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private void generateVerticalShaft(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int height = random.nextInt(20) + 20;
        for (int y = 0; y < height; y++) {
            BlockPos currentPos = pos.below(y);
            world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
            if (random.nextFloat() < 0.2) {
                world.setBlock(currentPos.east(), Blocks.AIR.defaultBlockState(), 3);
                world.setBlock(currentPos.west(), Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private void generateSpiralCave(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int radius = random.nextInt(3) + 3;
        int turns = random.nextInt(10) + 5;
        BlockPos.MutableBlockPos mutablePos = pos.mutable();

        for (int i = 0; i < turns; i++) {
            mutablePos.move(random.nextBoolean() ? 1 : -1, -1, random.nextBoolean() ? 1 : -1);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= radius * radius) {
                        world.setBlock(mutablePos.offset(x, 0, z), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void generateChasm(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int length = random.nextInt(30) + 30;
        int height = random.nextInt(10) + 10;

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                BlockPos chasmPos = pos.offset(x, -y, 0);
                world.setBlock(chasmPos, Blocks.AIR.defaultBlockState(), 3);
                if (random.nextFloat() < 0.05) {
                    world.setBlock(chasmPos.east(), Blocks.STONE.defaultBlockState(), 3);
                    world.setBlock(chasmPos.west(), Blocks.STONE.defaultBlockState(), 3);
                }
            }
        }
    }

	private void generateBubbleCave(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int bubbles = random.nextInt(4) + 2;
		for (int i = 0; i < bubbles; i++) {
			int radius = random.nextInt(4) + 4;
			BlockPos bubbleCenter = pos.offset(random.nextInt(10) - 5, 0, random.nextInt(10) - 5);
			generateBubbleRoom(world, bubbleCenter, radius);

			// Add moss patches to the floor of each bubble
			addMossPatch(world, bubbleCenter, random);
		}
	}

    private void generateBubbleRoom(WorldGenLevel world, BlockPos pos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

	private void generateWaveTunnel(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int length = random.nextInt(30) + 20;
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < length; i++) {
			int dy = (i % 4 == 0) ? 1 : (i % 4 == 2 ? -1 : 0); // Wave pattern: up, flat, down, flat
			mutablePos.move(1, dy, 0);
			world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 3);
		}
	}

	private void generateBranchingTunnel(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int length = random.nextInt(25) + 15;
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < length; i++) {
			mutablePos.move(1, 0, 0); // Main tunnel moves forward
			world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 3);

			// Occasionally create a branch
			if (random.nextFloat() < 0.3) {
				int branchLength = random.nextInt(5) + 3;
				BlockPos.MutableBlockPos branchPos = mutablePos.mutable().move(random.nextBoolean() ? 1 : -1, 0, random.nextBoolean() ? 1 : -1);
				for (int j = 0; j < branchLength; j++) {
					branchPos.move(random.nextInt(3) - 1, random.nextInt(2) - 1, random.nextInt(3) - 1);
					world.setBlock(branchPos, Blocks.AIR.defaultBlockState(), 3);
				}
			}
		}
	}

	private void generateColumnedHall(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int width = random.nextInt(8) + 10;
		int height = random.nextInt(6) + 6;
		int length = random.nextInt(15) + 15;

		for (int x = -width; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				for (int z = -length; z <= length; z++) {
					BlockPos hallPos = pos.offset(x, y, z);
					world.setBlock(hallPos, Blocks.AIR.defaultBlockState(), 3);

					// Place columns every 5 blocks along both X and Z axes
					if (y < height && (x % 5 == 0 || z % 5 == 0)) {
						world.setBlock(hallPos.below(y), Blocks.STONE.defaultBlockState(), 3);
						world.setBlock(hallPos.above(), Blocks.STONE.defaultBlockState(), 3);
					}
				}
			}
		}
	}

	private void generateTightSqueeze(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int length = random.nextInt(20) + 10;
		BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for (int i = 0; i < length; i++) {
			int height = random.nextBoolean() ? 1 : 2; // Randomly select height
			for (int h = 0; h < height; h++) {
				world.setBlock(mutablePos.move(1, h, 0), Blocks.AIR.defaultBlockState(), 3);
			}
			mutablePos.move(0, -height, 1); // Shift position for the next part of the tunnel
		}
	}

	private void generateCrossroads(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int radius = random.nextInt(6) + 4;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				for (int y = -radius / 2; y <= radius / 2; y++) {
					if (x * x + y * y + z * z <= radius * radius) {
						world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
		// Create tunnels branching from the crossroads
		for (int i = 0; i < 4; i++) { // 4 exits at right angles
			int tunnelLength = random.nextInt(10) + 5;
			BlockPos.MutableBlockPos tunnelPos = pos.mutable();
			for (int j = 0; j < tunnelLength; j++) {
				tunnelPos.move(i % 2 == 0 ? 1 : -1, 0, i < 2 ? 1 : -1);
				world.setBlock(tunnelPos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}

	private void generateSinkhole(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int radius = random.nextInt(6) + 6;
		int depth = random.nextInt(8) + 10;

		for (int y = 0; y < depth; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= radius * radius) {
						world.setBlock(pos.offset(x, -y, z), Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
			radius -= 1; // Gradually narrow as it goes deeper
		}
	}

	private void generateFissure(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int height = random.nextInt(15) + 10;
		int width = random.nextInt(2) + 1;

		for (int y = 0; y < height; y++) {
			for (int x = -width; x <= width; x++) {
				for (int z = -width; z <= width; z++) {
					if (x * x + z * z <= width * width) {
						world.setBlock(pos.offset(x, -y, z), Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
	}

	private void generateNaturalArchway(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int width = random.nextInt(10) + 10;
		int height = random.nextInt(8) + 6;

		for (int x = -width; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				BlockPos archPos = pos.offset(x, y, 0);
				if (x * x + y * y <= width * height) {
					world.setBlock(archPos, Blocks.AIR.defaultBlockState(), 3);
				}
				// Create the arch using stone
				if (y < height && Math.abs(x) % (width / 3) == 0) {
					world.setBlock(archPos.below(y), Blocks.STONE.defaultBlockState(), 3);
				}
			}
		}
	}

	private void generateMultiLayeredMaze(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int rooms = random.nextInt(3) + 3; // 3 to 5 rooms
		int layerHeight = 4; // Distance between layers

		for (int layer = 0; layer < rooms; layer++) {
			int radius = random.nextInt(4) + 4;
			BlockPos layerPos = pos.above(layer * layerHeight);

			// Create main room
			carveRoom(world, layerPos, radius);

			// Connect to the next layer
			if (layer < rooms - 1) {
				if (random.nextBoolean()) {
					// Staircase connection
					createStaircase(world, layerPos, layerHeight);
				} else {
					// Drop-off connection
					createDropOff(world, layerPos, layerHeight);
				}
			}
		}
	}

	private void carveRoom(WorldGenLevel world, BlockPos pos, int radius) {
		for (int x = -radius; x <= radius; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + y * y + z * z <= radius * radius) {
						world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
	}

	private void createStaircase(WorldGenLevel world, BlockPos pos, int height) {
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < height; i++) {
			mutablePos.move(1, -1, 0);
			world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 3);
		}
	}

	private void createDropOff(WorldGenLevel world, BlockPos pos, int height) {
		BlockPos dropPos = pos.below(height);
		world.setBlock(dropPos, Blocks.AIR.defaultBlockState(), 3);
	}

	private void generateCathedralCavern(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int width = random.nextInt(10) + 15;
		int height = random.nextInt(12) + 12;
		int length = random.nextInt(20) + 15;

		// Create main cavern space
		for (int x = -width; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				for (int z = -length; z <= length; z++) {
					world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);

					// Place pillars at intervals
					if ((x % 5 == 0 || z % 5 == 0) && y < height - 2) {
						world.setBlock(pos.offset(x, y, z), Blocks.STONE.defaultBlockState(), 3);
					}
				}
			}
		}

		// Add arches along the walls
		addArches(world, pos, width, height, length);
	}

	private void addArches(WorldGenLevel world, BlockPos pos, int width, int height, int length) {
		for (int y = 0; y < height / 2; y++) {
			for (int i = -width; i <= width; i++) {
				if (i % 5 == 0) {
					world.setBlock(pos.offset(i, y, -length), Blocks.STONE.defaultBlockState(), 3);
					world.setBlock(pos.offset(i, y, length), Blocks.STONE.defaultBlockState(), 3);
				}
			}
		}
	}

	private void generateWaterfallPassage(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int tunnelLength = random.nextInt(8) + 5;

		// Create a small tunnel behind a waterfall
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < tunnelLength; i++) {
			world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 3);
			mutablePos.move(1, 0, 0);
		}

		// Place waterfall at the entrance
		world.setBlock(pos.above(), Blocks.WATER.defaultBlockState(), 3);
		world.setBlock(pos.above(2), Blocks.WATER.defaultBlockState(), 3);
	}

	private void generateMazeRavine(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int length = random.nextInt(25) + 20;
		int width = random.nextInt(3) + 2;
		int height = random.nextInt(10) + 8;

		for (int x = 0; x < length; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = -width; z <= width; z++) {
					BlockPos ravinePos = pos.offset(x, -y, z);
					world.setBlock(ravinePos, Blocks.AIR.defaultBlockState(), 3);
				}
			}

			// Occasionally add a stone walkway
			if (x % 5 == 0 && random.nextBoolean()) {
				int walkwayHeight = random.nextInt(height - 2) + 2;
				for (int z = -width; z <= width; z++) {
					world.setBlock(pos.offset(x, walkwayHeight, z), Blocks.STONE.defaultBlockState(), 3);
				}
			}
		}
	}

	private void generateCollapsedMineshaft(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int length = random.nextInt(30) + 15;
		BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for (int i = 0; i < length; i++) {
			// Clear the tunnel space
			for (int y = -1; y <= 2; y++) {
				world.setBlock(mutablePos.above(y), Blocks.AIR.defaultBlockState(), 3);
			}

			// Add wooden beams every few blocks
			if (i % 5 == 0) {
				addWoodenBeam(world, mutablePos);
			}

			// Place rail tracks on the floor
			world.setBlock(mutablePos, Blocks.RAIL.defaultBlockState(), 3);

			// Occasionally add debris
			if (random.nextFloat() < 0.2) {
				world.setBlock(mutablePos.above(1), Blocks.COBBLESTONE.defaultBlockState(), 3);
			}

			mutablePos.move(1, 0, 0); // Move the tunnel forward
		}
	}

	private void addWoodenBeam(WorldGenLevel world, BlockPos pos) {
		for (int y = 0; y <= 2; y++) {
			world.setBlock(pos.above(y), Blocks.OAK_LOG.defaultBlockState(), 3);
			world.setBlock(pos.east(), Blocks.OAK_LOG.defaultBlockState(), 3);
			world.setBlock(pos.west(), Blocks.OAK_LOG.defaultBlockState(), 3);
		}
	}

	private void generateTwistingVinesCavern(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int radius = random.nextInt(8) + 10;
		int height = random.nextInt(8) + 6;

		for (int x = -radius; x <= radius; x++) {
			for (int y = 0; y <= height; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + y * y + z * z <= radius * radius) {
						world.setBlock(pos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);

						// Add vines on ceiling or walls
						if (y == height && random.nextFloat() < 0.3) {
							addVine(world, pos.offset(x, y, z), random);
						}
					}
				}
			}
		}
	}

	private void addVine(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int vineLength = random.nextInt(5) + 3;
		for (int i = 0; i < vineLength; i++) {
			world.setBlock(pos.below(i), Blocks.VINE.defaultBlockState(), 3);
		}
	}


	// Decorations and Biome Variants

	private void addNaturalDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
		if (random.nextFloat() < 0.15) {
			world.setBlock(pos, Blocks.GLOW_LICHEN.defaultBlockState(), 3);
		}
		if (random.nextFloat() < 0.1) {
			world.setBlock(pos, Blocks.BROWN_MUSHROOM.defaultBlockState(), 3);
		}
		if (random.nextFloat() < 0.2) {
			world.setBlock(pos, Blocks.POINTED_DRIPSTONE.defaultBlockState(), 3);
		}
	}

	private void addMossPatch(WorldGenLevel world, BlockPos pos, RandomSource random) {
		int patchRadius = random.nextInt(3) + 2; // Patch radius between 2 and 4 blocks

		for (int x = -patchRadius; x <= patchRadius; x++) {
			for (int z = -patchRadius; z <= patchRadius; z++) {
				// Use Pythagorean theorem to create a circular patch
				if (x * x + z * z <= patchRadius * patchRadius) {
					BlockPos groundPos = pos.offset(x, -1, z); // Place moss one block below the current position
					if (world.getBlockState(groundPos).is(Blocks.STONE) || world.getBlockState(groundPos).is(Blocks.DIRT)) {
						world.setBlock(groundPos, Blocks.MOSS_BLOCK.defaultBlockState(), 3);
					}
				}
			}
		}
	}

    private void generateBiomeVariant(WorldGenLevel world, BlockPos pos, RandomSource random) {
        float variantChance = random.nextFloat();
        if (variantChance < 0.2) {
            addCrystalIceCaveDecor(world, pos, random);
        } else if (variantChance < 0.4) {
            addLavaCaveDecor(world, pos, random);
        } else if (variantChance < 0.6) {
            addMossyCaveDecor(world, pos, random);
        } else if (variantChance < 0.8) {
            addSandstoneCaveDecor(world, pos, random);
        } else {
            addHauntedCaveDecor(world, pos, random);
        }

		addNaturalDecor(world, pos, random);
    }

    private void addCrystalIceCaveDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, Blocks.PACKED_ICE.defaultBlockState(), 3);
        if (random.nextFloat() < 0.1) {
            world.setBlock(pos.below(), Blocks.SNOW_BLOCK.defaultBlockState(), 3);
        }
        if (random.nextFloat() < 0.05) {
            world.setBlock(pos, Blocks.BLUE_ICE.defaultBlockState(), 3);
        }
    }

    private void addLavaCaveDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, Blocks.BASALT.defaultBlockState(), 3);
        if (random.nextFloat() < 0.2) {
            world.setBlock(pos.below(), Blocks.LAVA.defaultBlockState(), 3);
        } else if (random.nextFloat() < 0.3) {
            world.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
        }
    }

    private void addMossyCaveDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, Blocks.MOSS_BLOCK.defaultBlockState(), 3);
        if (random.nextFloat() < 0.1) {
            world.setBlock(pos.above(), Blocks.SPORE_BLOSSOM.defaultBlockState(), 3);
        }
    }

    private void addSandstoneCaveDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, Blocks.SANDSTONE.defaultBlockState(), 3);
        if (random.nextFloat() < 0.2) {
            world.setBlock(pos.below(), Blocks.SAND.defaultBlockState(), 3);
        }
    }

    private void addHauntedCaveDecor(WorldGenLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, Blocks.BLACKSTONE.defaultBlockState(), 3);
        if (random.nextFloat() < 0.15) {
            world.setBlock(pos.below(), Blocks.SOUL_SAND.defaultBlockState(), 3);
        }
        if (random.nextFloat() < 0.1) {
            world.setBlock(pos, Blocks.COBWEB.defaultBlockState(), 3);
        }
    }

}
*/
