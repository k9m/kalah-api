package org.k9m.kalah.persistence.repository;

import org.k9m.kalah.persistence.model.GameHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameHistoryRepository extends MongoRepository<GameHistory, String> {
    GameHistory findFirstByGameId(String gameId);
}