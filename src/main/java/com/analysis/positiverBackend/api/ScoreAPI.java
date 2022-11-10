package com.analysis.positiverBackend.api;

import com.analysis.positiverBackend.dao.ScoreRepository;
import com.analysis.positiverBackend.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ScoreAPI {
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreAPI(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @PostMapping("/postScore")
    public void postScore(Principal principal, @RequestBody Map<String, String> body) throws NumberFormatException{
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Score score = new Score(principal.getName(), out, Double.valueOf(body.get("score")));
        scoreRepository.save(score);
    }
    @GetMapping("/getScore")
    public List<Score> getScore(Principal principal){
        return scoreRepository.findScoreByEmail(principal.getName());
    }
}
