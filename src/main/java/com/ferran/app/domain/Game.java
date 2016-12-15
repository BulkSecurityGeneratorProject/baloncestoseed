package com.ferran.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time_start")
    private ZonedDateTime timeStart;

    @Column(name = "time_finish")
    private ZonedDateTime timeFinish;

    @NotNull
    @Column(name = "score_local", nullable = false)
    private Integer scoreLocal;

    @NotNull
    @Column(name = "score_visitor", nullable = false)
    private Integer scoreVisitor;

    @ManyToOne
    private Team gameLocalTeam;

    @ManyToOne
    private Team gameVisitorTeam;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private Set<GameUserRating> gameUserRatings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    public Game timeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeFinish() {
        return timeFinish;
    }

    public Game timeFinish(ZonedDateTime timeFinish) {
        this.timeFinish = timeFinish;
        return this;
    }

    public void setTimeFinish(ZonedDateTime timeFinish) {
        this.timeFinish = timeFinish;
    }

    public Integer getScoreLocal() {
        return scoreLocal;
    }

    public Game scoreLocal(Integer scoreLocal) {
        this.scoreLocal = scoreLocal;
        return this;
    }

    public void setScoreLocal(Integer scoreLocal) {
        this.scoreLocal = scoreLocal;
    }

    public Integer getScoreVisitor() {
        return scoreVisitor;
    }

    public Game scoreVisitor(Integer scoreVisitor) {
        this.scoreVisitor = scoreVisitor;
        return this;
    }

    public void setScoreVisitor(Integer scoreVisitor) {
        this.scoreVisitor = scoreVisitor;
    }

    public Team getGameLocalTeam() {
        return gameLocalTeam;
    }

    public Game gameLocalTeam(Team team) {
        this.gameLocalTeam = team;
        return this;
    }

    public void setGameLocalTeam(Team team) {
        this.gameLocalTeam = team;
    }

    public Team getGameVisitorTeam() {
        return gameVisitorTeam;
    }

    public Game gameVisitorTeam(Team team) {
        this.gameVisitorTeam = team;
        return this;
    }

    public void setGameVisitorTeam(Team team) {
        this.gameVisitorTeam = team;
    }

    public Set<GameUserRating> getGameUserRatings() {
        return gameUserRatings;
    }

    public Game gameUserRatings(Set<GameUserRating> gameUserRatings) {
        this.gameUserRatings = gameUserRatings;
        return this;
    }

    public Game addGameUserRating(GameUserRating gameUserRating) {
        gameUserRatings.add(gameUserRating);
        gameUserRating.setGame(this);
        return this;
    }

    public Game removeGameUserRating(GameUserRating gameUserRating) {
        gameUserRatings.remove(gameUserRating);
        gameUserRating.setGame(null);
        return this;
    }

    public void setGameUserRatings(Set<GameUserRating> gameUserRatings) {
        this.gameUserRatings = gameUserRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        if (game.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", timeStart='" + timeStart + "'" +
            ", timeFinish='" + timeFinish + "'" +
            ", scoreLocal='" + scoreLocal + "'" +
            ", scoreVisitor='" + scoreVisitor + "'" +
            '}';
    }
}
