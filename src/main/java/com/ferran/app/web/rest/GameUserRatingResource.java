package com.ferran.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ferran.app.domain.GameUserRating;
import com.ferran.app.service.GameUserRatingService;
import com.ferran.app.web.rest.util.HeaderUtil;
import com.ferran.app.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GameUserRating.
 */
@RestController
@RequestMapping("/api")
public class GameUserRatingResource {

    private final Logger log = LoggerFactory.getLogger(GameUserRatingResource.class);
        
    @Inject
    private GameUserRatingService gameUserRatingService;

    /**
     * POST  /game-user-ratings : Create a new gameUserRating.
     *
     * @param gameUserRating the gameUserRating to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameUserRating, or with status 400 (Bad Request) if the gameUserRating has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/game-user-ratings")
    @Timed
    public ResponseEntity<GameUserRating> createGameUserRating(@Valid @RequestBody GameUserRating gameUserRating) throws URISyntaxException {
        log.debug("REST request to save GameUserRating : {}", gameUserRating);
        if (gameUserRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gameUserRating", "idexists", "A new gameUserRating cannot already have an ID")).body(null);
        }
        GameUserRating result = gameUserRatingService.save(gameUserRating);
        return ResponseEntity.created(new URI("/api/game-user-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gameUserRating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /game-user-ratings : Updates an existing gameUserRating.
     *
     * @param gameUserRating the gameUserRating to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameUserRating,
     * or with status 400 (Bad Request) if the gameUserRating is not valid,
     * or with status 500 (Internal Server Error) if the gameUserRating couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/game-user-ratings")
    @Timed
    public ResponseEntity<GameUserRating> updateGameUserRating(@Valid @RequestBody GameUserRating gameUserRating) throws URISyntaxException {
        log.debug("REST request to update GameUserRating : {}", gameUserRating);
        if (gameUserRating.getId() == null) {
            return createGameUserRating(gameUserRating);
        }
        GameUserRating result = gameUserRatingService.save(gameUserRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gameUserRating", gameUserRating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /game-user-ratings : get all the gameUserRatings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gameUserRatings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/game-user-ratings")
    @Timed
    public ResponseEntity<List<GameUserRating>> getAllGameUserRatings(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GameUserRatings");
        Page<GameUserRating> page = gameUserRatingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/game-user-ratings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /game-user-ratings/:id : get the "id" gameUserRating.
     *
     * @param id the id of the gameUserRating to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameUserRating, or with status 404 (Not Found)
     */
    @GetMapping("/game-user-ratings/{id}")
    @Timed
    public ResponseEntity<GameUserRating> getGameUserRating(@PathVariable Long id) {
        log.debug("REST request to get GameUserRating : {}", id);
        GameUserRating gameUserRating = gameUserRatingService.findOne(id);
        return Optional.ofNullable(gameUserRating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /game-user-ratings/:id : delete the "id" gameUserRating.
     *
     * @param id the id of the gameUserRating to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/game-user-ratings/{id}")
    @Timed
    public ResponseEntity<Void> deleteGameUserRating(@PathVariable Long id) {
        log.debug("REST request to delete GameUserRating : {}", id);
        gameUserRatingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gameUserRating", id.toString())).build();
    }

}
