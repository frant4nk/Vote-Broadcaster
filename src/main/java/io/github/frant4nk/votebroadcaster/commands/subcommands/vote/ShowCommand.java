package io.github.frant4nk.votebroadcaster.commands.subcommands.vote;


import com.google.common.reflect.TypeToken;
import io.github.frant4nk.votebroadcaster.SQLUtil;
import io.github.frant4nk.votebroadcaster.Votebroadcaster;
import io.github.frant4nk.votebroadcaster.commands.VoteCommand;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//Fijarse en subcommands the catclearlag
public class ShowCommand extends VoteCommand implements CommandExecutor
{
    private final Votebroadcaster plugin = Votebroadcaster.instance;

    public static CommandSpec getCommand()
    {
        return CommandSpec.builder()
                .description(Text.of("List all the websites"))
                .permission("votebroadcaster.command.vote.show") //Añadir argumentos si hicieran falta paginas??
                .executor(new ShowCommand())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args)
    {
        plugin.getPaginationService().builder()
                .contents(getWebsites())
                .title(Text.builder("Websites").color(TextColors.LIGHT_PURPLE).build())
                .sendTo(src);
        return CommandResult.success();
    }

    public List<Text> getWebsites()
    {
        List<String> url = null, names = null;
        List<Text> texts = new ArrayList<>();
        texts.add(Text.builder("").build());

        try {
            url = plugin.getConfig().getNode("websites", "url").getList(TypeToken.of(String.class));
            names = plugin.getConfig().getNode("websites", "names").getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        for(int i = 0 ; i < url.size() ; i++)
        {
            try {
                texts.add(Text.builder(names.get(i)).color(TextColors.DARK_GREEN).onClick(TextActions.openUrl(new URL(url.get(i)))).build());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return texts;
    }
}
