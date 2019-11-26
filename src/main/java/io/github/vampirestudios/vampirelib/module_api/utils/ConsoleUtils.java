package io.github.vampirestudios.vampirelib.module_api.utils;

import io.github.vampirestudios.vampirelib.module_api.ModuleManager;
import io.github.vampirestudios.vampirelib.module_api.api.Feature;
import io.github.vampirestudios.vampirelib.module_api.api.Module;
import io.github.vampirestudios.vampirelib.module_api.api.SubModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.vampirestudios.vampirelib.module_api.ModuleManager.MODULES;

public class ConsoleUtils {

    protected static Logger LOGGER = LogManager.getFormatterLogger("[VampireLib: Console Utils]");

    public static void logServerModules() {
        String moduleText;
        if (MODULES.size() > 1) {
            moduleText = "Loading %d modules:";
        } else {
            moduleText = "Loading %d module:";
        }

        LOGGER.info("[" + ModuleManager.class.getSimpleName() + "] " + moduleText, MODULES.keySet().size());

        for (Module candidate : MODULES.keySet()) {
            LOGGER.info(String.format(" - %s - Enabled: %s", candidate.getRegistryName(), candidate.enabled ? "true" : "false"));

            if (!candidate.getFeatures().isEmpty()) {
                String featureText;
                if (candidate.getFeatures().size() > 1) {
                    featureText = "Loading %d features for %s:";
                } else {
                    featureText = "Loading %d feature for %s:";
                }

                if (candidate.getFeatures().size() > 1) {
                    LOGGER.info(String.format("     [" + ModuleManager.class.getSimpleName() + "] " + featureText, candidate.getFeatures().size(),
                            candidate.registryName.toString()));
                    for (Feature feature : candidate.getFeatures()) {
                        LOGGER.info(String.format("     - %s - Enabled: %s", feature.name, feature.isEnabled() ? "true" : "false"));
                    }
                }
            }

            if (!candidate.getSubModules().isEmpty()) {
                String featureText;
                if (candidate.getSubModules().size() > 1) {
                    featureText = "Loading %d sub-modules for %s:";
                } else {
                    featureText = "Loading %d sub-modules for %s:";
                }

                if (candidate.getSubModules().size() > 1) {
                    LOGGER.info(String.format("     [" + ModuleManager.class.getSimpleName() + "] " + featureText, candidate.getSubModules().size(),
                            candidate.registryName.toString()));
                    for (SubModule subModule : candidate.getSubModules()) {
                        LOGGER.info(String.format("         - %s - Enabled: %s", subModule.name, subModule.enabled ? "true" : "false"));

                        if (!subModule.getFeatures().isEmpty()) {
                            String subModuleFeatureText;
                            if (subModule.getFeatures().size() > 1) {
                                subModuleFeatureText = "Loading %d features for %s:";
                            } else {
                                subModuleFeatureText = "Loading %d feature for %s:";
                            }

                            if (subModule.getFeatures().size() > 1) {
                                LOGGER.info(String.format("         [" + ModuleManager.class.getSimpleName() + "] " + subModuleFeatureText, subModule.getFeatures().size(),
                                        subModule.name));
                                for (Feature subModuleFeature : subModule.getFeatures()) {
                                    LOGGER.info(String.format("             - %s - Enabled: %s", subModuleFeature.name, subModuleFeature.isEnabled() ? "true" : "false"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void logClientModules() {
        String moduleText;
        if (MODULES.size() > 1) {
            moduleText = "Loading %d client modules:";
        } else {
            moduleText = "Loading %d client module:";
        }

        LOGGER.info("[" + ModuleManager.class.getSimpleName() + "] " + moduleText, MODULES.keySet().size());

        for (Module candidate : MODULES.keySet()) {
            LOGGER.info(String.format(" - %s - Enabled: %s", candidate.getRegistryName(), candidate.enabled ? "true" : "false"));

            if (!candidate.getFeatures().isEmpty()) {
                String featureText;
                if (candidate.getFeatures().size() > 1) {
                    featureText = "Loading %d client features for %s:";
                } else {
                    featureText = "Loading %d client feature for %s:";
                }

                if (candidate.getFeatures().size() > 1) {
                    LOGGER.info(String.format("     [" + ModuleManager.class.getSimpleName() + "] " + featureText, candidate.getFeatures().size(),
                            candidate.registryName.toString()));
                    for (Feature feature : candidate.getFeatures()) {
                        LOGGER.info(String.format("     - %s - Enabled: %s", feature.name, feature.isEnabled() ? "true" : "false"));
                    }
                    for (Feature feature : candidate.getClientFeatures()) {
                        LOGGER.info(String.format("     - %s - Enabled: %s", feature.name, feature.isEnabled() ? "true" : "false"));
                    }
                }
            }

            if (!candidate.getSubModules().isEmpty()) {
                String featureText;
                if (candidate.getSubModules().size() > 1) {
                    featureText = "Loading %d client sub-modules for %s:";
                } else {
                    featureText = "Loading %d client sub-modules for %s:";
                }

                if (candidate.getSubModules().size() > 1) {
                    LOGGER.info(String.format("     [" + ModuleManager.class.getSimpleName() + "] " + featureText, candidate.getSubModules().size(),
                            candidate.registryName.toString()));
                    for (SubModule subModule : candidate.getSubModules()) {
                        LOGGER.info(String.format("         - %s - Enabled: %s", subModule.name, subModule.enabled ? "true" : "false"));

                        if (!subModule.getFeatures().isEmpty()) {
                            String subModuleFeatureText;
                            if (subModule.getFeatures().size() > 1) {
                                subModuleFeatureText = "Loading %d client features for %s:";
                            } else {
                                subModuleFeatureText = "Loading %d client feature for %s:";
                            }

                            if (subModule.getFeatures().size() > 1) {
                                LOGGER.info(String.format("         [" + ModuleManager.class.getSimpleName() + "] " + subModuleFeatureText, subModule.getFeatures().size(),
                                        subModule.name));
                                for (Feature subModuleFeature : subModule.getFeatures()) {
                                    LOGGER.info(String.format("             - %s - Enabled: %s", subModuleFeature.name, subModuleFeature.isEnabled() ? "true" : "false"));
                                }
                            }
                            if (subModule.getClientFeatures().size() > 1) {
                                LOGGER.info(String.format("         [" + ModuleManager.class.getSimpleName() + "] " + subModuleFeatureText, subModule.getFeatures().size(),
                                        subModule.name));
                                for (Feature subModuleFeature : subModule.getClientFeatures()) {
                                    LOGGER.info(String.format("             - %s - Enabled: %s", subModuleFeature.name, subModuleFeature.isEnabled() ? "true" : "false"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}