package work.lclpnet.mdsi.mixin;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.custom.DebugPathCustomPayload;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mdsi.DebugSenderImpl;
import work.lclpnet.mdsi.network.DebugDataConfig;
import work.lclpnet.mdsi.network.MCDSINetworking;

import java.util.function.Supplier;

@Mixin(DebugInfoSender.class)
public abstract class DebugInfoSenderMixin {

    @Inject(
            method = "sendPathfindingData",
            at = @At("HEAD")
    )
    private static void mcdsi$sendPathfindingData(World world, MobEntity mob, Path path, float nodeReachProximity, CallbackInfo ci) {
        if (path == null) return;

        mcdsi$send(world, () -> new DebugPathCustomPayload(mob.getId(), path, nodeReachProximity));
    }

    @Unique
    private static void mcdsi$send(World world, Supplier<CustomPayload> payload) {
        var sender = DebugSenderImpl.get();
        MCDSINetworking networking = sender.networking();

        if (networking == null || !(world instanceof ServerWorld serverWorld)) return;

        networking.sendIf(serverWorld.getPlayers(), DebugDataConfig::isPathFinding, () -> new CustomPayloadS2CPacket(payload.get()));
    }
}
