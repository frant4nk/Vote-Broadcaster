package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class VoteConsoleCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("Command con comunicate Vote Server and this server"))
                .permission("votebroadcaster.admin.voteconsole")
                .arguments(
                        GenericArguments.flags()
                        .valueFlag(GenericArguments.string(Text.of("player")), "-player")
                        .valueFlag(GenericArguments.string(Text.of("website")), "-website")
                        .buildWith(GenericArguments.none())
                )
                .executor(new VoteConsoleCommand())
                .build();
    }

    
    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        MessageChannel channel = Sponge.getServer().getBroadcastChannel();
        Optional<String> player = args.<String>getOne("player");
        Optional<String> website = args.<String>getOne("website");

        //TODO hacer el mensaje a partir de la config

        //player.get(), website.get()
        Text prefix = Text.builder("[VOTE] ").color(TextColors.GOLD).style(TextStyles.BOLD).build();
        Text message = Text.builder(player.get() + " thanks for your vote on " + website.get())
                .color(TextColors.AQUA).style(TextStyles.RESET).build();

        channel.send(Text.of(prefix.concat(message)));
        return CommandResult.success(); //TODO dejar preparado con el votifier tan pronto como se pueda

        //TODO hacer que desde el server del votifier se puedan añadir links u otras cosas de interes a las configs de todos los servers
        //TODO basicamente que se pueda manejar la config desde alli, con lo cual el comando reload lo ejecutara el server de votifier tambien
    }
}
