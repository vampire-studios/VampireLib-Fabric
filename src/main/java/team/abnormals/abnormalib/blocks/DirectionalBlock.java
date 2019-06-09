package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;

public abstract class DirectionalBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.of("facing");

    public DirectionalBlock(FabricBlockSettings builder) {
        super(builder.build());
    }

}