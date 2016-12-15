package com.ferran.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserPlayerFavorite.
 */
@Entity
@Table(name = "user_player_favorite")
public class UserPlayerFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "liked")
    private Boolean liked;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isLiked() {
        return liked;
    }

    public UserPlayerFavorite liked(Boolean liked) {
        this.liked = liked;
        return this;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public UserPlayerFavorite timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public UserPlayerFavorite user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public UserPlayerFavorite player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPlayerFavorite userPlayerFavorite = (UserPlayerFavorite) o;
        if (userPlayerFavorite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userPlayerFavorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserPlayerFavorite{" +
            "id=" + id +
            ", liked='" + liked + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
