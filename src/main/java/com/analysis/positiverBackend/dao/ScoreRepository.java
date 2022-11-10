package com.analysis.positiverBackend.dao;

import com.analysis.positiverBackend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT e FROM Score e WHERE e.email = ?1 ORDER BY e.utilDate")
    List<Score> findScoreByEmail(String email);
}
