package work.lclpnet.mdsi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import work.lclpnet.kibu.config.ConfigAccess;
import work.lclpnet.kibu.config.ConfigManager;
import work.lclpnet.mdsi.config.DebugConfig;
import work.lclpnet.mdsi.network.MCDSIClientNetworking;

public class MCDSIClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		var configAccess = MCDSIModInit.configManager()
				.orElseThrow(() -> new IllegalStateException("Config not loaded yet"));

		var networking = new MCDSIClientNetworking(MCDSIModInit.LOGGER);
		networking.init();

		var handler = new DebugClientHandler(configAccess, networking);
		DebugClientHandler.bind(handler);

		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> MCDSIModInit.configManager().ifPresent(ConfigManager::close));
	}
}