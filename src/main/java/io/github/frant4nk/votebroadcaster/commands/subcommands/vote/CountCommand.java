package io.github.frant4nk.votebroadcaster.commands.subcommands.vote;

import io.github.frant4nk.votebroadcaster.SQLUtil;
import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import static org.spongepowered.api.text.Text.builder;

public class CountCommand extends VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("List all your votes"))
                .permission("votebroadcaster.command.vote.count") //Añadir argumentos si hicieran falta paginas??
                .executor(new CountCommand())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        if(!(src instanceof Player))
        {
            src.sendMessage(Text.of("Console cant execute this command!"));
            return CommandResult.success();
        }
        Player player = (Player) src;
        String votes = "0";
        try {
            votes = SQLUtil.getVotes(player.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = plugin.getConfig().getNode("currentvotes").getString();
        String msg_replace = msg.replace("%votes", votes);
        src.sendMessage(Text.builder(msg_replace).color(TextColors.GREEN).build());

        return CommandResult.success();
    }
}
