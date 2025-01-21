package work.lclpnet.mdsi;

import net.fabricmc.api.ClientModInitializer;
import work.lclpnet.mdsi.network.MCDSIClientNetworking;

public class MCDSIClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		new MCDSIClientNetworking(MCDSIModInit.LOGGER).init();
	}
}