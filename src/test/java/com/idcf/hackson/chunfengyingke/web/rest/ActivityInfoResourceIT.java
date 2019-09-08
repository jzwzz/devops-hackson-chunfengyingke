package com.idcf.hackson.chunfengyingke.web.rest;

import com.idcf.hackson.chunfengyingke.ChunfengApp;
import com.idcf.hackson.chunfengyingke.domain.ActivityInfo;
import com.idcf.hackson.chunfengyingke.repository.ActivityInfoRepository;
import com.idcf.hackson.chunfengyingke.service.ActivityInfoService;
import com.idcf.hackson.chunfengyingke.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.idcf.hackson.chunfengyingke.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ActivityInfoResource} REST controller.
 */
@SpringBootTest(classes = ChunfengApp.class)
public class ActivityInfoResourceIT {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ADD_DATE = "BBBBBBBBBB";

    @Autowired
    private ActivityInfoRepository activityInfoRepository;

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restActivityInfoMockMvc;

    private ActivityInfo activityInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityInfoResource activityInfoResource = new ActivityInfoResource(activityInfoService);
        this.restActivityInfoMockMvc = MockMvcBuilders.standaloneSetup(activityInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityInfo createEntity(EntityManager em) {
        ActivityInfo activityInfo = new ActivityInfo()
            .activityName(DEFAULT_ACTIVITY_NAME)
            .user(DEFAULT_USER)
            .telephone(DEFAULT_TELEPHONE)
            .payStatus(DEFAULT_PAY_STATUS)
            .addDate(DEFAULT_ADD_DATE);
        return activityInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityInfo createUpdatedEntity(EntityManager em) {
        ActivityInfo activityInfo = new ActivityInfo()
            .activityName(UPDATED_ACTIVITY_NAME)
            .user(UPDATED_USER)
            .telephone(UPDATED_TELEPHONE)
            .payStatus(UPDATED_PAY_STATUS)
            .addDate(UPDATED_ADD_DATE);
        return activityInfo;
    }

    @BeforeEach
    public void initTest() {
        activityInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityInfo() throws Exception {
        int databaseSizeBeforeCreate = activityInfoRepository.findAll().size();

        // Create the ActivityInfo
        restActivityInfoMockMvc.perform(post("/api/activity-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityInfo)))
            .andExpect(status().isCreated());

        // Validate the ActivityInfo in the database
        List<ActivityInfo> activityInfoList = activityInfoRepository.findAll();
        assertThat(activityInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityInfo testActivityInfo = activityInfoList.get(activityInfoList.size() - 1);
        assertThat(testActivityInfo.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testActivityInfo.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testActivityInfo.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testActivityInfo.getPayStatus()).isEqualTo(DEFAULT_PAY_STATUS);
        assertThat(testActivityInfo.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createActivityInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityInfoRepository.findAll().size();

        // Create the ActivityInfo with an existing ID
        activityInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityInfoMockMvc.perform(post("/api/activity-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityInfo in the database
        List<ActivityInfo> activityInfoList = activityInfoRepository.findAll();
        assertThat(activityInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivityInfos() throws Exception {
        // Initialize the database
        activityInfoRepository.saveAndFlush(activityInfo);

        // Get all the activityInfoList
        restActivityInfoMockMvc.perform(get("/api/activity-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].payStatus").value(hasItem(DEFAULT_PAY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getActivityInfo() throws Exception {
        // Initialize the database
        activityInfoRepository.saveAndFlush(activityInfo);

        // Get the activityInfo
        restActivityInfoMockMvc.perform(get("/api/activity-infos/{id}", activityInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityInfo.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.payStatus").value(DEFAULT_PAY_STATUS.toString()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityInfo() throws Exception {
        // Get the activityInfo
        restActivityInfoMockMvc.perform(get("/api/activity-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityInfo() throws Exception {
        // Initialize the database
        activityInfoService.save(activityInfo);

        int databaseSizeBeforeUpdate = activityInfoRepository.findAll().size();

        // Update the activityInfo
        ActivityInfo updatedActivityInfo = activityInfoRepository.findById(activityInfo.getId()).get();
        // Disconnect from session so that the updates on updatedActivityInfo are not directly saved in db
        em.detach(updatedActivityInfo);
        updatedActivityInfo
            .activityName(UPDATED_ACTIVITY_NAME)
            .user(UPDATED_USER)
            .telephone(UPDATED_TELEPHONE)
            .payStatus(UPDATED_PAY_STATUS)
            .addDate(UPDATED_ADD_DATE);

        restActivityInfoMockMvc.perform(put("/api/activity-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivityInfo)))
            .andExpect(status().isOk());

        // Validate the ActivityInfo in the database
        List<ActivityInfo> activityInfoList = activityInfoRepository.findAll();
        assertThat(activityInfoList).hasSize(databaseSizeBeforeUpdate);
        ActivityInfo testActivityInfo = activityInfoList.get(activityInfoList.size() - 1);
        assertThat(testActivityInfo.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testActivityInfo.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testActivityInfo.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testActivityInfo.getPayStatus()).isEqualTo(UPDATED_PAY_STATUS);
        assertThat(testActivityInfo.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityInfo() throws Exception {
        int databaseSizeBeforeUpdate = activityInfoRepository.findAll().size();

        // Create the ActivityInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityInfoMockMvc.perform(put("/api/activity-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityInfo in the database
        List<ActivityInfo> activityInfoList = activityInfoRepository.findAll();
        assertThat(activityInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityInfo() throws Exception {
        // Initialize the database
        activityInfoService.save(activityInfo);

        int databaseSizeBeforeDelete = activityInfoRepository.findAll().size();

        // Delete the activityInfo
        restActivityInfoMockMvc.perform(delete("/api/activity-infos/{id}", activityInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityInfo> activityInfoList = activityInfoRepository.findAll();
        assertThat(activityInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityInfo.class);
        ActivityInfo activityInfo1 = new ActivityInfo();
        activityInfo1.setId(1L);
        ActivityInfo activityInfo2 = new ActivityInfo();
        activityInfo2.setId(activityInfo1.getId());
        assertThat(activityInfo1).isEqualTo(activityInfo2);
        activityInfo2.setId(2L);
        assertThat(activityInfo1).isNotEqualTo(activityInfo2);
        activityInfo1.setId(null);
        assertThat(activityInfo1).isNotEqualTo(activityInfo2);
    }
}
