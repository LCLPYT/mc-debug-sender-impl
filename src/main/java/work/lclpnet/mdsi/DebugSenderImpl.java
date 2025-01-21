package work.lclpnet.mdsi;

import net.minecraft.SharedConstants;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.kibu.config.ConfigAccess;
import work.lclpnet.mdsi.config.DebugConfig;
import work.lclpnet.mdsi.network.MCDSINetworking;

public class DebugSenderImpl {

    private final boolean initialDevelopment = SharedConstants.isDevelopment;
    private DebugConfig config = new DebugConfig();
    private @Nullable MCDSINetworking networking = null;
    private @Nullable Runnable onConfigChange = null;

    void bind(ConfigAccess<DebugConfig> configAccess, MCDSINetworking networking) {
        this.config = configAccess.config();
        this.networking = networking;
    }

    public DebugConfig config() {
        return config;
    }

    @Nullable
    public MCDSINetworking networking() {
        return networking;
    }

    public static DebugSenderImpl get() {
        return Holder.instance;
    }

    public synchronized void onConfigChanged() {
        SharedConstants.isDevelopment = initialDevelopment || config.isDebugEnabled();

        if (onConfigChange != null) {
            onConfigChange.run();
        }
    }

    public synchronized void setOnConfigChanged(@Nullable Runnable onConfigChange) {
        this.onConfigChange = onConfigChange;
    }

    private static class Holder {
        private static final DebugSenderImpl instance = new DebugSenderImpl();
    }
}
