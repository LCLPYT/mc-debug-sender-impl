package work.lclpnet.mdsi.mixin;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(Path.class)
public abstract class PathMixin {

    @Shadow abstract void setDebugInfo(PathNode[] debugNodes, PathNode[] debugSecondNodes, Set<TargetPathNode> debugTargetNodes);

    @Shadow @Final private List<PathNode> nodes;

    @Shadow @Final private BlockPos target;

    @Inject(
            method = "toBuf",
            at = @At("HEAD")
    )
    public void mcdsi$setDebugData(PacketByteBuf buf, CallbackInfo ci) {
        List<PathNode> unvisited = new ArrayList<>(), visited = new ArrayList<>();

        for (PathNode node : nodes) {
            if (node.visited) {
                visited.add(node);
            } else {
                unvisited.add(node);
            }
        }

        var targets = Set.of(new TargetPathNode(target.getX(), target.getY(), target.getZ()));

        setDebugInfo(unvisited.toArray(PathNode[]::new), visited.toArray(PathNode[]::new), targets);
    }
}
