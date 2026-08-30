package io.github.vampirestudios.vampirelib;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;

public class TestWoodTypes {
    public static final Set<WoodType> WOOD_TYPES = new HashSet<>();

    public static final WoodType TEST_NETHER_WOOD_1 = registerWoodType("test_nether_wood_1",
            TestBlockSetType.TEST_NETHER_WOOD_1, SoundType.NETHER_WOOD,
            SoundType.NETHER_WOOD_HANGING_SIGN,
            SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE,
            SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN);

    private static WoodType registerWoodType(String path, BlockSetType blockSetType, SoundType soundType, SoundType hangingSignSoundType, SoundEvent fenceGateCloseSound, SoundEvent fenceGateOpenSound) {
        WoodType woodType = WoodTypeBuilder.copyOf(WoodType.CRIMSON)
			.soundType(soundType)
			.hangingSignSoundType(hangingSignSoundType)
			.fenceGateCloseSound(fenceGateCloseSound)
			.fenceGateOpenSound(fenceGateOpenSound)
			.register(VampireLib.INSTANCE.identifier(path), blockSetType);
		WOOD_TYPES.add(woodType);
		return woodType;
    }
}
