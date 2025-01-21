package work.lclpnet.mdsi;

import org.jetbrains.annotations.Nullable;
import work.lclpnet.kibu.config.ConfigAccess;
import work.lclpnet.mdsi.config.DebugConfig;
import work.lclpnet.mdsi.network.MCDSIClientNetworking;

import java.util.Optional;

public class DebugClientHandler {

    private static @Nullable DebugClientHandler _instance = null;

    private final ConfigAccess<DebugConfig> configAccess;
    private final DebugConfig config;
    private final MCDSIClientNetworking networking;

    public DebugClientHandler(ConfigAccess<DebugConfig> configAccess, MCDSIClientNetworking networking) {
        this.configAccess = configAccess;
        this.networking = networking;

        this.config = configAccess.config();
    }

    public DebugConfig config() {
        return config;
    }

    public void update() {
        configAccess.save();
        updateDataConfig();
    }

    public void updateDataConfig() {
        networking.sendDataConfig(config);
    }

    static void bind(DebugClientHandler handler) {
        _instance = handler;
    }

    public static Optional<DebugClientHandler> get() {
        return Optional.ofNullable(_instance);
    }
}
