package com.ferran.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ferran.app.domain.enumeration.Position;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @NotNull
    @Column(name = "nbaskets", nullable = false)
    private Integer nbaskets;

    @NotNull
    @Column(name = "nassists", nullable = false)
    private Integer nassists;

    @Column(name = "nrebots")
    private Integer nrebots;

    @Enumerated(EnumType.STRING)
    @Column(name = "pos")
    private Position pos;

    @OneToOne
    @JoinColumn(unique = true)
    private City city;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private Set<UserPlayerFavorite> userPlayerFavorites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Player surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Player birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getNbaskets() {
        return nbaskets;
    }

    public Player nbaskets(Integer nbaskets) {
        this.nbaskets = nbaskets;
        return this;
    }

    public void setNbaskets(Integer nbaskets) {
        this.nbaskets = nbaskets;
    }

    public Integer getNassists() {
        return nassists;
    }

    public Player nassists(Integer nassists) {
        this.nassists = nassists;
        return this;
    }

    public void setNassists(Integer nassists) {
        this.nassists = nassists;
    }

    public Integer getNrebots() {
        return nrebots;
    }

    public Player nrebots(Integer nrebots) {
        this.nrebots = nrebots;
        return this;
    }

    public void setNrebots(Integer nrebots) {
        this.nrebots = nrebots;
    }

    public Position getPos() {
        return pos;
    }

    public Player pos(Position pos) {
        this.pos = pos;
        return this;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public City getCity() {
        return city;
    }

    public Player city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Team getTeam() {
        return team;
    }

    public Player team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<UserPlayerFavorite> getUserPlayerFavorites() {
        return userPlayerFavorites;
    }

    public Player userPlayerFavorites(Set<UserPlayerFavorite> userPlayerFavorites) {
        this.userPlayerFavorites = userPlayerFavorites;
        return this;
    }

    public Player addUserPlayerFavorite(UserPlayerFavorite userPlayerFavorite) {
        userPlayerFavorites.add(userPlayerFavorite);
        userPlayerFavorite.setPlayer(this);
        return this;
    }

    public Player removeUserPlayerFavorite(UserPlayerFavorite userPlayerFavorite) {
        userPlayerFavorites.remove(userPlayerFavorite);
        userPlayerFavorite.setPlayer(null);
        return this;
    }

    public void setUserPlayerFavorites(Set<UserPlayerFavorite> userPlayerFavorites) {
        this.userPlayerFavorites = userPlayerFavorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if (player.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", surname='" + surname + "'" +
            ", birthdate='" + birthdate + "'" +
            ", nbaskets='" + nbaskets + "'" +
            ", nassists='" + nassists + "'" +
            ", nrebots='" + nrebots + "'" +
            ", pos='" + pos + "'" +
            '}';
    }
}
