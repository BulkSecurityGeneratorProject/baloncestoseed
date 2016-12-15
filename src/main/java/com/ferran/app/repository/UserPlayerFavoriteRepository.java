package com.ferran.app.repository;

import com.ferran.app.domain.UserPlayerFavorite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserPlayerFavorite entity.
 */
@SuppressWarnings("unused")
public interface UserPlayerFavoriteRepository extends JpaRepository<UserPlayerFavorite,Long> {

    @Query("select userPlayerFavorite from UserPlayerFavorite userPlayerFavorite where userPlayerFavorite.user.login = ?#{principal.username}")
    List<UserPlayerFavorite> findByUserIsCurrentUser();

}
