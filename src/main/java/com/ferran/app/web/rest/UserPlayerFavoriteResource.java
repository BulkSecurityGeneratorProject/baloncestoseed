package com.ferran.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ferran.app.domain.UserPlayerFavorite;
import com.ferran.app.service.UserPlayerFavoriteService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserPlayerFavorite.
 */
@RestController
@RequestMapping("/api")
public class UserPlayerFavoriteResource {

    private final Logger log = LoggerFactory.getLogger(UserPlayerFavoriteResource.class);
        
    @Inject
    private UserPlayerFavoriteService userPlayerFavoriteService;

    /**
     * POST  /user-player-favorites : Create a new userPlayerFavorite.
     *
     * @param userPlayerFavorite the userPlayerFavorite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPlayerFavorite, or with status 400 (Bad Request) if the userPlayerFavorite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-player-favorites")
    @Timed
    public ResponseEntity<UserPlayerFavorite> createUserPlayerFavorite(@RequestBody UserPlayerFavorite userPlayerFavorite) throws URISyntaxException {
        log.debug("REST request to save UserPlayerFavorite : {}", userPlayerFavorite);
        if (userPlayerFavorite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userPlayerFavorite", "idexists", "A new userPlayerFavorite cannot already have an ID")).body(null);
        }
        UserPlayerFavorite result = userPlayerFavoriteService.save(userPlayerFavorite);
        return ResponseEntity.created(new URI("/api/user-player-favorites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userPlayerFavorite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-player-favorites : Updates an existing userPlayerFavorite.
     *
     * @param userPlayerFavorite the userPlayerFavorite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPlayerFavorite,
     * or with status 400 (Bad Request) if the userPlayerFavorite is not valid,
     * or with status 500 (Internal Server Error) if the userPlayerFavorite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-player-favorites")
    @Timed
    public ResponseEntity<UserPlayerFavorite> updateUserPlayerFavorite(@RequestBody UserPlayerFavorite userPlayerFavorite) throws URISyntaxException {
        log.debug("REST request to update UserPlayerFavorite : {}", userPlayerFavorite);
        if (userPlayerFavorite.getId() == null) {
            return createUserPlayerFavorite(userPlayerFavorite);
        }
        UserPlayerFavorite result = userPlayerFavoriteService.save(userPlayerFavorite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userPlayerFavorite", userPlayerFavorite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-player-favorites : get all the userPlayerFavorites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userPlayerFavorites in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-player-favorites")
    @Timed
    public ResponseEntity<List<UserPlayerFavorite>> getAllUserPlayerFavorites(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserPlayerFavorites");
        Page<UserPlayerFavorite> page = userPlayerFavoriteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-player-favorites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-player-favorites/:id : get the "id" userPlayerFavorite.
     *
     * @param id the id of the userPlayerFavorite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPlayerFavorite, or with status 404 (Not Found)
     */
    @GetMapping("/user-player-favorites/{id}")
    @Timed
    public ResponseEntity<UserPlayerFavorite> getUserPlayerFavorite(@PathVariable Long id) {
        log.debug("REST request to get UserPlayerFavorite : {}", id);
        UserPlayerFavorite userPlayerFavorite = userPlayerFavoriteService.findOne(id);
        return Optional.ofNullable(userPlayerFavorite)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-player-favorites/:id : delete the "id" userPlayerFavorite.
     *
     * @param id the id of the userPlayerFavorite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-player-favorites/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPlayerFavorite(@PathVariable Long id) {
        log.debug("REST request to delete UserPlayerFavorite : {}", id);
        userPlayerFavoriteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userPlayerFavorite", id.toString())).build();
    }

}
