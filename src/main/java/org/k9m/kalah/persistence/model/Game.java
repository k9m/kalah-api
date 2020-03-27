package org.k9m.kalah.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Game {

    @Id
    private String gameId;
    private String playerTurn;
    private BoardStatus boardStatus;


}
