package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "date")
    private Instant date;

    @Column(name = "verification_number")
    private String verificationNumber;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "paid")
    private Boolean paid;

    @OneToOne
    @JoinColumn(unique = true)
    private Transaction transaction;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InvoiceItem> items = new HashSet<>();

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

    public Instant getDate() {
        return date;
    }

    public Invoice date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Invoice totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public Invoice amountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Invoice paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Invoice transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public Invoice items(Set<InvoiceItem> invoiceItems) {
        this.items = invoiceItems;
        return this;
    }

    public Invoice addItems(InvoiceItem invoiceItem) {
        this.items.add(invoiceItem);
        invoiceItem.setInvoice(this);
        return this;
    }

    public Invoice removeItems(InvoiceItem invoiceItem) {
        this.items.remove(invoiceItem);
        invoiceItem.setInvoice(null);
        return this;
    }

    public void setItems(Set<InvoiceItem> invoiceItems) {
        this.items = invoiceItems;
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
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", date='" + getDate() + "'" +
            ", verificationNumber='" + getVerificationNumber() + "'" +
            ", unitName='" + getUnitName() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", amountPaid=" + getAmountPaid() +
            ", paid='" + isPaid() + "'" +
            "}";
    }
}
