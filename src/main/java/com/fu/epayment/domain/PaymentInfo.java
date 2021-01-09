package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PaymentInfo.
 */
@Entity
@Table(name = "payment_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "accounts", allowSetters = true)
    private Customer customer;

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

    public PaymentInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PaymentInfo accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public PaymentInfo cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PaymentInfo customer(Customer customer) {
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
        if (!(o instanceof PaymentInfo)) {
            return false;
        }
        return id != null && id.equals(((PaymentInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            "}";
    }
}
