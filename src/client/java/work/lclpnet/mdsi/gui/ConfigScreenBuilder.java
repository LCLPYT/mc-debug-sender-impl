package work.lclpnet.mdsi.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import work.lclpnet.mdsi.DebugClientHandler;
import work.lclpnet.mdsi.config.DebugConfig;

import static net.minecraft.text.Text.translatable;
import static work.lclpnet.mdsi.MCDSIModInit.MOD_ID;

public class ConfigScreenBuilder implements ConfigScreenFactory<Screen> {

    public static final String TITLE = MOD_ID + ".config.title";
    private final DebugClientHandler handler;

    public ConfigScreenBuilder(DebugClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public Screen create(Screen parent) {
        var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(translatable(TITLE))
                .setSavingRunnable(handler::update);

        DebugConfig config = handler.config();
        DebugConfig defaultConfig = new DebugConfig();

        String keyRenderers = TITLE + ".renderers";
        ConfigCategory renderers = builder.getOrCreateCategory(translatable(keyRenderers));

        renderers.addEntry(builder.entryBuilder()
                .startBooleanToggle(translatable(keyRenderers + ".pathFinding"), config.isPathFinding())
                .setDefaultValue(defaultConfig.isPathFinding())
                .setSaveConsumer(config::setPathFinding)
                .build());

        return builder.build();
    }
}
