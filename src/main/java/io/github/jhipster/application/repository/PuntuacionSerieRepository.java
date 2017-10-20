package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PuntuacionSerie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PuntuacionSerie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntuacionSerieRepository extends JpaRepository<PuntuacionSerie, Long> {

    @Query("select puntuacion_serie from PuntuacionSerie puntuacion_serie where puntuacion_serie.user.login = ?#{principal.username}")
    List<PuntuacionSerie> findByUserIsCurrentUser();

}
