package io.github.vampirestudios.vampirelib.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.fabricmc.api.ModInitializer;

import io.github.vampirestudios.vampirelib.modules.FeatureManager;
import io.github.vampirestudios.vampirelib.utils.IdentifierUtils;

@EnvironmentInterfaces(
		@EnvironmentInterface(value = EnvType.CLIENT, itf = ClientModInitializer.class)
)
public abstract class BasicModClass implements ModInitializer, ClientModInitializer {
	private static final Map<Identifier, FeatureManager> FEATURE_MANAGERS = new HashMap<>();

	public static FeatureManager featureManager;
	private final String modId;
	private final String modName;
	private final String modVersion;
	private final Logger logger;
	private boolean printVersionMessage = true;

	/**
	 * Creates a copy of the ${@link BasicModClass} given and defines if it's client-side or not.
	 *
	 * @param basicModClass another instance of this class
	 * @param client        if this is client-side or not
	 */
	protected BasicModClass(BasicModClass basicModClass, boolean client) {
		this(basicModClass.modId, basicModClass.modName, basicModClass.modVersion, client);
	}

	/**
	 * Creates a new instance of this class with client set to false.
	 *
	 * @param modName    the name of this mod
	 * @param modVersion the version of this mod
	 */
	protected BasicModClass(String modName, String modVersion) {
		this(modName, modName, modVersion, false);
	}

	/**
	 * Creates a new instance of this class with client set to false.
	 *
	 * @param modId      the mod id of this mod
	 * @param modName    the name of this mod
	 * @param modVersion the version of this mod
	 */
	protected BasicModClass(String modId, String modName, String modVersion) {
		this(modId, modName, modVersion, false);
	}

	/**
	 * Creates a new instance of this class with mod id being the lowercase version of the name.
	 *
	 * @param modName    the name of this mod
	 * @param modVersion the version of this mod
	 * @param client     if this is client-side or not
	 */
	protected BasicModClass(String modName, String modVersion, boolean client) {
		this(modName, modName, modVersion, client);
	}

	/**
	 * Creates a new instance of this class.
	 *
	 * @param modId      the mod id of this mod
	 * @param modName    the name of this mod
	 * @param modVersion the version of this mod
	 * @param client     if this is client-side or not
	 */
	protected BasicModClass(String modId, String modName, String modVersion, boolean client) {
		this.modId = modId.toLowerCase(Locale.ROOT);
		this.modName = modName;
		this.modVersion = modVersion;
		this.logger = LoggerFactory.getLogger(this.modName + (client ? " Client" : ""));
		if (!client) {
			featureManager = registerFeatureManager();
		}
		IdentifierUtils.setModInstance(this);
	}

	/**
	 * Creates an ${@link FeatureManager} instance with the mod id of this mod
	 * and checks if it's not already registered.
	 *
	 * @return a ${@link FeatureManager} with the mod id of this mod
	 */
	private FeatureManager registerFeatureManager() {
		Identifier id = Identifier.fromNamespaceAndPath(modId, "feature_manager");
		if (FEATURE_MANAGERS.containsKey(id)) {
			return FEATURE_MANAGERS.get(id);
		} else {
			FeatureManager manager = FeatureManager.createFeatureManager(id);
			FEATURE_MANAGERS.put(id, manager);
			return manager;
		}
	}

	/**
	 * Creates an ${@link Identifier} with the specified and path and the mod id of the mod.
	 *
	 * @param path the path for this resource location
	 *
	 * @return a new ${@link Identifier} with the specified and path and the mod id of the mod
	 */
	public Identifier identifier(String path) {
		return IdentifierUtils.modId(path);
	}

	/**
	 * Creates an ${@link Identifier} with the specified namespace and path.
	 *
	 * @param namespace the namespace for this resource location
	 * @param path      the path for this resource location
	 *
	 * @return a new ${@link Identifier} with the specified namespace and path
	 */
	public Identifier identifier(String namespace, String path) {
		return Identifier.fromNamespaceAndPath(namespace, path);
	}

	/**
	 * Registers the common features.
	 */
	public void registerFeatures() {
	}

	/**
	 * Registers the client features.
	 */
	@Environment(EnvType.CLIENT)
	public void registerFeaturesClient() {
	}

	/**
	 * Registers the server features.
	 */
	@Environment(EnvType.SERVER)
	public void registerFeaturesServer() {
	}

	/**
	 * Initializes the common.
	 */
	public void commonPostRegisterFeatures() {
		featureManager.initCommon();
	}

	/**
	 * Initializes the client features.
	 */
	@Environment(EnvType.CLIENT)
	public void clientPostRegisterFeatures() {
		featureManager.initClient();
	}

	/**
	 * Initializes the server features.
	 */
	@Environment(EnvType.SERVER)
	public void serverPostRegisterFeatures() {
		featureManager.initServer();
	}

	public FeatureManager featureManager() {
		return featureManager;
	}

	/**
	 * @return the logger for this instance of the mod
	 */
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * @return the mod id of the mod
	 */
	public String modId() {
		return this.modId;
	}

	/**
	 * @return the name of the mod
	 */
	public String modName() {
		return this.modName;
	}

	/**
	 * @return the version of the mod
	 */
	public String modVersion() {
		return this.modVersion;
	}

	/**
	 * Makes it so the default version info does not get printed.
	 */
	public void shouldNotPrintVersionMessage() {
		this.printVersionMessage = false;
	}

	@Override
	public void onInitialize() {
		if (this.printVersionMessage) {
			this.getLogger().info("You're now running {} v{} for {}", this.modName(), this.modVersion(),
					SharedConstants.getCurrentVersion().name());
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void onInitializeClient() {
		if (this.printVersionMessage) {
			this.getLogger().info("You're now running {} v{} on Client-Side for {}", this.modName(), this.modVersion(),
					SharedConstants.getCurrentVersion().name());
		}
	}
}
