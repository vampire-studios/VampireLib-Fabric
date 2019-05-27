package team.abnormals.abnormalib.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import team.abnormals.abnormalib.api.Climbable;

public class TrapdoorBaseBlock extends TrapdoorBlock implements Climbable {
    public TrapdoorBaseBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    /**
     * Determines if the passed LivingEntity can climb this block.
     *
     * @param entity The LivingEntity that is attempting to climb this block.
     * @param state  The block state of the ladder being climbed.
     * @param pos    The position of the block.
     */
    @Override
    public ClimbBehavior canClimb(LivingEntity entity, BlockState state, BlockPos pos) {
        return ClimbBehavior.True;
    }

}
