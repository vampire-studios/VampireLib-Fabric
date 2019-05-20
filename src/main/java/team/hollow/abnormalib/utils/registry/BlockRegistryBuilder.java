package team.hollow.abnormalib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import team.hollow.abnormalib.blocks.*;

public class BlockRegistryBuilder {

    /**
     * Returns a new instance of this class
     */
    private static final ThreadLocal<BlockRegistryBuilder> INSTANCE = ThreadLocal.withInitial(BlockRegistryBuilder::new);

    /**
     * The base block of this registry
     */
    private static Block baseBlock;

    /**
     * Name that will be used as a base for all of the blocks
     */
    private static Identifier name;

    /**
     * @param nameIn The name that will be used as a base for all blocks
     * @param blockIn the base block which will be used for some blocks
     * @return a new instance of this class
     */
    public static BlockRegistryBuilder getInstance(Identifier nameIn, Block blockIn) {
        name = nameIn;
        baseBlock = blockIn;
        return INSTANCE.get();
    }

    /**
     * Adds a new slab block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder slab() {
        Block slab = new SlabBaseBlock();
        RegistryUtils.register(slab, new Identifier(name.getNamespace(), name.getPath() + "_slab"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    /**
     * Adds a new stair block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder stair() {
        Block stair = new StairsBaseBlock(baseBlock.getDefaultState());
        RegistryUtils.register(stair, new Identifier(name.getNamespace(), name.getPath() + name + "_stairs"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    /**
     * Adds a new fence block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder fence() {
        Block fence = new FenceBaseBlock(baseBlock.getDefaultState());
        RegistryUtils.register(fence, new Identifier(name.getNamespace(), name.getPath() + name + "_fence"));
        return this;
    }

    /**
     * Adds a new fence gate block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder fenceGate() {
        RegistryUtils.register(new FenceGateBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + name + "_fence_gate"), ItemGroup.REDSTONE);
        return this;
    }

    /**
     * Adds a new wall block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder wall() {
        Block wall = new WallBaseBlock(baseBlock.getDefaultState());
        RegistryUtils.register(wall, new Identifier(name.getNamespace(), name.getPath() + name + "_wall"), ItemGroup.DECORATIONS);
        return this;
    }

    /**
     * Adds a new button block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder button(boolean wooden) {
        Block button = new ButtonBaseBlock(wooden);
        RegistryUtils.register(button, new Identifier(name.getNamespace(), name.getPath() + name + "_button"), ItemGroup.REDSTONE);
        return this;
    }

    /**
     * Adds a new pressure plate block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder pressurePlate(PressurePlateBlock.ActivationRule type) {
        RegistryUtils.register(new PressurePlateBaseBlock(baseBlock.getMaterial(baseBlock.getDefaultState()), type),
                new Identifier(name.getNamespace(), name.getPath() + name + "_pressure_plate"), ItemGroup.REDSTONE);
        return this;
    }

    /**
     * Adds a new corner block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder corner() {
        Block corner = new CornerBaseBlock(baseBlock.getDefaultState(), Block.Settings.copy(baseBlock));
        RegistryUtils.register(corner, new Identifier(name.getNamespace(), name.getPath() + name + "_corner"));
        return this;
    }

    /**
     * Adds a new post block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder post() {
        Block post = new PostBaseBlock(Block.Settings.copy(baseBlock));
        RegistryUtils.register(post, new Identifier(name.getNamespace(), name.getPath() + name + "_post"));
        return this;
    }

    /**
     * Adds a new siding block based on the base block properties
     * @return an instance of this class
     */
    public BlockRegistryBuilder siding() {
        Block siding = new SidingBaseBlock(Block.Settings.copy(baseBlock));
        RegistryUtils.register(siding, new Identifier(name.getNamespace(), name.getPath() + name + "_siding"));
        return this;
    }

}