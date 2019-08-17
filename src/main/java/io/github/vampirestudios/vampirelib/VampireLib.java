package io.github.vampirestudios.vampirelib;

import io.github.vampirestudios.vampirelib.utils.registry.BlockChiseler;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VampireLib implements ModInitializer {

    public static String MOD_ID = "vampirelib";
    public static String MOD_NAME = "VampireLib";
    public static String MOD_VERSION = "1.0.2-1.14.4";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Your running " + MOD_NAME + " v" + MOD_VERSION);

        BlockChiseler.setup();
    }

}