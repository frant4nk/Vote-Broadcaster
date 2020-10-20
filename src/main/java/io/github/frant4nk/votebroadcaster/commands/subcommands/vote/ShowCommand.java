package io.github.frant4nk.votebroadcaster.commands.subcommands.vote;


import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

//Fijarse en subcommands the catclearlag
public class ShowCommand extends VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("List all the websites"))
                .permission("votebroadcaster.command.vote") //Añadir argumentos si hicieran falta paginas??
                .executor(new ShowCommand())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {

        src.sendMessage(Text.of("Sitio 1"));
        return CommandResult.success();
    }
}
