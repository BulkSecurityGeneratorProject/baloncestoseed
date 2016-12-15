package com.ferran.app.service;

import com.ferran.app.domain.UserPlayerFavorite;
import com.ferran.app.repository.UserPlayerFavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing UserPlayerFavorite.
 */
@Service
@Transactional
public class UserPlayerFavoriteService {

    private final Logger log = LoggerFactory.getLogger(UserPlayerFavoriteService.class);
    
    @Inject
    private UserPlayerFavoriteRepository userPlayerFavoriteRepository;

    /**
     * Save a userPlayerFavorite.
     *
     * @param userPlayerFavorite the entity to save
     * @return the persisted entity
     */
    public UserPlayerFavorite save(UserPlayerFavorite userPlayerFavorite) {
        log.debug("Request to save UserPlayerFavorite : {}", userPlayerFavorite);
        UserPlayerFavorite result = userPlayerFavoriteRepository.save(userPlayerFavorite);
        return result;
    }

    /**
     *  Get all the userPlayerFavorites.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserPlayerFavorite> findAll(Pageable pageable) {
        log.debug("Request to get all UserPlayerFavorites");
        Page<UserPlayerFavorite> result = userPlayerFavoriteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one userPlayerFavorite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserPlayerFavorite findOne(Long id) {
        log.debug("Request to get UserPlayerFavorite : {}", id);
        UserPlayerFavorite userPlayerFavorite = userPlayerFavoriteRepository.findOne(id);
        return userPlayerFavorite;
    }

    /**
     *  Delete the  userPlayerFavorite by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPlayerFavorite : {}", id);
        userPlayerFavoriteRepository.delete(id);
    }
}
