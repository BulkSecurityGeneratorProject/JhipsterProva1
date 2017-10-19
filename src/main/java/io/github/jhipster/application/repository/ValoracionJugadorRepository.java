package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ValoracionJugador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ValoracionJugador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionJugadorRepository extends JpaRepository<ValoracionJugador, Long> {

    @Query("select valoracion_jugador from ValoracionJugador valoracion_jugador where valoracion_jugador.user.login = ?#{principal.username}")
    List<ValoracionJugador> findByUserIsCurrentUser();

}
