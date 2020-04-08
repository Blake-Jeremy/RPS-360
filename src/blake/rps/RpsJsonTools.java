package blake.rps;
/*******************************************************************
 *  RpsJsonTools class
 *  Description:  This is where all of the Json interactions are
 *  for the export of the results from the user interface.
 *******************************************************************/

// Imported Libraries
import com.google.gson.*;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RpsJsonTools {

    public static JsonArray jArray = new JsonArray();

    public static void addToJsonArray(String counter, String user, String comp, String userScore, String compScore, String result)
    {
        // build object and output to gson
        Gson gsonObj = new Gson();
        // create and populate hashmap
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("round", counter);
        inputMap.put("user", user);
        inputMap.put("comp", comp);
        inputMap.put("userScore", userScore);
        inputMap.put("compScore", compScore);
        inputMap.put("result", result);
        // convert hashmap to gson object
        String jsonStr = gsonObj.toJson(inputMap);
        // convert hashmap to json object
        JSONObject json = new JSONObject(inputMap);
        // send JSON object to Tomcat server as POST
        sendRequest(json);
        // add json based string to gson array
        jArray.add(jsonStr);
    }

    public static void saveToFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get("results.json"));
            gson.toJson(jArray, writer);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendRequest(JSONObject json) {
        URL url;
        HttpURLConnection connection = null;
        ObjectOutputStream out;
        try {
            url = new URL("http://localhost:8080/RPS_war_exploded/Servlet");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // Send request
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            System.out.println(json.toString());
            osw.write(json.toString());
            osw.flush();
            osw.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Ok response");
            } else {
                System.out.println("Bad response");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
