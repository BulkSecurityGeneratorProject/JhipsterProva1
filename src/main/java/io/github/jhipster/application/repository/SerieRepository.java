package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Serie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Serie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    @Query("select distinct serie from Serie serie left join fetch serie.actors left join fetch serie.generos")
    List<Serie> findAllWithEagerRelationships();

    @Query("select serie from Serie serie left join fetch serie.actors left join fetch serie.generos where serie.id =:id")
    Serie findOneWithEagerRelationships(@Param("id") Long id);

}
