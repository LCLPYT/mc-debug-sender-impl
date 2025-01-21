package work.lclpnet.mdsi.network;

import org.slf4j.Logger;
import work.lclpnet.kibu.networking.protocol.ClientProtocolHandler;

public class MCDSIClientNetworking {

    private final Logger logger;

    public MCDSIClientNetworking(Logger logger) {
        this.logger = logger;
    }

    public void init() {
        new ClientProtocolHandler(MCDSINetworking.PROTOCOL, logger).register();
    }
}
