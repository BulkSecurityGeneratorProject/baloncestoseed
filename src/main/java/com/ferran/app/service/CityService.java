package com.ferran.app.service;

import com.ferran.app.domain.City;
import com.ferran.app.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing City.
 */
@Service
@Transactional
public class CityService {

    private final Logger log = LoggerFactory.getLogger(CityService.class);
    
    @Inject
    private CityRepository cityRepository;

    /**
     * Save a city.
     *
     * @param city the entity to save
     * @return the persisted entity
     */
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        City result = cityRepository.save(city);
        return result;
    }

    /**
     *  Get all the cities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<City> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        Page<City> result = cityRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the cities where Player is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<City> findAllWherePlayerIsNull() {
        log.debug("Request to get all cities where Player is null");
        return StreamSupport
            .stream(cityRepository.findAll().spliterator(), false)
            .filter(city -> city.getPlayer() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the cities where Team is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<City> findAllWhereTeamIsNull() {
        log.debug("Request to get all cities where Team is null");
        return StreamSupport
            .stream(cityRepository.findAll().spliterator(), false)
            .filter(city -> city.getTeam() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one city by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public City findOne(Long id) {
        log.debug("Request to get City : {}", id);
        City city = cityRepository.findOne(id);
        return city;
    }

    /**
     *  Delete the  city by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.delete(id);
    }
}
