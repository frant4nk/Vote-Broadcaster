package io.github.frant4nk.votebroadcaster.commands.subcommands.vote;


import com.google.common.reflect.TypeToken;
import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.List;

//Fijarse en subcommands the catclearlag
public class ShowCommand extends VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("List all the websites"))
                .permission("votebroadcaster.player.vote") //Añadir argumentos si hicieran falta paginas??
                .executor(new ShowCommand())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        List<String> msg = null;
        try {
            msg = plugin.getConfig().getNode("msg").getList(TypeToken.of(String.class)); //TODO cambiar los nodos bien
        } catch (ObjectMappingException e) {
            e.printStackTrace(); //TODO add color, links etc
        }
        for(String _msg : msg)
        {
            src.sendMessage(Text.of(_msg)); //TODO add color here?
        }
        return CommandResult.success();
    }
}
