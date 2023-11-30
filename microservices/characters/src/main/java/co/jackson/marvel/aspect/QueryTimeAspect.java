package co.jackson.marvel.aspect;

import co.jackson.marvel.persistence.QueryLogEntity;
import co.jackson.marvel.persistence.QueryLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class QueryTimeAspect {

    private final QueryLogRepository queryLogRepository;

    @Autowired
    public QueryTimeAspect(QueryLogRepository queryLogRepository) {
        this.queryLogRepository = queryLogRepository;
    }

    @After("@annotation(co.jackson.marvel.aspect.LogQueryTime)")
    public void logQueryTime(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime queryTime = LocalDateTime.now();

        QueryLogEntity queryLog = new QueryLogEntity();
        queryLog.setMethodName(methodName);
        queryLog.setQueryTime(queryTime);

        queryLogRepository.save(queryLog);
    }
}
