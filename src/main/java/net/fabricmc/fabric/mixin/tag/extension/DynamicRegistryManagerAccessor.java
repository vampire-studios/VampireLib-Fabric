package net.fabricmc.fabric.mixin.tag.extension;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(DynamicRegistryManager.class)
public interface DynamicRegistryManagerAccessor {
	@Accessor("INFOS")
	static Map<RegistryKey<? extends Registry<?>>, ?> getInfos() {
		throw new AssertionError();
	}
}