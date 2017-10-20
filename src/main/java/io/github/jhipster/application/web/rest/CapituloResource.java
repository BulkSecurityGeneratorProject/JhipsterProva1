package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Capitulo;

import io.github.jhipster.application.repository.CapituloRepository;
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
 * REST controller for managing Capitulo.
 */
@RestController
@RequestMapping("/api")
public class CapituloResource {

    private final Logger log = LoggerFactory.getLogger(CapituloResource.class);

    private static final String ENTITY_NAME = "capitulo";

    private final CapituloRepository capituloRepository;

    public CapituloResource(CapituloRepository capituloRepository) {
        this.capituloRepository = capituloRepository;
    }

    /**
     * POST  /capitulos : Create a new capitulo.
     *
     * @param capitulo the capitulo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new capitulo, or with status 400 (Bad Request) if the capitulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/capitulos")
    @Timed
    public ResponseEntity<Capitulo> createCapitulo(@RequestBody Capitulo capitulo) throws URISyntaxException {
        log.debug("REST request to save Capitulo : {}", capitulo);
        if (capitulo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new capitulo cannot already have an ID")).body(null);
        }
        Capitulo result = capituloRepository.save(capitulo);
        return ResponseEntity.created(new URI("/api/capitulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /capitulos : Updates an existing capitulo.
     *
     * @param capitulo the capitulo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated capitulo,
     * or with status 400 (Bad Request) if the capitulo is not valid,
     * or with status 500 (Internal Server Error) if the capitulo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/capitulos")
    @Timed
    public ResponseEntity<Capitulo> updateCapitulo(@RequestBody Capitulo capitulo) throws URISyntaxException {
        log.debug("REST request to update Capitulo : {}", capitulo);
        if (capitulo.getId() == null) {
            return createCapitulo(capitulo);
        }
        Capitulo result = capituloRepository.save(capitulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, capitulo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /capitulos : get all the capitulos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of capitulos in body
     */
    @GetMapping("/capitulos")
    @Timed
    public List<Capitulo> getAllCapitulos() {
        log.debug("REST request to get all Capitulos");
        return capituloRepository.findAll();
        }

    /**
     * GET  /capitulos/:id : get the "id" capitulo.
     *
     * @param id the id of the capitulo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the capitulo, or with status 404 (Not Found)
     */
    @GetMapping("/capitulos/{id}")
    @Timed
    public ResponseEntity<Capitulo> getCapitulo(@PathVariable Long id) {
        log.debug("REST request to get Capitulo : {}", id);
        Capitulo capitulo = capituloRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(capitulo));
    }

    /**
     * DELETE  /capitulos/:id : delete the "id" capitulo.
     *
     * @param id the id of the capitulo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/capitulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCapitulo(@PathVariable Long id) {
        log.debug("REST request to delete Capitulo : {}", id);
        capituloRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
