package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Leonsito;
import io.github.jhipster.application.repository.LeonsitoRepository;
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
 * Test class for the LeonsitoResource REST controller.
 *
 * @see LeonsitoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class LeonsitoResourceIntTest {

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
    private LeonsitoRepository leonsitoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeonsitoMockMvc;

    private Leonsito leonsito;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeonsitoResource leonsitoResource = new LeonsitoResource(leonsitoRepository);
        this.restLeonsitoMockMvc = MockMvcBuilders.standaloneSetup(leonsitoResource)
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
    public static Leonsito createEntity(EntityManager em) {
        Leonsito leonsito = new Leonsito()
            .nombre(DEFAULT_NOMBRE)
            .anoCreacion(DEFAULT_ANO_CREACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .goodCat(DEFAULT_GOOD_CAT);
        return leonsito;
    }

    @Before
    public void initTest() {
        leonsito = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeonsito() throws Exception {
        int databaseSizeBeforeCreate = leonsitoRepository.findAll().size();

        // Create the Leonsito
        restLeonsitoMockMvc.perform(post("/api/leonsitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leonsito)))
            .andExpect(status().isCreated());

        // Validate the Leonsito in the database
        List<Leonsito> leonsitoList = leonsitoRepository.findAll();
        assertThat(leonsitoList).hasSize(databaseSizeBeforeCreate + 1);
        Leonsito testLeonsito = leonsitoList.get(leonsitoList.size() - 1);
        assertThat(testLeonsito.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLeonsito.getAnoCreacion()).isEqualTo(DEFAULT_ANO_CREACION);
        assertThat(testLeonsito.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testLeonsito.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testLeonsito.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testLeonsito.isGoodCat()).isEqualTo(DEFAULT_GOOD_CAT);
    }

    @Test
    @Transactional
    public void createLeonsitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leonsitoRepository.findAll().size();

        // Create the Leonsito with an existing ID
        leonsito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeonsitoMockMvc.perform(post("/api/leonsitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leonsito)))
            .andExpect(status().isBadRequest());

        // Validate the Leonsito in the database
        List<Leonsito> leonsitoList = leonsitoRepository.findAll();
        assertThat(leonsitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLeonsitos() throws Exception {
        // Initialize the database
        leonsitoRepository.saveAndFlush(leonsito);

        // Get all the leonsitoList
        restLeonsitoMockMvc.perform(get("/api/leonsitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leonsito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].anoCreacion").value(hasItem(DEFAULT_ANO_CREACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].goodCat").value(hasItem(DEFAULT_GOOD_CAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getLeonsito() throws Exception {
        // Initialize the database
        leonsitoRepository.saveAndFlush(leonsito);

        // Get the leonsito
        restLeonsitoMockMvc.perform(get("/api/leonsitos/{id}", leonsito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leonsito.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.anoCreacion").value(DEFAULT_ANO_CREACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.goodCat").value(DEFAULT_GOOD_CAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLeonsito() throws Exception {
        // Get the leonsito
        restLeonsitoMockMvc.perform(get("/api/leonsitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeonsito() throws Exception {
        // Initialize the database
        leonsitoRepository.saveAndFlush(leonsito);
        int databaseSizeBeforeUpdate = leonsitoRepository.findAll().size();

        // Update the leonsito
        Leonsito updatedLeonsito = leonsitoRepository.findOne(leonsito.getId());
        updatedLeonsito
            .nombre(UPDATED_NOMBRE)
            .anoCreacion(UPDATED_ANO_CREACION)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .goodCat(UPDATED_GOOD_CAT);

        restLeonsitoMockMvc.perform(put("/api/leonsitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeonsito)))
            .andExpect(status().isOk());

        // Validate the Leonsito in the database
        List<Leonsito> leonsitoList = leonsitoRepository.findAll();
        assertThat(leonsitoList).hasSize(databaseSizeBeforeUpdate);
        Leonsito testLeonsito = leonsitoList.get(leonsitoList.size() - 1);
        assertThat(testLeonsito.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLeonsito.getAnoCreacion()).isEqualTo(UPDATED_ANO_CREACION);
        assertThat(testLeonsito.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testLeonsito.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testLeonsito.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testLeonsito.isGoodCat()).isEqualTo(UPDATED_GOOD_CAT);
    }

    @Test
    @Transactional
    public void updateNonExistingLeonsito() throws Exception {
        int databaseSizeBeforeUpdate = leonsitoRepository.findAll().size();

        // Create the Leonsito

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeonsitoMockMvc.perform(put("/api/leonsitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leonsito)))
            .andExpect(status().isCreated());

        // Validate the Leonsito in the database
        List<Leonsito> leonsitoList = leonsitoRepository.findAll();
        assertThat(leonsitoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeonsito() throws Exception {
        // Initialize the database
        leonsitoRepository.saveAndFlush(leonsito);
        int databaseSizeBeforeDelete = leonsitoRepository.findAll().size();

        // Get the leonsito
        restLeonsitoMockMvc.perform(delete("/api/leonsitos/{id}", leonsito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Leonsito> leonsitoList = leonsitoRepository.findAll();
        assertThat(leonsitoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leonsito.class);
        Leonsito leonsito1 = new Leonsito();
        leonsito1.setId(1L);
        Leonsito leonsito2 = new Leonsito();
        leonsito2.setId(leonsito1.getId());
        assertThat(leonsito1).isEqualTo(leonsito2);
        leonsito2.setId(2L);
        assertThat(leonsito1).isNotEqualTo(leonsito2);
        leonsito1.setId(null);
        assertThat(leonsito1).isNotEqualTo(leonsito2);
    }
}
