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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.fu.epayment.domain.Transaction} entity. This class is used
 * in {@link com.fu.epayment.web.rest.TransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter uuid;

    private DoubleFilter amount;

    private InstantFilter dateTime;

    private StringFilter paymentInfo;

    private LongFilter invoiceId;

    public TransactionCriteria() {
    }

    public TransactionCriteria(TransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.dateTime = other.dateTime == null ? null : other.dateTime.copy();
        this.paymentInfo = other.paymentInfo == null ? null : other.paymentInfo.copy();
        this.invoiceId = other.invoiceId == null ? null : other.invoiceId.copy();
    }

    @Override
    public TransactionCriteria copy() {
        return new TransactionCriteria(this);
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

    public StringFilter getUuid() {
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public InstantFilter getDateTime() {
        return dateTime;
    }

    public void setDateTime(InstantFilter dateTime) {
        this.dateTime = dateTime;
    }

    public StringFilter getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(StringFilter paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionCriteria that = (TransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(dateTime, that.dateTime) &&
            Objects.equals(paymentInfo, that.paymentInfo) &&
            Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        uuid,
        amount,
        dateTime,
        paymentInfo,
        invoiceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (dateTime != null ? "dateTime=" + dateTime + ", " : "") +
                (paymentInfo != null ? "paymentInfo=" + paymentInfo + ", " : "") +
                (invoiceId != null ? "invoiceId=" + invoiceId + ", " : "") +
            "}";
    }

}
