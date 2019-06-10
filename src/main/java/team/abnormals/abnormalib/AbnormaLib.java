package team.abnormals.abnormalib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.abnormalib.blocks.entity.ChestBaseBlockEntity;
import team.abnormals.abnormalib.utils.registry.BlockChiseler;
import team.abnormals.abnormalib.utils.registry.RegistryUtils;
import team.abnormals.module_api.data.ModuleDataManager;

public class AbnormaLib implements ModInitializer {

    public static String MOD_ID = "abnormalib";
    public static String MOD_NAME = "AbnormaLib";
    public static String MOD_VERSION = "0.7.1-1.14.3-pre2";
    public static BlockEntityType<ChestBaseBlockEntity> CHEST_BASE_BE = RegistryUtils.registerBE(new Identifier(MOD_ID, "chest_base_be"),
            BlockEntityType.Builder.create(ChestBaseBlockEntity::new));

    @Override
    public void onInitialize() {
        Logger logger = LogManager.getLogger(MOD_NAME);
        logger.info("Your running " + MOD_NAME + " v" + MOD_VERSION);

        BlockChiseler.setup();
        ModuleDataManager.INSTANCE.registerReloadListener();
    }

}