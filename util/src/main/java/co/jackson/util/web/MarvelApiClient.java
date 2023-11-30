package co.jackson.util.web;

import co.jackson.marvel.composite.character.CharacterAggregate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MarvelApiClient {
    private static final String API_BASE_URL = "https://gateway.marvel.com/v1/public/";
    private static final String API_CHARACTERS_ENDPOINT = "characters";
    private static final String API_PUBLIC_KEY = "05806db8184c39bef6b92a7a521b5a25";
    private static final String API_PRIVATE_KEY = "320d759b76193aabd3ae17b5367752de33987ee8";


    public List<CharacterAggregate> listCharacters() {
        try {
            String url = buildUrl(API_CHARACTERS_ENDPOINT);
            String response = sendRequest(url);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode charactersNode = rootNode.path("data").path("results");
            List<CharacterAggregate> characterAggregates = new ArrayList<>();

            for (JsonNode character : charactersNode) {
                CharacterAggregate characterAggregateObj = objectMapper.treeToValue(character, CharacterAggregate.class);
                characterAggregates.add(characterAggregateObj);
            }

            return characterAggregates;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); //lanzar una excepción personalizada aquí
        }
    }

    public CharacterAggregate getCharacterById(Long characterId) {
        try {
            String endpoint = API_CHARACTERS_ENDPOINT + "/" + characterId;
            String url = buildUrl(endpoint);
            String response = sendRequest(url);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode characterNode = rootNode.path("data").path("results").path(0);
            return objectMapper.treeToValue(characterNode, CharacterAggregate.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // o puedes lanzar una excepción personalizada aquí
        }
    }

    private String buildUrl(String endpoint) {
        long timestamp = System.currentTimeMillis();
        String hash = generateHash(timestamp, API_PRIVATE_KEY, API_PUBLIC_KEY);

        return String.format("%s%s?apikey=%s&ts=%d&hash=%s", API_BASE_URL, endpoint, API_PUBLIC_KEY, timestamp, hash);
    }

    private String sendRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return new String(connection.getInputStream().readAllBytes());
        } else {
            throw new HttpClientErrorException(
                HttpStatusCode.valueOf(responseCode),
                "Request failed with response code: "
            );
        }
    }

    private String generateHash(long timestamp, String privateKey, String publicKey) {
        String input = timestamp + privateKey + publicKey;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xFF & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            //log.error("Error generating hash", e);
            return "";
        }
    }
}

