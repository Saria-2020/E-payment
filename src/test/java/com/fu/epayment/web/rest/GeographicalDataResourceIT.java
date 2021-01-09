package com.fu.epayment.web.rest;

import com.fu.epayment.EPaymentApp;
import com.fu.epayment.domain.GeographicalData;
import com.fu.epayment.domain.Customer;
import com.fu.epayment.repository.GeographicalDataRepository;
import com.fu.epayment.service.GeographicalDataService;
import com.fu.epayment.service.dto.GeographicalDataCriteria;
import com.fu.epayment.service.GeographicalDataQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GeographicalDataResource} REST controller.
 */
@SpringBootTest(classes = EPaymentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GeographicalDataResourceIT {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_SQUARE = "AAAAAAAAAA";
    private static final String UPDATED_SQUARE = "BBBBBBBBBB";

    private static final String DEFAULT_REAL_ESTATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REAL_ESTATE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_OF_THE_REAL_ESTATE = "AAAAAAAAAA";
    private static final String UPDATED_AREA_OF_THE_REAL_ESTATE = "BBBBBBBBBB";

    @Autowired
    private GeographicalDataRepository geographicalDataRepository;

    @Autowired
    private GeographicalDataService geographicalDataService;

    @Autowired
    private GeographicalDataQueryService geographicalDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeographicalDataMockMvc;

    private GeographicalData geographicalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicalData createEntity(EntityManager em) {
        GeographicalData geographicalData = new GeographicalData()
            .state(DEFAULT_STATE)
            .units(DEFAULT_UNITS)
            .district(DEFAULT_DISTRICT)
            .square(DEFAULT_SQUARE)
            .realEstateNumber(DEFAULT_REAL_ESTATE_NUMBER)
            .activityNumber(DEFAULT_ACTIVITY_NUMBER)
            .areaOfTheRealEstate(DEFAULT_AREA_OF_THE_REAL_ESTATE);
        return geographicalData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicalData createUpdatedEntity(EntityManager em) {
        GeographicalData geographicalData = new GeographicalData()
            .state(UPDATED_STATE)
            .units(UPDATED_UNITS)
            .district(UPDATED_DISTRICT)
            .square(UPDATED_SQUARE)
            .realEstateNumber(UPDATED_REAL_ESTATE_NUMBER)
            .activityNumber(UPDATED_ACTIVITY_NUMBER)
            .areaOfTheRealEstate(UPDATED_AREA_OF_THE_REAL_ESTATE);
        return geographicalData;
    }

    @BeforeEach
    public void initTest() {
        geographicalData = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeographicalData() throws Exception {
        int databaseSizeBeforeCreate = geographicalDataRepository.findAll().size();
        // Create the GeographicalData
        restGeographicalDataMockMvc.perform(post("/api/geographical-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicalData)))
            .andExpect(status().isCreated());

        // Validate the GeographicalData in the database
        List<GeographicalData> geographicalDataList = geographicalDataRepository.findAll();
        assertThat(geographicalDataList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicalData testGeographicalData = geographicalDataList.get(geographicalDataList.size() - 1);
        assertThat(testGeographicalData.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testGeographicalData.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testGeographicalData.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testGeographicalData.getSquare()).isEqualTo(DEFAULT_SQUARE);
        assertThat(testGeographicalData.getRealEstateNumber()).isEqualTo(DEFAULT_REAL_ESTATE_NUMBER);
        assertThat(testGeographicalData.getActivityNumber()).isEqualTo(DEFAULT_ACTIVITY_NUMBER);
        assertThat(testGeographicalData.getAreaOfTheRealEstate()).isEqualTo(DEFAULT_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void createGeographicalDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geographicalDataRepository.findAll().size();

        // Create the GeographicalData with an existing ID
        geographicalData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicalDataMockMvc.perform(post("/api/geographical-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicalData)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicalData in the database
        List<GeographicalData> geographicalDataList = geographicalDataRepository.findAll();
        assertThat(geographicalDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeographicalData() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList
        restGeographicalDataMockMvc.perform(get("/api/geographical-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].square").value(hasItem(DEFAULT_SQUARE)))
            .andExpect(jsonPath("$.[*].realEstateNumber").value(hasItem(DEFAULT_REAL_ESTATE_NUMBER)))
            .andExpect(jsonPath("$.[*].activityNumber").value(hasItem(DEFAULT_ACTIVITY_NUMBER)))
            .andExpect(jsonPath("$.[*].areaOfTheRealEstate").value(hasItem(DEFAULT_AREA_OF_THE_REAL_ESTATE)));
    }
    
    @Test
    @Transactional
    public void getGeographicalData() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get the geographicalData
        restGeographicalDataMockMvc.perform(get("/api/geographical-data/{id}", geographicalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geographicalData.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT))
            .andExpect(jsonPath("$.square").value(DEFAULT_SQUARE))
            .andExpect(jsonPath("$.realEstateNumber").value(DEFAULT_REAL_ESTATE_NUMBER))
            .andExpect(jsonPath("$.activityNumber").value(DEFAULT_ACTIVITY_NUMBER))
            .andExpect(jsonPath("$.areaOfTheRealEstate").value(DEFAULT_AREA_OF_THE_REAL_ESTATE));
    }


    @Test
    @Transactional
    public void getGeographicalDataByIdFiltering() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        Long id = geographicalData.getId();

        defaultGeographicalDataShouldBeFound("id.equals=" + id);
        defaultGeographicalDataShouldNotBeFound("id.notEquals=" + id);

        defaultGeographicalDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeographicalDataShouldNotBeFound("id.greaterThan=" + id);

        defaultGeographicalDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeographicalDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state equals to DEFAULT_STATE
        defaultGeographicalDataShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the geographicalDataList where state equals to UPDATED_STATE
        defaultGeographicalDataShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state not equals to DEFAULT_STATE
        defaultGeographicalDataShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the geographicalDataList where state not equals to UPDATED_STATE
        defaultGeographicalDataShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByStateIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state in DEFAULT_STATE or UPDATED_STATE
        defaultGeographicalDataShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the geographicalDataList where state equals to UPDATED_STATE
        defaultGeographicalDataShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state is not null
        defaultGeographicalDataShouldBeFound("state.specified=true");

        // Get all the geographicalDataList where state is null
        defaultGeographicalDataShouldNotBeFound("state.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByStateContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state contains DEFAULT_STATE
        defaultGeographicalDataShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the geographicalDataList where state contains UPDATED_STATE
        defaultGeographicalDataShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByStateNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where state does not contain DEFAULT_STATE
        defaultGeographicalDataShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the geographicalDataList where state does not contain UPDATED_STATE
        defaultGeographicalDataShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByUnitsIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units equals to DEFAULT_UNITS
        defaultGeographicalDataShouldBeFound("units.equals=" + DEFAULT_UNITS);

        // Get all the geographicalDataList where units equals to UPDATED_UNITS
        defaultGeographicalDataShouldNotBeFound("units.equals=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByUnitsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units not equals to DEFAULT_UNITS
        defaultGeographicalDataShouldNotBeFound("units.notEquals=" + DEFAULT_UNITS);

        // Get all the geographicalDataList where units not equals to UPDATED_UNITS
        defaultGeographicalDataShouldBeFound("units.notEquals=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByUnitsIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units in DEFAULT_UNITS or UPDATED_UNITS
        defaultGeographicalDataShouldBeFound("units.in=" + DEFAULT_UNITS + "," + UPDATED_UNITS);

        // Get all the geographicalDataList where units equals to UPDATED_UNITS
        defaultGeographicalDataShouldNotBeFound("units.in=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByUnitsIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units is not null
        defaultGeographicalDataShouldBeFound("units.specified=true");

        // Get all the geographicalDataList where units is null
        defaultGeographicalDataShouldNotBeFound("units.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByUnitsContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units contains DEFAULT_UNITS
        defaultGeographicalDataShouldBeFound("units.contains=" + DEFAULT_UNITS);

        // Get all the geographicalDataList where units contains UPDATED_UNITS
        defaultGeographicalDataShouldNotBeFound("units.contains=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByUnitsNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where units does not contain DEFAULT_UNITS
        defaultGeographicalDataShouldNotBeFound("units.doesNotContain=" + DEFAULT_UNITS);

        // Get all the geographicalDataList where units does not contain UPDATED_UNITS
        defaultGeographicalDataShouldBeFound("units.doesNotContain=" + UPDATED_UNITS);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district equals to DEFAULT_DISTRICT
        defaultGeographicalDataShouldBeFound("district.equals=" + DEFAULT_DISTRICT);

        // Get all the geographicalDataList where district equals to UPDATED_DISTRICT
        defaultGeographicalDataShouldNotBeFound("district.equals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByDistrictIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district not equals to DEFAULT_DISTRICT
        defaultGeographicalDataShouldNotBeFound("district.notEquals=" + DEFAULT_DISTRICT);

        // Get all the geographicalDataList where district not equals to UPDATED_DISTRICT
        defaultGeographicalDataShouldBeFound("district.notEquals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByDistrictIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district in DEFAULT_DISTRICT or UPDATED_DISTRICT
        defaultGeographicalDataShouldBeFound("district.in=" + DEFAULT_DISTRICT + "," + UPDATED_DISTRICT);

        // Get all the geographicalDataList where district equals to UPDATED_DISTRICT
        defaultGeographicalDataShouldNotBeFound("district.in=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByDistrictIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district is not null
        defaultGeographicalDataShouldBeFound("district.specified=true");

        // Get all the geographicalDataList where district is null
        defaultGeographicalDataShouldNotBeFound("district.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByDistrictContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district contains DEFAULT_DISTRICT
        defaultGeographicalDataShouldBeFound("district.contains=" + DEFAULT_DISTRICT);

        // Get all the geographicalDataList where district contains UPDATED_DISTRICT
        defaultGeographicalDataShouldNotBeFound("district.contains=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByDistrictNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where district does not contain DEFAULT_DISTRICT
        defaultGeographicalDataShouldNotBeFound("district.doesNotContain=" + DEFAULT_DISTRICT);

        // Get all the geographicalDataList where district does not contain UPDATED_DISTRICT
        defaultGeographicalDataShouldBeFound("district.doesNotContain=" + UPDATED_DISTRICT);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataBySquareIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square equals to DEFAULT_SQUARE
        defaultGeographicalDataShouldBeFound("square.equals=" + DEFAULT_SQUARE);

        // Get all the geographicalDataList where square equals to UPDATED_SQUARE
        defaultGeographicalDataShouldNotBeFound("square.equals=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataBySquareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square not equals to DEFAULT_SQUARE
        defaultGeographicalDataShouldNotBeFound("square.notEquals=" + DEFAULT_SQUARE);

        // Get all the geographicalDataList where square not equals to UPDATED_SQUARE
        defaultGeographicalDataShouldBeFound("square.notEquals=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataBySquareIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square in DEFAULT_SQUARE or UPDATED_SQUARE
        defaultGeographicalDataShouldBeFound("square.in=" + DEFAULT_SQUARE + "," + UPDATED_SQUARE);

        // Get all the geographicalDataList where square equals to UPDATED_SQUARE
        defaultGeographicalDataShouldNotBeFound("square.in=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataBySquareIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square is not null
        defaultGeographicalDataShouldBeFound("square.specified=true");

        // Get all the geographicalDataList where square is null
        defaultGeographicalDataShouldNotBeFound("square.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataBySquareContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square contains DEFAULT_SQUARE
        defaultGeographicalDataShouldBeFound("square.contains=" + DEFAULT_SQUARE);

        // Get all the geographicalDataList where square contains UPDATED_SQUARE
        defaultGeographicalDataShouldNotBeFound("square.contains=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataBySquareNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where square does not contain DEFAULT_SQUARE
        defaultGeographicalDataShouldNotBeFound("square.doesNotContain=" + DEFAULT_SQUARE);

        // Get all the geographicalDataList where square does not contain UPDATED_SQUARE
        defaultGeographicalDataShouldBeFound("square.doesNotContain=" + UPDATED_SQUARE);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber equals to DEFAULT_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldBeFound("realEstateNumber.equals=" + DEFAULT_REAL_ESTATE_NUMBER);

        // Get all the geographicalDataList where realEstateNumber equals to UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.equals=" + UPDATED_REAL_ESTATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber not equals to DEFAULT_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.notEquals=" + DEFAULT_REAL_ESTATE_NUMBER);

        // Get all the geographicalDataList where realEstateNumber not equals to UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldBeFound("realEstateNumber.notEquals=" + UPDATED_REAL_ESTATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber in DEFAULT_REAL_ESTATE_NUMBER or UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldBeFound("realEstateNumber.in=" + DEFAULT_REAL_ESTATE_NUMBER + "," + UPDATED_REAL_ESTATE_NUMBER);

        // Get all the geographicalDataList where realEstateNumber equals to UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.in=" + UPDATED_REAL_ESTATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber is not null
        defaultGeographicalDataShouldBeFound("realEstateNumber.specified=true");

        // Get all the geographicalDataList where realEstateNumber is null
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber contains DEFAULT_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldBeFound("realEstateNumber.contains=" + DEFAULT_REAL_ESTATE_NUMBER);

        // Get all the geographicalDataList where realEstateNumber contains UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.contains=" + UPDATED_REAL_ESTATE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByRealEstateNumberNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where realEstateNumber does not contain DEFAULT_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldNotBeFound("realEstateNumber.doesNotContain=" + DEFAULT_REAL_ESTATE_NUMBER);

        // Get all the geographicalDataList where realEstateNumber does not contain UPDATED_REAL_ESTATE_NUMBER
        defaultGeographicalDataShouldBeFound("realEstateNumber.doesNotContain=" + UPDATED_REAL_ESTATE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber equals to DEFAULT_ACTIVITY_NUMBER
        defaultGeographicalDataShouldBeFound("activityNumber.equals=" + DEFAULT_ACTIVITY_NUMBER);

        // Get all the geographicalDataList where activityNumber equals to UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldNotBeFound("activityNumber.equals=" + UPDATED_ACTIVITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber not equals to DEFAULT_ACTIVITY_NUMBER
        defaultGeographicalDataShouldNotBeFound("activityNumber.notEquals=" + DEFAULT_ACTIVITY_NUMBER);

        // Get all the geographicalDataList where activityNumber not equals to UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldBeFound("activityNumber.notEquals=" + UPDATED_ACTIVITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber in DEFAULT_ACTIVITY_NUMBER or UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldBeFound("activityNumber.in=" + DEFAULT_ACTIVITY_NUMBER + "," + UPDATED_ACTIVITY_NUMBER);

        // Get all the geographicalDataList where activityNumber equals to UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldNotBeFound("activityNumber.in=" + UPDATED_ACTIVITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber is not null
        defaultGeographicalDataShouldBeFound("activityNumber.specified=true");

        // Get all the geographicalDataList where activityNumber is null
        defaultGeographicalDataShouldNotBeFound("activityNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber contains DEFAULT_ACTIVITY_NUMBER
        defaultGeographicalDataShouldBeFound("activityNumber.contains=" + DEFAULT_ACTIVITY_NUMBER);

        // Get all the geographicalDataList where activityNumber contains UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldNotBeFound("activityNumber.contains=" + UPDATED_ACTIVITY_NUMBER);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByActivityNumberNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where activityNumber does not contain DEFAULT_ACTIVITY_NUMBER
        defaultGeographicalDataShouldNotBeFound("activityNumber.doesNotContain=" + DEFAULT_ACTIVITY_NUMBER);

        // Get all the geographicalDataList where activityNumber does not contain UPDATED_ACTIVITY_NUMBER
        defaultGeographicalDataShouldBeFound("activityNumber.doesNotContain=" + UPDATED_ACTIVITY_NUMBER);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate equals to DEFAULT_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.equals=" + DEFAULT_AREA_OF_THE_REAL_ESTATE);

        // Get all the geographicalDataList where areaOfTheRealEstate equals to UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.equals=" + UPDATED_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate not equals to DEFAULT_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.notEquals=" + DEFAULT_AREA_OF_THE_REAL_ESTATE);

        // Get all the geographicalDataList where areaOfTheRealEstate not equals to UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.notEquals=" + UPDATED_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateIsInShouldWork() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate in DEFAULT_AREA_OF_THE_REAL_ESTATE or UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.in=" + DEFAULT_AREA_OF_THE_REAL_ESTATE + "," + UPDATED_AREA_OF_THE_REAL_ESTATE);

        // Get all the geographicalDataList where areaOfTheRealEstate equals to UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.in=" + UPDATED_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate is not null
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.specified=true");

        // Get all the geographicalDataList where areaOfTheRealEstate is null
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate contains DEFAULT_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.contains=" + DEFAULT_AREA_OF_THE_REAL_ESTATE);

        // Get all the geographicalDataList where areaOfTheRealEstate contains UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.contains=" + UPDATED_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void getAllGeographicalDataByAreaOfTheRealEstateNotContainsSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);

        // Get all the geographicalDataList where areaOfTheRealEstate does not contain DEFAULT_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldNotBeFound("areaOfTheRealEstate.doesNotContain=" + DEFAULT_AREA_OF_THE_REAL_ESTATE);

        // Get all the geographicalDataList where areaOfTheRealEstate does not contain UPDATED_AREA_OF_THE_REAL_ESTATE
        defaultGeographicalDataShouldBeFound("areaOfTheRealEstate.doesNotContain=" + UPDATED_AREA_OF_THE_REAL_ESTATE);
    }


    @Test
    @Transactional
    public void getAllGeographicalDataByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicalDataRepository.saveAndFlush(geographicalData);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        geographicalData.setCustomer(customer);
        geographicalDataRepository.saveAndFlush(geographicalData);
        Long customerId = customer.getId();

        // Get all the geographicalDataList where customer equals to customerId
        defaultGeographicalDataShouldBeFound("customerId.equals=" + customerId);

        // Get all the geographicalDataList where customer equals to customerId + 1
        defaultGeographicalDataShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeographicalDataShouldBeFound(String filter) throws Exception {
        restGeographicalDataMockMvc.perform(get("/api/geographical-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].square").value(hasItem(DEFAULT_SQUARE)))
            .andExpect(jsonPath("$.[*].realEstateNumber").value(hasItem(DEFAULT_REAL_ESTATE_NUMBER)))
            .andExpect(jsonPath("$.[*].activityNumber").value(hasItem(DEFAULT_ACTIVITY_NUMBER)))
            .andExpect(jsonPath("$.[*].areaOfTheRealEstate").value(hasItem(DEFAULT_AREA_OF_THE_REAL_ESTATE)));

        // Check, that the count call also returns 1
        restGeographicalDataMockMvc.perform(get("/api/geographical-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeographicalDataShouldNotBeFound(String filter) throws Exception {
        restGeographicalDataMockMvc.perform(get("/api/geographical-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeographicalDataMockMvc.perform(get("/api/geographical-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGeographicalData() throws Exception {
        // Get the geographicalData
        restGeographicalDataMockMvc.perform(get("/api/geographical-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeographicalData() throws Exception {
        // Initialize the database
        geographicalDataService.save(geographicalData);

        int databaseSizeBeforeUpdate = geographicalDataRepository.findAll().size();

        // Update the geographicalData
        GeographicalData updatedGeographicalData = geographicalDataRepository.findById(geographicalData.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicalData are not directly saved in db
        em.detach(updatedGeographicalData);
        updatedGeographicalData
            .state(UPDATED_STATE)
            .units(UPDATED_UNITS)
            .district(UPDATED_DISTRICT)
            .square(UPDATED_SQUARE)
            .realEstateNumber(UPDATED_REAL_ESTATE_NUMBER)
            .activityNumber(UPDATED_ACTIVITY_NUMBER)
            .areaOfTheRealEstate(UPDATED_AREA_OF_THE_REAL_ESTATE);

        restGeographicalDataMockMvc.perform(put("/api/geographical-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeographicalData)))
            .andExpect(status().isOk());

        // Validate the GeographicalData in the database
        List<GeographicalData> geographicalDataList = geographicalDataRepository.findAll();
        assertThat(geographicalDataList).hasSize(databaseSizeBeforeUpdate);
        GeographicalData testGeographicalData = geographicalDataList.get(geographicalDataList.size() - 1);
        assertThat(testGeographicalData.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGeographicalData.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testGeographicalData.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testGeographicalData.getSquare()).isEqualTo(UPDATED_SQUARE);
        assertThat(testGeographicalData.getRealEstateNumber()).isEqualTo(UPDATED_REAL_ESTATE_NUMBER);
        assertThat(testGeographicalData.getActivityNumber()).isEqualTo(UPDATED_ACTIVITY_NUMBER);
        assertThat(testGeographicalData.getAreaOfTheRealEstate()).isEqualTo(UPDATED_AREA_OF_THE_REAL_ESTATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeographicalData() throws Exception {
        int databaseSizeBeforeUpdate = geographicalDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicalDataMockMvc.perform(put("/api/geographical-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geographicalData)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicalData in the database
        List<GeographicalData> geographicalDataList = geographicalDataRepository.findAll();
        assertThat(geographicalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeographicalData() throws Exception {
        // Initialize the database
        geographicalDataService.save(geographicalData);

        int databaseSizeBeforeDelete = geographicalDataRepository.findAll().size();

        // Delete the geographicalData
        restGeographicalDataMockMvc.perform(delete("/api/geographical-data/{id}", geographicalData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeographicalData> geographicalDataList = geographicalDataRepository.findAll();
        assertThat(geographicalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
