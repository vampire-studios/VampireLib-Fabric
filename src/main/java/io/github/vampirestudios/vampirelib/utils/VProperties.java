package io.github.vampirestudios.vampirelib.utils;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;
import io.github.vampirestudios.vampirelib.enums.SidingType;

public class VProperties {
    public static final EnumProperty<SidingType> SIDING_TYPE;
    public static final EnumProperty<Direction.Axis> AXIS;

    static {
        SIDING_TYPE = EnumProperty.of("type", SidingType.class);
        AXIS = EnumProperty.of("axis", Direction.Axis.class);
    }
}