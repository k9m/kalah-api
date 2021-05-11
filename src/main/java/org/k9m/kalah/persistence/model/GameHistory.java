package org.k9m.kalah.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class GameHistory {

    @Id
    private String id;

    private String gameId;
    private List<BoardStatus> history;

    public void appendHistory(BoardStatus boardStatus) {
        history.add(boardStatus);
    }
}
