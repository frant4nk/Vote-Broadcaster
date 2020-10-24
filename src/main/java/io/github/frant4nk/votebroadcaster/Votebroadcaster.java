package io.github.frant4nk.votebroadcaster;

import com.google.inject.Inject;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import io.github.frant4nk.votebroadcaster.commands.VoteConsoleCommand;
import io.github.frant4nk.votebroadcaster.commands.VoteReloadCommand;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.pagination.PaginationService;

import java.io.IOException;
import java.nio.file.Path;

@Plugin(
        id = "votebroadcaster",
        name = "Vote Broadcaster",
        description = "Simple plugin to broadcast to server a succesfull vote",
        authors = {
                "FranT4NK"
        }
)
public class Votebroadcaster
{

    public static Votebroadcaster instance;

    private final Logger logger;
    private final Game game;


    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path configPath;

    @Inject
    @DefaultConfig(sharedRoot = true)
    ConfigurationLoader<CommentedConfigurationNode> loader;

    ConfigurationNode rootNode;

    @Inject
    public Votebroadcaster(Logger logger, Game game)
    {
        this.logger = logger;
        this.game = game;
        instance = this;
    }

    @Listener
    public void preInit(GamePreInitializationEvent e) throws IOException, ObjectMappingException
    {
        createConfig();
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
        Sponge.getCommandManager().register(this, VoteConsoleCommand.getCommand(), "voteconsole");
        Sponge.getCommandManager().register(this, VoteReloadCommand.getCommand(), "votereload");
        //TODO add ignore command
    }

    public Logger getLogger()
    {
        return logger;
    }

    public PaginationService getPaginationService()
    {
        return game.getServiceManager().provide(PaginationService.class).get();
    }

    public void createConfig() throws IOException
    {
        Sponge.getAssetManager().getAsset(this, "votebroadcaster.conf").get().copyToFile(configPath, false, true);
        loader = HoconConfigurationLoader.builder().setPath(configPath).build();
        rootNode = loader.load(); //TODO hacer la config bien
    }

    public void loadConfig()
    {
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig()
    {
        try {
            loader.save(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationNode getConfig()
    {
        return rootNode;
    }
}
