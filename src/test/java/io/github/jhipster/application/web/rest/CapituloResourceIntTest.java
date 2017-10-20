package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Capitulo;
import io.github.jhipster.application.repository.CapituloRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CapituloResource REST controller.
 *
 * @see CapituloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class CapituloResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_DURACION = 1D;
    private static final Double UPDATED_DURACION = 2D;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private CapituloRepository capituloRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCapituloMockMvc;

    private Capitulo capitulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapituloResource capituloResource = new CapituloResource(capituloRepository);
        this.restCapituloMockMvc = MockMvcBuilders.standaloneSetup(capituloResource)
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
    public static Capitulo createEntity(EntityManager em) {
        Capitulo capitulo = new Capitulo()
            .numero(DEFAULT_NUMERO)
            .nombre(DEFAULT_NOMBRE)
            .duracion(DEFAULT_DURACION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return capitulo;
    }

    @Before
    public void initTest() {
        capitulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapitulo() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo
        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isCreated());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate + 1);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCapitulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCapitulo.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testCapitulo.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testCapitulo.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCapituloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo with an existing ID
        capitulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isBadRequest());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCapitulos() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get all the capituloList
        restCapituloMockMvc.perform(get("/api/capitulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION.doubleValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", capitulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capitulo.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION.doubleValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingCapitulo() throws Exception {
        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);
        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Update the capitulo
        Capitulo updatedCapitulo = capituloRepository.findOne(capitulo.getId());
        updatedCapitulo
            .numero(UPDATED_NUMERO)
            .nombre(UPDATED_NOMBRE)
            .duracion(UPDATED_DURACION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCapitulo)))
            .andExpect(status().isOk());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCapitulo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCapitulo.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testCapitulo.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testCapitulo.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCapitulo() throws Exception {
        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Create the Capitulo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isCreated());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);
        int databaseSizeBeforeDelete = capituloRepository.findAll().size();

        // Get the capitulo
        restCapituloMockMvc.perform(delete("/api/capitulos/{id}", capitulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capitulo.class);
        Capitulo capitulo1 = new Capitulo();
        capitulo1.setId(1L);
        Capitulo capitulo2 = new Capitulo();
        capitulo2.setId(capitulo1.getId());
        assertThat(capitulo1).isEqualTo(capitulo2);
        capitulo2.setId(2L);
        assertThat(capitulo1).isNotEqualTo(capitulo2);
        capitulo1.setId(null);
        assertThat(capitulo1).isNotEqualTo(capitulo2);
    }
}
