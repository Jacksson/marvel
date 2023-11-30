package co.jackson.marvel.log;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/log")
public interface QueryLogService {

    @GetMapping
    List<QueryLog> getAllQueryLogs();
}
