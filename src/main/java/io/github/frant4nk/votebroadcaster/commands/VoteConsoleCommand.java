package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.SQLUtil;
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

        String msgBroadcast = plugin.getConfig().getNode("msgBroadcast").getString();
        String replacedBroadcast = msgBroadcast.replace("%player", player.get()).replace("%website", website.get());

        String votes = "";

        if(player.isPresent() && website.isPresent())
        {
            Text prefix = Text.builder("[VOTE] ").color(TextColors.GOLD).style(TextStyles.BOLD).build();
            Text message = Text.builder(replacedBroadcast)
                    .color(TextColors.AQUA).style(TextStyles.RESET).build();

            channel.send(Text.of(prefix.concat(message)));
            try {
                SQLUtil.saveVote(player.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                votes = SQLUtil.getVotes(player.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            int goals = plugin.getConfig().getNode("goals").getInt();
            for(int i = 1 ; i <= goals ; i++)
            {
                int meta = plugin.getConfig().getNode("votegoal", "goal" + i, "votes").getInt();
                if(Integer.parseInt(votes) == meta)
                {
                    String msgGoal = plugin.getConfig().getNode("votegoal", "goal" + i, "msg").getString();
                    String replacedMsgGoal = msgGoal.replace("%player", player.get());

                    Text prefix2 = Text.builder("[VOTE] ").color(TextColors.GOLD).style(TextStyles.BOLD).build();
                    Text message2 = Text.builder(replacedMsgGoal)
                            .color(TextColors.AQUA).style(TextStyles.RESET).build();
                    channel.send(Text.of(prefix2.concat(message2)));

                    String cmd = plugin.getConfig().getNode("votegoal", "goal" + i, "command").getString();
                    String replacedCMD = cmd.replace("%player", player.get());

                    System.out.println(replacedCMD);
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), replacedCMD);
                }
            }

            return CommandResult.success();
        } else
        {
            src.sendMessage(Text.of("You forgot something!!"));
            return CommandResult.success();
        }
    }
}