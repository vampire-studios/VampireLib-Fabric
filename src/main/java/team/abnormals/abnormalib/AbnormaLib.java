package team.abnormals.abnormalib;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.abnormalib.utils.registry.BlockChiseler;

public class AbnormaLib implements ModInitializer {

    public static String MOD_ID = "abnormalib";
    public static String MOD_NAME = "AbnormaLib";
    public static String MOD_VERSION = "0.7.8-1.14.4-pre5";

    @Override
    public void onInitialize() {
        Logger logger = LogManager.getLogger(MOD_NAME);
        logger.info("Your running " + MOD_NAME + " v" + MOD_VERSION);

        BlockChiseler.setup();
    }

}