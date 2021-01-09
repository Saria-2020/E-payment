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
 * Criteria class for the {@link com.fu.epayment.domain.Customer} entity. This class is used
 * in {@link com.fu.epayment.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumber;

    private LongFilter userId;

    private LongFilter invoiceId;

    private LongFilter activityInformationId;

    private LongFilter geographicalDataId;

    private LongFilter cardId;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
        this.activityInformationId = other.activityInformationId == null ? null : other.activityInformationId.copy();
        this.geographicalDataId = other.geographicalDataId == null ? null : other.geographicalDataId.copy();
        this.cardId = other.cardId == null ? null : other.cardId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getActivityInformationId() {
        return activityInformationId;
    }

    public void setActivityInformationId(LongFilter activityInformationId) {
        this.activityInformationId = activityInformationId;
    }

    public LongFilter getGeographicalDataId() {
        return geographicalDataId;
    }

    public void setGeographicalDataId(LongFilter geographicalDataId) {
        this.geographicalDataId = geographicalDataId;
    }

    public LongFilter getCardId() {
        return cardId;
    }

    public void setCardId(LongFilter cardId) {
        this.cardId = cardId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(activityInformationId, that.activityInformationId) &&
            Objects.equals(geographicalDataId, that.geographicalDataId) &&
            Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phoneNumber,
        userId,
        invoiceId,
        activityInformationId,
        geographicalDataId,
        cardId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
                (activityInformationId != null ? "activityInformationId=" + activityInformationId + ", " : "") +
                (geographicalDataId != null ? "geographicalDataId=" + geographicalDataId + ", " : "") +
                (cardId != null ? "cardId=" + cardId + ", " : "") +
            "}";
    }

}
