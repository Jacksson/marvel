package co.jackson.marvel.services;

import co.jackson.marvel.log.QueryLog;
import co.jackson.marvel.log.QueryLogService;
import co.jackson.marvel.persistence.QueryLogEntity;
import co.jackson.marvel.persistence.QueryLogRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QueryLogServiceImpl implements QueryLogService {

    private static final Logger LOG = LoggerFactory.getLogger(QueryLogServiceImpl.class);

    private final QueryLogRepository queryLogRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public QueryLogServiceImpl(
        QueryLogRepository queryLogRepository,
        ModelMapper modelMapper) {

        this.queryLogRepository = queryLogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QueryLog> getAllQueryLogs() {
        LOG.debug("tries get all logs");
        List<QueryLogEntity> queryLogs = queryLogRepository.findAll();
        LOG.debug("{} logs found", queryLogs.size());
        return queryLogs.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private QueryLog convertToDto(QueryLogEntity queryLogEntity) {
        return modelMapper.map(queryLogEntity, QueryLog.class);
    }
}
