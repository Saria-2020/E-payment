package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Type(type = "uuid-char")
    @Column(name = "unique_number_customer", length = 36)
    private UUID uniqueNumberCustomer;

    @Column(name = "name_of_the_card_owner")
    private String nameOfTheCardOwner;

    @Column(name = "card_expiration_date")
    private String cardExpirationDate;

    @Column(name = "verification_number")
    private String verificationNumber;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "amount_of_the_invoice")
    private String amountOfTheInvoice;

    @Column(name = "amount_paid")
    private String amountPaid;

    @OneToOne
    @JoinColumn(unique = true)
    private Card card;

    @ManyToOne
    @JsonIgnoreProperties(value = "invoices", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Invoice date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UUID getUniqueNumberCustomer() {
        return uniqueNumberCustomer;
    }

    public Invoice uniqueNumberCustomer(UUID uniqueNumberCustomer) {
        this.uniqueNumberCustomer = uniqueNumberCustomer;
        return this;
    }

    public void setUniqueNumberCustomer(UUID uniqueNumberCustomer) {
        this.uniqueNumberCustomer = uniqueNumberCustomer;
    }

    public String getNameOfTheCardOwner() {
        return nameOfTheCardOwner;
    }

    public Invoice nameOfTheCardOwner(String nameOfTheCardOwner) {
        this.nameOfTheCardOwner = nameOfTheCardOwner;
        return this;
    }

    public void setNameOfTheCardOwner(String nameOfTheCardOwner) {
        this.nameOfTheCardOwner = nameOfTheCardOwner;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public Invoice cardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
        return this;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getVerificationNumber() {
        return verificationNumber;
    }

    public Invoice verificationNumber(String verificationNumber) {
        this.verificationNumber = verificationNumber;
        return this;
    }

    public void setVerificationNumber(String verificationNumber) {
        this.verificationNumber = verificationNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public Invoice transactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
        return this;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Invoice invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public Invoice unitName(String unitName) {
        this.unitName = unitName;
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Invoice customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAmountOfTheInvoice() {
        return amountOfTheInvoice;
    }

    public Invoice amountOfTheInvoice(String amountOfTheInvoice) {
        this.amountOfTheInvoice = amountOfTheInvoice;
        return this;
    }

    public void setAmountOfTheInvoice(String amountOfTheInvoice) {
        this.amountOfTheInvoice = amountOfTheInvoice;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public Invoice amountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Card getCard() {
        return card;
    }

    public Invoice card(Card card) {
        this.card = card;
        return this;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Invoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", uniqueNumberCustomer='" + getUniqueNumberCustomer() + "'" +
            ", nameOfTheCardOwner='" + getNameOfTheCardOwner() + "'" +
            ", cardExpirationDate='" + getCardExpirationDate() + "'" +
            ", verificationNumber='" + getVerificationNumber() + "'" +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", unitName='" + getUnitName() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", amountOfTheInvoice='" + getAmountOfTheInvoice() + "'" +
            ", amountPaid='" + getAmountPaid() + "'" +
            "}";
    }
}
