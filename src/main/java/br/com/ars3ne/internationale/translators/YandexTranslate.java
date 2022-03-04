package br.com.ars3ne.internationale.translators;

import br.com.ars3ne.internationale.Translator;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class YandexTranslate implements Translator {

    /**
     * This method translates the text into the destination language.
     *
     * @param text This is the text that will be translated.
     * @param destinationLanguage This is the language that the text will be translated to.
     * @param sourceLanguage The language of the original text.
     * @return A string containing the translated text, or null on failure.
     *
     */
    @Override
    public String translate(String text, String destinationLanguage, String sourceLanguage) {

        UUID uuid = UUID.randomUUID();
        String ucid = uuid.toString().replace("-", "");

        try {
            String content = makeRequest("https://translate.yandex.net/api/v1/tr.json/translate?ucid=" + ucid + "&srv=android&format=text", "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()) + "&lang=" + sourceLanguage + "-" + destinationLanguage);
            JSONObject json = new JSONObject(content);
            return json.getJSONArray("text").getString(0);
        } catch (IOException ignored) { }

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
    @Override
    public String translate(String text, String destinationLanguage) {
        return translate(text, destinationLanguage, getLanguage(text));
    }

    /**
     * This method returns the language of the text.
     *
     * @param text The text to detect the language.
     * @return A string containg the language, or null on failure.
     *
     */
    @Override
    public String getLanguage(String text) {

        UUID uuid = UUID.randomUUID();
        String ucid = uuid.toString().replace("-", "");

        try {
            String content = makeRequest("https://translate.yandex.net/api/v1/tr.json/detect?ucid=" + ucid + "&srv=android&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()) + "&hint=en");
            JSONObject json = new JSONObject(content);
            return json.getString("lang");
        } catch (IOException ignored) { }

        return null;

    }

    /**
     * Internal function.
     */
    private String makeRequest(String endpoint, String postData) throws IOException {

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "ru.yandex.translate/3.20.2024");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = postData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

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

    /**
     * Internal function.
     */
    private String makeRequest(String endpoint) throws IOException {

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "ru.yandex.translate/3.20.2024");
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
