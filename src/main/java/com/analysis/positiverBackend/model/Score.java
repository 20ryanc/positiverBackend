package com.analysis.positiverBackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date utilDate;
    private Double score;

    public Score() {
    }

    public Score(String email, Date utilDate, Double score) {
        this.email = email;
        this.utilDate = utilDate;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(Date utilDate) {
        this.utilDate = utilDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", utilDate=" + utilDate +
                ", score=" + score +
                '}';
    }

}
