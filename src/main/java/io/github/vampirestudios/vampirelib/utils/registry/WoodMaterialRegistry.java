package io.github.vampirestudios.vampirelib.utils.registry;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.utils.WoodMaterial;

public abstract class WoodMaterialRegistry implements StringRepresentable {
	public static final Registry<WoodMaterial> WOOD_MATERIALS = FabricRegistryBuilder.create(WoodMaterial.class,
		VampireLib.INSTANCE.identifier("wood_material_registry")
	).buildAndRegister();

	private static final Queue<ModdedTypeListener> listeners = new ConcurrentLinkedQueue<>();

	public static WoodMaterial get(Identifier id) {
		return WOOD_MATERIALS.getValue(id);
	}

	public static boolean contains(Identifier id) {
		return WOOD_MATERIALS.containsKey(id);
	}

	public static boolean contains(WoodMaterial woodMaterial) {
		return WOOD_MATERIALS.getKey(woodMaterial) != null;
	}

	public static WoodMaterial registerVanilla(WoodMaterial woodMaterial) {
		return Registry.register(WOOD_MATERIALS, woodMaterial.resourceLocation(), woodMaterial);
	}

	public static WoodMaterial registerModded(WoodMaterial woodMaterial) {
		registerVanilla(woodMaterial);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodMaterial));
		return woodMaterial;
	}

	public static WoodMaterial registerModded(WoodRegistry woodRegistry, WoodType woodType) {
		WoodMaterial woodMaterial = new WoodMaterial(woodRegistry.name(), woodType, woodRegistry.leaves(), woodRegistry.log(), woodRegistry.isNetherWood());
		registerVanilla(woodMaterial);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodMaterial));
		return woodMaterial;
	}

	public static WoodMaterial registerModded(WoodRegistry woodRegistry, Block leaves, WoodType woodType) {
		WoodMaterial woodMaterial = new WoodMaterial(woodRegistry.name(), woodType, leaves, woodRegistry.log(), woodRegistry.isNetherWood());
		registerVanilla(woodMaterial);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodMaterial));
		return woodMaterial;
	}

	public static WoodMaterial registerModded(WoodRegistry woodRegistry) {
		WoodMaterial woodMaterial = new WoodMaterial(woodRegistry.name(), null, woodRegistry.leaves(), woodRegistry.log(), woodRegistry.isNetherWood());
		registerVanilla(woodMaterial);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodMaterial));
		return woodMaterial;
	}

	public static WoodMaterial registerModded(WoodRegistry woodRegistry, Block leaves) {
		WoodMaterial woodMaterial = new WoodMaterial(woodRegistry.name(), null, leaves, woodRegistry.log(), woodRegistry.isNetherWood());
		registerVanilla(woodMaterial);
		listeners.forEach(listener -> listener.onModdedWoodTypeRegistered(woodMaterial));
		return woodMaterial;
	}

	public static void registerModdedTypeListener(ModdedTypeListener listener) {
		listeners.add(listener);
	}

	public interface ModdedTypeListener {
		void onModdedWoodTypeRegistered(WoodMaterial woodMaterial);
	}

}
