package work.lclpnet.mdsi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import work.lclpnet.kibu.config.ConfigManager;
import work.lclpnet.mdsi.network.MCDSIClientNetworking;
import work.lclpnet.mdsi.type.MCDSIDebugRenderer;

public class MCDSIClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		var configAccess = MCDSIModInit.configManager()
				.orElseThrow(() -> new IllegalStateException("Config not loaded yet"));

		var networking = new MCDSIClientNetworking(MCDSIModInit.LOGGER);
		networking.init();

		var handler = new DebugClientHandler(configAccess, networking);
		DebugClientHandler.bind(handler);

		DebugSenderImpl.get().setOnConfigChanged(handler::updateDataConfig);

		ClientLifecycleEvents.CLIENT_STARTED.register(client ->
				((MCDSIDebugRenderer) client.debugRenderer).mcdsi$setConfig(configAccess.config()));

		ClientLifecycleEvents.CLIENT_STOPPING.register(client ->
				MCDSIModInit.configManager().ifPresent(ConfigManager::close));

		ClientPlayConnectionEvents.JOIN.register((playNetworkHandler, sender, client) ->
				handler.updateDataConfig());
	}
}