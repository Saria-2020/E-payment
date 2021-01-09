package com.fu.epayment.web.rest;

import com.fu.epayment.EPaymentApp;
import com.fu.epayment.domain.Invoice;
import com.fu.epayment.domain.Card;
import com.fu.epayment.domain.Customer;
import com.fu.epayment.repository.InvoiceRepository;
import com.fu.epayment.service.InvoiceService;
import com.fu.epayment.service.dto.InvoiceCriteria;
import com.fu.epayment.service.InvoiceQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.fu.epayment.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = EPaymentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UNIQUE_NUMBER_CUSTOMER = UUID.randomUUID();
    private static final UUID UPDATED_UNIQUE_NUMBER_CUSTOMER = UUID.randomUUID();

    private static final String DEFAULT_NAME_OF_THE_CARD_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_THE_CARD_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_EXPIRATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRATION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT_OF_THE_INVOICE = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_OF_THE_INVOICE = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT_PAID = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT_PAID = "BBBBBBBBBB";

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .date(DEFAULT_DATE)
            .uniqueNumberCustomer(DEFAULT_UNIQUE_NUMBER_CUSTOMER)
            .nameOfTheCardOwner(DEFAULT_NAME_OF_THE_CARD_OWNER)
            .cardExpirationDate(DEFAULT_CARD_EXPIRATION_DATE)
            .verificationNumber(DEFAULT_VERIFICATION_NUMBER)
            .transactionNumber(DEFAULT_TRANSACTION_NUMBER)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .unitName(DEFAULT_UNIT_NAME)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .amountOfTheInvoice(DEFAULT_AMOUNT_OF_THE_INVOICE)
            .amountPaid(DEFAULT_AMOUNT_PAID);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .date(UPDATED_DATE)
            .uniqueNumberCustomer(UPDATED_UNIQUE_NUMBER_CUSTOMER)
            .nameOfTheCardOwner(UPDATED_NAME_OF_THE_CARD_OWNER)
            .cardExpirationDate(UPDATED_CARD_EXPIRATION_DATE)
            .verificationNumber(UPDATED_VERIFICATION_NUMBER)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .unitName(UPDATED_UNIT_NAME)
            .customerName(UPDATED_CUSTOMER_NAME)
            .amountOfTheInvoice(UPDATED_AMOUNT_OF_THE_INVOICE)
            .amountPaid(UPDATED_AMOUNT_PAID);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();
        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testInvoice.getUniqueNumberCustomer()).isEqualTo(DEFAULT_UNIQUE_NUMBER_CUSTOMER);
        assertThat(testInvoice.getNameOfTheCardOwner()).isEqualTo(DEFAULT_NAME_OF_THE_CARD_OWNER);
        assertThat(testInvoice.getCardExpirationDate()).isEqualTo(DEFAULT_CARD_EXPIRATION_DATE);
        assertThat(testInvoice.getVerificationNumber()).isEqualTo(DEFAULT_VERIFICATION_NUMBER);
        assertThat(testInvoice.getTransactionNumber()).isEqualTo(DEFAULT_TRANSACTION_NUMBER);
        assertThat(testInvoice.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testInvoice.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
        assertThat(testInvoice.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testInvoice.getAmountOfTheInvoice()).isEqualTo(DEFAULT_AMOUNT_OF_THE_INVOICE);
        assertThat(testInvoice.getAmountPaid()).isEqualTo(DEFAULT_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].uniqueNumberCustomer").value(hasItem(DEFAULT_UNIQUE_NUMBER_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].nameOfTheCardOwner").value(hasItem(DEFAULT_NAME_OF_THE_CARD_OWNER)))
            .andExpect(jsonPath("$.[*].cardExpirationDate").value(hasItem(DEFAULT_CARD_EXPIRATION_DATE)))
            .andExpect(jsonPath("$.[*].verificationNumber").value(hasItem(DEFAULT_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].amountOfTheInvoice").value(hasItem(DEFAULT_AMOUNT_OF_THE_INVOICE)))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID)));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.uniqueNumberCustomer").value(DEFAULT_UNIQUE_NUMBER_CUSTOMER.toString()))
            .andExpect(jsonPath("$.nameOfTheCardOwner").value(DEFAULT_NAME_OF_THE_CARD_OWNER))
            .andExpect(jsonPath("$.cardExpirationDate").value(DEFAULT_CARD_EXPIRATION_DATE))
            .andExpect(jsonPath("$.verificationNumber").value(DEFAULT_VERIFICATION_NUMBER))
            .andExpect(jsonPath("$.transactionNumber").value(DEFAULT_TRANSACTION_NUMBER))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.amountOfTheInvoice").value(DEFAULT_AMOUNT_OF_THE_INVOICE))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        Long id = invoice.getId();

        defaultInvoiceShouldBeFound("id.equals=" + id);
        defaultInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date equals to DEFAULT_DATE
        defaultInvoiceShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the invoiceList where date equals to UPDATED_DATE
        defaultInvoiceShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date not equals to DEFAULT_DATE
        defaultInvoiceShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the invoiceList where date not equals to UPDATED_DATE
        defaultInvoiceShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date in DEFAULT_DATE or UPDATED_DATE
        defaultInvoiceShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the invoiceList where date equals to UPDATED_DATE
        defaultInvoiceShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date is not null
        defaultInvoiceShouldBeFound("date.specified=true");

        // Get all the invoiceList where date is null
        defaultInvoiceShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date is greater than or equal to DEFAULT_DATE
        defaultInvoiceShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the invoiceList where date is greater than or equal to UPDATED_DATE
        defaultInvoiceShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date is less than or equal to DEFAULT_DATE
        defaultInvoiceShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the invoiceList where date is less than or equal to SMALLER_DATE
        defaultInvoiceShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date is less than DEFAULT_DATE
        defaultInvoiceShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the invoiceList where date is less than UPDATED_DATE
        defaultInvoiceShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where date is greater than DEFAULT_DATE
        defaultInvoiceShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the invoiceList where date is greater than SMALLER_DATE
        defaultInvoiceShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByUniqueNumberCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where uniqueNumberCustomer equals to DEFAULT_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldBeFound("uniqueNumberCustomer.equals=" + DEFAULT_UNIQUE_NUMBER_CUSTOMER);

        // Get all the invoiceList where uniqueNumberCustomer equals to UPDATED_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldNotBeFound("uniqueNumberCustomer.equals=" + UPDATED_UNIQUE_NUMBER_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUniqueNumberCustomerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where uniqueNumberCustomer not equals to DEFAULT_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldNotBeFound("uniqueNumberCustomer.notEquals=" + DEFAULT_UNIQUE_NUMBER_CUSTOMER);

        // Get all the invoiceList where uniqueNumberCustomer not equals to UPDATED_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldBeFound("uniqueNumberCustomer.notEquals=" + UPDATED_UNIQUE_NUMBER_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUniqueNumberCustomerIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where uniqueNumberCustomer in DEFAULT_UNIQUE_NUMBER_CUSTOMER or UPDATED_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldBeFound("uniqueNumberCustomer.in=" + DEFAULT_UNIQUE_NUMBER_CUSTOMER + "," + UPDATED_UNIQUE_NUMBER_CUSTOMER);

        // Get all the invoiceList where uniqueNumberCustomer equals to UPDATED_UNIQUE_NUMBER_CUSTOMER
        defaultInvoiceShouldNotBeFound("uniqueNumberCustomer.in=" + UPDATED_UNIQUE_NUMBER_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUniqueNumberCustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where uniqueNumberCustomer is not null
        defaultInvoiceShouldBeFound("uniqueNumberCustomer.specified=true");

        // Get all the invoiceList where uniqueNumberCustomer is null
        defaultInvoiceShouldNotBeFound("uniqueNumberCustomer.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner equals to DEFAULT_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.equals=" + DEFAULT_NAME_OF_THE_CARD_OWNER);

        // Get all the invoiceList where nameOfTheCardOwner equals to UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.equals=" + UPDATED_NAME_OF_THE_CARD_OWNER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner not equals to DEFAULT_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.notEquals=" + DEFAULT_NAME_OF_THE_CARD_OWNER);

        // Get all the invoiceList where nameOfTheCardOwner not equals to UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.notEquals=" + UPDATED_NAME_OF_THE_CARD_OWNER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner in DEFAULT_NAME_OF_THE_CARD_OWNER or UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.in=" + DEFAULT_NAME_OF_THE_CARD_OWNER + "," + UPDATED_NAME_OF_THE_CARD_OWNER);

        // Get all the invoiceList where nameOfTheCardOwner equals to UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.in=" + UPDATED_NAME_OF_THE_CARD_OWNER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner is not null
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.specified=true");

        // Get all the invoiceList where nameOfTheCardOwner is null
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner contains DEFAULT_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.contains=" + DEFAULT_NAME_OF_THE_CARD_OWNER);

        // Get all the invoiceList where nameOfTheCardOwner contains UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.contains=" + UPDATED_NAME_OF_THE_CARD_OWNER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByNameOfTheCardOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where nameOfTheCardOwner does not contain DEFAULT_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldNotBeFound("nameOfTheCardOwner.doesNotContain=" + DEFAULT_NAME_OF_THE_CARD_OWNER);

        // Get all the invoiceList where nameOfTheCardOwner does not contain UPDATED_NAME_OF_THE_CARD_OWNER
        defaultInvoiceShouldBeFound("nameOfTheCardOwner.doesNotContain=" + UPDATED_NAME_OF_THE_CARD_OWNER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate equals to DEFAULT_CARD_EXPIRATION_DATE
        defaultInvoiceShouldBeFound("cardExpirationDate.equals=" + DEFAULT_CARD_EXPIRATION_DATE);

        // Get all the invoiceList where cardExpirationDate equals to UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldNotBeFound("cardExpirationDate.equals=" + UPDATED_CARD_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate not equals to DEFAULT_CARD_EXPIRATION_DATE
        defaultInvoiceShouldNotBeFound("cardExpirationDate.notEquals=" + DEFAULT_CARD_EXPIRATION_DATE);

        // Get all the invoiceList where cardExpirationDate not equals to UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldBeFound("cardExpirationDate.notEquals=" + UPDATED_CARD_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate in DEFAULT_CARD_EXPIRATION_DATE or UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldBeFound("cardExpirationDate.in=" + DEFAULT_CARD_EXPIRATION_DATE + "," + UPDATED_CARD_EXPIRATION_DATE);

        // Get all the invoiceList where cardExpirationDate equals to UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldNotBeFound("cardExpirationDate.in=" + UPDATED_CARD_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate is not null
        defaultInvoiceShouldBeFound("cardExpirationDate.specified=true");

        // Get all the invoiceList where cardExpirationDate is null
        defaultInvoiceShouldNotBeFound("cardExpirationDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate contains DEFAULT_CARD_EXPIRATION_DATE
        defaultInvoiceShouldBeFound("cardExpirationDate.contains=" + DEFAULT_CARD_EXPIRATION_DATE);

        // Get all the invoiceList where cardExpirationDate contains UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldNotBeFound("cardExpirationDate.contains=" + UPDATED_CARD_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCardExpirationDateNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where cardExpirationDate does not contain DEFAULT_CARD_EXPIRATION_DATE
        defaultInvoiceShouldNotBeFound("cardExpirationDate.doesNotContain=" + DEFAULT_CARD_EXPIRATION_DATE);

        // Get all the invoiceList where cardExpirationDate does not contain UPDATED_CARD_EXPIRATION_DATE
        defaultInvoiceShouldBeFound("cardExpirationDate.doesNotContain=" + UPDATED_CARD_EXPIRATION_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber equals to DEFAULT_VERIFICATION_NUMBER
        defaultInvoiceShouldBeFound("verificationNumber.equals=" + DEFAULT_VERIFICATION_NUMBER);

        // Get all the invoiceList where verificationNumber equals to UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldNotBeFound("verificationNumber.equals=" + UPDATED_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber not equals to DEFAULT_VERIFICATION_NUMBER
        defaultInvoiceShouldNotBeFound("verificationNumber.notEquals=" + DEFAULT_VERIFICATION_NUMBER);

        // Get all the invoiceList where verificationNumber not equals to UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldBeFound("verificationNumber.notEquals=" + UPDATED_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber in DEFAULT_VERIFICATION_NUMBER or UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldBeFound("verificationNumber.in=" + DEFAULT_VERIFICATION_NUMBER + "," + UPDATED_VERIFICATION_NUMBER);

        // Get all the invoiceList where verificationNumber equals to UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldNotBeFound("verificationNumber.in=" + UPDATED_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber is not null
        defaultInvoiceShouldBeFound("verificationNumber.specified=true");

        // Get all the invoiceList where verificationNumber is null
        defaultInvoiceShouldNotBeFound("verificationNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber contains DEFAULT_VERIFICATION_NUMBER
        defaultInvoiceShouldBeFound("verificationNumber.contains=" + DEFAULT_VERIFICATION_NUMBER);

        // Get all the invoiceList where verificationNumber contains UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldNotBeFound("verificationNumber.contains=" + UPDATED_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVerificationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where verificationNumber does not contain DEFAULT_VERIFICATION_NUMBER
        defaultInvoiceShouldNotBeFound("verificationNumber.doesNotContain=" + DEFAULT_VERIFICATION_NUMBER);

        // Get all the invoiceList where verificationNumber does not contain UPDATED_VERIFICATION_NUMBER
        defaultInvoiceShouldBeFound("verificationNumber.doesNotContain=" + UPDATED_VERIFICATION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber equals to DEFAULT_TRANSACTION_NUMBER
        defaultInvoiceShouldBeFound("transactionNumber.equals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the invoiceList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldNotBeFound("transactionNumber.equals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber not equals to DEFAULT_TRANSACTION_NUMBER
        defaultInvoiceShouldNotBeFound("transactionNumber.notEquals=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the invoiceList where transactionNumber not equals to UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldBeFound("transactionNumber.notEquals=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber in DEFAULT_TRANSACTION_NUMBER or UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldBeFound("transactionNumber.in=" + DEFAULT_TRANSACTION_NUMBER + "," + UPDATED_TRANSACTION_NUMBER);

        // Get all the invoiceList where transactionNumber equals to UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldNotBeFound("transactionNumber.in=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber is not null
        defaultInvoiceShouldBeFound("transactionNumber.specified=true");

        // Get all the invoiceList where transactionNumber is null
        defaultInvoiceShouldNotBeFound("transactionNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber contains DEFAULT_TRANSACTION_NUMBER
        defaultInvoiceShouldBeFound("transactionNumber.contains=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the invoiceList where transactionNumber contains UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldNotBeFound("transactionNumber.contains=" + UPDATED_TRANSACTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTransactionNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where transactionNumber does not contain DEFAULT_TRANSACTION_NUMBER
        defaultInvoiceShouldNotBeFound("transactionNumber.doesNotContain=" + DEFAULT_TRANSACTION_NUMBER);

        // Get all the invoiceList where transactionNumber does not contain UPDATED_TRANSACTION_NUMBER
        defaultInvoiceShouldBeFound("transactionNumber.doesNotContain=" + UPDATED_TRANSACTION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber not equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.notEquals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber not equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.notEquals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber is not null
        defaultInvoiceShouldBeFound("invoiceNumber.specified=true");

        // Get all the invoiceList where invoiceNumber is null
        defaultInvoiceShouldNotBeFound("invoiceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber contains DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.contains=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber contains UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.contains=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceNumber does not contain DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoiceNumber.doesNotContain=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoiceNumber does not contain UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoiceNumber.doesNotContain=" + UPDATED_INVOICE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByUnitNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName equals to DEFAULT_UNIT_NAME
        defaultInvoiceShouldBeFound("unitName.equals=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceList where unitName equals to UPDATED_UNIT_NAME
        defaultInvoiceShouldNotBeFound("unitName.equals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUnitNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName not equals to DEFAULT_UNIT_NAME
        defaultInvoiceShouldNotBeFound("unitName.notEquals=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceList where unitName not equals to UPDATED_UNIT_NAME
        defaultInvoiceShouldBeFound("unitName.notEquals=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUnitNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName in DEFAULT_UNIT_NAME or UPDATED_UNIT_NAME
        defaultInvoiceShouldBeFound("unitName.in=" + DEFAULT_UNIT_NAME + "," + UPDATED_UNIT_NAME);

        // Get all the invoiceList where unitName equals to UPDATED_UNIT_NAME
        defaultInvoiceShouldNotBeFound("unitName.in=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUnitNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName is not null
        defaultInvoiceShouldBeFound("unitName.specified=true");

        // Get all the invoiceList where unitName is null
        defaultInvoiceShouldNotBeFound("unitName.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByUnitNameContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName contains DEFAULT_UNIT_NAME
        defaultInvoiceShouldBeFound("unitName.contains=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceList where unitName contains UPDATED_UNIT_NAME
        defaultInvoiceShouldNotBeFound("unitName.contains=" + UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByUnitNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where unitName does not contain DEFAULT_UNIT_NAME
        defaultInvoiceShouldNotBeFound("unitName.doesNotContain=" + DEFAULT_UNIT_NAME);

        // Get all the invoiceList where unitName does not contain UPDATED_UNIT_NAME
        defaultInvoiceShouldBeFound("unitName.doesNotContain=" + UPDATED_UNIT_NAME);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName equals to DEFAULT_CUSTOMER_NAME
        defaultInvoiceShouldBeFound("customerName.equals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the invoiceList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldNotBeFound("customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName not equals to DEFAULT_CUSTOMER_NAME
        defaultInvoiceShouldNotBeFound("customerName.notEquals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the invoiceList where customerName not equals to UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldBeFound("customerName.notEquals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName in DEFAULT_CUSTOMER_NAME or UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldBeFound("customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME);

        // Get all the invoiceList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldNotBeFound("customerName.in=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName is not null
        defaultInvoiceShouldBeFound("customerName.specified=true");

        // Get all the invoiceList where customerName is null
        defaultInvoiceShouldNotBeFound("customerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName contains DEFAULT_CUSTOMER_NAME
        defaultInvoiceShouldBeFound("customerName.contains=" + DEFAULT_CUSTOMER_NAME);

        // Get all the invoiceList where customerName contains UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldNotBeFound("customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where customerName does not contain DEFAULT_CUSTOMER_NAME
        defaultInvoiceShouldNotBeFound("customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME);

        // Get all the invoiceList where customerName does not contain UPDATED_CUSTOMER_NAME
        defaultInvoiceShouldBeFound("customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice equals to DEFAULT_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldBeFound("amountOfTheInvoice.equals=" + DEFAULT_AMOUNT_OF_THE_INVOICE);

        // Get all the invoiceList where amountOfTheInvoice equals to UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.equals=" + UPDATED_AMOUNT_OF_THE_INVOICE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice not equals to DEFAULT_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.notEquals=" + DEFAULT_AMOUNT_OF_THE_INVOICE);

        // Get all the invoiceList where amountOfTheInvoice not equals to UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldBeFound("amountOfTheInvoice.notEquals=" + UPDATED_AMOUNT_OF_THE_INVOICE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice in DEFAULT_AMOUNT_OF_THE_INVOICE or UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldBeFound("amountOfTheInvoice.in=" + DEFAULT_AMOUNT_OF_THE_INVOICE + "," + UPDATED_AMOUNT_OF_THE_INVOICE);

        // Get all the invoiceList where amountOfTheInvoice equals to UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.in=" + UPDATED_AMOUNT_OF_THE_INVOICE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice is not null
        defaultInvoiceShouldBeFound("amountOfTheInvoice.specified=true");

        // Get all the invoiceList where amountOfTheInvoice is null
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice contains DEFAULT_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldBeFound("amountOfTheInvoice.contains=" + DEFAULT_AMOUNT_OF_THE_INVOICE);

        // Get all the invoiceList where amountOfTheInvoice contains UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.contains=" + UPDATED_AMOUNT_OF_THE_INVOICE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountOfTheInvoiceNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountOfTheInvoice does not contain DEFAULT_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldNotBeFound("amountOfTheInvoice.doesNotContain=" + DEFAULT_AMOUNT_OF_THE_INVOICE);

        // Get all the invoiceList where amountOfTheInvoice does not contain UPDATED_AMOUNT_OF_THE_INVOICE
        defaultInvoiceShouldBeFound("amountOfTheInvoice.doesNotContain=" + UPDATED_AMOUNT_OF_THE_INVOICE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByAmountPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid equals to DEFAULT_AMOUNT_PAID
        defaultInvoiceShouldBeFound("amountPaid.equals=" + DEFAULT_AMOUNT_PAID);

        // Get all the invoiceList where amountPaid equals to UPDATED_AMOUNT_PAID
        defaultInvoiceShouldNotBeFound("amountPaid.equals=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountPaidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid not equals to DEFAULT_AMOUNT_PAID
        defaultInvoiceShouldNotBeFound("amountPaid.notEquals=" + DEFAULT_AMOUNT_PAID);

        // Get all the invoiceList where amountPaid not equals to UPDATED_AMOUNT_PAID
        defaultInvoiceShouldBeFound("amountPaid.notEquals=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountPaidIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid in DEFAULT_AMOUNT_PAID or UPDATED_AMOUNT_PAID
        defaultInvoiceShouldBeFound("amountPaid.in=" + DEFAULT_AMOUNT_PAID + "," + UPDATED_AMOUNT_PAID);

        // Get all the invoiceList where amountPaid equals to UPDATED_AMOUNT_PAID
        defaultInvoiceShouldNotBeFound("amountPaid.in=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid is not null
        defaultInvoiceShouldBeFound("amountPaid.specified=true");

        // Get all the invoiceList where amountPaid is null
        defaultInvoiceShouldNotBeFound("amountPaid.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByAmountPaidContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid contains DEFAULT_AMOUNT_PAID
        defaultInvoiceShouldBeFound("amountPaid.contains=" + DEFAULT_AMOUNT_PAID);

        // Get all the invoiceList where amountPaid contains UPDATED_AMOUNT_PAID
        defaultInvoiceShouldNotBeFound("amountPaid.contains=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllInvoicesByAmountPaidNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where amountPaid does not contain DEFAULT_AMOUNT_PAID
        defaultInvoiceShouldNotBeFound("amountPaid.doesNotContain=" + DEFAULT_AMOUNT_PAID);

        // Get all the invoiceList where amountPaid does not contain UPDATED_AMOUNT_PAID
        defaultInvoiceShouldBeFound("amountPaid.doesNotContain=" + UPDATED_AMOUNT_PAID);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCardIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Card card = CardResourceIT.createEntity(em);
        em.persist(card);
        em.flush();
        invoice.setCard(card);
        invoiceRepository.saveAndFlush(invoice);
        Long cardId = card.getId();

        // Get all the invoiceList where card equals to cardId
        defaultInvoiceShouldBeFound("cardId.equals=" + cardId);

        // Get all the invoiceList where card equals to cardId + 1
        defaultInvoiceShouldNotBeFound("cardId.equals=" + (cardId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        invoice.setCustomer(customer);
        invoiceRepository.saveAndFlush(invoice);
        Long customerId = customer.getId();

        // Get all the invoiceList where customer equals to customerId
        defaultInvoiceShouldBeFound("customerId.equals=" + customerId);

        // Get all the invoiceList where customer equals to customerId + 1
        defaultInvoiceShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceShouldBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].uniqueNumberCustomer").value(hasItem(DEFAULT_UNIQUE_NUMBER_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].nameOfTheCardOwner").value(hasItem(DEFAULT_NAME_OF_THE_CARD_OWNER)))
            .andExpect(jsonPath("$.[*].cardExpirationDate").value(hasItem(DEFAULT_CARD_EXPIRATION_DATE)))
            .andExpect(jsonPath("$.[*].verificationNumber").value(hasItem(DEFAULT_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionNumber").value(hasItem(DEFAULT_TRANSACTION_NUMBER)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].amountOfTheInvoice").value(hasItem(DEFAULT_AMOUNT_OF_THE_INVOICE)))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID)));

        // Check, that the count call also returns 1
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceShouldNotBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .date(UPDATED_DATE)
            .uniqueNumberCustomer(UPDATED_UNIQUE_NUMBER_CUSTOMER)
            .nameOfTheCardOwner(UPDATED_NAME_OF_THE_CARD_OWNER)
            .cardExpirationDate(UPDATED_CARD_EXPIRATION_DATE)
            .verificationNumber(UPDATED_VERIFICATION_NUMBER)
            .transactionNumber(UPDATED_TRANSACTION_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .unitName(UPDATED_UNIT_NAME)
            .customerName(UPDATED_CUSTOMER_NAME)
            .amountOfTheInvoice(UPDATED_AMOUNT_OF_THE_INVOICE)
            .amountPaid(UPDATED_AMOUNT_PAID);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInvoice.getUniqueNumberCustomer()).isEqualTo(UPDATED_UNIQUE_NUMBER_CUSTOMER);
        assertThat(testInvoice.getNameOfTheCardOwner()).isEqualTo(UPDATED_NAME_OF_THE_CARD_OWNER);
        assertThat(testInvoice.getCardExpirationDate()).isEqualTo(UPDATED_CARD_EXPIRATION_DATE);
        assertThat(testInvoice.getVerificationNumber()).isEqualTo(UPDATED_VERIFICATION_NUMBER);
        assertThat(testInvoice.getTransactionNumber()).isEqualTo(UPDATED_TRANSACTION_NUMBER);
        assertThat(testInvoice.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoice.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testInvoice.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testInvoice.getAmountOfTheInvoice()).isEqualTo(UPDATED_AMOUNT_OF_THE_INVOICE);
        assertThat(testInvoice.getAmountPaid()).isEqualTo(UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
