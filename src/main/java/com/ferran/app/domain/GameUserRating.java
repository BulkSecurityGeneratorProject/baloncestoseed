package com.ferran.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A GameUserRating.
 */
@Entity
@Table(name = "game_user_rating")
public class GameUserRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @ManyToOne
    private Game game;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public GameUserRating rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public GameUserRating timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Game getGame() {
        return game;
    }

    public GameUserRating game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public GameUserRating user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameUserRating gameUserRating = (GameUserRating) o;
        if (gameUserRating.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gameUserRating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GameUserRating{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
