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
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class TopCommand extends VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("List all your votes"))
                .permission("votebroadcaster.command.vote.top") //Añadir argumentos si hicieran falta paginas??
                .executor(new TopCommand())
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
        plugin.getPaginationService().builder()
                .contents(getTop())
                .title(Text.builder("TOP 10").color(TextColors.LIGHT_PURPLE).style(TextStyles.BOLD).build())
                .sendTo(src);
        return CommandResult.success();
    }

    public List<Text> getTop()
    {
        List<String> topNames = null;
        List<String> topVotes = null;
        try {
            topNames = SQLUtil.getTop10Names();
            topVotes = SQLUtil.getTop10Votes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Text> texts = new ArrayList<>();
        texts.add(Text.builder("").build());
        texts.add(Text.builder("    " + "1: " + topNames.get(0) + " with " + topVotes.get(0) + " votes")
                .color(TextColors.GOLD)
                .style(TextStyles.BOLD)
                .build());
        texts.add(Text.builder("    " + "2: " + topNames.get(1) + " with " + topVotes.get(1) + " votes")
                .color(TextColors.DARK_GRAY)
                .style(TextStyles.BOLD)
                .build());
        texts.add(Text.builder("    " + "3: " + topNames.get(2) + " with " + topVotes.get(2) + " votes")
                .color(TextColors.RED)
                .style(TextStyles.BOLD)
                .build());
        for(int i = 3 ; i < topNames.size() ; i++)
        {
            int pos = i + 1;
            texts.add(Text.builder("    " + pos + ": " + topNames.get(i) + " with " + topVotes.get(i) + " votes")
                    .color(TextColors.WHITE)
                    .build());
        }
        return texts;
    }
}
