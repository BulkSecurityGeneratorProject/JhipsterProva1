package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Gatito;
import io.github.jhipster.application.repository.GatitoRepository;
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
 * Test class for the GatitoResource REST controller.
 *
 * @see GatitoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class GatitoResourceIntTest {

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
    private GatitoRepository gatitoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGatitoMockMvc;

    private Gatito gatito;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GatitoResource gatitoResource = new GatitoResource(gatitoRepository);
        this.restGatitoMockMvc = MockMvcBuilders.standaloneSetup(gatitoResource)
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
    public static Gatito createEntity(EntityManager em) {
        Gatito gatito = new Gatito()
            .nombre(DEFAULT_NOMBRE)
            .anoCreacion(DEFAULT_ANO_CREACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .goodCat(DEFAULT_GOOD_CAT);
        return gatito;
    }

    @Before
    public void initTest() {
        gatito = createEntity(em);
    }

    @Test
    @Transactional
    public void createGatito() throws Exception {
        int databaseSizeBeforeCreate = gatitoRepository.findAll().size();

        // Create the Gatito
        restGatitoMockMvc.perform(post("/api/gatitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatito)))
            .andExpect(status().isCreated());

        // Validate the Gatito in the database
        List<Gatito> gatitoList = gatitoRepository.findAll();
        assertThat(gatitoList).hasSize(databaseSizeBeforeCreate + 1);
        Gatito testGatito = gatitoList.get(gatitoList.size() - 1);
        assertThat(testGatito.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testGatito.getAnoCreacion()).isEqualTo(DEFAULT_ANO_CREACION);
        assertThat(testGatito.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGatito.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testGatito.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testGatito.isGoodCat()).isEqualTo(DEFAULT_GOOD_CAT);
    }

    @Test
    @Transactional
    public void createGatitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gatitoRepository.findAll().size();

        // Create the Gatito with an existing ID
        gatito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGatitoMockMvc.perform(post("/api/gatitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatito)))
            .andExpect(status().isBadRequest());

        // Validate the Gatito in the database
        List<Gatito> gatitoList = gatitoRepository.findAll();
        assertThat(gatitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGatitos() throws Exception {
        // Initialize the database
        gatitoRepository.saveAndFlush(gatito);

        // Get all the gatitoList
        restGatitoMockMvc.perform(get("/api/gatitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gatito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].anoCreacion").value(hasItem(DEFAULT_ANO_CREACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].goodCat").value(hasItem(DEFAULT_GOOD_CAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getGatito() throws Exception {
        // Initialize the database
        gatitoRepository.saveAndFlush(gatito);

        // Get the gatito
        restGatitoMockMvc.perform(get("/api/gatitos/{id}", gatito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gatito.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.anoCreacion").value(DEFAULT_ANO_CREACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.goodCat").value(DEFAULT_GOOD_CAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGatito() throws Exception {
        // Get the gatito
        restGatitoMockMvc.perform(get("/api/gatitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGatito() throws Exception {
        // Initialize the database
        gatitoRepository.saveAndFlush(gatito);
        int databaseSizeBeforeUpdate = gatitoRepository.findAll().size();

        // Update the gatito
        Gatito updatedGatito = gatitoRepository.findOne(gatito.getId());
        updatedGatito
            .nombre(UPDATED_NOMBRE)
            .anoCreacion(UPDATED_ANO_CREACION)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .goodCat(UPDATED_GOOD_CAT);

        restGatitoMockMvc.perform(put("/api/gatitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGatito)))
            .andExpect(status().isOk());

        // Validate the Gatito in the database
        List<Gatito> gatitoList = gatitoRepository.findAll();
        assertThat(gatitoList).hasSize(databaseSizeBeforeUpdate);
        Gatito testGatito = gatitoList.get(gatitoList.size() - 1);
        assertThat(testGatito.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testGatito.getAnoCreacion()).isEqualTo(UPDATED_ANO_CREACION);
        assertThat(testGatito.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGatito.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testGatito.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testGatito.isGoodCat()).isEqualTo(UPDATED_GOOD_CAT);
    }

    @Test
    @Transactional
    public void updateNonExistingGatito() throws Exception {
        int databaseSizeBeforeUpdate = gatitoRepository.findAll().size();

        // Create the Gatito

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGatitoMockMvc.perform(put("/api/gatitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatito)))
            .andExpect(status().isCreated());

        // Validate the Gatito in the database
        List<Gatito> gatitoList = gatitoRepository.findAll();
        assertThat(gatitoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGatito() throws Exception {
        // Initialize the database
        gatitoRepository.saveAndFlush(gatito);
        int databaseSizeBeforeDelete = gatitoRepository.findAll().size();

        // Get the gatito
        restGatitoMockMvc.perform(delete("/api/gatitos/{id}", gatito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gatito> gatitoList = gatitoRepository.findAll();
        assertThat(gatitoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gatito.class);
        Gatito gatito1 = new Gatito();
        gatito1.setId(1L);
        Gatito gatito2 = new Gatito();
        gatito2.setId(gatito1.getId());
        assertThat(gatito1).isEqualTo(gatito2);
        gatito2.setId(2L);
        assertThat(gatito1).isNotEqualTo(gatito2);
        gatito1.setId(null);
        assertThat(gatito1).isNotEqualTo(gatito2);
    }
}
