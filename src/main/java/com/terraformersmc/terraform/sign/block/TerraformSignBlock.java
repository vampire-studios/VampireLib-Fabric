package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TerraformSignBlock extends StandingSignBlock implements TerraformSign {
	private final ResourceLocation texture;

	public TerraformSignBlock(ResourceLocation texture, Properties settings) {
		super(settings, WoodType.OAK);
		this.texture = texture;
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
}