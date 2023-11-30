package co.jackson.marvel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLogEntity, Long> {
}
