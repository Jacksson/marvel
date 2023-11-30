package co.jackson.marvel.core.Series;

import lombok.Data;

import java.util.List;

@Data
public class SeriesCollection {
    private int available;
    private int returned;
    private String collectionURI;
    private List<SeriesItem> items;
}
