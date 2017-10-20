package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.PuntuacionSerie;

import io.github.jhipster.application.repository.PuntuacionSerieRepository;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PuntuacionSerie.
 */
@RestController
@RequestMapping("/api")
public class PuntuacionSerieResource {

    private final Logger log = LoggerFactory.getLogger(PuntuacionSerieResource.class);

    private static final String ENTITY_NAME = "puntuacionSerie";

    private final PuntuacionSerieRepository puntuacionSerieRepository;

    public PuntuacionSerieResource(PuntuacionSerieRepository puntuacionSerieRepository) {
        this.puntuacionSerieRepository = puntuacionSerieRepository;
    }

    /**
     * POST  /puntuacion-series : Create a new puntuacionSerie.
     *
     * @param puntuacionSerie the puntuacionSerie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puntuacionSerie, or with status 400 (Bad Request) if the puntuacionSerie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/puntuacion-series")
    @Timed
    public ResponseEntity<PuntuacionSerie> createPuntuacionSerie(@RequestBody PuntuacionSerie puntuacionSerie) throws URISyntaxException {
        log.debug("REST request to save PuntuacionSerie : {}", puntuacionSerie);
        if (puntuacionSerie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new puntuacionSerie cannot already have an ID")).body(null);
        }
        PuntuacionSerie result = puntuacionSerieRepository.save(puntuacionSerie);
        return ResponseEntity.created(new URI("/api/puntuacion-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /puntuacion-series : Updates an existing puntuacionSerie.
     *
     * @param puntuacionSerie the puntuacionSerie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puntuacionSerie,
     * or with status 400 (Bad Request) if the puntuacionSerie is not valid,
     * or with status 500 (Internal Server Error) if the puntuacionSerie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/puntuacion-series")
    @Timed
    public ResponseEntity<PuntuacionSerie> updatePuntuacionSerie(@RequestBody PuntuacionSerie puntuacionSerie) throws URISyntaxException {
        log.debug("REST request to update PuntuacionSerie : {}", puntuacionSerie);
        if (puntuacionSerie.getId() == null) {
            return createPuntuacionSerie(puntuacionSerie);
        }
        PuntuacionSerie result = puntuacionSerieRepository.save(puntuacionSerie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puntuacionSerie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /puntuacion-series : get all the puntuacionSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of puntuacionSeries in body
     */
    @GetMapping("/puntuacion-series")
    @Timed
    public List<PuntuacionSerie> getAllPuntuacionSeries() {
        log.debug("REST request to get all PuntuacionSeries");
        return puntuacionSerieRepository.findAll();
        }

    /**
     * GET  /puntuacion-series/:id : get the "id" puntuacionSerie.
     *
     * @param id the id of the puntuacionSerie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puntuacionSerie, or with status 404 (Not Found)
     */
    @GetMapping("/puntuacion-series/{id}")
    @Timed
    public ResponseEntity<PuntuacionSerie> getPuntuacionSerie(@PathVariable Long id) {
        log.debug("REST request to get PuntuacionSerie : {}", id);
        PuntuacionSerie puntuacionSerie = puntuacionSerieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(puntuacionSerie));
    }

    /**
     * DELETE  /puntuacion-series/:id : delete the "id" puntuacionSerie.
     *
     * @param id the id of the puntuacionSerie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/puntuacion-series/{id}")
    @Timed
    public ResponseEntity<Void> deletePuntuacionSerie(@PathVariable Long id) {
        log.debug("REST request to delete PuntuacionSerie : {}", id);
        puntuacionSerieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
