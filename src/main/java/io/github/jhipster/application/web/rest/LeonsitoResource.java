package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Leonsito;

import io.github.jhipster.application.repository.LeonsitoRepository;
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
 * REST controller for managing Leonsito.
 */
@RestController
@RequestMapping("/api")
public class LeonsitoResource {

    private final Logger log = LoggerFactory.getLogger(LeonsitoResource.class);

    private static final String ENTITY_NAME = "leonsito";

    private final LeonsitoRepository leonsitoRepository;

    public LeonsitoResource(LeonsitoRepository leonsitoRepository) {
        this.leonsitoRepository = leonsitoRepository;
    }

    /**
     * POST  /leonsitos : Create a new leonsito.
     *
     * @param leonsito the leonsito to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leonsito, or with status 400 (Bad Request) if the leonsito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leonsitos")
    @Timed
    public ResponseEntity<Leonsito> createLeonsito(@RequestBody Leonsito leonsito) throws URISyntaxException {
        log.debug("REST request to save Leonsito : {}", leonsito);
        if (leonsito.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new leonsito cannot already have an ID")).body(null);
        }
        Leonsito result = leonsitoRepository.save(leonsito);
        return ResponseEntity.created(new URI("/api/leonsitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leonsitos : Updates an existing leonsito.
     *
     * @param leonsito the leonsito to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leonsito,
     * or with status 400 (Bad Request) if the leonsito is not valid,
     * or with status 500 (Internal Server Error) if the leonsito couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leonsitos")
    @Timed
    public ResponseEntity<Leonsito> updateLeonsito(@RequestBody Leonsito leonsito) throws URISyntaxException {
        log.debug("REST request to update Leonsito : {}", leonsito);
        if (leonsito.getId() == null) {
            return createLeonsito(leonsito);
        }
        Leonsito result = leonsitoRepository.save(leonsito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leonsito.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leonsitos : get all the leonsitos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of leonsitos in body
     */
    @GetMapping("/leonsitos")
    @Timed
    public List<Leonsito> getAllLeonsitos() {
        log.debug("REST request to get all Leonsitos");
        return leonsitoRepository.findAll();
        }

    /**
     * GET  /leonsitos/:id : get the "id" leonsito.
     *
     * @param id the id of the leonsito to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leonsito, or with status 404 (Not Found)
     */
    @GetMapping("/leonsitos/{id}")
    @Timed
    public ResponseEntity<Leonsito> getLeonsito(@PathVariable Long id) {
        log.debug("REST request to get Leonsito : {}", id);
        Leonsito leonsito = leonsitoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leonsito));
    }

    /**
     * DELETE  /leonsitos/:id : delete the "id" leonsito.
     *
     * @param id the id of the leonsito to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leonsitos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeonsito(@PathVariable Long id) {
        log.debug("REST request to delete Leonsito : {}", id);
        leonsitoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
