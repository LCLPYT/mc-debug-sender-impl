package work.lclpnet.mdsi;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lclpnet.kibu.config.ConfigManager;
import work.lclpnet.mdsi.config.DebugConfig;
import work.lclpnet.mdsi.network.MCDSINetworking;

import java.nio.file.Path;
import java.util.Optional;

public class MCDSIModInit implements ModInitializer {

	public static final String MOD_ID = "mc-debug-sender-impl";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static volatile ConfigManager<DebugConfig> _configManager = null;

	@Override
	public void onInitialize() {
		var manager = loadConfig();
		_configManager = manager;

		var networking = new MCDSINetworking(LOGGER);

		networking.init();

		var sender = DebugSenderImpl.get();
		sender.bind(manager, networking);

		manager.onChanged(sender::onConfigChanged);

		LOGGER.info("Initialized.");
	}

	private static @NotNull ConfigManager<DebugConfig> loadConfig() {
		Path configFile = FabricLoader.getInstance().getConfigDir()
				.resolve(MOD_ID)
				.resolve("config.toml");

		var manager = new ConfigManager<>(configFile, new DebugConfig());

		manager.load();
		return manager;
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

	@ApiStatus.Internal
	public static Optional<ConfigManager<DebugConfig>> configManager() {
		return Optional.ofNullable(_configManager);
	}
}