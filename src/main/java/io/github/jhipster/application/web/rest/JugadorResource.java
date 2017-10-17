package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Jugador;

import io.github.jhipster.application.repository.JugadorRepository;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Jugador.
 */
@RestController
@RequestMapping("/api")
public class JugadorResource {

    private final Logger log = LoggerFactory.getLogger(JugadorResource.class);

    private static final String ENTITY_NAME = "jugador";

    private final JugadorRepository jugadorRepository;

    public JugadorResource(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    /**
     * POST  /jugadors : Create a new jugador.
     *
     * @param jugador the jugador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jugador, or with status 400 (Bad Request) if the jugador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jugadors")
    @Timed
    public ResponseEntity<Jugador> createJugador(@Valid @RequestBody Jugador jugador) throws URISyntaxException {
        log.debug("REST request to save Jugador : {}", jugador);
        if (jugador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jugador cannot already have an ID")).body(null);
        }
        Jugador result = jugadorRepository.save(jugador);
        return ResponseEntity.created(new URI("/api/jugadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jugadors : Updates an existing jugador.
     *
     * @param jugador the jugador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jugador,
     * or with status 400 (Bad Request) if the jugador is not valid,
     * or with status 500 (Internal Server Error) if the jugador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jugadors")
    @Timed
    public ResponseEntity<Jugador> updateJugador(@Valid @RequestBody Jugador jugador) throws URISyntaxException {
        log.debug("REST request to update Jugador : {}", jugador);
        if (jugador.getId() == null) {
            return createJugador(jugador);
        }
        Jugador result = jugadorRepository.save(jugador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jugador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jugadors : get all the jugadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jugadors in body
     */
    @GetMapping("/jugadors")
    @Timed
    public List<Jugador> getAllJugadors() {
        log.debug("REST request to get all Jugadors");
        return jugadorRepository.findAll();
        }

    /**
     * GET  /jugadors/:id : get the "id" jugador.
     *
     * @param id the id of the jugador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jugador, or with status 404 (Not Found)
     */
    @GetMapping("/jugadors/{id}")
    @Timed
    public ResponseEntity<Jugador> getJugador(@PathVariable Long id) {
        log.debug("REST request to get Jugador : {}", id);
        Jugador jugador = jugadorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jugador));
    }

    /**
     * DELETE  /jugadors/:id : delete the "id" jugador.
     *
     * @param id the id of the jugador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jugadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteJugador(@PathVariable Long id) {
        log.debug("REST request to delete Jugador : {}", id);
        jugadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
