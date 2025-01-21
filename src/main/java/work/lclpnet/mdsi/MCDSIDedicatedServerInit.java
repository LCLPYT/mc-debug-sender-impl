package work.lclpnet.mdsi;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import work.lclpnet.kibu.config.ConfigManager;

public class MCDSIDedicatedServerInit implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STOPPING.register(client ->
                MCDSIModInit.configManager().ifPresent(ConfigManager::close));
    }
}
