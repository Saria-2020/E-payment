package com.fu.epayment.web.rest;

import com.fu.epayment.EPaymentApp;
import com.fu.epayment.domain.Card;
import com.fu.epayment.domain.Customer;
import com.fu.epayment.repository.CardRepository;
import com.fu.epayment.service.CardService;
import com.fu.epayment.service.dto.CardCriteria;
import com.fu.epayment.service.CardQueryService;

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
 * Integration tests for the {@link CardResource} REST controller.
 */
@SpringBootTest(classes = EPaymentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CardResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXP_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXP_DATE = "BBBBBBBBBB";

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardQueryService cardQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardMockMvc;

    private Card card;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .number(DEFAULT_NUMBER)
            .name(DEFAULT_NAME)
            .expDate(DEFAULT_EXP_DATE);
        return card;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createUpdatedEntity(EntityManager em) {
        Card card = new Card()
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .expDate(UPDATED_EXP_DATE);
        return card;
    }

    @BeforeEach
    public void initTest() {
        card = createEntity(em);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();
        // Create the Card
        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCard.getExpDate()).isEqualTo(DEFAULT_EXP_DATE);
    }

    @Test
    @Transactional
    public void createCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card with an existing ID
        card.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expDate").value(hasItem(DEFAULT_EXP_DATE)));
    }
    
    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.expDate").value(DEFAULT_EXP_DATE));
    }


    @Test
    @Transactional
    public void getCardsByIdFiltering() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        Long id = card.getId();

        defaultCardShouldBeFound("id.equals=" + id);
        defaultCardShouldNotBeFound("id.notEquals=" + id);

        defaultCardShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardShouldNotBeFound("id.greaterThan=" + id);

        defaultCardShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCardsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number equals to DEFAULT_NUMBER
        defaultCardShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the cardList where number equals to UPDATED_NUMBER
        defaultCardShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number not equals to DEFAULT_NUMBER
        defaultCardShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the cardList where number not equals to UPDATED_NUMBER
        defaultCardShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultCardShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the cardList where number equals to UPDATED_NUMBER
        defaultCardShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number is not null
        defaultCardShouldBeFound("number.specified=true");

        // Get all the cardList where number is null
        defaultCardShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardsByNumberContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number contains DEFAULT_NUMBER
        defaultCardShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the cardList where number contains UPDATED_NUMBER
        defaultCardShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where number does not contain DEFAULT_NUMBER
        defaultCardShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the cardList where number does not contain UPDATED_NUMBER
        defaultCardShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCardsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name equals to DEFAULT_NAME
        defaultCardShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cardList where name equals to UPDATED_NAME
        defaultCardShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name not equals to DEFAULT_NAME
        defaultCardShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cardList where name not equals to UPDATED_NAME
        defaultCardShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCardShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cardList where name equals to UPDATED_NAME
        defaultCardShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name is not null
        defaultCardShouldBeFound("name.specified=true");

        // Get all the cardList where name is null
        defaultCardShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardsByNameContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name contains DEFAULT_NAME
        defaultCardShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cardList where name contains UPDATED_NAME
        defaultCardShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where name does not contain DEFAULT_NAME
        defaultCardShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cardList where name does not contain UPDATED_NAME
        defaultCardShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCardsByExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate equals to DEFAULT_EXP_DATE
        defaultCardShouldBeFound("expDate.equals=" + DEFAULT_EXP_DATE);

        // Get all the cardList where expDate equals to UPDATED_EXP_DATE
        defaultCardShouldNotBeFound("expDate.equals=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByExpDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate not equals to DEFAULT_EXP_DATE
        defaultCardShouldNotBeFound("expDate.notEquals=" + DEFAULT_EXP_DATE);

        // Get all the cardList where expDate not equals to UPDATED_EXP_DATE
        defaultCardShouldBeFound("expDate.notEquals=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate in DEFAULT_EXP_DATE or UPDATED_EXP_DATE
        defaultCardShouldBeFound("expDate.in=" + DEFAULT_EXP_DATE + "," + UPDATED_EXP_DATE);

        // Get all the cardList where expDate equals to UPDATED_EXP_DATE
        defaultCardShouldNotBeFound("expDate.in=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate is not null
        defaultCardShouldBeFound("expDate.specified=true");

        // Get all the cardList where expDate is null
        defaultCardShouldNotBeFound("expDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardsByExpDateContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate contains DEFAULT_EXP_DATE
        defaultCardShouldBeFound("expDate.contains=" + DEFAULT_EXP_DATE);

        // Get all the cardList where expDate contains UPDATED_EXP_DATE
        defaultCardShouldNotBeFound("expDate.contains=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByExpDateNotContainsSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList where expDate does not contain DEFAULT_EXP_DATE
        defaultCardShouldNotBeFound("expDate.doesNotContain=" + DEFAULT_EXP_DATE);

        // Get all the cardList where expDate does not contain UPDATED_EXP_DATE
        defaultCardShouldBeFound("expDate.doesNotContain=" + UPDATED_EXP_DATE);
    }


    @Test
    @Transactional
    public void getAllCardsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        card.setCustomer(customer);
        cardRepository.saveAndFlush(card);
        Long customerId = customer.getId();

        // Get all the cardList where customer equals to customerId
        defaultCardShouldBeFound("customerId.equals=" + customerId);

        // Get all the cardList where customer equals to customerId + 1
        defaultCardShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardShouldBeFound(String filter) throws Exception {
        restCardMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expDate").value(hasItem(DEFAULT_EXP_DATE)));

        // Check, that the count call also returns 1
        restCardMockMvc.perform(get("/api/cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardShouldNotBeFound(String filter) throws Exception {
        restCardMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardMockMvc.perform(get("/api/cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardService.save(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).get();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .expDate(UPDATED_EXP_DATE);

        restCardMockMvc.perform(put("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCard)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCard.getExpDate()).isEqualTo(UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc.perform(put("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardService.save(card);

        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Delete the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
