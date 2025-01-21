package work.lclpnet.mdsi.config;

import com.electronwill.nightconfig.core.serde.annotations.SerdeComment;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DebugConfig {

    @SerdeComment("Whether Minecraft should be in development mode (required for debug data to be sent)")
    private boolean debugEnabled = false;

    @SerdeComment("Renders path debug info for all mobs")
    private boolean pathFinding = false;
}
