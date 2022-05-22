package io.github.vampirestudios.vampirelib.village;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class PointOfInterestTypeCustom extends PoiType {
    private final Set<BlockState> workStationStates;

    public PointOfInterestTypeCustom(String id, Set<BlockState> workStationStates) {
        this(id, workStationStates, 1, 1);
    }

    public PointOfInterestTypeCustom(String id, Set<BlockState> workStationStates, int ticketCount) {
        this(id, workStationStates, ticketCount, 1);
    }

    public PointOfInterestTypeCustom(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        super(id, workStationStates, ticketCount, searchDistance);
        this.workStationStates = workStationStates;
    }

    public Set<BlockState> getWorkStationStates() {
        return workStationStates;
    }

}