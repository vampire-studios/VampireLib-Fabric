package io.github.vampirestudios.vampirelib.init;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import io.github.vampirestudios.vampirelib.VampireLib;

public class VAttributes {
    public static final Attribute STEP_HEIGHT_ADDITION = new RangedAttribute("vampirelib.stepHeight", 0.0D, -512.0D, 512.0D).setSyncable(true);
    public static final Attribute ENTITY_GRAVITY = new RangedAttribute("vampirelib.entity_gravity", 0.08D, -8.0D, 8.0D).setSyncable(true);
    public static final Attribute SWIM_SPEED = new RangedAttribute("vampirelib.swimSpeed", 1.0D, 0.0D, 1024.0D).setSyncable(true);

    public static void init() {
        Registry.register(Registry.ATTRIBUTE, VampireLib.INSTANCE.identifier("step_height_addition"), STEP_HEIGHT_ADDITION);
        Registry.register(Registry.ATTRIBUTE, VampireLib.INSTANCE.identifier("entity_gravity"), ENTITY_GRAVITY);
        Registry.register(Registry.ATTRIBUTE, VampireLib.INSTANCE.identifier("swim_speed"), SWIM_SPEED);
    }
}
