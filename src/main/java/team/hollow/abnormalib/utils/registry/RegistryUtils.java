package team.hollow.abnormalib.utils.registry;

import net.minecraft.block.Block;
import net.minecraft.item.*;
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
        Registry.register(Registry.ITEM, name, new BlockItem(block, new Item.Settings().itemGroup(itemGroup)));
        return block;
    }

    public static Block register(String modId, Block block, String name, ItemGroup itemGroup) {
        register(block, new Identifier(modId, name), itemGroup);
        return block;
    }

    public static Block registerScaffolding(Block block, Identifier name) {
        Registry.register(Registry.BLOCK, name, block);
        Registry.register(Registry.ITEM, name, new ScaffoldingItem(block, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
        return block;
    }

    public static Block register(Block block, Block wallBlock, Identifier name) {
        Registry.register(Registry.BLOCK, name, block);
        Registry.register(Registry.ITEM, name, new WallStandingBlockItem(block, wallBlock, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
        return block;
    }

    public static Block registerNoBI(Block block, Identifier name) {
        Registry.register(Registry.BLOCK, name, block);
        return block;
    }

    public static Item registerItem(Item item, Identifier name) {
        Registry.register(Registry.ITEM, name, item);
        return item;
    }

}