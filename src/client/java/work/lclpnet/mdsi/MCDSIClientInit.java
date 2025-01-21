package work.lclpnet.mdsi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import work.lclpnet.kibu.config.ConfigManager;
import work.lclpnet.mdsi.network.MCDSIClientNetworking;

public class MCDSIClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		new MCDSIClientNetworking(MCDSIModInit.LOGGER).init();

		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> MCDSIModInit.configManager().ifPresent(ConfigManager::close));
	}
}