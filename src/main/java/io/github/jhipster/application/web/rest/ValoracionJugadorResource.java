package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ValoracionJugador;

import io.github.jhipster.application.repository.UserRepository;
import io.github.jhipster.application.repository.ValoracionJugadorRepository;
import io.github.jhipster.application.security.SecurityUtils;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValoracionJugador.
 */
@RestController
@RequestMapping("/api")
public class ValoracionJugadorResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionJugadorResource.class);

    private static final String ENTITY_NAME = "valoracionJugador";

    private final ValoracionJugadorRepository valoracionJugadorRepository;

    private final UserRepository userRepository;


    public ValoracionJugadorResource(ValoracionJugadorRepository valoracionJugadorRepository, UserRepository userRepository) {
        this.valoracionJugadorRepository = valoracionJugadorRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /valoracion-jugadors : Create a new valoracionJugador.
     *
     * @param valoracionJugador the valoracionJugador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionJugador, or with status 400 (Bad Request) if the valoracionJugador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoracion-jugadors")
    @Timed
    public ResponseEntity<ValoracionJugador> createValoracionJugador(@Valid @RequestBody ValoracionJugador valoracionJugador) throws URISyntaxException {
        log.debug("REST request to save ValoracionJugador : {}", valoracionJugador);
        if (valoracionJugador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new valoracionJugador cannot already have an ID")).body(null);
        }

        valoracionJugador.setMomento(ZonedDateTime.now());

        valoracionJugador.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        ValoracionJugador result = valoracionJugadorRepository.save(valoracionJugador);
        return ResponseEntity.created(new URI("/api/valoracion-jugadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valoracion-jugadors : Updates an existing valoracionJugador.
     *
     * @param valoracionJugador the valoracionJugador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionJugador,
     * or with status 400 (Bad Request) if the valoracionJugador is not valid,
     * or with status 500 (Internal Server Error) if the valoracionJugador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoracion-jugadors")
    @Timed
    public ResponseEntity<ValoracionJugador> updateValoracionJugador(@Valid @RequestBody ValoracionJugador valoracionJugador) throws URISyntaxException {
        log.debug("REST request to update ValoracionJugador : {}", valoracionJugador);
        if (valoracionJugador.getId() == null) {
            return createValoracionJugador(valoracionJugador);
        }
        ValoracionJugador result = valoracionJugadorRepository.save(valoracionJugador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valoracionJugador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoracion-jugadors : get all the valoracionJugadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valoracionJugadors in body
     */
    @GetMapping("/valoracion-jugadors")
    @Timed
    public List<ValoracionJugador> getAllValoracionJugadors() {
        log.debug("REST request to get all ValoracionJugadors");
        return valoracionJugadorRepository.findAll();
        }

    /**
     * GET  /valoracion-jugadors/:id : get the "id" valoracionJugador.
     *
     * @param id the id of the valoracionJugador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionJugador, or with status 404 (Not Found)
     */
    @GetMapping("/valoracion-jugadors/{id}")
    @Timed
    public ResponseEntity<ValoracionJugador> getValoracionJugador(@PathVariable Long id) {
        log.debug("REST request to get ValoracionJugador : {}", id);
        ValoracionJugador valoracionJugador = valoracionJugadorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valoracionJugador));
    }

    /**
     * DELETE  /valoracion-jugadors/:id : delete the "id" valoracionJugador.
     *
     * @param id the id of the valoracionJugador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoracion-jugadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoracionJugador(@PathVariable Long id) {
        log.debug("REST request to delete ValoracionJugador : {}", id);
        valoracionJugadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
