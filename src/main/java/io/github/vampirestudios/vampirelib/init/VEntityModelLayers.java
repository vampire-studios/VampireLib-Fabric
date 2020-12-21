package io.github.vampirestudios.vampirelib.init;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.utils.Utils;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VEntityModelLayers {

    public static EntityModelLayer createBoat(String type) {
        return new EntityModelLayer(new Identifier(VampireLib.MOD_ID, "boat/" + type), "main");
    }

    public static EntityModelLayer createBoat(Identifier type) {
        return new EntityModelLayer(Utils.appendToPath(type, "boat/"), "main");
    }

}
