package team.abnormals.module_api.data;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.abnormalib.AbnormaLib;
import team.abnormals.module_api.ModuleManager;
import team.abnormals.module_api.api.Feature;
import team.abnormals.module_api.api.Module;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ModuleDataManager implements SimpleResourceReloadListener<ModuleDataManager.Data> {

    private static final Gson GSON = new GsonBuilder().create();

    public static final ModuleDataManager INSTANCE = new ModuleDataManager();

    private static final Logger LOGGER = LogManager.getFormatterLogger(String.format("[%s Logger : Module Data Manager]", AbnormaLib.MOD_NAME));

    @Override
    public Identifier getFabricId() {
        return new Identifier(AbnormaLib.MOD_ID, "data");
    }

    public void registerReloadListener() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(this);
    }

    @Override
    public CompletableFuture<Data> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> new Data(manager), executor);
    }

    @Override
    public CompletableFuture<Void> apply(Data data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(data::apply, executor);
    }

    public static class Data {
        public Map<String, ModuleData> modules = new HashMap<>();
        public Map<String, FeatureData> features = new HashMap<>();

        public Data(ResourceManager manager) {
            for (Identifier id : manager.findResources("config/modules", path -> path.endsWith(".json"))) {
                try {
                    InputStreamReader reader = new InputStreamReader(manager.getResource(id).getInputStream());
                    if (id.getPath().startsWith("config/modules/")) loadSlotTypesResource(id, reader);
                    else if (id.getPath().startsWith("config/modules/features/")) loadItemsResource(id, reader);
                    else LOGGER.warn("Unknown resource '{}'", id);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    LOGGER.error("Error while parsing resource '{}'", id, ex);
                } catch (IOException ex) {
                    LOGGER.error("Error reading resource '{}'", id, ex);
                }
            }
        }

        private <TKey, TValue extends Loadable<TKey>> void loadResourceMany(Identifier id, InputStreamReader reader, Map<TKey, TValue> dstMap, Type type) {
            Map<String, TValue> map = GSON.fromJson(reader, type);
            for (Map.Entry<String, TValue> entry : map.entrySet())
                try { dstMap.put(entry.getValue().load(entry.getKey()), entry.getValue()); }
                catch (Exception ex) { LOGGER.error("Error loading resource '{}': {}", id, ex.getMessage()); }
            GSON.fromJson(reader, new TypeToken<List<FeatureData>>(){}.getType());
        }

        private void loadSlotTypesResource(Identifier id, InputStreamReader reader) {
            loadResourceMany(id, reader, modules, new TypeToken<Map<String, ModuleData>>(){}.getType());
        }

        private void loadItemsResource(Identifier id, InputStreamReader reader) {
            loadResourceMany(id, reader, features, new TypeToken<Map<String, FeatureData>>(){}.getType());
        }

        public void apply() {
            for (Map.Entry<String, ModuleData> moduleDataEntry : modules.entrySet()) {
                String moduleName = moduleDataEntry.getKey();
                ModuleData moduleData = moduleDataEntry.getValue();
                ModuleManager.registerModule(moduleName, moduleData);
            }
        }

    }

    public interface Loadable<T> {
        T load(String key);
    }

    public static class ModuleData implements Loadable<String> {
        public String name;
        public String description;
        public Class<? extends Module> moduleClass;
        public Class<? extends ConfigData> configClass;
        public List<FeatureData> features;
        public List<String> version;
        public List<String> nonCompatibleMods;

        @Override
        public String load(String key) {
            return null;
        }
    }

    public static class FeatureData implements Loadable<String> {
        public String name;
        public String description;
        public Class<? extends Feature> featureClass;

        @Override
        public String load(String key) {
            return null;
        }
    }

    public static class FeatureDataAdapter extends TypeAdapter<FeatureData> {
        @Override public void write(JsonWriter out, FeatureData value) throws IOException {

        }

        @Override public FeatureData read(JsonReader in) throws IOException {
            FeatureData featureData = new FeatureData();
            in.beginObject();
            while(in.hasNext()) {
                switch(in.nextName()) {
                    case "name":
                        featureData.name = in.nextString();
                        break;
                    case "description":
                        featureData.description = in.nextString();
                        break;
                    case "class":
                        try {
                            featureData.featureClass = (Class<? extends Feature>)Class.forName(in.nextString());
                        } catch(ClassNotFoundException e) {
                            throw new UncheckedExecutionException(e);
                        }
                        break;
                }
            }
            in.endObject();
            return featureData;
        }
    }

}