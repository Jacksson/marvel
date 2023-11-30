package co.jackson.marvel.services;

import co.jackson.marvel.aspect.LogQueryTime;
import co.jackson.marvel.composite.character.CharacterAggregate;
import co.jackson.marvel.composite.character.CharacterCompositeService;
import co.jackson.marvel.exceptions.InvalidInputException;
import co.jackson.marvel.exceptions.NotFoundException;
import co.jackson.util.http.HttpErrorInfo;
import co.jackson.util.web.MarvelApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class CharacterCompositeServiceImpl implements CharacterCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(CharacterCompositeServiceImpl.class);

    private final ObjectMapper mapper;
    private final MarvelApiClient marvelApiClient;

    @Autowired
    public CharacterCompositeServiceImpl(ObjectMapper mapper, MarvelApiClient marvelApiClient) {
        this.mapper = mapper;
        this.marvelApiClient = marvelApiClient;
    }


    @Override
    @LogQueryTime
    public List<CharacterAggregate> listCharacters() {
        try {
            LOG.debug("Will call Character list from Marvel API");

            var result = marvelApiClient.listCharacters();
            LOG.debug("Found {} Characters", result.size());

            return result;

        } catch (HttpClientErrorException ex) {

            switch (Objects.requireNonNull(HttpStatus.resolve(ex.getStatusCode().value()))) {
                case NOT_FOUND:
                    throw new NotFoundException(ex.getResponseBodyAsString());

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(ex.getResponseBodyAsString());

                default:
                    LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    @Override
    @LogQueryTime
    public CharacterAggregate getCharacterById(Long id) {
        try {
            LOG.debug("Will call Character list from Marvel API");

            var result = marvelApiClient.getCharacterById(id);
            LOG.debug("Found a Characters with Id: {}", id);

            return result;

        } catch (HttpClientErrorException ex) {

            switch (Objects.requireNonNull(HttpStatus.resolve(ex.getStatusCode().value()))) {
                case NOT_FOUND:
                    throw new NotFoundException(ex.getMessage());

                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(ex.getResponseBodyAsString());

                default:
                    LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }
}

