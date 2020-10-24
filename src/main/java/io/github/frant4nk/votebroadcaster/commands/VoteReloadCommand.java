package io.github.frant4nk.votebroadcaster.commands;

import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.io.IOException;

public class VoteReloadCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("Reload the plugin config"))
                .permission("votebroadcaster.admin.reload")
                .executor(new VoteReloadCommand())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        plugin.loadConfig();
        src.sendMessage(Text.of("Loaded config."));
        return CommandResult.success();
    }
}

