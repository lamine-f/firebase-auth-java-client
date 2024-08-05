package sn.lord.firebase.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sn.lord.configs.configfile.ConfigurationFileManagerImpl;
import sn.lord.configs.configfile.enums.ConfigurationFile;
import sn.lord.configs.configfile.exceptions.ConfigurationFileNotFound;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WithEmailAndPassword {

    public static final String FIREBASE_API_KEY;

    static {
        try {
            FIREBASE_API_KEY = ConfigurationFileManagerImpl.getInstance().getConfigValue(ConfigurationFile.FIREBASE_CONFIGURATION_FILE, "FIREBASE_API_KEY");
        } catch (ConfigurationFileNotFound e) {
            throw new RuntimeException(e);
        }
    }

    public static String signIn(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String requestBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"returnSecureToken\":true}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
                    String responseBody = scanner.useDelimiter("\\A").next();

                    // Parse response to extract token using Gson
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                    return jsonObject.get("idToken").getAsString();
                }
            } else {
                try (Scanner scanner = new Scanner(connection.getErrorStream(), StandardCharsets.UTF_8)) {
                    String responseBody = scanner.useDelimiter("\\A").next();
                    System.err.println("Error: " + responseBody);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
