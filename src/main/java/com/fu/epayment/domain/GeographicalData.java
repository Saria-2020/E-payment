package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A GeographicalData.
 */
@Entity
@Table(name = "geographical_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GeographicalData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "units")
    private String units;

    @Column(name = "district")
    private String district;

    @Column(name = "square")
    private String square;

    @Column(name = "real_estate_number")
    private String realEstateNumber;

    @Column(name = "activity_number")
    private String activityNumber;

    @Column(name = "area_of_the_real_estate")
    private String areaOfTheRealEstate;

    @ManyToOne
    @JsonIgnoreProperties(value = "geographicalData", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public GeographicalData state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUnits() {
        return units;
    }

    public GeographicalData units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDistrict() {
        return district;
    }

    public GeographicalData district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSquare() {
        return square;
    }

    public GeographicalData square(String square) {
        this.square = square;
        return this;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getRealEstateNumber() {
        return realEstateNumber;
    }

    public GeographicalData realEstateNumber(String realEstateNumber) {
        this.realEstateNumber = realEstateNumber;
        return this;
    }

    public void setRealEstateNumber(String realEstateNumber) {
        this.realEstateNumber = realEstateNumber;
    }

    public String getActivityNumber() {
        return activityNumber;
    }

    public GeographicalData activityNumber(String activityNumber) {
        this.activityNumber = activityNumber;
        return this;
    }

    public void setActivityNumber(String activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getAreaOfTheRealEstate() {
        return areaOfTheRealEstate;
    }

    public GeographicalData areaOfTheRealEstate(String areaOfTheRealEstate) {
        this.areaOfTheRealEstate = areaOfTheRealEstate;
        return this;
    }

    public void setAreaOfTheRealEstate(String areaOfTheRealEstate) {
        this.areaOfTheRealEstate = areaOfTheRealEstate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public GeographicalData customer(Customer customer) {
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
        if (!(o instanceof GeographicalData)) {
            return false;
        }
        return id != null && id.equals(((GeographicalData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicalData{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", units='" + getUnits() + "'" +
            ", district='" + getDistrict() + "'" +
            ", square='" + getSquare() + "'" +
            ", realEstateNumber='" + getRealEstateNumber() + "'" +
            ", activityNumber='" + getActivityNumber() + "'" +
            ", areaOfTheRealEstate='" + getAreaOfTheRealEstate() + "'" +
            "}";
    }
}
