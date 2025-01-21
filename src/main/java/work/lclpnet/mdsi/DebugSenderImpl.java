package work.lclpnet.mdsi;

import work.lclpnet.kibu.config.ConfigAccess;
import work.lclpnet.mdsi.config.DebugConfig;

public class DebugSenderImpl {

    private DebugConfig config = new DebugConfig();

    public DebugConfig config() {
        return config;
    }

    void bind(ConfigAccess<DebugConfig> configAccess) {
        config = configAccess.config();
    }

    public static DebugSenderImpl get() {
        return Holder.instance;
    }

    private static class Holder {
        private static final DebugSenderImpl instance = new DebugSenderImpl();
    }
}
