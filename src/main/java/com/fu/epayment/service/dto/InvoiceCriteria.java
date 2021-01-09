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
 * Criteria class for the {@link com.fu.epayment.domain.Invoice} entity. This class is used
 * in {@link com.fu.epayment.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoiceNumber;

    private InstantFilter date;

    private StringFilter verificationNumber;

    private StringFilter unitName;

    private DoubleFilter totalAmount;

    private DoubleFilter amountPaid;

    private BooleanFilter paid;

    private LongFilter transactionId;

    private LongFilter itemsId;

    private LongFilter customerId;

    public InvoiceCriteria() {
    }

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.verificationNumber = other.verificationNumber == null ? null : other.verificationNumber.copy();
        this.unitName = other.unitName == null ? null : other.unitName.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.amountPaid = other.amountPaid == null ? null : other.amountPaid.copy();
        this.paid = other.paid == null ? null : other.paid.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.itemsId = other.itemsId == null ? null : other.itemsId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getVerificationNumber() {
        return verificationNumber;
    }

    public void setVerificationNumber(StringFilter verificationNumber) {
        this.verificationNumber = verificationNumber;
    }

    public StringFilter getUnitName() {
        return unitName;
    }

    public void setUnitName(StringFilter unitName) {
        this.unitName = unitName;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DoubleFilter getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(DoubleFilter amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BooleanFilter getPaid() {
        return paid;
    }

    public void setPaid(BooleanFilter paid) {
        this.paid = paid;
    }

    public LongFilter getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(LongFilter transactionId) {
        this.transactionId = transactionId;
    }

    public LongFilter getItemsId() {
        return itemsId;
    }

    public void setItemsId(LongFilter itemsId) {
        this.itemsId = itemsId;
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
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(date, that.date) &&
            Objects.equals(verificationNumber, that.verificationNumber) &&
            Objects.equals(unitName, that.unitName) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(amountPaid, that.amountPaid) &&
            Objects.equals(paid, that.paid) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(itemsId, that.itemsId) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceNumber,
        date,
        verificationNumber,
        unitName,
        totalAmount,
        amountPaid,
        paid,
        transactionId,
        itemsId,
        customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (verificationNumber != null ? "verificationNumber=" + verificationNumber + ", " : "") +
                (unitName != null ? "unitName=" + unitName + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (amountPaid != null ? "amountPaid=" + amountPaid + ", " : "") +
                (paid != null ? "paid=" + paid + ", " : "") +
                (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
                (itemsId != null ? "itemsId=" + itemsId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
