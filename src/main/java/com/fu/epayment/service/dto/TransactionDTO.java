package com.fu.epayment.service.dto;

import com.fu.epayment.domain.Invoice;
import com.fu.epayment.domain.PaymentInfo;

import javax.validation.constraints.NotNull;

public class TransactionDTO {
    @NotNull
    private Invoice invoice;

    @NotNull
    private PaymentInfo paymentInfo;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
