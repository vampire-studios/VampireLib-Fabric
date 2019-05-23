package team.hollow.abnormalib.utils.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringEscapeUtils;
import team.hollow.abnormalib.utils.generators.recipe.ShapedRecipeIngredient;
import team.hollow.abnormalib.utils.generators.recipe.ShapelessRecipeIngredient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecipeGenerator {

    public static final RecipeGenerator INSTANCE = new RecipeGenerator();
    private static String modid;

    public static RecipeGenerator getInstance(String id) {
        modid = id;
        return INSTANCE;
    }

    public RecipeGenerator addShaped(ItemStack result, Identifier recipeName, String group, String[] rows, ShapedRecipeIngredient... shapedRecipeIngredients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path base = Paths.get("src", "main", "resources", "data", recipeName.getNamespace(), "recipes");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:crafting_shaped");
        if (!group.equalsIgnoreCase("")) root.addProperty("group", group);
        JsonArray pattern = new JsonArray();
        for (String row : rows) {
            if (!row.equalsIgnoreCase(" ")) pattern.add(row);
        }
        root.add("pattern", pattern);
        JsonObject key = new JsonObject();
        for(ShapedRecipeIngredient shapedRecipeIngredient1 : shapedRecipeIngredients) {
            if (!shapedRecipeIngredient1.getPattern().equalsIgnoreCase("")) {
                JsonObject item = new JsonObject();
                item.addProperty("item", Registry.ITEM.getId(shapedRecipeIngredient1.getStack().getItem()).toString());
                key.add(shapedRecipeIngredient1.getPattern(), item);
            }
        }
        root.add("key", key);
        JsonObject resultName = new JsonObject();
        resultName.addProperty("item", Registry.ITEM.getId(result.getItem()).toString());
        if (result.getAmount() > 1) resultName.addProperty("count", result.getAmount());
        root.add("result", resultName);
        String json = gson.toJson(root);
        try {
            if (!base.resolve(recipeName.getPath() + ".json").toFile().exists())
                FileUtils.writeStringToFile(base.resolve(recipeName.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating recipe file %s.json" + "\n", recipeName.getPath());
        }
        return this;
    }

    public void addShapeless(ItemStack result, Identifier recipeName, String group, ShapelessRecipeIngredient... shapelessRecipeIngredients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path base = Paths.get("src", "main", "resources", "data", recipeName.getNamespace(), "recipes");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:crafting_shapeless");
        if (!group.equalsIgnoreCase("")) root.addProperty("group", group);
        JsonArray ingredients = new JsonArray();
        JsonObject item = new JsonObject();
        for(ShapelessRecipeIngredient shapelessRecipeIngredient1 : shapelessRecipeIngredients) {
            item.addProperty("item", Registry.ITEM.getId(shapelessRecipeIngredient1.getStack().getItem()).toString());
        }
        ingredients.add(item);
        root.add("ingredients", ingredients);
        JsonObject resultName = new JsonObject();
        resultName.addProperty("item", Registry.ITEM.getId(result.getItem()).toString());
        if (result.getAmount() > 1) resultName.addProperty("count", result.getAmount());
        root.add("result", resultName);
        String json = gson.toJson(root);
        try {
            if (!base.resolve(recipeName.getPath() + ".json").toFile().exists())
                FileUtils.writeStringToFile(base.resolve(recipeName.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating recipe file %s.json" + "\n", recipeName.getPath());
        }
    }

    public RecipeGenerator addStonecutting(ItemStack result, Identifier recipeName, String group, ShapelessRecipeIngredient shapelessRecipeIngredient) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path base = Paths.get("src", "main", "resources", "data", recipeName.getNamespace(), "recipes");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:stonecutting");
        if (!group.equalsIgnoreCase("")) root.addProperty("group", group);
        JsonObject ingredients = new JsonObject();
        ingredients.addProperty("item", Registry.ITEM.getId(shapelessRecipeIngredient.getStack().getItem()).toString());
        root.add("ingredient", ingredients);
        root.addProperty("result", Registry.ITEM.getId(result.getItem()).toString());
        if (result.getAmount() > 1) root.addProperty("count", result.getAmount());
        String json = gson.toJson(root);
        try {
            if (!base.resolve(recipeName.getPath() + ".json").toFile().exists())
                FileUtils.writeStringToFile(base.resolve(recipeName.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating recipe file %s.json" + "\n", recipeName.getPath());
        }
        return this;
    }

    public RecipeGenerator addSmelting(ItemStack result, Identifier recipeName, String group, ShapelessRecipeIngredient... shapelessRecipeIngredients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path base = Paths.get("src", "main", "resources", "data", recipeName.getNamespace(), "recipes");
        if (!base.toFile().exists()) {
            base.toFile().mkdirs();
        }
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:smelting");
        if (!group.equalsIgnoreCase("")) root.addProperty("group", group);
        JsonObject key = new JsonObject();
        JsonArray ingredients = new JsonArray();
        JsonObject item = new JsonObject();
        for(ShapelessRecipeIngredient shapelessRecipeIngredient1 : shapelessRecipeIngredients) {
            item.addProperty("item", Registry.ITEM.getId(shapelessRecipeIngredient1.getStack().getItem()).toString());
        }
        ingredients.add(item);
        root.add("key", key);
        JsonObject resultName = new JsonObject();
        resultName.addProperty("item", Registry.ITEM.getId(result.getItem()).toString());
        if (result.getAmount() > 1) resultName.addProperty("count", result.getAmount());
        root.add("result", resultName);
        String json = gson.toJson(root);
        try {
            if (!base.resolve(recipeName.getPath() + ".json").toFile().exists())
                FileUtils.writeStringToFile(base.resolve(recipeName.getPath() + ".json").toFile(), StringEscapeUtils.unescapeJson(json), CharEncoding.UTF_8);
        } catch (IOException e) {
            System.out.printf("Error creating recipe file %s.json" + "\n", recipeName.getPath());
        }
        return this;
    }

}
