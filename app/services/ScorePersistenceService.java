package services;

import jpa.Score;

import java.util.List;

public interface ScorePersistenceService {
    void saveScore(Score t);

    List<Score> fetchAllScores();
}