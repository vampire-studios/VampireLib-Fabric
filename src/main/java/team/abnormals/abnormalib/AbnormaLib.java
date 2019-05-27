package team.abnormals.abnormalib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.abnormalib.blocks.entity.ChestBaseBlockEntity;
import team.abnormals.abnormalib.entity.FlyingLanternEntity;
import team.abnormals.abnormalib.utils.registry.EntityRegistryBuilder;
import team.abnormals.abnormalib.utils.registry.RegistryUtils;

public class AbnormaLib implements ModInitializer {

    public static String MOD_ID = "abnormalib";
    public static final EntityType<FlyingLanternEntity> FLYING_LANTERN = EntityRegistryBuilder
            .<FlyingLanternEntity>createBuilder(new Identifier(MOD_ID, "flying_lantern"))
            .entity(FlyingLanternEntity::new)
            .category(EntityCategory.MISC)
            .size(EntitySize.constant(1.0F, 1.0F))
            .hasEgg(false)
            .build();
    public static String MOD_NAME = "AbnormaLib";
    public static String MOD_VERSION = "0.6.0-1.14.2-ALPHA";
    public static BlockEntityType<ChestBaseBlockEntity> CHEST_BASE_BE = RegistryUtils.registerBE(new Identifier(MOD_ID, "chest_base_be"),
            BlockEntityType.Builder.create(ChestBaseBlockEntity::new));

    @Override
    public void onInitialize() {
        Logger logger = LogManager.getLogger(MOD_NAME);
        logger.info("Your running " + MOD_NAME + " v" + MOD_VERSION);
    }

}