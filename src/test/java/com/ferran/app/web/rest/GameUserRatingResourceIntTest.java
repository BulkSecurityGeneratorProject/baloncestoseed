package com.ferran.app.web.rest;

import com.ferran.app.BaloncestoseedApp;

import com.ferran.app.domain.GameUserRating;
import com.ferran.app.repository.GameUserRatingRepository;
import com.ferran.app.service.GameUserRatingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ferran.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GameUserRatingResource REST controller.
 *
 * @see GameUserRatingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaloncestoseedApp.class)
public class GameUserRatingResourceIntTest {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private GameUserRatingRepository gameUserRatingRepository;

    @Inject
    private GameUserRatingService gameUserRatingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGameUserRatingMockMvc;

    private GameUserRating gameUserRating;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GameUserRatingResource gameUserRatingResource = new GameUserRatingResource();
        ReflectionTestUtils.setField(gameUserRatingResource, "gameUserRatingService", gameUserRatingService);
        this.restGameUserRatingMockMvc = MockMvcBuilders.standaloneSetup(gameUserRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameUserRating createEntity(EntityManager em) {
        GameUserRating gameUserRating = new GameUserRating()
                .rating(DEFAULT_RATING)
                .timestamp(DEFAULT_TIMESTAMP);
        return gameUserRating;
    }

    @Before
    public void initTest() {
        gameUserRating = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameUserRating() throws Exception {
        int databaseSizeBeforeCreate = gameUserRatingRepository.findAll().size();

        // Create the GameUserRating

        restGameUserRatingMockMvc.perform(post("/api/game-user-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUserRating)))
            .andExpect(status().isCreated());

        // Validate the GameUserRating in the database
        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeCreate + 1);
        GameUserRating testGameUserRating = gameUserRatingList.get(gameUserRatingList.size() - 1);
        assertThat(testGameUserRating.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testGameUserRating.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createGameUserRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameUserRatingRepository.findAll().size();

        // Create the GameUserRating with an existing ID
        GameUserRating existingGameUserRating = new GameUserRating();
        existingGameUserRating.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameUserRatingMockMvc.perform(post("/api/game-user-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGameUserRating)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameUserRatingRepository.findAll().size();
        // set the field null
        gameUserRating.setRating(null);

        // Create the GameUserRating, which fails.

        restGameUserRatingMockMvc.perform(post("/api/game-user-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUserRating)))
            .andExpect(status().isBadRequest());

        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGameUserRatings() throws Exception {
        // Initialize the database
        gameUserRatingRepository.saveAndFlush(gameUserRating);

        // Get all the gameUserRatingList
        restGameUserRatingMockMvc.perform(get("/api/game-user-ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameUserRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void getGameUserRating() throws Exception {
        // Initialize the database
        gameUserRatingRepository.saveAndFlush(gameUserRating);

        // Get the gameUserRating
        restGameUserRatingMockMvc.perform(get("/api/game-user-ratings/{id}", gameUserRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gameUserRating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingGameUserRating() throws Exception {
        // Get the gameUserRating
        restGameUserRatingMockMvc.perform(get("/api/game-user-ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameUserRating() throws Exception {
        // Initialize the database
        gameUserRatingService.save(gameUserRating);

        int databaseSizeBeforeUpdate = gameUserRatingRepository.findAll().size();

        // Update the gameUserRating
        GameUserRating updatedGameUserRating = gameUserRatingRepository.findOne(gameUserRating.getId());
        updatedGameUserRating
                .rating(UPDATED_RATING)
                .timestamp(UPDATED_TIMESTAMP);

        restGameUserRatingMockMvc.perform(put("/api/game-user-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameUserRating)))
            .andExpect(status().isOk());

        // Validate the GameUserRating in the database
        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeUpdate);
        GameUserRating testGameUserRating = gameUserRatingList.get(gameUserRatingList.size() - 1);
        assertThat(testGameUserRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testGameUserRating.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingGameUserRating() throws Exception {
        int databaseSizeBeforeUpdate = gameUserRatingRepository.findAll().size();

        // Create the GameUserRating

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGameUserRatingMockMvc.perform(put("/api/game-user-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUserRating)))
            .andExpect(status().isCreated());

        // Validate the GameUserRating in the database
        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGameUserRating() throws Exception {
        // Initialize the database
        gameUserRatingService.save(gameUserRating);

        int databaseSizeBeforeDelete = gameUserRatingRepository.findAll().size();

        // Get the gameUserRating
        restGameUserRatingMockMvc.perform(delete("/api/game-user-ratings/{id}", gameUserRating.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GameUserRating> gameUserRatingList = gameUserRatingRepository.findAll();
        assertThat(gameUserRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
