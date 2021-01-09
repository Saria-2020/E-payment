package com.fu.epayment.web.rest;

import com.fu.epayment.EPaymentApp;
import com.fu.epayment.domain.ActivityInformation;
import com.fu.epayment.domain.Category;
import com.fu.epayment.domain.Customer;
import com.fu.epayment.repository.ActivityInformationRepository;
import com.fu.epayment.service.ActivityInformationService;
import com.fu.epayment.service.dto.ActivityInformationCriteria;
import com.fu.epayment.service.ActivityInformationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ActivityInformationResource} REST controller.
 */
@SpringBootTest(classes = EPaymentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActivityInformationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECTOR = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_OF_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_OF_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_AREA_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_ARCHITECTURE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ARCHITECTURE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_OF_FLOORS = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_FLOORS = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_FEATURES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_OF_THE_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_OF_THE_FEATURES = "BBBBBBBBBB";

    @Autowired
    private ActivityInformationRepository activityInformationRepository;

    @Autowired
    private ActivityInformationService activityInformationService;

    @Autowired
    private ActivityInformationQueryService activityInformationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityInformationMockMvc;

    private ActivityInformation activityInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityInformation createEntity(EntityManager em) {
        ActivityInformation activityInformation = new ActivityInformation()
            .name(DEFAULT_NAME)
            .sector(DEFAULT_SECTOR)
            .typeOfActivity(DEFAULT_TYPE_OF_ACTIVITY)
            .propertyType(DEFAULT_PROPERTY_TYPE)
            .areaClass(DEFAULT_AREA_CLASS)
            .architectureType(DEFAULT_ARCHITECTURE_TYPE)
            .numberOfFloors(DEFAULT_NUMBER_OF_FLOORS)
            .features(DEFAULT_FEATURES)
            .descriptionOfTheFeatures(DEFAULT_DESCRIPTION_OF_THE_FEATURES);
        return activityInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityInformation createUpdatedEntity(EntityManager em) {
        ActivityInformation activityInformation = new ActivityInformation()
            .name(UPDATED_NAME)
            .sector(UPDATED_SECTOR)
            .typeOfActivity(UPDATED_TYPE_OF_ACTIVITY)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .areaClass(UPDATED_AREA_CLASS)
            .architectureType(UPDATED_ARCHITECTURE_TYPE)
            .numberOfFloors(UPDATED_NUMBER_OF_FLOORS)
            .features(UPDATED_FEATURES)
            .descriptionOfTheFeatures(UPDATED_DESCRIPTION_OF_THE_FEATURES);
        return activityInformation;
    }

    @BeforeEach
    public void initTest() {
        activityInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityInformation() throws Exception {
        int databaseSizeBeforeCreate = activityInformationRepository.findAll().size();
        // Create the ActivityInformation
        restActivityInformationMockMvc.perform(post("/api/activity-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityInformation)))
            .andExpect(status().isCreated());

        // Validate the ActivityInformation in the database
        List<ActivityInformation> activityInformationList = activityInformationRepository.findAll();
        assertThat(activityInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityInformation testActivityInformation = activityInformationList.get(activityInformationList.size() - 1);
        assertThat(testActivityInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivityInformation.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testActivityInformation.getTypeOfActivity()).isEqualTo(DEFAULT_TYPE_OF_ACTIVITY);
        assertThat(testActivityInformation.getPropertyType()).isEqualTo(DEFAULT_PROPERTY_TYPE);
        assertThat(testActivityInformation.getAreaClass()).isEqualTo(DEFAULT_AREA_CLASS);
        assertThat(testActivityInformation.getArchitectureType()).isEqualTo(DEFAULT_ARCHITECTURE_TYPE);
        assertThat(testActivityInformation.getNumberOfFloors()).isEqualTo(DEFAULT_NUMBER_OF_FLOORS);
        assertThat(testActivityInformation.getFeatures()).isEqualTo(DEFAULT_FEATURES);
        assertThat(testActivityInformation.getDescriptionOfTheFeatures()).isEqualTo(DEFAULT_DESCRIPTION_OF_THE_FEATURES);
    }

    @Test
    @Transactional
    public void createActivityInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityInformationRepository.findAll().size();

        // Create the ActivityInformation with an existing ID
        activityInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityInformationMockMvc.perform(post("/api/activity-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityInformation)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityInformation in the database
        List<ActivityInformation> activityInformationList = activityInformationRepository.findAll();
        assertThat(activityInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivityInformations() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList
        restActivityInformationMockMvc.perform(get("/api/activity-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR)))
            .andExpect(jsonPath("$.[*].typeOfActivity").value(hasItem(DEFAULT_TYPE_OF_ACTIVITY)))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE)))
            .andExpect(jsonPath("$.[*].areaClass").value(hasItem(DEFAULT_AREA_CLASS)))
            .andExpect(jsonPath("$.[*].architectureType").value(hasItem(DEFAULT_ARCHITECTURE_TYPE)))
            .andExpect(jsonPath("$.[*].numberOfFloors").value(hasItem(DEFAULT_NUMBER_OF_FLOORS)))
            .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES)))
            .andExpect(jsonPath("$.[*].descriptionOfTheFeatures").value(hasItem(DEFAULT_DESCRIPTION_OF_THE_FEATURES.toString())));
    }
    
    @Test
    @Transactional
    public void getActivityInformation() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get the activityInformation
        restActivityInformationMockMvc.perform(get("/api/activity-informations/{id}", activityInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityInformation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR))
            .andExpect(jsonPath("$.typeOfActivity").value(DEFAULT_TYPE_OF_ACTIVITY))
            .andExpect(jsonPath("$.propertyType").value(DEFAULT_PROPERTY_TYPE))
            .andExpect(jsonPath("$.areaClass").value(DEFAULT_AREA_CLASS))
            .andExpect(jsonPath("$.architectureType").value(DEFAULT_ARCHITECTURE_TYPE))
            .andExpect(jsonPath("$.numberOfFloors").value(DEFAULT_NUMBER_OF_FLOORS))
            .andExpect(jsonPath("$.features").value(DEFAULT_FEATURES))
            .andExpect(jsonPath("$.descriptionOfTheFeatures").value(DEFAULT_DESCRIPTION_OF_THE_FEATURES.toString()));
    }


    @Test
    @Transactional
    public void getActivityInformationsByIdFiltering() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        Long id = activityInformation.getId();

        defaultActivityInformationShouldBeFound("id.equals=" + id);
        defaultActivityInformationShouldNotBeFound("id.notEquals=" + id);

        defaultActivityInformationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityInformationShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityInformationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityInformationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name equals to DEFAULT_NAME
        defaultActivityInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the activityInformationList where name equals to UPDATED_NAME
        defaultActivityInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name not equals to DEFAULT_NAME
        defaultActivityInformationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the activityInformationList where name not equals to UPDATED_NAME
        defaultActivityInformationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultActivityInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the activityInformationList where name equals to UPDATED_NAME
        defaultActivityInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name is not null
        defaultActivityInformationShouldBeFound("name.specified=true");

        // Get all the activityInformationList where name is null
        defaultActivityInformationShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByNameContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name contains DEFAULT_NAME
        defaultActivityInformationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the activityInformationList where name contains UPDATED_NAME
        defaultActivityInformationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where name does not contain DEFAULT_NAME
        defaultActivityInformationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the activityInformationList where name does not contain UPDATED_NAME
        defaultActivityInformationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsBySectorIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector equals to DEFAULT_SECTOR
        defaultActivityInformationShouldBeFound("sector.equals=" + DEFAULT_SECTOR);

        // Get all the activityInformationList where sector equals to UPDATED_SECTOR
        defaultActivityInformationShouldNotBeFound("sector.equals=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsBySectorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector not equals to DEFAULT_SECTOR
        defaultActivityInformationShouldNotBeFound("sector.notEquals=" + DEFAULT_SECTOR);

        // Get all the activityInformationList where sector not equals to UPDATED_SECTOR
        defaultActivityInformationShouldBeFound("sector.notEquals=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsBySectorIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector in DEFAULT_SECTOR or UPDATED_SECTOR
        defaultActivityInformationShouldBeFound("sector.in=" + DEFAULT_SECTOR + "," + UPDATED_SECTOR);

        // Get all the activityInformationList where sector equals to UPDATED_SECTOR
        defaultActivityInformationShouldNotBeFound("sector.in=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsBySectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector is not null
        defaultActivityInformationShouldBeFound("sector.specified=true");

        // Get all the activityInformationList where sector is null
        defaultActivityInformationShouldNotBeFound("sector.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsBySectorContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector contains DEFAULT_SECTOR
        defaultActivityInformationShouldBeFound("sector.contains=" + DEFAULT_SECTOR);

        // Get all the activityInformationList where sector contains UPDATED_SECTOR
        defaultActivityInformationShouldNotBeFound("sector.contains=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsBySectorNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where sector does not contain DEFAULT_SECTOR
        defaultActivityInformationShouldNotBeFound("sector.doesNotContain=" + DEFAULT_SECTOR);

        // Get all the activityInformationList where sector does not contain UPDATED_SECTOR
        defaultActivityInformationShouldBeFound("sector.doesNotContain=" + UPDATED_SECTOR);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity equals to DEFAULT_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldBeFound("typeOfActivity.equals=" + DEFAULT_TYPE_OF_ACTIVITY);

        // Get all the activityInformationList where typeOfActivity equals to UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldNotBeFound("typeOfActivity.equals=" + UPDATED_TYPE_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity not equals to DEFAULT_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldNotBeFound("typeOfActivity.notEquals=" + DEFAULT_TYPE_OF_ACTIVITY);

        // Get all the activityInformationList where typeOfActivity not equals to UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldBeFound("typeOfActivity.notEquals=" + UPDATED_TYPE_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity in DEFAULT_TYPE_OF_ACTIVITY or UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldBeFound("typeOfActivity.in=" + DEFAULT_TYPE_OF_ACTIVITY + "," + UPDATED_TYPE_OF_ACTIVITY);

        // Get all the activityInformationList where typeOfActivity equals to UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldNotBeFound("typeOfActivity.in=" + UPDATED_TYPE_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity is not null
        defaultActivityInformationShouldBeFound("typeOfActivity.specified=true");

        // Get all the activityInformationList where typeOfActivity is null
        defaultActivityInformationShouldNotBeFound("typeOfActivity.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity contains DEFAULT_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldBeFound("typeOfActivity.contains=" + DEFAULT_TYPE_OF_ACTIVITY);

        // Get all the activityInformationList where typeOfActivity contains UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldNotBeFound("typeOfActivity.contains=" + UPDATED_TYPE_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByTypeOfActivityNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where typeOfActivity does not contain DEFAULT_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldNotBeFound("typeOfActivity.doesNotContain=" + DEFAULT_TYPE_OF_ACTIVITY);

        // Get all the activityInformationList where typeOfActivity does not contain UPDATED_TYPE_OF_ACTIVITY
        defaultActivityInformationShouldBeFound("typeOfActivity.doesNotContain=" + UPDATED_TYPE_OF_ACTIVITY);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType equals to DEFAULT_PROPERTY_TYPE
        defaultActivityInformationShouldBeFound("propertyType.equals=" + DEFAULT_PROPERTY_TYPE);

        // Get all the activityInformationList where propertyType equals to UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldNotBeFound("propertyType.equals=" + UPDATED_PROPERTY_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType not equals to DEFAULT_PROPERTY_TYPE
        defaultActivityInformationShouldNotBeFound("propertyType.notEquals=" + DEFAULT_PROPERTY_TYPE);

        // Get all the activityInformationList where propertyType not equals to UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldBeFound("propertyType.notEquals=" + UPDATED_PROPERTY_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType in DEFAULT_PROPERTY_TYPE or UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldBeFound("propertyType.in=" + DEFAULT_PROPERTY_TYPE + "," + UPDATED_PROPERTY_TYPE);

        // Get all the activityInformationList where propertyType equals to UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldNotBeFound("propertyType.in=" + UPDATED_PROPERTY_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType is not null
        defaultActivityInformationShouldBeFound("propertyType.specified=true");

        // Get all the activityInformationList where propertyType is null
        defaultActivityInformationShouldNotBeFound("propertyType.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType contains DEFAULT_PROPERTY_TYPE
        defaultActivityInformationShouldBeFound("propertyType.contains=" + DEFAULT_PROPERTY_TYPE);

        // Get all the activityInformationList where propertyType contains UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldNotBeFound("propertyType.contains=" + UPDATED_PROPERTY_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByPropertyTypeNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where propertyType does not contain DEFAULT_PROPERTY_TYPE
        defaultActivityInformationShouldNotBeFound("propertyType.doesNotContain=" + DEFAULT_PROPERTY_TYPE);

        // Get all the activityInformationList where propertyType does not contain UPDATED_PROPERTY_TYPE
        defaultActivityInformationShouldBeFound("propertyType.doesNotContain=" + UPDATED_PROPERTY_TYPE);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass equals to DEFAULT_AREA_CLASS
        defaultActivityInformationShouldBeFound("areaClass.equals=" + DEFAULT_AREA_CLASS);

        // Get all the activityInformationList where areaClass equals to UPDATED_AREA_CLASS
        defaultActivityInformationShouldNotBeFound("areaClass.equals=" + UPDATED_AREA_CLASS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass not equals to DEFAULT_AREA_CLASS
        defaultActivityInformationShouldNotBeFound("areaClass.notEquals=" + DEFAULT_AREA_CLASS);

        // Get all the activityInformationList where areaClass not equals to UPDATED_AREA_CLASS
        defaultActivityInformationShouldBeFound("areaClass.notEquals=" + UPDATED_AREA_CLASS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass in DEFAULT_AREA_CLASS or UPDATED_AREA_CLASS
        defaultActivityInformationShouldBeFound("areaClass.in=" + DEFAULT_AREA_CLASS + "," + UPDATED_AREA_CLASS);

        // Get all the activityInformationList where areaClass equals to UPDATED_AREA_CLASS
        defaultActivityInformationShouldNotBeFound("areaClass.in=" + UPDATED_AREA_CLASS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass is not null
        defaultActivityInformationShouldBeFound("areaClass.specified=true");

        // Get all the activityInformationList where areaClass is null
        defaultActivityInformationShouldNotBeFound("areaClass.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass contains DEFAULT_AREA_CLASS
        defaultActivityInformationShouldBeFound("areaClass.contains=" + DEFAULT_AREA_CLASS);

        // Get all the activityInformationList where areaClass contains UPDATED_AREA_CLASS
        defaultActivityInformationShouldNotBeFound("areaClass.contains=" + UPDATED_AREA_CLASS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByAreaClassNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where areaClass does not contain DEFAULT_AREA_CLASS
        defaultActivityInformationShouldNotBeFound("areaClass.doesNotContain=" + DEFAULT_AREA_CLASS);

        // Get all the activityInformationList where areaClass does not contain UPDATED_AREA_CLASS
        defaultActivityInformationShouldBeFound("areaClass.doesNotContain=" + UPDATED_AREA_CLASS);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType equals to DEFAULT_ARCHITECTURE_TYPE
        defaultActivityInformationShouldBeFound("architectureType.equals=" + DEFAULT_ARCHITECTURE_TYPE);

        // Get all the activityInformationList where architectureType equals to UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldNotBeFound("architectureType.equals=" + UPDATED_ARCHITECTURE_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType not equals to DEFAULT_ARCHITECTURE_TYPE
        defaultActivityInformationShouldNotBeFound("architectureType.notEquals=" + DEFAULT_ARCHITECTURE_TYPE);

        // Get all the activityInformationList where architectureType not equals to UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldBeFound("architectureType.notEquals=" + UPDATED_ARCHITECTURE_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType in DEFAULT_ARCHITECTURE_TYPE or UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldBeFound("architectureType.in=" + DEFAULT_ARCHITECTURE_TYPE + "," + UPDATED_ARCHITECTURE_TYPE);

        // Get all the activityInformationList where architectureType equals to UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldNotBeFound("architectureType.in=" + UPDATED_ARCHITECTURE_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType is not null
        defaultActivityInformationShouldBeFound("architectureType.specified=true");

        // Get all the activityInformationList where architectureType is null
        defaultActivityInformationShouldNotBeFound("architectureType.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType contains DEFAULT_ARCHITECTURE_TYPE
        defaultActivityInformationShouldBeFound("architectureType.contains=" + DEFAULT_ARCHITECTURE_TYPE);

        // Get all the activityInformationList where architectureType contains UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldNotBeFound("architectureType.contains=" + UPDATED_ARCHITECTURE_TYPE);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByArchitectureTypeNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where architectureType does not contain DEFAULT_ARCHITECTURE_TYPE
        defaultActivityInformationShouldNotBeFound("architectureType.doesNotContain=" + DEFAULT_ARCHITECTURE_TYPE);

        // Get all the activityInformationList where architectureType does not contain UPDATED_ARCHITECTURE_TYPE
        defaultActivityInformationShouldBeFound("architectureType.doesNotContain=" + UPDATED_ARCHITECTURE_TYPE);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors equals to DEFAULT_NUMBER_OF_FLOORS
        defaultActivityInformationShouldBeFound("numberOfFloors.equals=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the activityInformationList where numberOfFloors equals to UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldNotBeFound("numberOfFloors.equals=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors not equals to DEFAULT_NUMBER_OF_FLOORS
        defaultActivityInformationShouldNotBeFound("numberOfFloors.notEquals=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the activityInformationList where numberOfFloors not equals to UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldBeFound("numberOfFloors.notEquals=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors in DEFAULT_NUMBER_OF_FLOORS or UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldBeFound("numberOfFloors.in=" + DEFAULT_NUMBER_OF_FLOORS + "," + UPDATED_NUMBER_OF_FLOORS);

        // Get all the activityInformationList where numberOfFloors equals to UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldNotBeFound("numberOfFloors.in=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors is not null
        defaultActivityInformationShouldBeFound("numberOfFloors.specified=true");

        // Get all the activityInformationList where numberOfFloors is null
        defaultActivityInformationShouldNotBeFound("numberOfFloors.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors contains DEFAULT_NUMBER_OF_FLOORS
        defaultActivityInformationShouldBeFound("numberOfFloors.contains=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the activityInformationList where numberOfFloors contains UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldNotBeFound("numberOfFloors.contains=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByNumberOfFloorsNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where numberOfFloors does not contain DEFAULT_NUMBER_OF_FLOORS
        defaultActivityInformationShouldNotBeFound("numberOfFloors.doesNotContain=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the activityInformationList where numberOfFloors does not contain UPDATED_NUMBER_OF_FLOORS
        defaultActivityInformationShouldBeFound("numberOfFloors.doesNotContain=" + UPDATED_NUMBER_OF_FLOORS);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features equals to DEFAULT_FEATURES
        defaultActivityInformationShouldBeFound("features.equals=" + DEFAULT_FEATURES);

        // Get all the activityInformationList where features equals to UPDATED_FEATURES
        defaultActivityInformationShouldNotBeFound("features.equals=" + UPDATED_FEATURES);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features not equals to DEFAULT_FEATURES
        defaultActivityInformationShouldNotBeFound("features.notEquals=" + DEFAULT_FEATURES);

        // Get all the activityInformationList where features not equals to UPDATED_FEATURES
        defaultActivityInformationShouldBeFound("features.notEquals=" + UPDATED_FEATURES);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesIsInShouldWork() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features in DEFAULT_FEATURES or UPDATED_FEATURES
        defaultActivityInformationShouldBeFound("features.in=" + DEFAULT_FEATURES + "," + UPDATED_FEATURES);

        // Get all the activityInformationList where features equals to UPDATED_FEATURES
        defaultActivityInformationShouldNotBeFound("features.in=" + UPDATED_FEATURES);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features is not null
        defaultActivityInformationShouldBeFound("features.specified=true");

        // Get all the activityInformationList where features is null
        defaultActivityInformationShouldNotBeFound("features.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features contains DEFAULT_FEATURES
        defaultActivityInformationShouldBeFound("features.contains=" + DEFAULT_FEATURES);

        // Get all the activityInformationList where features contains UPDATED_FEATURES
        defaultActivityInformationShouldNotBeFound("features.contains=" + UPDATED_FEATURES);
    }

    @Test
    @Transactional
    public void getAllActivityInformationsByFeaturesNotContainsSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);

        // Get all the activityInformationList where features does not contain DEFAULT_FEATURES
        defaultActivityInformationShouldNotBeFound("features.doesNotContain=" + DEFAULT_FEATURES);

        // Get all the activityInformationList where features does not contain UPDATED_FEATURES
        defaultActivityInformationShouldBeFound("features.doesNotContain=" + UPDATED_FEATURES);
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        activityInformation.setCategory(category);
        activityInformationRepository.saveAndFlush(activityInformation);
        Long categoryId = category.getId();

        // Get all the activityInformationList where category equals to categoryId
        defaultActivityInformationShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the activityInformationList where category equals to categoryId + 1
        defaultActivityInformationShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllActivityInformationsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        activityInformationRepository.saveAndFlush(activityInformation);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        activityInformation.setCustomer(customer);
        activityInformationRepository.saveAndFlush(activityInformation);
        Long customerId = customer.getId();

        // Get all the activityInformationList where customer equals to customerId
        defaultActivityInformationShouldBeFound("customerId.equals=" + customerId);

        // Get all the activityInformationList where customer equals to customerId + 1
        defaultActivityInformationShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityInformationShouldBeFound(String filter) throws Exception {
        restActivityInformationMockMvc.perform(get("/api/activity-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR)))
            .andExpect(jsonPath("$.[*].typeOfActivity").value(hasItem(DEFAULT_TYPE_OF_ACTIVITY)))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE)))
            .andExpect(jsonPath("$.[*].areaClass").value(hasItem(DEFAULT_AREA_CLASS)))
            .andExpect(jsonPath("$.[*].architectureType").value(hasItem(DEFAULT_ARCHITECTURE_TYPE)))
            .andExpect(jsonPath("$.[*].numberOfFloors").value(hasItem(DEFAULT_NUMBER_OF_FLOORS)))
            .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES)))
            .andExpect(jsonPath("$.[*].descriptionOfTheFeatures").value(hasItem(DEFAULT_DESCRIPTION_OF_THE_FEATURES.toString())));

        // Check, that the count call also returns 1
        restActivityInformationMockMvc.perform(get("/api/activity-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityInformationShouldNotBeFound(String filter) throws Exception {
        restActivityInformationMockMvc.perform(get("/api/activity-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityInformationMockMvc.perform(get("/api/activity-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingActivityInformation() throws Exception {
        // Get the activityInformation
        restActivityInformationMockMvc.perform(get("/api/activity-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityInformation() throws Exception {
        // Initialize the database
        activityInformationService.save(activityInformation);

        int databaseSizeBeforeUpdate = activityInformationRepository.findAll().size();

        // Update the activityInformation
        ActivityInformation updatedActivityInformation = activityInformationRepository.findById(activityInformation.getId()).get();
        // Disconnect from session so that the updates on updatedActivityInformation are not directly saved in db
        em.detach(updatedActivityInformation);
        updatedActivityInformation
            .name(UPDATED_NAME)
            .sector(UPDATED_SECTOR)
            .typeOfActivity(UPDATED_TYPE_OF_ACTIVITY)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .areaClass(UPDATED_AREA_CLASS)
            .architectureType(UPDATED_ARCHITECTURE_TYPE)
            .numberOfFloors(UPDATED_NUMBER_OF_FLOORS)
            .features(UPDATED_FEATURES)
            .descriptionOfTheFeatures(UPDATED_DESCRIPTION_OF_THE_FEATURES);

        restActivityInformationMockMvc.perform(put("/api/activity-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivityInformation)))
            .andExpect(status().isOk());

        // Validate the ActivityInformation in the database
        List<ActivityInformation> activityInformationList = activityInformationRepository.findAll();
        assertThat(activityInformationList).hasSize(databaseSizeBeforeUpdate);
        ActivityInformation testActivityInformation = activityInformationList.get(activityInformationList.size() - 1);
        assertThat(testActivityInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivityInformation.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testActivityInformation.getTypeOfActivity()).isEqualTo(UPDATED_TYPE_OF_ACTIVITY);
        assertThat(testActivityInformation.getPropertyType()).isEqualTo(UPDATED_PROPERTY_TYPE);
        assertThat(testActivityInformation.getAreaClass()).isEqualTo(UPDATED_AREA_CLASS);
        assertThat(testActivityInformation.getArchitectureType()).isEqualTo(UPDATED_ARCHITECTURE_TYPE);
        assertThat(testActivityInformation.getNumberOfFloors()).isEqualTo(UPDATED_NUMBER_OF_FLOORS);
        assertThat(testActivityInformation.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testActivityInformation.getDescriptionOfTheFeatures()).isEqualTo(UPDATED_DESCRIPTION_OF_THE_FEATURES);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityInformation() throws Exception {
        int databaseSizeBeforeUpdate = activityInformationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityInformationMockMvc.perform(put("/api/activity-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityInformation)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityInformation in the database
        List<ActivityInformation> activityInformationList = activityInformationRepository.findAll();
        assertThat(activityInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityInformation() throws Exception {
        // Initialize the database
        activityInformationService.save(activityInformation);

        int databaseSizeBeforeDelete = activityInformationRepository.findAll().size();

        // Delete the activityInformation
        restActivityInformationMockMvc.perform(delete("/api/activity-informations/{id}", activityInformation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityInformation> activityInformationList = activityInformationRepository.findAll();
        assertThat(activityInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
