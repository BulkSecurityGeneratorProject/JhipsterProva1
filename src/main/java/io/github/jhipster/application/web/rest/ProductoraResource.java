package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Productora;

import io.github.jhipster.application.repository.ProductoraRepository;
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
 * REST controller for managing Productora.
 */
@RestController
@RequestMapping("/api")
public class ProductoraResource {

    private final Logger log = LoggerFactory.getLogger(ProductoraResource.class);

    private static final String ENTITY_NAME = "productora";

    private final ProductoraRepository productoraRepository;

    public ProductoraResource(ProductoraRepository productoraRepository) {
        this.productoraRepository = productoraRepository;
    }

    /**
     * POST  /productoras : Create a new productora.
     *
     * @param productora the productora to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productora, or with status 400 (Bad Request) if the productora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productoras")
    @Timed
    public ResponseEntity<Productora> createProductora(@RequestBody Productora productora) throws URISyntaxException {
        log.debug("REST request to save Productora : {}", productora);
        if (productora.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productora cannot already have an ID")).body(null);
        }
        Productora result = productoraRepository.save(productora);
        return ResponseEntity.created(new URI("/api/productoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productoras : Updates an existing productora.
     *
     * @param productora the productora to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productora,
     * or with status 400 (Bad Request) if the productora is not valid,
     * or with status 500 (Internal Server Error) if the productora couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productoras")
    @Timed
    public ResponseEntity<Productora> updateProductora(@RequestBody Productora productora) throws URISyntaxException {
        log.debug("REST request to update Productora : {}", productora);
        if (productora.getId() == null) {
            return createProductora(productora);
        }
        Productora result = productoraRepository.save(productora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productora.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productoras : get all the productoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productoras in body
     */
    @GetMapping("/productoras")
    @Timed
    public List<Productora> getAllProductoras() {
        log.debug("REST request to get all Productoras");
        return productoraRepository.findAll();
        }

    /**
     * GET  /productoras/:id : get the "id" productora.
     *
     * @param id the id of the productora to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productora, or with status 404 (Not Found)
     */
    @GetMapping("/productoras/{id}")
    @Timed
    public ResponseEntity<Productora> getProductora(@PathVariable Long id) {
        log.debug("REST request to get Productora : {}", id);
        Productora productora = productoraRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productora));
    }

    /**
     * DELETE  /productoras/:id : delete the "id" productora.
     *
     * @param id the id of the productora to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productoras/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductora(@PathVariable Long id) {
        log.debug("REST request to delete Productora : {}", id);
        productoraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
