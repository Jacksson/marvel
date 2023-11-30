package co.jackson.marvel;
import co.jackson.marvel.composite.character.CharacterAggregate;
import co.jackson.marvel.exceptions.InvalidInputException;
import co.jackson.marvel.exceptions.NotFoundException;
import co.jackson.marvel.services.CharacterCompositeServiceImpl;
import co.jackson.util.web.MarvelApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterCompositeServiceTest {

    @Mock
    private MarvelApiClient marvelApiClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CharacterCompositeServiceImpl characterCompositeService;

    @Test
    void testListCharacters() {
        // Arrange
        List<CharacterAggregate> characters = Arrays.asList(
            new CharacterAggregate(),
            new CharacterAggregate()
        );
        when(marvelApiClient.listCharacters()).thenReturn(characters);

        // Act
        List<CharacterAggregate> result = characterCompositeService.listCharacters();

        // Assert
        verify(marvelApiClient, times(1)).listCharacters();
        assertEquals(characters, result);
    }

    @Test
    void testGetCharacterById() {
        // Arrange
        Long characterId = 1L;
        CharacterAggregate character = new CharacterAggregate();
        when(marvelApiClient.getCharacterById(characterId)).thenReturn(character);

        // Act
        CharacterAggregate result = characterCompositeService.getCharacterById(characterId);

        // Assert
        verify(marvelApiClient, times(1)).getCharacterById(characterId);
        assertEquals(character, result);
    }

    @Test
    void testListCharacters_HttpClientErrorException_NotFound() {
        // Arrange
        when(marvelApiClient.listCharacters()).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> characterCompositeService.listCharacters());
    }

    @Test
    void testListCharacters_HttpClientErrorException_UnprocessableEntity() {
        // Arrange
        when(marvelApiClient.listCharacters()).thenThrow(new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY));

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> characterCompositeService.listCharacters());
    }

}
