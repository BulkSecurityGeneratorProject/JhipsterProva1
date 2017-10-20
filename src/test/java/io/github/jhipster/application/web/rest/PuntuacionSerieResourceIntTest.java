package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.PuntuacionSerie;
import io.github.jhipster.application.repository.PuntuacionSerieRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PuntuacionSerieResource REST controller.
 *
 * @see PuntuacionSerieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class PuntuacionSerieResourceIntTest {

    private static final Double DEFAULT_PUNTUACION = 1D;
    private static final Double UPDATED_PUNTUACION = 2D;

    private static final ZonedDateTime DEFAULT_MOMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PuntuacionSerieRepository puntuacionSerieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPuntuacionSerieMockMvc;

    private PuntuacionSerie puntuacionSerie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuntuacionSerieResource puntuacionSerieResource = new PuntuacionSerieResource(puntuacionSerieRepository);
        this.restPuntuacionSerieMockMvc = MockMvcBuilders.standaloneSetup(puntuacionSerieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PuntuacionSerie createEntity(EntityManager em) {
        PuntuacionSerie puntuacionSerie = new PuntuacionSerie()
            .puntuacion(DEFAULT_PUNTUACION)
            .momento(DEFAULT_MOMENTO);
        return puntuacionSerie;
    }

    @Before
    public void initTest() {
        puntuacionSerie = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuntuacionSerie() throws Exception {
        int databaseSizeBeforeCreate = puntuacionSerieRepository.findAll().size();

        // Create the PuntuacionSerie
        restPuntuacionSerieMockMvc.perform(post("/api/puntuacion-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionSerie)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionSerie in the database
        List<PuntuacionSerie> puntuacionSerieList = puntuacionSerieRepository.findAll();
        assertThat(puntuacionSerieList).hasSize(databaseSizeBeforeCreate + 1);
        PuntuacionSerie testPuntuacionSerie = puntuacionSerieList.get(puntuacionSerieList.size() - 1);
        assertThat(testPuntuacionSerie.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testPuntuacionSerie.getMomento()).isEqualTo(DEFAULT_MOMENTO);
    }

    @Test
    @Transactional
    public void createPuntuacionSerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puntuacionSerieRepository.findAll().size();

        // Create the PuntuacionSerie with an existing ID
        puntuacionSerie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntuacionSerieMockMvc.perform(post("/api/puntuacion-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionSerie)))
            .andExpect(status().isBadRequest());

        // Validate the PuntuacionSerie in the database
        List<PuntuacionSerie> puntuacionSerieList = puntuacionSerieRepository.findAll();
        assertThat(puntuacionSerieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuntuacionSeries() throws Exception {
        // Initialize the database
        puntuacionSerieRepository.saveAndFlush(puntuacionSerie);

        // Get all the puntuacionSerieList
        restPuntuacionSerieMockMvc.perform(get("/api/puntuacion-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntuacionSerie.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION.doubleValue())))
            .andExpect(jsonPath("$.[*].momento").value(hasItem(sameInstant(DEFAULT_MOMENTO))));
    }

    @Test
    @Transactional
    public void getPuntuacionSerie() throws Exception {
        // Initialize the database
        puntuacionSerieRepository.saveAndFlush(puntuacionSerie);

        // Get the puntuacionSerie
        restPuntuacionSerieMockMvc.perform(get("/api/puntuacion-series/{id}", puntuacionSerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puntuacionSerie.getId().intValue()))
            .andExpect(jsonPath("$.puntuacion").value(DEFAULT_PUNTUACION.doubleValue()))
            .andExpect(jsonPath("$.momento").value(sameInstant(DEFAULT_MOMENTO)));
    }

    @Test
    @Transactional
    public void getNonExistingPuntuacionSerie() throws Exception {
        // Get the puntuacionSerie
        restPuntuacionSerieMockMvc.perform(get("/api/puntuacion-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuntuacionSerie() throws Exception {
        // Initialize the database
        puntuacionSerieRepository.saveAndFlush(puntuacionSerie);
        int databaseSizeBeforeUpdate = puntuacionSerieRepository.findAll().size();

        // Update the puntuacionSerie
        PuntuacionSerie updatedPuntuacionSerie = puntuacionSerieRepository.findOne(puntuacionSerie.getId());
        updatedPuntuacionSerie
            .puntuacion(UPDATED_PUNTUACION)
            .momento(UPDATED_MOMENTO);

        restPuntuacionSerieMockMvc.perform(put("/api/puntuacion-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPuntuacionSerie)))
            .andExpect(status().isOk());

        // Validate the PuntuacionSerie in the database
        List<PuntuacionSerie> puntuacionSerieList = puntuacionSerieRepository.findAll();
        assertThat(puntuacionSerieList).hasSize(databaseSizeBeforeUpdate);
        PuntuacionSerie testPuntuacionSerie = puntuacionSerieList.get(puntuacionSerieList.size() - 1);
        assertThat(testPuntuacionSerie.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testPuntuacionSerie.getMomento()).isEqualTo(UPDATED_MOMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPuntuacionSerie() throws Exception {
        int databaseSizeBeforeUpdate = puntuacionSerieRepository.findAll().size();

        // Create the PuntuacionSerie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPuntuacionSerieMockMvc.perform(put("/api/puntuacion-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionSerie)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionSerie in the database
        List<PuntuacionSerie> puntuacionSerieList = puntuacionSerieRepository.findAll();
        assertThat(puntuacionSerieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePuntuacionSerie() throws Exception {
        // Initialize the database
        puntuacionSerieRepository.saveAndFlush(puntuacionSerie);
        int databaseSizeBeforeDelete = puntuacionSerieRepository.findAll().size();

        // Get the puntuacionSerie
        restPuntuacionSerieMockMvc.perform(delete("/api/puntuacion-series/{id}", puntuacionSerie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PuntuacionSerie> puntuacionSerieList = puntuacionSerieRepository.findAll();
        assertThat(puntuacionSerieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntuacionSerie.class);
        PuntuacionSerie puntuacionSerie1 = new PuntuacionSerie();
        puntuacionSerie1.setId(1L);
        PuntuacionSerie puntuacionSerie2 = new PuntuacionSerie();
        puntuacionSerie2.setId(puntuacionSerie1.getId());
        assertThat(puntuacionSerie1).isEqualTo(puntuacionSerie2);
        puntuacionSerie2.setId(2L);
        assertThat(puntuacionSerie1).isNotEqualTo(puntuacionSerie2);
        puntuacionSerie1.setId(null);
        assertThat(puntuacionSerie1).isNotEqualTo(puntuacionSerie2);
    }
}
