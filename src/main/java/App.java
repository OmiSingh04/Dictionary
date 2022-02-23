import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class App {


    private static JDA jda;

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))

        // Disable parts of the cache
        .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
        // Enable the bulk delete event
        .setBulkDeleteSplittingEnabled(false)
        // Disable compression (not recommended)
        .setCompression(Compression.NONE)
        // Set activity (like "playing Something")
        .setActivity(Activity.listening("d.help")).build();
        jda.addEventListener(new Word());


    }
}
