package io.github.vampirestudios.vampirelib.loot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.VampireLib;

public class LootModifierManager extends SimpleJsonResourceReloadListener { // FIXME this probably needs to be registered!
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON_INSTANCE = Deserializers.createFunctionSerializer().create();
	private static final String folder = "loot_modifiers";
	public static MappedRegistry<GlobalLootModifierSerializer> SERIALIZER = FabricRegistryBuilder.createSimple(GlobalLootModifierSerializer.class, VampireLib.INSTANCE.identifier("loot_modifier")).buildAndRegister();
	private Map<ResourceLocation, IGlobalLootModifier> registeredLootModifiers = ImmutableMap.of();

	public LootModifierManager() {
		super(GSON_INSTANCE, folder);
	}

	public static GlobalLootModifierSerializer<?> getSerializerForName(ResourceLocation resourcelocation) {
		return SERIALIZER.get(resourcelocation);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> resourceList, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
		ImmutableMap.Builder<ResourceLocation, IGlobalLootModifier> builder = ImmutableMap.builder();
		ArrayList<ResourceLocation> finalLocations = new ArrayList<ResourceLocation>();
		ResourceLocation resourcelocation = new ResourceLocation("vampirelib","loot_modifiers/global_loot_modifiers.json");
		//read in all data files from forge:loot_modifiers/global_loot_modifiers in order to do layering
		for(Resource iresource : resourceManagerIn.getResourceStack(resourcelocation)) {
			try (InputStream inputstream = iresource.open();
				 Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8))
			) {
				JsonObject jsonobject = GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);
				boolean replace = jsonobject.get("replace").getAsBoolean();
				if(replace) finalLocations.clear();
				JsonArray entryList = jsonobject.get("entries").getAsJsonArray();
				for(JsonElement entry : entryList) {
					String loc = entry.getAsString();
					ResourceLocation res = new ResourceLocation(loc);
					if(finalLocations.contains(res)) finalLocations.remove(res);
					finalLocations.add(res);
				}
			} catch (RuntimeException | IOException ioexception) {
				LOGGER.error("Couldn't read global loot modifier list {} in data pack {}", resourcelocation, iresource.sourcePackId(), ioexception);
			}
		}
		//use layered config to fetch modifier data files (modifiers missing from config are disabled)
		finalLocations.forEach(location -> {
			try {
				IGlobalLootModifier modifier = deserializeModifier(location, resourceList.get(location));
				if(modifier != null) builder.put(location, modifier);
			} catch (Exception exception) {
				LOGGER.error("Couldn't parse loot modifier {}", location, exception);
			}
		});
		this.registeredLootModifiers = builder.build();
	}

	private IGlobalLootModifier deserializeModifier(ResourceLocation location, JsonElement element) {
		if (!element.isJsonObject()) return null;
		JsonObject object = element.getAsJsonObject();
		LootItemCondition[] lootConditions = GSON_INSTANCE.fromJson(object.get("conditions"), LootItemCondition[].class);

		// For backward compatibility with the initial implementation, fall back to using the location as the type.
		// TODO: Remove fallback in 1.16
		ResourceLocation serializer = location;
		if (object.has("type")) {
			serializer = new ResourceLocation(GsonHelper.getAsString(object, "type"));
		}

		return SERIALIZER.get(serializer).read(location, object, lootConditions);
	}

	/**
	 * An immutable collection of the registered loot modifiers in layered order.
	 *
	 * @return
	 */
	public Collection<IGlobalLootModifier> getAllLootMods() {
		return registeredLootModifiers.values();
	}

}