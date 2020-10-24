package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.subcommands.vote.CountCommand;
import io.github.frant4nk.votebroadcaster.commands.subcommands.vote.ShowCommand;
import io.github.frant4nk.votebroadcaster.commands.subcommands.vote.TopCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;


//Fijarse en LaggyChunksCommands de CatClearLag
public class VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("To be defined"))
                .permission("votebroadcaster.command.vote")
                .executor(new VoteCommand())
                .child(ShowCommand.getCommand(), "show", "s")
                .child(CountCommand.getCommand(), "count", "c")
                .child(TopCommand.getCommand(), "top", "t")
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        plugin.getPaginationService().builder()
                .contents(getCommands())
                .header(Text.builder("Commands:").color(TextColors.LIGHT_PURPLE).build())
                .title(Text.builder("Helioss ")
                .color(TextColors.LIGHT_PURPLE)
                .style(TextStyles.BOLD)
                .append(Text.builder("Vote ")
                .color(TextColors.DARK_PURPLE)
                .style(TextStyles.BOLD)
                .append(Text.builder("System")
                .color(TextColors.LIGHT_PURPLE)
                .style(TextStyles.BOLD)
                .build()).build()).build())
                .build().sendTo(src);

        return CommandResult.success();
    }

    private List<Text> getCommands()
    {
        List<Text> texts = new ArrayList<>();
        texts.add(Text.builder("").build()); //Para el estilo
        texts.add(Text.builder()
                .onClick(TextActions.suggestCommand("/vote show"))
                .color(TextColors.YELLOW)
                .append(Text.of("   /vote show  - List all the websites")) //one tab
                .build());
        texts.add(Text.builder()
                .onClick(TextActions.suggestCommand("/vote count"))
                .color(TextColors.YELLOW)
                .append(Text.of("   /vote count - List your total votes")) //one tab
                .build());
        texts.add(Text.builder()
                .onClick(TextActions.suggestCommand("/vote top"))
                .color(TextColors.YELLOW)
                .append(Text.of("   /vote top    - List the Top 10")) //one tab
                .build());

        return texts;
    }
}
