package work.lclpnet.mdsi.network;

import org.slf4j.Logger;
import work.lclpnet.kibu.networking.protocol.ClientProtocolHandler;

public class MCDSIClientNetworking {

    private final Logger logger;
    private ClientProtocolHandler handler;

    public MCDSIClientNetworking(Logger logger) {
        this.logger = logger;
    }

    public void init() {
        handler = new ClientProtocolHandler(MCDSINetworking.PROTOCOL, logger);
        handler.register();
    }

    public boolean understands() {
        return handler.understands();
    }
}
