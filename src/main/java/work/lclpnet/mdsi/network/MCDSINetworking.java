package work.lclpnet.mdsi.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import work.lclpnet.kibu.hook.player.PlayerConnectionHooks;
import work.lclpnet.kibu.networking.protocol.Protocol;
import work.lclpnet.kibu.networking.protocol.ServerProtocolHandler;
import work.lclpnet.mdsi.MCDSIModInit;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MCDSINetworking {

    public static final Protocol PROTOCOL = new Protocol(MCDSIModInit.identifier("version"), 1);
    private final Logger logger;
    private final Map<UUID, DebugDataConfig> playerConfigs = new HashMap<>();

    public MCDSINetworking(Logger logger) {
        this.logger = logger;
    }

    public void init() {
        @Nullable ServerProtocolHandler protocolHandler = new ServerProtocolHandler(PROTOCOL, logger);
        protocolHandler.register();

        PayloadTypeRegistry.playC2S().register(DataConfigC2SPacket.ID, DataConfigC2SPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(DataConfigC2SPacket.ID, this::onDataConfigReceive);

        PlayerConnectionHooks.QUIT.register(player -> {
            synchronized (this) {
                playerConfigs.remove(player.getUuid());
            }
        });
    }

    private void onDataConfigReceive(DataConfigC2SPacket packet, ServerPlayNetworking.Context ctx) {
        synchronized (this) {
            playerConfigs.put(ctx.player().getUuid(), packet.dataConfig());
        }
    }

    public synchronized void sendIf(Iterable<? extends ServerPlayerEntity> players, Predicate<DebugDataConfig> predicate, Supplier<Packet<?>> factory) {
        Packet<?> packet = null;

        for (ServerPlayerEntity player : players) {
            DebugDataConfig dataConfig = playerConfigs.get(player.getUuid());

            if (dataConfig == null || !predicate.test(dataConfig)) continue;

            if (packet == null) {
                packet = Objects.requireNonNull(factory.get());
            }

            player.networkHandler.sendPacket(packet);
        }
    }
}
