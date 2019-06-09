package team.abnormals.abnormalib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ScaffoldingItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryUtils {

    public static Block register(Block block, Identifier name) {
        return register(block, name, ItemGroup.DECORATIONS);
    }

    public static Block register(String modId, Block block, String name) {
        return register(block, new Identifier(modId, name), ItemGroup.DECORATIONS);
    }

    public static Block register(Block block, Identifier name, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new BlockItem(block, new Item.Settings().group(itemGroup));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    public static Block register(String modId, Block block, String name, ItemGroup itemGroup) {
        register(block, new Identifier(modId, name), itemGroup);
        return block;
    }

    public static Block registerScaffolding(Block block, Identifier name) {
        Registry.register(Registry.BLOCK, name, block);
        Registry.register(Registry.ITEM, name, new ScaffoldingItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        return block;
    }

    public static Block registerNoBI(Block block, Identifier identifier) {
        Registry.register(Registry.BLOCK, identifier, block);
        return block;
    }

    public static Item registerItem(Item item, Identifier name) {
        Registry.register(Registry.ITEM, name, item);
        return item;
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBE(Identifier name, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY, name, blockEntityType);
        return blockEntityType;
    }

}