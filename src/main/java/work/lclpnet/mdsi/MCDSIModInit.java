package work.lclpnet.mdsi;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lclpnet.mdsi.network.MCDSINetworking;

public class MCDSIModInit implements ModInitializer {

	public static final String MOD_ID = "mc-debug-sender-impl";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		new MCDSINetworking(LOGGER).init();

		LOGGER.info("Initialized.");
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}