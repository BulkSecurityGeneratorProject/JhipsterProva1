package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Jaguar;
import io.github.jhipster.application.repository.JaguarRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JaguarResource REST controller.
 *
 * @see JaguarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class JaguarResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ANO_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_GOOD_CAT = false;
    private static final Boolean UPDATED_GOOD_CAT = true;

    @Autowired
    private JaguarRepository jaguarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJaguarMockMvc;

    private Jaguar jaguar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JaguarResource jaguarResource = new JaguarResource(jaguarRepository);
        this.restJaguarMockMvc = MockMvcBuilders.standaloneSetup(jaguarResource)
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
    public static Jaguar createEntity(EntityManager em) {
        Jaguar jaguar = new Jaguar()
            .nombre(DEFAULT_NOMBRE)
            .anoCreacion(DEFAULT_ANO_CREACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .goodCat(DEFAULT_GOOD_CAT);
        return jaguar;
    }

    @Before
    public void initTest() {
        jaguar = createEntity(em);
    }

    @Test
    @Transactional
    public void createJaguar() throws Exception {
        int databaseSizeBeforeCreate = jaguarRepository.findAll().size();

        // Create the Jaguar
        restJaguarMockMvc.perform(post("/api/jaguars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jaguar)))
            .andExpect(status().isCreated());

        // Validate the Jaguar in the database
        List<Jaguar> jaguarList = jaguarRepository.findAll();
        assertThat(jaguarList).hasSize(databaseSizeBeforeCreate + 1);
        Jaguar testJaguar = jaguarList.get(jaguarList.size() - 1);
        assertThat(testJaguar.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJaguar.getAnoCreacion()).isEqualTo(DEFAULT_ANO_CREACION);
        assertThat(testJaguar.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testJaguar.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testJaguar.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testJaguar.isGoodCat()).isEqualTo(DEFAULT_GOOD_CAT);
    }

    @Test
    @Transactional
    public void createJaguarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jaguarRepository.findAll().size();

        // Create the Jaguar with an existing ID
        jaguar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJaguarMockMvc.perform(post("/api/jaguars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jaguar)))
            .andExpect(status().isBadRequest());

        // Validate the Jaguar in the database
        List<Jaguar> jaguarList = jaguarRepository.findAll();
        assertThat(jaguarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJaguars() throws Exception {
        // Initialize the database
        jaguarRepository.saveAndFlush(jaguar);

        // Get all the jaguarList
        restJaguarMockMvc.perform(get("/api/jaguars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jaguar.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].anoCreacion").value(hasItem(DEFAULT_ANO_CREACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].goodCat").value(hasItem(DEFAULT_GOOD_CAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getJaguar() throws Exception {
        // Initialize the database
        jaguarRepository.saveAndFlush(jaguar);

        // Get the jaguar
        restJaguarMockMvc.perform(get("/api/jaguars/{id}", jaguar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jaguar.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.anoCreacion").value(DEFAULT_ANO_CREACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.goodCat").value(DEFAULT_GOOD_CAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJaguar() throws Exception {
        // Get the jaguar
        restJaguarMockMvc.perform(get("/api/jaguars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJaguar() throws Exception {
        // Initialize the database
        jaguarRepository.saveAndFlush(jaguar);
        int databaseSizeBeforeUpdate = jaguarRepository.findAll().size();

        // Update the jaguar
        Jaguar updatedJaguar = jaguarRepository.findOne(jaguar.getId());
        updatedJaguar
            .nombre(UPDATED_NOMBRE)
            .anoCreacion(UPDATED_ANO_CREACION)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .goodCat(UPDATED_GOOD_CAT);

        restJaguarMockMvc.perform(put("/api/jaguars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJaguar)))
            .andExpect(status().isOk());

        // Validate the Jaguar in the database
        List<Jaguar> jaguarList = jaguarRepository.findAll();
        assertThat(jaguarList).hasSize(databaseSizeBeforeUpdate);
        Jaguar testJaguar = jaguarList.get(jaguarList.size() - 1);
        assertThat(testJaguar.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJaguar.getAnoCreacion()).isEqualTo(UPDATED_ANO_CREACION);
        assertThat(testJaguar.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testJaguar.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testJaguar.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testJaguar.isGoodCat()).isEqualTo(UPDATED_GOOD_CAT);
    }

    @Test
    @Transactional
    public void updateNonExistingJaguar() throws Exception {
        int databaseSizeBeforeUpdate = jaguarRepository.findAll().size();

        // Create the Jaguar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJaguarMockMvc.perform(put("/api/jaguars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jaguar)))
            .andExpect(status().isCreated());

        // Validate the Jaguar in the database
        List<Jaguar> jaguarList = jaguarRepository.findAll();
        assertThat(jaguarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJaguar() throws Exception {
        // Initialize the database
        jaguarRepository.saveAndFlush(jaguar);
        int databaseSizeBeforeDelete = jaguarRepository.findAll().size();

        // Get the jaguar
        restJaguarMockMvc.perform(delete("/api/jaguars/{id}", jaguar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jaguar> jaguarList = jaguarRepository.findAll();
        assertThat(jaguarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jaguar.class);
        Jaguar jaguar1 = new Jaguar();
        jaguar1.setId(1L);
        Jaguar jaguar2 = new Jaguar();
        jaguar2.setId(jaguar1.getId());
        assertThat(jaguar1).isEqualTo(jaguar2);
        jaguar2.setId(2L);
        assertThat(jaguar1).isNotEqualTo(jaguar2);
        jaguar1.setId(null);
        assertThat(jaguar1).isNotEqualTo(jaguar2);
    }
}
