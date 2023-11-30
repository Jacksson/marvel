package co.jackson.marvel.core.Event;


import lombok.Data;

import java.util.List;

@Data
public class EventCollection {
    private int available;
    private int returned;
    private String collectionURI;
    private List<EventItem> items;
}
