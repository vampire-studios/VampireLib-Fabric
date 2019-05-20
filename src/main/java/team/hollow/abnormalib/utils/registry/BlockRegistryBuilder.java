package team.hollow.abnormalib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import team.hollow.abnormalib.blocks.*;

public class BlockRegistryBuilder {

    public static final BlockRegistryBuilder INSTANCE = new BlockRegistryBuilder();
    private static Block baseBlock;
    private static Identifier name;

    public static BlockRegistryBuilder getInstance(Identifier nameIn, Block blockIn) {
        name = nameIn;
        baseBlock = blockIn;
        return INSTANCE;
    }

    public BlockRegistryBuilder slab() {
        RegistryUtils.register(new SlabBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_slab"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    public BlockRegistryBuilder stair() {
        RegistryUtils.register(new StairsBaseBlock(baseBlock.getDefaultState()), new Identifier(name.getNamespace(), name.getPath() + "_stairs"), ItemGroup.BUILDING_BLOCKS);
        return this;
    }

    public BlockRegistryBuilder fence() {
        RegistryUtils.register(new FenceBaseBlock(baseBlock.getDefaultState()), new Identifier(name.getNamespace(), name.getPath() + "_fence"));
        return this;
    }

    public BlockRegistryBuilder fenceGate() {
        RegistryUtils.register(new FenceGateBaseBlock(), new Identifier(name.getNamespace(), name.getPath() + "_fence_gate"), ItemGroup.REDSTONE);
        return this;
    }

    public BlockRegistryBuilder wall() {
        RegistryUtils.register(new WallBaseBlock(baseBlock.getDefaultState()), new Identifier(name.getNamespace(), name.getPath() + "_wall"), ItemGroup.DECORATIONS);
        return this;
    }

    public BlockRegistryBuilder button(boolean wooden) {
        RegistryUtils.register(new ButtonBaseBlock(wooden), new Identifier(name.getNamespace(), name.getPath() + "_button"), ItemGroup.REDSTONE);
        return this;
    }

    public BlockRegistryBuilder pressurePlate(PressurePlateBlock.Type type) {
        RegistryUtils.register(new PressurePlateBaseBlock(baseBlock.getMaterial(baseBlock.getDefaultState()), type), new Identifier(name.getNamespace(), name.getPath() + "_pressure_plate"), ItemGroup.REDSTONE);
        return this;
    }

    public BlockRegistryBuilder corner() {
        RegistryUtils.register(new CornerBaseBlock(baseBlock.getDefaultState(),Block.Settings.copy(baseBlock)), new Identifier(name.getNamespace(), name.getPath() + name + "_corner"));
        return this;
    }

    public BlockRegistryBuilder post() {
        RegistryUtils.register(new PostBaseBlock(Block.Settings.copy(baseBlock)), new Identifier(name.getNamespace(), name.getPath() + name + "_post"));
        return this;
    }

    public BlockRegistryBuilder siding() {
        RegistryUtils.register(new SidingBaseBlock(Block.Settings.copy(baseBlock)), new Identifier(name.getNamespace(), name.getPath() + name + "_siding"));
        return this;
    }

}
