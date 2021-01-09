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
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

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

    private ZonedDateTimeFilter date;

    private UUIDFilter uniqueNumberCustomer;

    private StringFilter nameOfTheCardOwner;

    private StringFilter cardExpirationDate;

    private StringFilter verificationNumber;

    private StringFilter transactionNumber;

    private StringFilter invoiceNumber;

    private StringFilter unitName;

    private StringFilter customerName;

    private StringFilter amountOfTheInvoice;

    private StringFilter amountPaid;

    private LongFilter cardId;

    private LongFilter customerId;

    public InvoiceCriteria() {
    }

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.uniqueNumberCustomer = other.uniqueNumberCustomer == null ? null : other.uniqueNumberCustomer.copy();
        this.nameOfTheCardOwner = other.nameOfTheCardOwner == null ? null : other.nameOfTheCardOwner.copy();
        this.cardExpirationDate = other.cardExpirationDate == null ? null : other.cardExpirationDate.copy();
        this.verificationNumber = other.verificationNumber == null ? null : other.verificationNumber.copy();
        this.transactionNumber = other.transactionNumber == null ? null : other.transactionNumber.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.unitName = other.unitName == null ? null : other.unitName.copy();
        this.customerName = other.customerName == null ? null : other.customerName.copy();
        this.amountOfTheInvoice = other.amountOfTheInvoice == null ? null : other.amountOfTheInvoice.copy();
        this.amountPaid = other.amountPaid == null ? null : other.amountPaid.copy();
        this.cardId = other.cardId == null ? null : other.cardId.copy();
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

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public UUIDFilter getUniqueNumberCustomer() {
        return uniqueNumberCustomer;
    }

    public void setUniqueNumberCustomer(UUIDFilter uniqueNumberCustomer) {
        this.uniqueNumberCustomer = uniqueNumberCustomer;
    }

    public StringFilter getNameOfTheCardOwner() {
        return nameOfTheCardOwner;
    }

    public void setNameOfTheCardOwner(StringFilter nameOfTheCardOwner) {
        this.nameOfTheCardOwner = nameOfTheCardOwner;
    }

    public StringFilter getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(StringFilter cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public StringFilter getVerificationNumber() {
        return verificationNumber;
    }

    public void setVerificationNumber(StringFilter verificationNumber) {
        this.verificationNumber = verificationNumber;
    }

    public StringFilter getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(StringFilter transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public StringFilter getUnitName() {
        return unitName;
    }

    public void setUnitName(StringFilter unitName) {
        this.unitName = unitName;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getAmountOfTheInvoice() {
        return amountOfTheInvoice;
    }

    public void setAmountOfTheInvoice(StringFilter amountOfTheInvoice) {
        this.amountOfTheInvoice = amountOfTheInvoice;
    }

    public StringFilter getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(StringFilter amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LongFilter getCardId() {
        return cardId;
    }

    public void setCardId(LongFilter cardId) {
        this.cardId = cardId;
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
            Objects.equals(date, that.date) &&
            Objects.equals(uniqueNumberCustomer, that.uniqueNumberCustomer) &&
            Objects.equals(nameOfTheCardOwner, that.nameOfTheCardOwner) &&
            Objects.equals(cardExpirationDate, that.cardExpirationDate) &&
            Objects.equals(verificationNumber, that.verificationNumber) &&
            Objects.equals(transactionNumber, that.transactionNumber) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(unitName, that.unitName) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(amountOfTheInvoice, that.amountOfTheInvoice) &&
            Objects.equals(amountPaid, that.amountPaid) &&
            Objects.equals(cardId, that.cardId) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        uniqueNumberCustomer,
        nameOfTheCardOwner,
        cardExpirationDate,
        verificationNumber,
        transactionNumber,
        invoiceNumber,
        unitName,
        customerName,
        amountOfTheInvoice,
        amountPaid,
        cardId,
        customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (uniqueNumberCustomer != null ? "uniqueNumberCustomer=" + uniqueNumberCustomer + ", " : "") +
                (nameOfTheCardOwner != null ? "nameOfTheCardOwner=" + nameOfTheCardOwner + ", " : "") +
                (cardExpirationDate != null ? "cardExpirationDate=" + cardExpirationDate + ", " : "") +
                (verificationNumber != null ? "verificationNumber=" + verificationNumber + ", " : "") +
                (transactionNumber != null ? "transactionNumber=" + transactionNumber + ", " : "") +
                (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
                (unitName != null ? "unitName=" + unitName + ", " : "") +
                (customerName != null ? "customerName=" + customerName + ", " : "") +
                (amountOfTheInvoice != null ? "amountOfTheInvoice=" + amountOfTheInvoice + ", " : "") +
                (amountPaid != null ? "amountPaid=" + amountPaid + ", " : "") +
                (cardId != null ? "cardId=" + cardId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
