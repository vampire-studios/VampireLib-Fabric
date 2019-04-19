package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.hollow.abnormalib.api.Climbable;

public class CustomLadderBlock extends LadderBlock implements Climbable {

    public CustomLadderBlock() {
        super(FabricBlockSettings.of(Material.PART).hardness(0.4F).sounds(BlockSoundGroup.LADDER).build());
        setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public boolean canClimb(LivingEntity entity, BlockState state, BlockPos pos) {
        return true;
    }

}