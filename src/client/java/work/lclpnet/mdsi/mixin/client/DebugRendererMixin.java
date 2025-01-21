package work.lclpnet.mdsi.mixin.client;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mdsi.config.DebugConfig;
import work.lclpnet.mdsi.type.MCDSIDebugRenderer;

import java.util.Objects;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin implements MCDSIDebugRenderer {

    @Shadow @Final public PathfindingDebugRenderer pathfindingDebugRenderer;
    @Unique private DebugConfig config = new DebugConfig();

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void mcdsi$renderImpl(MatrixStack matrices, Frustum frustum, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        if (config.isPathFinding()) {
            pathfindingDebugRenderer.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
        }
    }

    @Override
    public void mcdsi$setConfig(DebugConfig config) {
        this.config = Objects.requireNonNull(config);
    }
}
