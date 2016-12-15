package com.ferran.app.repository;

import com.ferran.app.domain.GameUserRating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GameUserRating entity.
 */
@SuppressWarnings("unused")
public interface GameUserRatingRepository extends JpaRepository<GameUserRating,Long> {

    @Query("select gameUserRating from GameUserRating gameUserRating where gameUserRating.user.login = ?#{principal.username}")
    List<GameUserRating> findByUserIsCurrentUser();

}
