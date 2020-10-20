package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.subcommands.vote.ShowCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

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
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        plugin.getPaginationService().builder()
                .contents(getCommands())
                .title(Text.builder().color(TextColors.LIGHT_PURPLE).append(Text.of("commands"))
                .build()).sendTo(src);

        return CommandResult.success();
    }

    private List<Text> getCommands()
    {
        List<Text> texts = new ArrayList<>();
        texts.add(Text.builder()
                .onClick(TextActions.suggestCommand("/vote show"))
                .onHover(TextActions.showText(Text.of("List all the websites")))
                .append(Text.of("/vote show"))
                .build());

        return texts;
    }
}
