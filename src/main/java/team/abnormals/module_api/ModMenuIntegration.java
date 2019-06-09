package team.abnormals.module_api;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import team.abnormals.module_api.api.Module;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public String getModId() {
        return ModuleManager.modid;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        for(Module module : ModuleManager.MODULES.keySet()) return screen -> AutoConfig.getConfigScreen(module.getConfig(), screen).get();
        return null;
    }

}