package team.abnormals.abnormalib.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.ViewableWorld;

public class RodBaseBlock extends FacingBlock {

    protected static final VoxelShape BB_AXIS_Y = Block.createCuboidShape(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    protected static final VoxelShape BB_AXIS_Z = Block.createCuboidShape(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
    protected static final VoxelShape BB_AXIS_X = Block.createCuboidShape(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

    @SuppressWarnings("unused")
    public RodBaseBlock(boolean emitsLight) {
        super(emitsLight ? FabricBlockSettings.of(Material.STONE).strength(0.3F, 1.0F).lightLevel(13).build() : FabricBlockSettings.of(Material.STONE).build());
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
    }

    @SuppressWarnings("unused")
    public RodBaseBlock(Material material, boolean emitsLight) {
        super(emitsLight ? FabricBlockSettings.of(material).hardness(0.3F).lightLevel(13).build() : FabricBlockSettings.of(material).build());
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
    }

    public BlockState rotate(BlockState blockState_1, BlockRotation rotation_1) {
        return blockState_1.with(FACING, rotation_1.rotate(blockState_1.get(FACING)));
    }

    public BlockState mirror(BlockState blockState_1, BlockMirror mirror_1) {
        return blockState_1.with(FACING, mirror_1.apply(blockState_1.get(FACING)));
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        switch (blockState_1.get(FACING).getAxis()) {
            case X:
            default:
                return BB_AXIS_X;
            case Z:
                return BB_AXIS_Z;
            case Y:
                return BB_AXIS_Y;
        }
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean canPlaceAt(BlockState blockState_1, ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        Direction direction_1 = itemPlacementContext_1.getPlayerFacing();
        BlockState blockState_1 = itemPlacementContext_1.getWorld().getBlockState(itemPlacementContext_1.getBlockPos().offset(direction_1.getOpposite()));
        return blockState_1.getBlock() == this && blockState_1.get(FACING) == direction_1 ? this.getDefaultState().with(FACING, direction_1.getOpposite()) : this.getDefaultState().with(FACING, direction_1);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING);
    }

}