package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	private final ResourceLocation texture;

	public TerraformWallSignBlock(ResourceLocation texture, Properties settings) {
		super(settings, WoodType.OAK); //TODO: take a look at this again
		this.texture = texture;
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
}