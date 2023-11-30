package co.jackson.marvel.core.Comic;

import lombok.Data;

import java.util.List;

@Data
public class ComicCollection {
    private int available;
    private int returned;
    private String collectionURI;
    private List<ComicItem> items;
}
