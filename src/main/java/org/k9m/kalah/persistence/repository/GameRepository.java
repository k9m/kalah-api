package org.k9m.kalah.persistence.repository;

import org.k9m.kalah.persistence.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {}