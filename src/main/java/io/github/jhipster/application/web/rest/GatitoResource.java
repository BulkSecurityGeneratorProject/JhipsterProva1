package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Gatito;

import io.github.jhipster.application.repository.GatitoRepository;
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
 * REST controller for managing Gatito.
 */
@RestController
@RequestMapping("/api")
public class GatitoResource {

    private final Logger log = LoggerFactory.getLogger(GatitoResource.class);

    private static final String ENTITY_NAME = "gatito";

    private final GatitoRepository gatitoRepository;

    public GatitoResource(GatitoRepository gatitoRepository) {
        this.gatitoRepository = gatitoRepository;
    }

    /**
     * POST  /gatitos : Create a new gatito.
     *
     * @param gatito the gatito to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gatito, or with status 400 (Bad Request) if the gatito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gatitos")
    @Timed
    public ResponseEntity<Gatito> createGatito(@RequestBody Gatito gatito) throws URISyntaxException {
        log.debug("REST request to save Gatito : {}", gatito);
        if (gatito.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gatito cannot already have an ID")).body(null);
        }
        Gatito result = gatitoRepository.save(gatito);
        return ResponseEntity.created(new URI("/api/gatitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gatitos : Updates an existing gatito.
     *
     * @param gatito the gatito to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gatito,
     * or with status 400 (Bad Request) if the gatito is not valid,
     * or with status 500 (Internal Server Error) if the gatito couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gatitos")
    @Timed
    public ResponseEntity<Gatito> updateGatito(@RequestBody Gatito gatito) throws URISyntaxException {
        log.debug("REST request to update Gatito : {}", gatito);
        if (gatito.getId() == null) {
            return createGatito(gatito);
        }
        Gatito result = gatitoRepository.save(gatito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gatito.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gatitos : get all the gatitos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gatitos in body
     */
    @GetMapping("/gatitos")
    @Timed
    public List<Gatito> getAllGatitos() {
        log.debug("REST request to get all Gatitos");
        return gatitoRepository.findAll();
        }

    /**
     * GET  /gatitos/:id : get the "id" gatito.
     *
     * @param id the id of the gatito to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gatito, or with status 404 (Not Found)
     */
    @GetMapping("/gatitos/{id}")
    @Timed
    public ResponseEntity<Gatito> getGatito(@PathVariable Long id) {
        log.debug("REST request to get Gatito : {}", id);
        Gatito gatito = gatitoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gatito));
    }

    /**
     * DELETE  /gatitos/:id : delete the "id" gatito.
     *
     * @param id the id of the gatito to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gatitos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGatito(@PathVariable Long id) {
        log.debug("REST request to delete Gatito : {}", id);
        gatitoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
