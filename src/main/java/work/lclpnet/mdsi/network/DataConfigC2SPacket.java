package work.lclpnet.mdsi.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import work.lclpnet.mdsi.MCDSIModInit;

public record DataConfigC2SPacket(DebugDataConfig dataConfig) implements CustomPayload {

    public static final Id<DataConfigC2SPacket> ID = new Id<>(MCDSIModInit.identifier("abilities"));

    public static final PacketCodec<PacketByteBuf, DataConfigC2SPacket> CODEC = PacketCodec.tuple(
            DebugDataConfig.PACKET_CODEC, DataConfigC2SPacket::dataConfig,
            DataConfigC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
