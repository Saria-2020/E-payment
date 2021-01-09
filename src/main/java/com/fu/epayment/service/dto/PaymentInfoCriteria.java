package com.fu.epayment.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fu.epayment.domain.PaymentInfo} entity. This class is used
 * in {@link com.fu.epayment.web.rest.PaymentInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter accountNumber;

    private StringFilter cardNumber;

    private LongFilter customerId;

    public PaymentInfoCriteria() {
    }

    public PaymentInfoCriteria(PaymentInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.cardNumber = other.cardNumber == null ? null : other.cardNumber.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public PaymentInfoCriteria copy() {
        return new PaymentInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(StringFilter cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentInfoCriteria that = (PaymentInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(cardNumber, that.cardNumber) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        accountNumber,
        cardNumber,
        customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (cardNumber != null ? "cardNumber=" + cardNumber + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
