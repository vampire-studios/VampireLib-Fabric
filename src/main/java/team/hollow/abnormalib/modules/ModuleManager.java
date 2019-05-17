package team.hollow.abnormalib.modules;

import team.hollow.abnormalib.modules.api.MainModule;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ModuleManager {
    protected final String modId;

    private ConcurrentLinkedQueue<MainModule> modules = new ConcurrentLinkedQueue<>();

    public ModuleManager(String modId) {
        this.modId = modId;
    }

    public void registerModules(MainModule... newModules) {
        modules.addAll(Arrays.asList(newModules));
    }

    public ConcurrentLinkedQueue<MainModule> getModules() {
        return modules;
    }

    public void setup() {
    	modules.forEach(mainModule -> mainModule.setup(modId));
    }
}