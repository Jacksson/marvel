package co.jackson.marvel.composite.character;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import co.jackson.marvel.aspect.LogQueryTime;

@RequestMapping("/api/characters")
public interface CharacterCompositeService {

    @LogQueryTime
    @GetMapping
    List<CharacterAggregate> listCharacters();

    @LogQueryTime
    @GetMapping("/{id}")
    CharacterAggregate getCharacterById(@PathVariable Long id);

}
