package com.ferran.app.web.rest;

import com.ferran.app.BaloncestoseedApp;

import com.ferran.app.domain.UserPlayerFavorite;
import com.ferran.app.repository.UserPlayerFavoriteRepository;
import com.ferran.app.service.UserPlayerFavoriteService;

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
 * Test class for the UserPlayerFavoriteResource REST controller.
 *
 * @see UserPlayerFavoriteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaloncestoseedApp.class)
public class UserPlayerFavoriteResourceIntTest {

    private static final Boolean DEFAULT_LIKED = false;
    private static final Boolean UPDATED_LIKED = true;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private UserPlayerFavoriteRepository userPlayerFavoriteRepository;

    @Inject
    private UserPlayerFavoriteService userPlayerFavoriteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserPlayerFavoriteMockMvc;

    private UserPlayerFavorite userPlayerFavorite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserPlayerFavoriteResource userPlayerFavoriteResource = new UserPlayerFavoriteResource();
        ReflectionTestUtils.setField(userPlayerFavoriteResource, "userPlayerFavoriteService", userPlayerFavoriteService);
        this.restUserPlayerFavoriteMockMvc = MockMvcBuilders.standaloneSetup(userPlayerFavoriteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPlayerFavorite createEntity(EntityManager em) {
        UserPlayerFavorite userPlayerFavorite = new UserPlayerFavorite()
                .liked(DEFAULT_LIKED)
                .timestamp(DEFAULT_TIMESTAMP);
        return userPlayerFavorite;
    }

    @Before
    public void initTest() {
        userPlayerFavorite = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPlayerFavorite() throws Exception {
        int databaseSizeBeforeCreate = userPlayerFavoriteRepository.findAll().size();

        // Create the UserPlayerFavorite

        restUserPlayerFavoriteMockMvc.perform(post("/api/user-player-favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPlayerFavorite)))
            .andExpect(status().isCreated());

        // Validate the UserPlayerFavorite in the database
        List<UserPlayerFavorite> userPlayerFavoriteList = userPlayerFavoriteRepository.findAll();
        assertThat(userPlayerFavoriteList).hasSize(databaseSizeBeforeCreate + 1);
        UserPlayerFavorite testUserPlayerFavorite = userPlayerFavoriteList.get(userPlayerFavoriteList.size() - 1);
        assertThat(testUserPlayerFavorite.isLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testUserPlayerFavorite.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createUserPlayerFavoriteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPlayerFavoriteRepository.findAll().size();

        // Create the UserPlayerFavorite with an existing ID
        UserPlayerFavorite existingUserPlayerFavorite = new UserPlayerFavorite();
        existingUserPlayerFavorite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPlayerFavoriteMockMvc.perform(post("/api/user-player-favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserPlayerFavorite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserPlayerFavorite> userPlayerFavoriteList = userPlayerFavoriteRepository.findAll();
        assertThat(userPlayerFavoriteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserPlayerFavorites() throws Exception {
        // Initialize the database
        userPlayerFavoriteRepository.saveAndFlush(userPlayerFavorite);

        // Get all the userPlayerFavoriteList
        restUserPlayerFavoriteMockMvc.perform(get("/api/user-player-favorites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPlayerFavorite.getId().intValue())))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED.booleanValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void getUserPlayerFavorite() throws Exception {
        // Initialize the database
        userPlayerFavoriteRepository.saveAndFlush(userPlayerFavorite);

        // Get the userPlayerFavorite
        restUserPlayerFavoriteMockMvc.perform(get("/api/user-player-favorites/{id}", userPlayerFavorite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPlayerFavorite.getId().intValue()))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED.booleanValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingUserPlayerFavorite() throws Exception {
        // Get the userPlayerFavorite
        restUserPlayerFavoriteMockMvc.perform(get("/api/user-player-favorites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPlayerFavorite() throws Exception {
        // Initialize the database
        userPlayerFavoriteService.save(userPlayerFavorite);

        int databaseSizeBeforeUpdate = userPlayerFavoriteRepository.findAll().size();

        // Update the userPlayerFavorite
        UserPlayerFavorite updatedUserPlayerFavorite = userPlayerFavoriteRepository.findOne(userPlayerFavorite.getId());
        updatedUserPlayerFavorite
                .liked(UPDATED_LIKED)
                .timestamp(UPDATED_TIMESTAMP);

        restUserPlayerFavoriteMockMvc.perform(put("/api/user-player-favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPlayerFavorite)))
            .andExpect(status().isOk());

        // Validate the UserPlayerFavorite in the database
        List<UserPlayerFavorite> userPlayerFavoriteList = userPlayerFavoriteRepository.findAll();
        assertThat(userPlayerFavoriteList).hasSize(databaseSizeBeforeUpdate);
        UserPlayerFavorite testUserPlayerFavorite = userPlayerFavoriteList.get(userPlayerFavoriteList.size() - 1);
        assertThat(testUserPlayerFavorite.isLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testUserPlayerFavorite.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPlayerFavorite() throws Exception {
        int databaseSizeBeforeUpdate = userPlayerFavoriteRepository.findAll().size();

        // Create the UserPlayerFavorite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPlayerFavoriteMockMvc.perform(put("/api/user-player-favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPlayerFavorite)))
            .andExpect(status().isCreated());

        // Validate the UserPlayerFavorite in the database
        List<UserPlayerFavorite> userPlayerFavoriteList = userPlayerFavoriteRepository.findAll();
        assertThat(userPlayerFavoriteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPlayerFavorite() throws Exception {
        // Initialize the database
        userPlayerFavoriteService.save(userPlayerFavorite);

        int databaseSizeBeforeDelete = userPlayerFavoriteRepository.findAll().size();

        // Get the userPlayerFavorite
        restUserPlayerFavoriteMockMvc.perform(delete("/api/user-player-favorites/{id}", userPlayerFavorite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPlayerFavorite> userPlayerFavoriteList = userPlayerFavoriteRepository.findAll();
        assertThat(userPlayerFavoriteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
