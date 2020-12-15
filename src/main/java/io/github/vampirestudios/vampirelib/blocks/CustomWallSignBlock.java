package io.github.vampirestudios.vampirelib.blocks;

import io.github.vampirestudios.vampirelib.api.CustomSign;
import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class CustomWallSignBlock extends WallSignBlock implements CustomSign {
    private final Identifier texture;

    public CustomWallSignBlock(Identifier texture, Settings settings) {
        super(settings, SignType.OAK);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture() {
        return texture;
    }
}