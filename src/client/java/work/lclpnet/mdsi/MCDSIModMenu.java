package work.lclpnet.mdsi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import work.lclpnet.mdsi.gui.ConfigScreenBuilder;

public class MCDSIModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> DebugClientHandler.get()
                .map(ConfigScreenBuilder::new)
                .map(builder -> builder.create(parent))
                .orElse(null);
    }
}
