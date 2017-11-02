package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Jaguar;

import io.github.jhipster.application.repository.JaguarRepository;
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
 * REST controller for managing Jaguar.
 */
@RestController
@RequestMapping("/api")
public class JaguarResource {

    private final Logger log = LoggerFactory.getLogger(JaguarResource.class);

    private static final String ENTITY_NAME = "jaguar";

    private final JaguarRepository jaguarRepository;

    public JaguarResource(JaguarRepository jaguarRepository) {
        this.jaguarRepository = jaguarRepository;
    }

    /**
     * POST  /jaguars : Create a new jaguar.
     *
     * @param jaguar the jaguar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jaguar, or with status 400 (Bad Request) if the jaguar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jaguars")
    @Timed
    public ResponseEntity<Jaguar> createJaguar(@RequestBody Jaguar jaguar) throws URISyntaxException {
        log.debug("REST request to save Jaguar : {}", jaguar);
        if (jaguar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jaguar cannot already have an ID")).body(null);
        }
        Jaguar result = jaguarRepository.save(jaguar);
        return ResponseEntity.created(new URI("/api/jaguars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jaguars : Updates an existing jaguar.
     *
     * @param jaguar the jaguar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jaguar,
     * or with status 400 (Bad Request) if the jaguar is not valid,
     * or with status 500 (Internal Server Error) if the jaguar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jaguars")
    @Timed
    public ResponseEntity<Jaguar> updateJaguar(@RequestBody Jaguar jaguar) throws URISyntaxException {
        log.debug("REST request to update Jaguar : {}", jaguar);
        if (jaguar.getId() == null) {
            return createJaguar(jaguar);
        }
        Jaguar result = jaguarRepository.save(jaguar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jaguar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jaguars : get all the jaguars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jaguars in body
     */
    @GetMapping("/jaguars")
    @Timed
    public List<Jaguar> getAllJaguars() {
        log.debug("REST request to get all Jaguars");
        return jaguarRepository.findAll();
        }

    /**
     * GET  /jaguars/:id : get the "id" jaguar.
     *
     * @param id the id of the jaguar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jaguar, or with status 404 (Not Found)
     */
    @GetMapping("/jaguars/{id}")
    @Timed
    public ResponseEntity<Jaguar> getJaguar(@PathVariable Long id) {
        log.debug("REST request to get Jaguar : {}", id);
        Jaguar jaguar = jaguarRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jaguar));
    }

    /**
     * DELETE  /jaguars/:id : delete the "id" jaguar.
     *
     * @param id the id of the jaguar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jaguars/{id}")
    @Timed
    public ResponseEntity<Void> deleteJaguar(@PathVariable Long id) {
        log.debug("REST request to delete Jaguar : {}", id);
        jaguarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
