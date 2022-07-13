package io.github.vampirestudios.vampirelib.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.loot.IGlobalLootModifier;
import io.github.vampirestudios.vampirelib.utils.blendfunctions.BlendingFunction;
import io.github.vampirestudios.vampirelib.utils.registry.ResourceLocationUtils;

public class VRegistries {

    public static ResourceKey<Registry<Codec<? extends BlendingFunction>>> BLENDING_FUNCTION_RESOURCE_KEY;
    public static Registry<Codec<? extends BlendingFunction>> BLENDING_FUNCTION;
    public static ResourceKey<Registry<Codec<? extends IGlobalLootModifier>>> LOOT_MODIFIER_SERIALIZERS_RESOURCE_KEY;
    public static Registry<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS;

    public static void init() {
        ResourceLocationUtils.setModInstance(VampireLib.INSTANCE);
        BLENDING_FUNCTION_RESOURCE_KEY = ResourceKey.createRegistryKey(ResourceLocationUtils.modId("blending_function"));
        BLENDING_FUNCTION = Registry.registerSimple(BLENDING_FUNCTION_RESOURCE_KEY, Lifecycle.stable(), registry -> BlendingFunction.CODEC);
        LOOT_MODIFIER_SERIALIZERS_RESOURCE_KEY = ResourceKey.createRegistryKey(ResourceLocationUtils.modId("loot_modifier"));
        LOOT_MODIFIER_SERIALIZERS = Registry.registerSimple(LOOT_MODIFIER_SERIALIZERS_RESOURCE_KEY, Lifecycle.stable(), registry -> IGlobalLootModifier.DIRECT_CODEC);
    }

}
