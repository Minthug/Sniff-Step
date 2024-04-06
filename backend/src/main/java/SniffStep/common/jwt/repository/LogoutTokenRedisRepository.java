package SniffStep.common.jwt.repository;

import SniffStep.common.jwt.entity.LogoutToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutTokenRedisRepository extends CrudRepository<LogoutToken, String> {
}
