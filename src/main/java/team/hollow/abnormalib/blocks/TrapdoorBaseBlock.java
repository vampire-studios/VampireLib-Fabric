package team.hollow.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import team.hollow.abnormalib.api.Climbable;

public class TrapdoorBaseBlock extends TrapdoorBlock implements Climbable {

    public TrapdoorBaseBlock() {
        super(FabricBlockSettings.of(Material.WOOD).strength(3.0F, 1.0F).build());
    }

    public TrapdoorBaseBlock(Material material) {
        super(FabricBlockSettings.of(material).strength(3.0F, 10F).friction(material == Material.ICE || material == Material.PACKED_ICE ? 0.98F : 0.0F).build());
    }

    /**
     * Determines if the passed LivingEntity can climb this block.
     *
     * @param entity The LivingEntity that is attempting to climb this block.
     * @param state  The block state of the ladder being climbed.
     * @param pos    The position of the block.
     */
    @Override
    public boolean canClimb(LivingEntity entity, BlockState state, BlockPos pos) {
        return true;
    }
}
