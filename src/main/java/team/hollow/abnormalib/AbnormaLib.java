package team.hollow.abnormalib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbnormaLib implements ModInitializer {
    @Override
    public void onInitialize() {
        Logger logger = LogManager.getLogger("AbnormaLib");
        Version version = FabricLoader.getInstance().getModContainer("abnormalib").get().getMetadata().getVersion();
        logger.info(String.format("Your running AbnormaLib v%s", version.getFriendlyString()));
    }
}
