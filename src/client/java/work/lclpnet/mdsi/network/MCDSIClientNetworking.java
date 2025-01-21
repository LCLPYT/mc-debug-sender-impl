package work.lclpnet.mdsi.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import work.lclpnet.kibu.networking.protocol.ClientProtocolHandler;
import work.lclpnet.mdsi.config.DebugConfig;

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

    public void sendDataConfig(DebugConfig config) {
        if (!understands() || MinecraftClient.getInstance().getNetworkHandler() == null) return;

        ClientPlayNetworking.send(new DataConfigC2SPacket(new DebugDataConfig(config)));
    }
}
