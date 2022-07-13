package io.github.vampirestudios.vampirelib.loot;

import java.util.function.Function;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import io.github.vampirestudios.vampirelib.init.VRegistries;

/**
 * Implementation that defines what a global loot modifier must implement in order to be functional.
 * {@link LootModifier} Supplies base functionality; most modders should only need to extend that.<br/>
 * Requires a {@link Codec} to be registered: {@link io.github.vampirestudios.vampirelib.init.VRegistries#LOOT_MODIFIER_SERIALIZERS}, and returned in {@link #codec()}
 * Individual instances of modifiers must be registered via json, see forge:loot_modifiers/global_loot_modifiers
 */
public interface IGlobalLootModifier {
	Codec<IGlobalLootModifier> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> VRegistries.LOOT_MODIFIER_SERIALIZERS.byNameCodec())
		.dispatch(IGlobalLootModifier::codec, Function.identity());

	Codec<LootItemCondition[]> LOOT_CONDITIONS_CODEC = Codec.PASSTHROUGH.flatXmap(
		d ->
		{
			try
			{
				LootItemCondition[] conditions = LootModifierManager.GSON_INSTANCE.fromJson(getJson(d), LootItemCondition[].class);
				return DataResult.success(conditions);
			}
			catch (JsonSyntaxException e)
			{
				LootModifierManager.LOGGER.warn("Unable to decode loot conditions", e);
				return DataResult.error(e.getMessage());
			}
		},
		conditions ->
		{
			try
			{
				JsonElement element = LootModifierManager.GSON_INSTANCE.toJsonTree(conditions);
				return DataResult.success(new Dynamic<>(JsonOps.INSTANCE, element));
			}
			catch (JsonSyntaxException e)
			{
				LootModifierManager.LOGGER.warn("Unable to encode loot conditions", e);
				return DataResult.error(e.getMessage());
			}
		}
	);

	@SuppressWarnings("unchecked")
	static <U> JsonElement getJson(Dynamic<?> dynamic) {
		Dynamic<U> typed = (Dynamic<U>) dynamic;
		return typed.getValue() instanceof JsonElement ?
			(JsonElement) typed.getValue() :
			typed.getOps().convertTo(JsonOps.INSTANCE, typed.getValue());
	}

	@NotNull
	ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context);

	/**
	 * Returns the registered codec for this modifier
	 */
	Codec<? extends IGlobalLootModifier> codec();
}