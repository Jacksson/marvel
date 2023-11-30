package co.jackson.marvel.core.Story;

import lombok.Data;

import java.util.List;

@Data
public class StoryCollection {
    private int available;
    private int returned;
    private String collectionURI;
    private List<StoryItem> items;
}
