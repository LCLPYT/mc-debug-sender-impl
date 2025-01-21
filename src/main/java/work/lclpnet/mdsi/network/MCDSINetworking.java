package work.lclpnet.mdsi.network;

import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import work.lclpnet.kibu.networking.protocol.Protocol;
import work.lclpnet.kibu.networking.protocol.ServerProtocolHandler;
import work.lclpnet.mdsi.MCDSIModInit;

public class MCDSINetworking {

    public static final Protocol PROTOCOL = new Protocol(MCDSIModInit.identifier("version"), 1);
    private final Logger logger;
    private @Nullable ServerProtocolHandler protocolHandler = null;

    public MCDSINetworking(Logger logger) {
        this.logger = logger;
    }

    public void init() {
        protocolHandler = new ServerProtocolHandler(PROTOCOL, logger);
        protocolHandler.register();
    }

    public boolean understands(ServerPlayerEntity player) {
        return protocolHandler != null && protocolHandler.understands(player);
    }
}
