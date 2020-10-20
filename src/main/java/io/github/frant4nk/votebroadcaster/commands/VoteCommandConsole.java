package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class VoteCommandConsole implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("Command con comunicate Vote Server and this server"))
                .permission("votebroadcaster.command.admin.voteconsole")
                .arguments(
                        GenericArguments.flags()
                        .valueFlag(GenericArguments.string(Text.of("player")), "-player")
                        .valueFlag(GenericArguments.string(Text.of("website")), "-website")
                        .buildWith(GenericArguments.none())
                )
                .executor(new VoteCommandConsole())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        Optional<String> player = args.<String>getOne("player");
        Optional<String> website = args.<String>getOne("website");

        src.sendMessage(Text.of(player.get() + " voted on " + website.get())); //Esto tiene que broadcastear a todos los sitios
        return CommandResult.success();
    }
}
