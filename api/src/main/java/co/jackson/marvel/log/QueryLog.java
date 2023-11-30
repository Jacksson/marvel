package co.jackson.marvel.log;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryLog {
    private Long id;
    private String methodName;
    private LocalDateTime queryTime;
}
