package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Prueba;

import io.github.jhipster.application.repository.PruebaRepository;
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
 * REST controller for managing Prueba.
 */
@RestController
@RequestMapping("/api")
public class PruebaResource {

    private final Logger log = LoggerFactory.getLogger(PruebaResource.class);

    private static final String ENTITY_NAME = "prueba";

    private final PruebaRepository pruebaRepository;

    public PruebaResource(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
    }

    /**
     * POST  /pruebas : Create a new prueba.
     *
     * @param prueba the prueba to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prueba, or with status 400 (Bad Request) if the prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pruebas")
    @Timed
    public ResponseEntity<Prueba> createPrueba(@RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to save Prueba : {}", prueba);
        if (prueba.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prueba cannot already have an ID")).body(null);
        }
        Prueba result = pruebaRepository.save(prueba);
        return ResponseEntity.created(new URI("/api/pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pruebas : Updates an existing prueba.
     *
     * @param prueba the prueba to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prueba,
     * or with status 400 (Bad Request) if the prueba is not valid,
     * or with status 500 (Internal Server Error) if the prueba couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pruebas")
    @Timed
    public ResponseEntity<Prueba> updatePrueba(@RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to update Prueba : {}", prueba);
        if (prueba.getId() == null) {
            return createPrueba(prueba);
        }
        Prueba result = pruebaRepository.save(prueba);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prueba.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pruebas : get all the pruebas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pruebas in body
     */
    @GetMapping("/pruebas")
    @Timed
    public List<Prueba> getAllPruebas() {
        log.debug("REST request to get all Pruebas");
        return pruebaRepository.findAll();
        }

    /**
     * GET  /pruebas/:id : get the "id" prueba.
     *
     * @param id the id of the prueba to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prueba, or with status 404 (Not Found)
     */
    @GetMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Prueba> getPrueba(@PathVariable Long id) {
        log.debug("REST request to get Prueba : {}", id);
        Prueba prueba = pruebaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prueba));
    }

    /**
     * DELETE  /pruebas/:id : delete the "id" prueba.
     *
     * @param id the id of the prueba to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deletePrueba(@PathVariable Long id) {
        log.debug("REST request to delete Prueba : {}", id);
        pruebaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
