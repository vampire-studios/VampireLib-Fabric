package io.github.vampirestudios.vampirelib.impl.sign.block;

import io.github.vampirestudios.vampirelib.impl.sign.TerraformSign;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	private final ResourceLocation texture;

	public TerraformSignBlock(ResourceLocation texture, Properties settings) {
		super(settings, WoodType.OAK);
		this.texture = texture;
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
}
