package com.ferran.app.service;

import com.ferran.app.domain.GameUserRating;
import com.ferran.app.repository.GameUserRatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing GameUserRating.
 */
@Service
@Transactional
public class GameUserRatingService {

    private final Logger log = LoggerFactory.getLogger(GameUserRatingService.class);
    
    @Inject
    private GameUserRatingRepository gameUserRatingRepository;

    /**
     * Save a gameUserRating.
     *
     * @param gameUserRating the entity to save
     * @return the persisted entity
     */
    public GameUserRating save(GameUserRating gameUserRating) {
        log.debug("Request to save GameUserRating : {}", gameUserRating);
        GameUserRating result = gameUserRatingRepository.save(gameUserRating);
        return result;
    }

    /**
     *  Get all the gameUserRatings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<GameUserRating> findAll(Pageable pageable) {
        log.debug("Request to get all GameUserRatings");
        Page<GameUserRating> result = gameUserRatingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one gameUserRating by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GameUserRating findOne(Long id) {
        log.debug("Request to get GameUserRating : {}", id);
        GameUserRating gameUserRating = gameUserRatingRepository.findOne(id);
        return gameUserRating;
    }

    /**
     *  Delete the  gameUserRating by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GameUserRating : {}", id);
        gameUserRatingRepository.delete(id);
    }
}
