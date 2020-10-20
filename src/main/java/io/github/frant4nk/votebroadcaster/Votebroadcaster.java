package io.github.frant4nk.votebroadcaster;

import com.google.inject.Inject;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import io.github.frant4nk.votebroadcaster.commands.VoteCommandConsole;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.pagination.PaginationService;

import java.io.File;

@Plugin(
        id = "votebroadcaster",
        name = "Votebroadcaster",
        description = "Simple plugin to broadcast to server a succesfull vote",
        authors = {
                "FranT4NK"
        }
)
public class Votebroadcaster
{

    public static Votebroadcaster instance;

    private final Logger logger;
    private final File configDir;
    private final Game game;

    @Inject
    public Votebroadcaster(Logger logger, @ConfigDir(sharedRoot = false) File configDir, Game game)
    {
        this.logger = logger;
        this.configDir = configDir;
        this.game = game;
        instance = this;
    }

    @Listener
    public void onInit(GameInitializationEvent event)
    {
        registerCommands();
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {

    }

    private void registerCommands()
    {
        getLogger().info("Registering commands...");
        Sponge.getCommandManager().register(this, VoteCommand.getCommand(), "vote");
        Sponge.getCommandManager().register(this, VoteCommandConsole.getCommand(), "consolevote");
    }

    public Logger getLogger()
    {
        return logger;
    }

    public PaginationService getPaginationService()
    {
        return game.getServiceManager().provide(PaginationService.class).get();
    }
}
