package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date_time")
    private Instant dateTime;

    @Column(name = "payment_info")
    private String paymentInfo;

    @OneToOne(mappedBy = "transaction")
    @JsonIgnore
    private Invoice invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Transaction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public Transaction uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public Transaction dateTime(Instant dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public Transaction paymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
        return this;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Transaction invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", amount=" + getAmount() +
            ", dateTime='" + getDateTime() + "'" +
            ", paymentInfo='" + getPaymentInfo() + "'" +
            "}";
    }
}
