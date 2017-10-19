package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Jugador;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    List<Jugador> findTop5ByOrderByNumCanastasDesc();
}
