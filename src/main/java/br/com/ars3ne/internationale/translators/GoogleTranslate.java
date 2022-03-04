package br.com.ars3ne.internationale.translators;

import br.com.ars3ne.internationale.Translator;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslate implements Translator {

    /**
     * This method translates the text into the destination language.
     *
     * @param text This is the text that will be translated.
     * @param destinationLanguage This is the language that the text will be translated to.
     * @param sourceLanguage The language of the original text.
     * @return A string containing the translated text, or null on failure.
     *
     */
    public String translate(String text, String destinationLanguage, String sourceLanguage) {

        String content;

        // Try to use the first endpoint.
        try {
            content = makeRequest("https://translate.googleapis.com/translate_a/single?client=gtx&dt=t&sl=" + sourceLanguage + "&tl=" + destinationLanguage + "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));
            JSONArray jsonArray = new JSONArray(content);
            return jsonArray.getJSONArray(0).getJSONArray(0).getString(0);
        }catch(IOException ignored) { }

        // If an error happened, try to use the second endpoint.
        try {
            content = makeRequest("https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=" + sourceLanguage + "&tl=" + destinationLanguage + "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));
            JSONArray jsonArray = new JSONArray(content);
            return jsonArray.getString(0);
        }catch(IOException ignored) { }

        // Unable to translate, return null.
        return null;

    }

    /**
     * This method translates the text into the destination language.
     *
     * @param text This is the text that will be translated.
     * @param destinationLanguage This is the language that the text will be translated to.
     * @return A string containing the translated text, or null on failure.
     *
     */
    public String translate(String text, String destinationLanguage){
        return translate(text, destinationLanguage, "auto");
    }

    /**
     * This method returns the language of the text.
     *
     * @param text The text to detect the language.
     * @return A string containg the language, or null on failure.
     *
     */
    public String getLanguage(String text) {

        String content;
        try {
            content = makeRequest("https://translate.googleapis.com/translate_a/single?client=dict-chrome-ex&sl=auto&tl=ja&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));
            JSONArray jsonArray = new JSONArray(content);
            return jsonArray.getString(2);
        }catch(IOException ignored) { }

        return null;

    }

    /**
     * Internal function.
     */
    private String makeRequest(String endpoint) throws IOException {

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        StringBuilder content;

        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } finally {
            connection.disconnect();
        }

        return content.toString();

    }

}
