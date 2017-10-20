package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterProva1App;

import io.github.jhipster.application.domain.Productora;
import io.github.jhipster.application.repository.ProductoraRepository;
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
 * Test class for the ProductoraResource REST controller.
 *
 * @see ProductoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterProva1App.class)
public class ProductoraResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductoraRepository productoraRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductoraMockMvc;

    private Productora productora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoraResource productoraResource = new ProductoraResource(productoraRepository);
        this.restProductoraMockMvc = MockMvcBuilders.standaloneSetup(productoraResource)
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
    public static Productora createEntity(EntityManager em) {
        Productora productora = new Productora()
            .nombre(DEFAULT_NOMBRE)
            .pais(DEFAULT_PAIS)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return productora;
    }

    @Before
    public void initTest() {
        productora = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductora() throws Exception {
        int databaseSizeBeforeCreate = productoraRepository.findAll().size();

        // Create the Productora
        restProductoraMockMvc.perform(post("/api/productoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productora)))
            .andExpect(status().isCreated());

        // Validate the Productora in the database
        List<Productora> productoraList = productoraRepository.findAll();
        assertThat(productoraList).hasSize(databaseSizeBeforeCreate + 1);
        Productora testProductora = productoraList.get(productoraList.size() - 1);
        assertThat(testProductora.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductora.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testProductora.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testProductora.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProductoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoraRepository.findAll().size();

        // Create the Productora with an existing ID
        productora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoraMockMvc.perform(post("/api/productoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productora)))
            .andExpect(status().isBadRequest());

        // Validate the Productora in the database
        List<Productora> productoraList = productoraRepository.findAll();
        assertThat(productoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductoras() throws Exception {
        // Initialize the database
        productoraRepository.saveAndFlush(productora);

        // Get all the productoraList
        restProductoraMockMvc.perform(get("/api/productoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getProductora() throws Exception {
        // Initialize the database
        productoraRepository.saveAndFlush(productora);

        // Get the productora
        restProductoraMockMvc.perform(get("/api/productoras/{id}", productora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productora.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingProductora() throws Exception {
        // Get the productora
        restProductoraMockMvc.perform(get("/api/productoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductora() throws Exception {
        // Initialize the database
        productoraRepository.saveAndFlush(productora);
        int databaseSizeBeforeUpdate = productoraRepository.findAll().size();

        // Update the productora
        Productora updatedProductora = productoraRepository.findOne(productora.getId());
        updatedProductora
            .nombre(UPDATED_NOMBRE)
            .pais(UPDATED_PAIS)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restProductoraMockMvc.perform(put("/api/productoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductora)))
            .andExpect(status().isOk());

        // Validate the Productora in the database
        List<Productora> productoraList = productoraRepository.findAll();
        assertThat(productoraList).hasSize(databaseSizeBeforeUpdate);
        Productora testProductora = productoraList.get(productoraList.size() - 1);
        assertThat(testProductora.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductora.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testProductora.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testProductora.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductora() throws Exception {
        int databaseSizeBeforeUpdate = productoraRepository.findAll().size();

        // Create the Productora

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductoraMockMvc.perform(put("/api/productoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productora)))
            .andExpect(status().isCreated());

        // Validate the Productora in the database
        List<Productora> productoraList = productoraRepository.findAll();
        assertThat(productoraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductora() throws Exception {
        // Initialize the database
        productoraRepository.saveAndFlush(productora);
        int databaseSizeBeforeDelete = productoraRepository.findAll().size();

        // Get the productora
        restProductoraMockMvc.perform(delete("/api/productoras/{id}", productora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Productora> productoraList = productoraRepository.findAll();
        assertThat(productoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Productora.class);
        Productora productora1 = new Productora();
        productora1.setId(1L);
        Productora productora2 = new Productora();
        productora2.setId(productora1.getId());
        assertThat(productora1).isEqualTo(productora2);
        productora2.setId(2L);
        assertThat(productora1).isNotEqualTo(productora2);
        productora1.setId(null);
        assertThat(productora1).isNotEqualTo(productora2);
    }
}
