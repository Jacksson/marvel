package co.jackson.marvel.composite.character;

import co.jackson.marvel.core.Event.EventCollection;
import co.jackson.marvel.core.Series.SeriesCollection;
import co.jackson.marvel.core.Story.StoryCollection;
import co.jackson.marvel.core.Comic.ComicCollection;
import co.jackson.marvel.core.resources.Thumbnail;
import co.jackson.marvel.core.resources.Url;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterAggregate {
    private int id;
    private String name;
    private String description;
    private String modified;
    private String resourceURI;
    private List<Url> urls;
    private Thumbnail thumbnail;
    private ComicCollection comics;
    private StoryCollection stories;
    private EventCollection events;
    private SeriesCollection series;
}
