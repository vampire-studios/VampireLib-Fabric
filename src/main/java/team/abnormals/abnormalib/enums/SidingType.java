package team.abnormals.abnormalib.enums;

import net.minecraft.util.StringIdentifiable;

public enum SidingType implements StringIdentifiable {
    SINGLE("single"),
    DOUBLE("double"),
    CORNER_RIGHT("corner_right"),
    CORNER_LEFT("corner_left");

    private final String name;

    SidingType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}