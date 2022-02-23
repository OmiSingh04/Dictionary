import net.dv8tion.jda.api.EmbedBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiCalls {

    public static Pair getDefinition(String word){
        try {
            URL url = new URL(String.format("https://api.dictionaryapi.dev/api/v2/entries/en/%s", word));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                jsonResponse.append(line + "\n");
            }
            reader.close();
            JSONParser parser = new JSONParser();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            JSONArray array = (JSONArray) parser.parse(jsonResponse.toString());
            JSONObject object = (JSONObject) array.get(0);

            embedBuilder.setTitle("Word - " + Character.toString(Character.toUpperCase(object.get("word").toString().charAt(0)))
                     + object.get("word").toString().substring(1));
            String audioURL = ((JSONObject)((JSONArray) object.get("phonetics")).get(0)).get("audio").toString();

            JSONArray meanings = (JSONArray) object.get("meanings");
            URL audio = new URL(audioURL);

            for(int i = 0; i < meanings.size(); i++){
                JSONObject obj = (JSONObject) meanings.get(i);
                String par = obj.get("partOfSpeech").toString();
                par = Character.toUpperCase(par.charAt(0)) + par.substring(1);
                JSONArray definitions = (JSONArray) obj.get("definitions");
                JSONObject defineObject = (JSONObject) definitions.get(0);
                String define  = defineObject.get("definition").toString();
                embedBuilder.addField(par, define, false);
            }
            return new Pair<EmbedBuilder, URL>(embedBuilder, audio);
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }

    }

}
