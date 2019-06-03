package team.abnormals.abnormalib.modules;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import team.abnormals.abnormalib.AbnormaLib;
import team.abnormals.abnormalib.utils.registry.RegistryUtils;
import team.abnormals.module_api.api.Feature;
import team.abnormals.module_api.api.Module;

public class TestModule extends Module {

    public TestModule() {
        registerFeature(new Feature("Test", "Testing") {
            @Override
            public void init() {
                RegistryUtils.register(new Block(Block.Settings.copy(Blocks.CARVED_PUMPKIN)), new Identifier(AbnormaLib.MOD_ID, "test_block"));
            }

            @Override
            public void initClient() {

            }
        });
    }

    @Override
    public Class<? extends ConfigData> getConfig() {
        return TestModuleConfig.class;
    }

}