package io.github.vampirestudios.vampirelib.utils;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;

public class ArtificeGenerationHelper {

    public static void generateBasicBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockState(name, blockStateBuilder ->
                blockStateBuilder.variant("", variant ->
                        variant.model(Utils.prependToPath(name, "block/"))));
    }

    public static void generateBasicBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier modelId) {
        clientResourcePackBuilder.addBlockState(name, blockStateBuilder ->
                blockStateBuilder.variant("", variant ->
                        variant.model(Utils.prependToPath(modelId, "block/"))));
    }

    public static void generatePillarBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockState(name, blockStateBuilder -> {
            blockStateBuilder.variant("axis=y", variant ->
                    variant.model(Utils.prependToPath(name, "block/")));
            blockStateBuilder.variant("axis=x", variant -> {
                variant.model(Utils.prependToPath(name, "block/"));
                variant.rotationX(90);
                variant.rotationY(90);
            });
            blockStateBuilder.variant("axis=z", variant -> {
                variant.model(Utils.prependToPath(name, "block/"));
                variant.rotationX(90);
            });
        });
    }

    public static void generatePillarBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier modelId) {
        clientResourcePackBuilder.addBlockState(name, blockStateBuilder -> {
            blockStateBuilder.variant("axis=y", variant ->
                    variant.model(Utils.prependToPath(modelId, "block/")));
            blockStateBuilder.variant("axis=x", variant -> {
                variant.model(Utils.prependToPath(modelId, "block/"));
                variant.rotationX(90);
                variant.rotationY(90);
            });
            blockStateBuilder.variant("axis=z", variant -> {
                variant.model(Utils.prependToPath(modelId, "block/"));
                variant.rotationX(90);
            });
        });
    }

    public static void generateHorizonalFacingBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {

    }

    public static void generateFacingBlockState(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockState(name, blockStateBuilder -> {
            blockStateBuilder.variant("facing=north", variant ->
                    variant.model(Utils.prependToPath(name, "block/")));
            blockStateBuilder.variant("facing=south", variant ->
                    variant.model(Utils.prependToPath(name, "block/")).rotationY(180));
            blockStateBuilder.variant("facing=east", variant ->
                    variant.model(Utils.prependToPath(name, "block/")).rotationY(90));
            blockStateBuilder.variant("facing=west", variant ->
                    variant.model(Utils.prependToPath(name, "block/")).rotationY(270));
        });
    }

    public static void generateAllBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cube_all"));
            modelBuilder.texture("all", Utils.prependToPath(name, "block/"));
        });
    }

    public static void generateAllBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier texture) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cube_all"));
            modelBuilder.texture("all", Utils.prependToPath(texture, "block/"));
        });
    }

    public static void generateCrossBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cross"));
            modelBuilder.texture("cross", Utils.prependToPath(name, "block/"));
        });
    }

    public static void generateCrossBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier texture) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cross"));
            modelBuilder.texture("cross", Utils.prependToPath(texture, "block/"));
        });
    }

    public static void generateColumnBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier endTexture, Identifier sideTexture) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cube_column"));
            modelBuilder.texture("end", Utils.prependToPath(endTexture, "block/"));
            modelBuilder.texture("side", Utils.prependToPath(sideTexture, "block/"));
        });
    }

    public static void generateTopBottomBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier topTexture, Identifier bottomTexture, Identifier sideTexture) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/cube_top_bottom"));
            modelBuilder.texture("top", topTexture);
            modelBuilder.texture("bottom", bottomTexture);
            modelBuilder.texture("side", sideTexture);
        });
    }

    public static void generateLadderBlockModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addBlockModel(name, modelBuilder -> {
            modelBuilder.parent(new Identifier("block/ladder"));
            modelBuilder.texture("texture", Utils.prependToPath(name, "block/"));
        });
    }

    public static void generateBlockItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addItemModel(name, modelBuilder -> modelBuilder.parent(Utils.prependToPath(name, "block/")));
    }

    public static void generateBlockItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier modelId) {
        clientResourcePackBuilder.addItemModel(name, modelBuilder -> modelBuilder.parent(Utils.prependToPath(modelId, "block/")));
    }

    public static void generateSimpleItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name) {
        clientResourcePackBuilder.addItemModel(name, modelBuilder -> modelBuilder.parent(new Identifier("item/generated")).texture("layer0", Utils.prependToPath(name, "item/")));
    }

    public static void generateSimpleItemModel(ArtificeResourcePack.ClientResourcePackBuilder clientResourcePackBuilder, Identifier name, Identifier texture) {
        clientResourcePackBuilder.addItemModel(name, modelBuilder -> modelBuilder.parent(new Identifier("item/generated")).texture("layer0", texture));
    }

}
