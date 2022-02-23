import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class Word extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {
        if(messageReceivedEvent.getMessage().getContentRaw().startsWith("d.define")){
            String word = messageReceivedEvent.getMessage().getContentRaw().split(" ")[1];
            Pair<EmbedBuilder, URL> pair = ApiCalls.getDefinition(word);
            EmbedBuilder embedBuilder = pair.getA();
            URL url = pair.getB();

            embedBuilder.setFooter("Requested by " + messageReceivedEvent.getAuthor().getName(),messageReceivedEvent.getAuthor().getAvatarUrl());
            messageReceivedEvent.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            messageReceivedEvent.getChannel().sendMessage(url.toString()).queue();
        }
    }
}
