package team.hollow.abnormalib.utils.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ResourceGenerator {
	public static final Path RESOURCES_PATH = Paths.get("..", "src", "main", "resources");
    public static final Path ASSETS_PATH = RESOURCES_PATH.resolve("assets");
    public static final Path DATA_PATH = RESOURCES_PATH.resolve("data");
	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static Path getAssetsModPath(String modId) {
	    return ASSETS_PATH.resolve(modId);
    }

    public static Path getDataPath(String modId) {
	    return DATA_PATH.resolve(modId);
    }

    public static Path getBlockStatesPath(String modId) {
	    Path path = getAssetsModPath(modId).resolve("blockstates");
        createPath(path.toFile());
	    return path;
    }

    public static Path getBlockModelsPath(String modId) {
	    Path path = getAssetsModPath(modId).resolve("models").resolve("block");
	    createPath(path.toFile());
	    return path;
    }

    public static Path getItemModelsPath(String modId) {
	    Path path = getAssetsModPath(modId).resolve("models").resolve("item");
	    createPath(path.toFile());
	    return path;
    }

    public static Path getLootTablesPath(String modId) {
	    Path path = getDataPath(modId).resolve("loot_tables");
	    createPath(path.toFile());
	    return path;
    }

    public static Path getBlockTagsPath(String modId) {
	    return getDataPath(modId).resolve("tags").resolve("blocks");
    }

    public static void createPath(File file) {
	    if(!file.exists())
	        file.mkdirs();
    }

    public static void writeJsonToFile(File file, JsonObject json) {
	    writeStringToFile(file, StringEscapeUtils.unescapeJson(GSON.toJson(json)));
    }

    public static void writeStringToFile(File file, String text) {
	    try {
	        FileUtils.writeStringToFile(file, text, CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", file.getAbsolutePath());
        }
    }

    protected Set<Identifier> fences = new HashSet<>();
    protected Set<Identifier> walls = new HashSet<>();

    public void finish() {
        genFencesTag();
        fences.clear();
        genWallsTag();
        walls.clear();
    }

    protected void genFencesTag() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);
        JsonArray values = new JsonArray();
        fences.forEach(identifier -> values.add(identifier.toString()));
        root.add("values", values);

        writeJsonToFile(getBlockTagsPath("minecraft").resolve("fences.json").toFile(), root);
    }

    protected void genWallsTag() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);
        JsonArray values = new JsonArray();
        walls.forEach(identifier -> values.add(identifier.toString()));
        root.add("values", values);

        writeJsonToFile(getBlockTagsPath("minecraft").resolve("walls.json").toFile(), root);
    }

    public void genStair(Identifier identifier, Identifier bottomTexture, Identifier topTexture, Identifier sideTexture) {

        String text = JsonTemplates.STAIRS_STATES.replace("modid", identifier.getNamespace())
                .replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genStairBlockJson(identifier, bottomTexture, topTexture, sideTexture);
        genStairItemModel(identifier);
    }

    public void genStairBlockJson(Identifier identifier, Identifier bottomTexture, Identifier topTexture, Identifier sideTexture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", String.format("%s:block/%s", bottomTexture.getNamespace(), bottomTexture.getPath()));
        textures.addProperty("top", String.format("%s:block/%s", topTexture.getNamespace(), topTexture.getPath()));
        textures.addProperty("side", String.format("%s:block/%s", sideTexture.getNamespace(), sideTexture.getPath()));

        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/stairs");
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        root = new JsonObject();
        root.addProperty("parent", "block/inner_stairs");
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_inner.json").toFile(), root);

        root = new JsonObject();
        root.addProperty("parent", "block/outer_stairs");
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_outer.json").toFile(), root);
    }

    public void genStairItemModel(Identifier identifier) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", identifier.getNamespace() + ":block/" + identifier.getPath());

        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genSimpleBlockstates(Identifier identifier) {
        JsonObject root = new JsonObject();

        JsonObject variants = new JsonObject();

        JsonObject model = new JsonObject();
        model.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath());

        variants.add("", model);
        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genSimpleBlock(Identifier identifier, Identifier textureName) {
        genSimpleBlockstates(identifier);
        genSimpleBlockModel(identifier, textureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genSimpleBlockModel(Identifier identifier, Identifier textureName) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/cube_all");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", textureName.getNamespace() + ":block/" + textureName.getPath());
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genSimpleBlockItemModel(Identifier identifier) {
		genBlockItemModel(identifier, identifier);
    }

    public void genBlockItemModel(Identifier identifier, Identifier blockModelIdentifier) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", blockModelIdentifier.getNamespace() + ":block/" + blockModelIdentifier.getPath());

        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genFlatBlockItemModel(Identifier identifier, Identifier textureName) {
	    JsonObject root = new JsonObject();
	    root.addProperty("parent", "item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", textureName.getNamespace() + ":block/" + textureName.getPath());
        root.add("textures", textures);

        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    /*public void genCustomBlock(Identifier identifier, Identifier modelPath) {
        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("forge_marker", 1);

        JsonObject defaults = new JsonObject();
        defaults.addProperty("model", modelPath.getNamespace() + ":" + modelPath.getPath());

        defaults.addProperty("transform", "forge:default-block");
        root.add("defaults", defaults);

        JsonObject variants = new JsonObject();

        JsonArray empty = new JsonArray();
        empty.add(new JsonObject());

        variants.add("normal", empty);
        variants.add("inventory", empty);
        root.add("variants", variants);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve(identifier.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", identifier.getPath());
        }
    }

    public void genCustomBlockWithTexture(Identifier identifier, Identifier modelPath, Identifier textureLocation) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", identifier.getNamespace(), "blockstates");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("forge_marker", 1);

        JsonObject defaults = new JsonObject();
        defaults.addProperty("model", modelPath.getNamespace() + ":" + modelPath.getPath());

        JsonObject textures = new JsonObject();
        textures.addProperty("all", textureLocation.getNamespace() + ":block/" + textureLocation.getPath());
        defaults.add("textures", textures);

        defaults.addProperty("transform", "forge:default-block");
        root.add("defaults", defaults);

        JsonObject variants = new JsonObject();

        JsonArray empty = new JsonArray();
        empty.add(new JsonObject());

        variants.add("normal", empty);
        variants.add("inventory", empty);
        root.add("variants", variants);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve(identifier.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", identifier.getPath());
        }
    }*/

    public void genPlant(Identifier identifier, Identifier textureName) {
        genSimpleBlockstates(identifier);
        genPlantBlockModel(identifier, textureName);
        genFlatBlockItemModel(identifier, textureName);
    }

    public void genPlantBlockModel(Identifier identifier, Identifier textureName) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/cross");

        JsonObject textures = new JsonObject();
        textures.addProperty("cross", textureName.getNamespace() + ":block/" + textureName.getPath());
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genOrientedBlock(Identifier identifier, Identifier topTextureName, Identifier frontTextureName, Identifier sidesTextureName) {
       genOrientedBlockStates(identifier);
        genOrientedBlockModel(identifier, topTextureName, frontTextureName, sidesTextureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genOrientedBlockStates(Identifier identifier) {
        JsonObject root = new JsonObject();

        JsonObject variants = new JsonObject();

        JsonObject north = new JsonObject();
        north.addProperty("model", String.format("%s:block/%s", identifier.getNamespace(), identifier.getPath()));
        variants.add("facing=north", north);

        JsonObject south = new JsonObject();
        south.addProperty("model", String.format("%s:block/%s", identifier.getNamespace(), identifier.getPath()));
        south.addProperty("y", 180);
        variants.add("facing=south", south);

        JsonObject east = new JsonObject();
        east.addProperty("model", String.format("%s:block/%s", identifier.getNamespace(), identifier.getPath()));
        east.addProperty("y", 90);
        variants.add("facing=east", east);

        JsonObject west = new JsonObject();
        west.addProperty("model", String.format("%s:block/%s", identifier.getNamespace(), identifier.getPath()));
        west.addProperty("y", 270);
        variants.add("facing=west", west);

        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genOrientedBlockModel(Identifier identifier, Identifier topTextureName, Identifier frontTextureName, Identifier sidesTextureName) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/orientable");
        JsonObject textures = new JsonObject();
        textures.addProperty("top", topTextureName.getNamespace() + ":block/" + topTextureName.getPath());
        textures.addProperty("front", frontTextureName.getNamespace() + ":block/" + frontTextureName.getPath());
        textures.addProperty("side", sidesTextureName.getNamespace() + ":block/" + sidesTextureName.getPath());
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genLadderModel(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());
        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/ladder");
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genPillarBlock(Identifier identifier, Identifier endTextureName, Identifier sidesTextureName) {
        String model = identifier.getNamespace() + ":block/" + identifier.getPath();

        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        JsonObject axisY = new JsonObject();
        axisY.addProperty("model", model);
        variants.add("axis=y", axisY);

        JsonObject axisZ = new JsonObject();
        axisZ.addProperty("x", 90);
        axisZ.addProperty("model", model);
        variants.add("axis=z", axisZ);

        JsonObject axisX = new JsonObject();
        axisX.addProperty("x", 90);
        axisX.addProperty("y", 90);
        axisX.addProperty("model", model);
        variants.add("axis=x", axisX);

        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        genPillarBlockModel(identifier, endTextureName, sidesTextureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genPillarBlockModel(Identifier identifier, Identifier endTextureName, Identifier sidesTextureName) {
    	JsonObject textures = new JsonObject();
    	textures.addProperty("end", endTextureName.getNamespace() + ":block/" + endTextureName.getPath());
        textures.addProperty("side", sidesTextureName.getNamespace() + ":block/" + sidesTextureName.getPath());

        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/cube_column");

        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genBottomTopBlock(Identifier identifier, Identifier bottomTextureName, Identifier topTextureName, Identifier sideTextureName) {
        genSimpleBlockstates(identifier);
        genBottomTopBlockModel(identifier, bottomTextureName, topTextureName, sideTextureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genBottomTopBlockModel(Identifier identifier, Identifier bottomTextureName, Identifier topTextureName, Identifier sidesTextureName) {
    	JsonObject textures = new JsonObject();
        textures.addProperty("bottom", bottomTextureName.getNamespace() + ":block/" + bottomTextureName.getPath());
    	textures.addProperty("top", topTextureName.getNamespace() + ":block/" + topTextureName.getPath());
        textures.addProperty("side", sidesTextureName.getNamespace() + ":block/" + sidesTextureName.getPath());

        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/cube_bottom_top");

        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genPressurePlate(Identifier identifier, Identifier textureName) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        JsonObject modelUp = new JsonObject();
        modelUp.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath());
        JsonObject modelDown = new JsonObject();
        modelDown.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_down");

        variants.add("powered=false", modelUp);
        variants.add("powered=true", modelDown);
        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
        genPressurePlateBlockModel(identifier, textureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genPressurePlateBlockModel(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());

        JsonObject root = new JsonObject();
        root.add("textures", textures);

        root.addProperty("parent", "block/pressure_plate_up");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        root.addProperty("parent", "block/pressure_plate_down");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_down.json").toFile(), root);
    }

    public void genSlab(Identifier identifier, Identifier fullBlock, Identifier topTextureLocation, Identifier sideTextureLocation, Identifier bottomTextureLocation) {
        JsonObject root = new JsonObject();

        JsonObject variants = new JsonObject();

        JsonObject bottom = new JsonObject();
        bottom.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath());
        variants.add("type=bottom", bottom);

        JsonObject top = new JsonObject();
        top.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_top");
        variants.add("type=top", top);

        JsonObject doubleSlab = new JsonObject();
        doubleSlab.addProperty("model", fullBlock.getNamespace() + ":block/" + fullBlock.getPath());
        variants.add("type=double", doubleSlab);

        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        genSlabBlockModel(identifier, topTextureLocation, sideTextureLocation, bottomTextureLocation);
        genSimpleBlockItemModel(identifier);
    }

    /*public void genSlabColored(Identifier identifier, Identifier topTextureLocation, Identifier sideTextureLocation, Identifier bottomTextureLocation) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", identifier.getNamespace(), "blockstates");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("forge_marker", 1);

        JsonObject variants = new JsonObject();

        JsonObject half = new JsonObject();

        JsonObject upper = new JsonObject();
        upper.addProperty("model", identifier.getNamespace() + ":upper_" + identifier.getPath());
        half.add("top", upper);

        JsonObject lower = new JsonObject();
        lower.addProperty("model", identifier.getNamespace() + ":half_" + identifier.getPath());
        half.add("bottom", lower);

        variants.add("half", half);

        root.add("variants", variants);

        String json = gson.toJson(root);

        JsonObject root2 = new JsonObject();
        root2.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root2.addProperty("forge_marker", 1);

        JsonObject variants2 = new JsonObject();

        JsonObject prop = new JsonObject();

        JsonObject blarg = new JsonObject();
        blarg.addProperty("model", "neutronia_legacy:cube_all_colored");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", sideTextureLocation.toString());

        blarg.add("textures", textures);

        prop.add("blarg", blarg);

        variants2.add("prop", prop);

        root2.add("variants", variants2);

        String json2 = gson.toJson(root2);

        try {
            FileUtils.writeStringToFile(base.resolve(identifier.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
            FileUtils.writeStringToFile(base.resolve(identifier.getPath() + "_double.json").toFile(), StringEscapeUtils.unescapeJson(json2), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", identifier.getPath());
        }

        genSlabBlockModel(identifier, topTextureLocation, sideTextureLocation, bottomTextureLocation);
        genSlabItemModel(identifier.getNamespace(), identifier.getPath());

    }*/

    public void genSlabBlockModel(Identifier identifier, Identifier topTextureLocation, Identifier sideTextureLocation, Identifier bottomTextureLocation) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/slab");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", bottomTextureLocation.getNamespace() + ":block/" + bottomTextureLocation.getPath());
        textures.addProperty("side", sideTextureLocation.getNamespace() + ":block/" + sideTextureLocation.getPath());
        textures.addProperty("top", topTextureLocation.getNamespace() + ":block/" + topTextureLocation.getPath());
        root.add("textures", textures);

        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        root.addProperty("parent", "minecraft:block/slab_top");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_top.json").toFile(), root);
    }

    public void genSiding(Identifier identifier, Identifier baseIdentifier) {
        String text = JsonTemplates.SIDING_STATES.replace("derived_modid", identifier.getNamespace()).replace("block_model", identifier.getPath())
            .replace("base_modid", baseIdentifier.getNamespace()).replace("base_model", baseIdentifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genSidingModels(identifier, baseIdentifier);
        genSimpleBlockItemModel(identifier);
    }

    public void genSidingModels(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", textureName.getNamespace() + ":block/" + textureName.getPath());
        JsonObject root = new JsonObject();
        root.addProperty("parent", "neutronia:persistent/siding");
        root.add("textures", textures);
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
        root.addProperty("parent", "neutronia:persistent/siding_corner");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_corner.json").toFile(), root);
    }

    public void genChest(Identifier identifier, Identifier baseTextureName) {
    	genSimpleBlockstates(identifier);

        JsonObject textures = new JsonObject();
        textures.addProperty("particle", baseTextureName.getNamespace() + ":block/" + baseTextureName.getPath());
        JsonObject root = new JsonObject();
        root.add("textures", textures);
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        root.addProperty("parent", "item/chest");
        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genBarrel(Identifier identifier, Identifier baseTextureName) {
        String text = JsonTemplates.BARREL_STATES.replace("modid", identifier.getNamespace()).replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        Identifier topTextureName = new Identifier(baseTextureName.getNamespace(), baseTextureName.getPath() + "_top");
        Identifier topOpenTextureName = new Identifier(baseTextureName.getNamespace(), baseTextureName.getPath() + "_top_open");
        Identifier sideTextureName = new Identifier(baseTextureName.getNamespace(), baseTextureName.getPath() + "_side");
        Identifier bottomTextureName = new Identifier(baseTextureName.getNamespace(), baseTextureName.getPath() + "_bottom");

        genBottomTopBlockModel(identifier, bottomTextureName, topTextureName, sideTextureName);
        genBottomTopBlockModel(new Identifier(identifier.getNamespace(), identifier.getPath() + "_open"), bottomTextureName, topOpenTextureName, sideTextureName);

        genSimpleBlockItemModel(identifier);
    }

    /*public void genSlabColoredBlockModel(Identifier identifier, Identifier topTextureLocation, Identifier sideTextureLocation, Identifier bottomTextureLocation) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", identifier.getNamespace(), "models", "block");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("parent", "neutronia:block/slab");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", bottomTextureLocation.toString());
        textures.addProperty("side", sideTextureLocation.toString());
        textures.addProperty("top", topTextureLocation.toString());
        root.add("textures", textures);

        String json = gson.toJson(root);

        JsonObject root2 = new JsonObject();
        root2.addProperty("parent", "neutronia:block/slab_top");

        JsonObject textures2 = new JsonObject();
        textures2.addProperty("bottom", bottomTextureLocation.toString());
        textures2.addProperty("side", sideTextureLocation.toString());
        textures2.addProperty("top", topTextureLocation.toString());
        root2.add("textures", textures2);

        String json2 = gson.toJson(root2);

        try {
            FileUtils.writeStringToFile(base.resolve("half_" + identifier.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
            FileUtils.writeStringToFile(base.resolve("upper_" + identifier.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json2), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", identifier.getPath());
        }

    }*/
    /*public void genLayeredSlab(String modId, String blockName, Identifier mainTexture, Identifier overlayTexture) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", modId, "blockstates");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("forge_marker", 1);

        JsonObject variants = new JsonObject();

        JsonObject half = new JsonObject();

        JsonObject upper = new JsonObject();
        upper.addProperty("model", modId + ":upper_" + blockName);
        half.add("top", upper);

        JsonObject lower = new JsonObject();
        lower.addProperty("model", modId + ":half_" + blockName);
        half.add("bottom", lower);

        variants.add("half", half);

        root.add("variants", variants);

        String json = gson.toJson(root);

        JsonObject root2 = new JsonObject();
        root2.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root2.addProperty("forge_marker", 1);

        JsonObject variants2 = new JsonObject();

        JsonObject prop = new JsonObject();

        JsonObject blarg = new JsonObject();
        blarg.addProperty("model", modId + ":upper_" + blockName);

        prop.add("blarg", blarg);

        variants2.add("prop", prop);

        root2.add("variants", variants2);

        String json2 = gson.toJson(root2);

        try {
            FileUtils.writeStringToFile(base.resolve(blockName + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
            FileUtils.writeStringToFile(base.resolve(blockName + "_double.json").toFile(), StringEscapeUtils.unescapeJson(json2), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", blockName);
        }

        genLayeredSlabModel(modId, blockName, mainTexture, overlayTexture);
        genLayeredSlabItemModel(modId, blockName);

    }

    public void genLayeredSlabModel(String modId, String blockName, Identifier mainTexture, Identifier overlayTexture) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", modId, "models", "block");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("parent", new Identifier("neutronia_legacy", "block/cube_bottom_half_overlay_all").toString());

        JsonObject textures = new JsonObject();
        textures.addProperty("all", mainTexture.toString());
        textures.addProperty("overlay", overlayTexture.toString());
        root.add("textures", textures);

        String json = gson.toJson(root);

        JsonObject root2 = new JsonObject();
        root2.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root2.addProperty("parent", new Identifier("neutronia_legacy", "block/cube_top_half_overlay_all").toString());

        JsonObject textures2 = new JsonObject();
        textures2.addProperty("all", mainTexture.toString());
        textures2.addProperty("overlay", overlayTexture.toString());
        root2.add("textures", textures2);

        String json2 = gson.toJson(root2);

        try {
            FileUtils.writeStringToFile(base.resolve("half_" + blockName + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
            FileUtils.writeStringToFile(base.resolve("upper_" + blockName + ".json").toFile(), StringEscapeUtils.unescapeJson(json2), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", blockName);
        }

    }

    public void genLayeredSlabItemModel(String modId, String blockName) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", modId, "models", "item");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");
        root.addProperty("parent", modId + ":block/" + "half_" + blockName);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve(blockName + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", blockName);
        }

    }*/

    public void genFence(Identifier identifier, Identifier textureName, boolean isWall) {
        JsonObject root = new JsonObject();

        JsonArray multipart = new JsonArray();

        JsonObject pole = new JsonObject();

        JsonObject applyPost = new JsonObject();
        applyPost.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_post");
        pole.add("apply", applyPost);

        multipart.add(pole);

        JsonObject sideNorth = new JsonObject();

        JsonObject whenNorth = new JsonObject();
        whenNorth.addProperty("north", "true");
        sideNorth.add("when", whenNorth);

        JsonObject applyNorth = new JsonObject();
        applyNorth.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_side");
        applyNorth.addProperty("uvlock", true);
        sideNorth.add("apply", applyNorth);

        multipart.add(sideNorth);

        JsonObject sideEast = new JsonObject();

        JsonObject whenEast = new JsonObject();
        whenEast.addProperty("east", "true");
        sideEast.add("when", whenEast);

        JsonObject applyEast = new JsonObject();
        applyEast.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_side");
        applyEast.addProperty("uvlock", true);
        applyEast.addProperty("y", 90);
        sideEast.add("apply", applyEast);

        multipart.add(sideEast);

        JsonObject sideSouth = new JsonObject();

        JsonObject whenSouth = new JsonObject();
        whenSouth.addProperty("south", "true");
        sideSouth.add("when", whenSouth);

        JsonObject applySouth = new JsonObject();
        applySouth.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_side");
        applySouth.addProperty("uvlock", true);
        applySouth.addProperty("y", 180);
        sideSouth.add("apply", applySouth);

        multipart.add(sideSouth);

        JsonObject sideWest = new JsonObject();

        JsonObject whenWest = new JsonObject();
        whenWest.addProperty("west", "true");
        sideWest.add("when", whenWest);

        JsonObject applyWest = new JsonObject();
        applyWest.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_side");
        applyWest.addProperty("uvlock", true);
        applyWest.addProperty("y", 270);
        sideWest.add("apply", applyWest);

        multipart.add(sideWest);

        root.add("multipart", multipart);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        if(isWall)
            walls.add(identifier);
        else
            fences.add(identifier);

        genFenceModel(identifier, textureName, isWall);
        genBlockItemModel(identifier, new Identifier(identifier.getNamespace(), identifier.getPath() + "_inventory"));
    }

    public void genFenceModel(Identifier identifier, Identifier textureName, boolean isWall) {
        String blockType = isWall ? "template_wall" : "fence";
        JsonObject root = new JsonObject();

        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());
        root.add("textures", textures);

        root.addProperty("parent", "block/" + blockType + "_post");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_post.json").toFile(), root);

        root.addProperty("parent", "block/" + blockType + "_side");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_side.json").toFile(), root);

        root.addProperty("parent", "block/" + blockType + "_inventory");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_inventory.json").toFile(), root);
    }

    public void genFenceGate(Identifier identifier, Identifier textureName) {
        String text = JsonTemplates.FENCE_GATE_STATES.replace("modid", identifier.getNamespace()).replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genFenceGateModels(identifier, textureName);
        genSimpleBlockItemModel(identifier);
    }

    public void genFenceGateModels(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());
        JsonObject root = new JsonObject();
        root.add("textures", textures);

        root.addProperty("parent", "block/template_fence_gate");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
        root.addProperty("parent", "block/template_fence_gate_open");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_open.json").toFile(), root);
        root.addProperty("parent", "block/template_fence_gate_wall");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_wall.json").toFile(), root);
        root.addProperty("parent", "block/template_fence_gate_wall_open");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_wall_open.json").toFile(), root);
    }

    public void genDoor(Identifier identifier, Identifier bottomTextureName, Identifier topTextureName) {
        String text = JsonTemplates.DOOR_STATES.replace("modid", identifier.getNamespace()).replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genDoorModels(identifier, bottomTextureName, topTextureName);
        genItemModel(identifier, identifier);
    }

    public void genDoorModels(Identifier identifier, Identifier bottomTextureName, Identifier topTextureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", bottomTextureName.getNamespace() + ":block/" + bottomTextureName.getPath());
        textures.addProperty("top", topTextureName.getNamespace() + ":block/" + topTextureName.getPath());

        JsonObject root = new JsonObject();
        root.add("textures", textures);

        root.addProperty("parent", "block/door_bottom");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_bottom.json").toFile(), root);

        root.addProperty("parent", "block/door_bottom_rh");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_bottom_hinge.json").toFile(), root);

        root.addProperty("parent", "block/door_top");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_top.json").toFile(), root);

        root.addProperty("parent", "block/door_top_rh");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_top_hinge.json").toFile(), root);
    }

    public void genButton(Identifier identifier, Identifier textureName) {
        String text = JsonTemplates.BUTTON_STATES.replace("modid", identifier.getNamespace()).replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genButtonModels(identifier, textureName);
        genBlockItemModel(identifier, new Identifier(identifier.getNamespace(), identifier.getPath() + "_inventory"));
    }

    public void genButtonModels(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());

        JsonObject root = new JsonObject();
        root.add("textures", textures);

        root.addProperty("parent", "block/button");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        root.addProperty("parent", "block/button_pressed");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_pressed.json").toFile(), root);

        root.addProperty("parent", "block/button_inventory");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_inventory.json").toFile(), root);
    }

    public void genTrapdoor(Identifier identifier, Identifier textureName) {
        String text = JsonTemplates.TRAPDOOR_STATES.replace("modid", identifier.getNamespace()).replace("block_model", identifier.getPath());
        writeStringToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), text);

        genTrapdoorModels(identifier, textureName);
        genBlockItemModel(identifier, new Identifier(identifier.getNamespace(), identifier.getPath() + "_bottom"));
    }

    public void genTrapdoorModels(Identifier identifier, Identifier textureName) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", textureName.getNamespace() + ":block/" + textureName.getPath());

        JsonObject root = new JsonObject();
        root.add("textures", textures);

        root.addProperty("parent", "block/template_orientable_trapdoor_bottom");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_bottom.json").toFile(), root);

        root.addProperty("parent", "block/template_orientable_trapdoor_open");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_open.json").toFile(), root);

        root.addProperty("parent", "block/template_orientable_trapdoor_top");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_top.json").toFile(), root);
    }

    public void genSign(Identifier identifier, Identifier wallSignIdentifier, Identifier baseTextureName) {
        genSimpleBlockstates(identifier);

        JsonObject textures = new JsonObject();
        textures.addProperty("particle", baseTextureName.getNamespace() + ":blocks/" + baseTextureName.getPath());
        JsonObject root = new JsonObject();
        root.add("textures", textures);
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
        writeJsonToFile(getBlockModelsPath(wallSignIdentifier.getNamespace()).resolve(wallSignIdentifier.getPath() + ".json").toFile(), root);

        genItemModel(identifier, identifier);
    }

    public void genCake(int bites, Identifier identifier, Identifier bottomTextureName, Identifier sideTextureName, Identifier innerTextureName, Identifier topTextureName) {
        JsonObject variants = new JsonObject();
        for(int i = 0; i < bites; i++) {
            JsonObject model = new JsonObject();
            model.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_slice" + i);
            variants.add("bites=" + i, model);
        }
        JsonObject root = new JsonObject();
        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);

        genCakeModels(bites, identifier, bottomTextureName, sideTextureName, innerTextureName, topTextureName);
        genItemModel(identifier, identifier);
    }

    public void genCakeModels(int bites, Identifier identifier, Identifier bottomTextureName, Identifier sideTextureName, Identifier innerTextureName, Identifier topTextureName) {
        for(int i = 0; i < bites; i++) {
            double position = 14.0D / (double) bites * i + 1.0D;
            String text = JsonTemplates.CAKE_MODEL.replace("cake_side", sideTextureName.getNamespace() + ":block/" + sideTextureName.getPath())
                .replace("cake_bottom", bottomTextureName.getNamespace() + ":block/" + bottomTextureName.getPath())
                .replace("cake_top", topTextureName.getNamespace() + ":block/" + topTextureName.getPath())
                .replace("cake_inner", innerTextureName.getNamespace() + ":block/" + innerTextureName.getPath())
                .replace("slice_position", String.valueOf(position));
            if(i == 0)
                text = text.replace("#inside", "#side");
            writeStringToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + "_slice" + i + ".json").toFile(), text);
        }
    }

    public void genPottedBlock(Identifier identifier, Identifier baseTextureName) {
        genSimpleBlockstates(identifier);

        JsonObject textures = new JsonObject();
        textures.addProperty("plant", baseTextureName.getNamespace() + ":block/" + baseTextureName.getPath());
        JsonObject root = new JsonObject();
        root.add("textures", textures);
        root.addProperty("parent", "block/flower_pot_cross");
        writeJsonToFile(getBlockModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genBush(Identifier identifier, Identifier baseTextureName) {
        JsonObject variants = new JsonObject();
        for(int i = 0; i < 4; i++) {
            JsonObject age = new JsonObject();
            age.addProperty("model", identifier.getNamespace() + ":block/" + identifier.getPath() + "_stage" + i);
            variants.add("age=" + i, age);

            genPlantBlockModel(identifier, new Identifier(baseTextureName.getNamespace(), baseTextureName.getNamespace() + "_stage" + i));
        }
        JsonObject root = new JsonObject();
        root.add("variants", variants);

        writeJsonToFile(getBlockStatesPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genItemModel(Identifier identifier, Identifier textureName) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", textureName.getNamespace() + ":item/" + textureName.getPath());
        root.add("textures", textures);
        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    public void genToolModel(Identifier identifier, Identifier textureName) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "item/handheld");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", textureName.getNamespace() + ":item/" + textureName.getPath());
        root.add("textures", textures);
        writeJsonToFile(getItemModelsPath(identifier.getNamespace()).resolve(identifier.getPath() + ".json").toFile(), root);
    }

    /*private void genLangFile(String modid, String block_name, String unlocalized_name, String lang_file_name) {
        Path base = Paths.get("src", "main", "resources", "assets", modid, "lang");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }
        try (BufferedWriter w = Files.newBufferedWriter(base.resolve(String.format("%s.lang", lang_file_name)), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String name = unlocalized_name.replace("_", " ");
            unlocalized_name = WordUtils.capitalizeFully(name);
            w.write("tile." + modid + ":" + block_name + ".name=" + unlocalized_name + "\n");
        } catch (IOException ignored) {
            System.out.printf("Error creating file %s.json" + "\n", lang_file_name);
        }
    }*/

    public void genAdvancementRootJson(String modId, String advancement_name, String item_name, String title, String desc, String background_texture_name, boolean show_toast, boolean announce_to_chat, boolean hidden) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", modId, "advancements");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");

        JsonObject display = new JsonObject();

        JsonObject icon = new JsonObject();
        icon.addProperty("item", modId + ":" + item_name);
        display.add("icon", icon);

        JsonObject titleObject = new JsonObject();
        titleObject.addProperty("translate", modId + ".advancements." + title);
        display.add("title", titleObject);

        JsonObject descObject = new JsonObject();
        descObject.addProperty("translate", modId + ".advancements." + desc + ".desc");
        display.add("description", descObject);

        display.addProperty("background", modId + ":textures/advancements/" + background_texture_name + ".png");
        display.addProperty("show_toast", show_toast);
        display.addProperty("announce_to_chat", announce_to_chat);
        display.addProperty("hidden", hidden);

        JsonObject criteria = new JsonObject();

        root.add("display", display);
        root.add("criteria", criteria);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve("root.json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", advancement_name);
        }

    }

    public void genAdvancementJson(String modId, String advancement_name, String item_name, String title, String desc, String background_texture_name, boolean show_toast, boolean announce_to_chat, boolean hidden) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("src", "main", "resources", "assets", modId, "advancements");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();
        root.addProperty("_comment", "Generated using Husky's JSON Generator v4.");

        JsonObject display = new JsonObject();

        JsonObject icon = new JsonObject();
        icon.addProperty("item", modId + ":" + item_name);
        display.add("icon", icon);

        JsonObject titleObject = new JsonObject();
        titleObject.addProperty("translate", modId + ".advancements." + title);
        display.add("title", titleObject);

        JsonObject descObject = new JsonObject();
        descObject.addProperty("translate", modId + ".advancements." + desc + ".desc");
        display.add("description", descObject);

        display.addProperty("background", modId + ":textures/advancements/" + background_texture_name + ".png");
        display.addProperty("show_toast", show_toast);
        display.addProperty("announce_to_chat", announce_to_chat);
        display.addProperty("hidden", hidden);

        JsonObject criteria = new JsonObject();

        root.add("display", display);
        root.addProperty("parent", modId + ":" + "root");
        root.add("criteria", criteria);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve("root.json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating file %s.json" + "\n", advancement_name);
        }

    }

    public void genSimpleLootTable(Identifier block, Identifier... items) {
    	File file = getLootTablesPath(block.getNamespace()).resolve("blocks").resolve(block.getPath() + ".json").toFile();
    	if(!file.exists()) {
    	    file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(file));
            jsonWriter.beginObject();
            jsonWriter.name("type"); jsonWriter.value("minecraft:block");
            jsonWriter.name("pools");
            jsonWriter.beginArray();
            for(Identifier item : items) {
                jsonWriter.beginObject();
                jsonWriter.name("rolls");
                jsonWriter.value(1);
                jsonWriter.name("entries");
                jsonWriter.beginArray();
                jsonWriter.beginObject();
                jsonWriter.name("type");
                jsonWriter.value("minecraft:item");
                jsonWriter.name("name");
                jsonWriter.value(item.toString());
                jsonWriter.endObject();
                jsonWriter.endArray();
                jsonWriter.name("conditions");
                jsonWriter.beginArray();
                jsonWriter.beginObject();
                jsonWriter.name("condition");
                jsonWriter.value("minecraft:survives_explosion");
                jsonWriter.endObject();
                jsonWriter.endArray();
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genPackMcMeta(String description) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path base = Paths.get("resourcepacks", "Neutronia");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }

        JsonObject root = new JsonObject();

        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", 4);
        pack.addProperty("description", description);

        root.add("pack", pack);

        String json = gson.toJson(root);

        try {
            FileUtils.writeStringToFile(base.resolve("pack.mcmeta").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.println("Error creating file pack.mcmeta");
        }

    }
}
