package org.k9m.kalah.config.mongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.service.GameHistoryService;
import org.k9m.kalah.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoSaveEventListener extends AbstractMongoEventListener<Game> {

  private final GameHistoryService gameHistoryService;

  @Override
  public void onAfterSave(AfterSaveEvent<Game> event) {
    gameHistoryService.saveGameHistory(event.getSource());
  }
}
