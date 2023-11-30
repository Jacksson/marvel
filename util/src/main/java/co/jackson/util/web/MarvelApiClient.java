package co.jackson.util.web;

import co.jackson.marvel.composite.character.CharacterAggregate;
import co.jackson.util.config.MarvelApiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final MarvelApiProperties apiProperties;

    @Autowired
    public MarvelApiClient(MarvelApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public List<CharacterAggregate> listCharacters() {
        try {
            String url = buildUrl(apiProperties.getCharactersEndpoint());
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
            String endpoint = apiProperties.getCharactersEndpoint() + "/" + characterId;
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
        String hash = generateHash(timestamp, apiProperties.getPrivateKey(), apiProperties.getPublicKey());

        return String.format(
            "%s%s?apikey=%s&ts=%d&hash=%s",
            apiProperties.getBaseUrl(),
            endpoint,
            apiProperties.getPublicKey(),
            timestamp,
            hash
        );
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

