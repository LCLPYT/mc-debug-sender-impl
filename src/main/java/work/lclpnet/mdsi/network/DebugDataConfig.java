package work.lclpnet.mdsi.network;

import lombok.Getter;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import work.lclpnet.mdsi.config.DebugConfig;

@Getter
public class DebugDataConfig {

    public static final PacketCodec<PacketByteBuf, DebugDataConfig> PACKET_CODEC = PacketCodec.of(DebugDataConfig::write, DebugDataConfig::new);

    private final boolean pathFinding;

    public DebugDataConfig(DebugConfig config) {
        pathFinding = config.isPathFinding();
    }

    public DebugDataConfig(PacketByteBuf buf) {
        pathFinding = buf.readBoolean();
    }

    private void write(PacketByteBuf buf) {
        buf.writeBoolean(pathFinding);
    }
}
