package io.github.vampirestudios.vampirelib;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.vampirestudios.vampirelib.utils.registry.BlockChiseler;

public class VampireLib implements ModInitializer {

    public static String MOD_ID = "vampirelib";
    public static String MOD_NAME = "VampireLib";
    public static String MOD_VERSION = "1.0.1-1.14.4";

    @Override
    public void onInitialize() {
        Logger logger = LogManager.getLogger(MOD_NAME);
        logger.info("Your running " + MOD_NAME + " v" + MOD_VERSION);

        BlockChiseler.setup();
    }

}