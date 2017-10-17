package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Jugador;
import io.github.jhipster.application.repository.JugadorRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class JugadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUM_CANASTAS = 1;
    private static final Integer UPDATED_NUM_CANASTAS = 2;

    private static final Integer DEFAULT_NUM_ASISTENCIAS = 0;
    private static final Integer UPDATED_NUM_ASISTENCIAS = 1;

    private static final Integer DEFAULT_NUM_REBOTES = 0;
    private static final Integer UPDATED_NUM_REBOTES = 1;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JugadorResource jugadorResource = new JugadorResource(jugadorRepository);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource)
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
    public static Jugador createEntity(EntityManager em) {
        Jugador jugador = new Jugador()
            .nombre(DEFAULT_NOMBRE)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .numCanastas(DEFAULT_NUM_CANASTAS)
            .numAsistencias(DEFAULT_NUM_ASISTENCIAS)
            .numRebotes(DEFAULT_NUM_REBOTES);
        return jugador;
    }

    @Before
    public void initTest() {
        jugador = createEntity(em);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador
        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJugador.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testJugador.getNumCanastas()).isEqualTo(DEFAULT_NUM_CANASTAS);
        assertThat(testJugador.getNumAsistencias()).isEqualTo(DEFAULT_NUM_ASISTENCIAS);
        assertThat(testJugador.getNumRebotes()).isEqualTo(DEFAULT_NUM_REBOTES);
    }

    @Test
    @Transactional
    public void createJugadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador with an existing ID
        jugador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadorList
        restJugadorMockMvc.perform(get("/api/jugadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].numCanastas").value(hasItem(DEFAULT_NUM_CANASTAS)))
            .andExpect(jsonPath("$.[*].numAsistencias").value(hasItem(DEFAULT_NUM_ASISTENCIAS)))
            .andExpect(jsonPath("$.[*].numRebotes").value(hasItem(DEFAULT_NUM_REBOTES)));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.numCanastas").value(DEFAULT_NUM_CANASTAS))
            .andExpect(jsonPath("$.numAsistencias").value(DEFAULT_NUM_ASISTENCIAS))
            .andExpect(jsonPath("$.numRebotes").value(DEFAULT_NUM_REBOTES));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        Jugador updatedJugador = jugadorRepository.findOne(jugador.getId());
        updatedJugador
            .nombre(UPDATED_NOMBRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .numCanastas(UPDATED_NUM_CANASTAS)
            .numAsistencias(UPDATED_NUM_ASISTENCIAS)
            .numRebotes(UPDATED_NUM_REBOTES);

        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJugador)))
            .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJugador.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testJugador.getNumCanastas()).isEqualTo(UPDATED_NUM_CANASTAS);
        assertThat(testJugador.getNumAsistencias()).isEqualTo(UPDATED_NUM_ASISTENCIAS);
        assertThat(testJugador.getNumRebotes()).isEqualTo(UPDATED_NUM_REBOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingJugador() throws Exception {
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Create the Jugador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jugador.class);
        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);
        Jugador jugador2 = new Jugador();
        jugador2.setId(jugador1.getId());
        assertThat(jugador1).isEqualTo(jugador2);
        jugador2.setId(2L);
        assertThat(jugador1).isNotEqualTo(jugador2);
        jugador1.setId(null);
        assertThat(jugador1).isNotEqualTo(jugador2);
    }
}
