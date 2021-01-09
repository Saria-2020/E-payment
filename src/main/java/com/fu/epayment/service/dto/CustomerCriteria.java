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

    private StringFilter nationalId;

    private LongFilter userId;

    private LongFilter invoicesId;

    private LongFilter activityInformationId;

    private LongFilter geographicalDataId;

    private LongFilter accountsId;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.nationalId = other.nationalId == null ? null : other.nationalId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.invoicesId = other.invoicesId == null ? null : other.invoicesId.copy();
        this.activityInformationId = other.activityInformationId == null ? null : other.activityInformationId.copy();
        this.geographicalDataId = other.geographicalDataId == null ? null : other.geographicalDataId.copy();
        this.accountsId = other.accountsId == null ? null : other.accountsId.copy();
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

    public StringFilter getNationalId() {
        return nationalId;
    }

    public void setNationalId(StringFilter nationalId) {
        this.nationalId = nationalId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getInvoicesId() {
        return invoicesId;
    }

    public void setInvoicesId(LongFilter invoicesId) {
        this.invoicesId = invoicesId;
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

    public LongFilter getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(LongFilter accountsId) {
        this.accountsId = accountsId;
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
            Objects.equals(nationalId, that.nationalId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(invoicesId, that.invoicesId) &&
            Objects.equals(activityInformationId, that.activityInformationId) &&
            Objects.equals(geographicalDataId, that.geographicalDataId) &&
            Objects.equals(accountsId, that.accountsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phoneNumber,
        nationalId,
        userId,
        invoicesId,
        activityInformationId,
        geographicalDataId,
        accountsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (nationalId != null ? "nationalId=" + nationalId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (invoicesId != null ? "invoicesId=" + invoicesId + ", " : "") +
                (activityInformationId != null ? "activityInformationId=" + activityInformationId + ", " : "") +
                (geographicalDataId != null ? "geographicalDataId=" + geographicalDataId + ", " : "") +
                (accountsId != null ? "accountsId=" + accountsId + ", " : "") +
            "}";
    }

}
