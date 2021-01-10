package com.fu.epayment.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "national_id")
    private String nationalId;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ActivityInformation> activityInformations = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GeographicalData> geographicalData = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PaymentInfo> accounts = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Customer nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Customer invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Customer addInvoices(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
        return this;
    }

    public Customer removeInvoices(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCustomer(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<ActivityInformation> getActivityInformations() {
        return activityInformations;
    }

    public Customer activityInformations(Set<ActivityInformation> activityInformations) {
        this.activityInformations = activityInformations;
        return this;
    }

    public Customer addActivityInformation(ActivityInformation activityInformation) {
        this.activityInformations.add(activityInformation);
        activityInformation.setCustomer(this);
        return this;
    }

    public Customer removeActivityInformation(ActivityInformation activityInformation) {
        this.activityInformations.remove(activityInformation);
        activityInformation.setCustomer(null);
        return this;
    }

    public void setActivityInformations(Set<ActivityInformation> activityInformations) {
        this.activityInformations = activityInformations;
    }

    public Set<GeographicalData> getGeographicalData() {
        return geographicalData;
    }

    public Customer geographicalData(Set<GeographicalData> geographicalData) {
        this.geographicalData = geographicalData;
        return this;
    }

    public Customer addGeographicalData(GeographicalData geographicalData) {
        this.geographicalData.add(geographicalData);
        geographicalData.setCustomer(this);
        return this;
    }

    public Customer removeGeographicalData(GeographicalData geographicalData) {
        this.geographicalData.remove(geographicalData);
        geographicalData.setCustomer(null);
        return this;
    }

    public void setGeographicalData(Set<GeographicalData> geographicalData) {
        this.geographicalData = geographicalData;
    }

    public Set<PaymentInfo> getAccounts() {
        return accounts;
    }

    public Customer accounts(Set<PaymentInfo> paymentInfos) {
        this.accounts = paymentInfos;
        return this;
    }

    public Customer addAccounts(PaymentInfo paymentInfo) {
        this.accounts.add(paymentInfo);
        paymentInfo.setCustomer(this);
        return this;
    }

    public Customer removeAccounts(PaymentInfo paymentInfo) {
        this.accounts.remove(paymentInfo);
        paymentInfo.setCustomer(null);
        return this;
    }

    public void setAccounts(Set<PaymentInfo> paymentInfos) {
        this.accounts = paymentInfos;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Customer transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Customer addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setCustomer(this);
        return this;
    }

    public Customer removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setCustomer(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            "}";
    }
}
