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
 * Criteria class for the {@link com.fu.epayment.domain.GeographicalData} entity. This class is used
 * in {@link com.fu.epayment.web.rest.GeographicalDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /geographical-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GeographicalDataCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter state;

    private StringFilter units;

    private StringFilter district;

    private StringFilter square;

    private StringFilter realEstateNumber;

    private StringFilter activityNumber;

    private StringFilter areaOfTheRealEstate;

    private LongFilter customerId;

    public GeographicalDataCriteria() {
    }

    public GeographicalDataCriteria(GeographicalDataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.units = other.units == null ? null : other.units.copy();
        this.district = other.district == null ? null : other.district.copy();
        this.square = other.square == null ? null : other.square.copy();
        this.realEstateNumber = other.realEstateNumber == null ? null : other.realEstateNumber.copy();
        this.activityNumber = other.activityNumber == null ? null : other.activityNumber.copy();
        this.areaOfTheRealEstate = other.areaOfTheRealEstate == null ? null : other.areaOfTheRealEstate.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public GeographicalDataCriteria copy() {
        return new GeographicalDataCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getUnits() {
        return units;
    }

    public void setUnits(StringFilter units) {
        this.units = units;
    }

    public StringFilter getDistrict() {
        return district;
    }

    public void setDistrict(StringFilter district) {
        this.district = district;
    }

    public StringFilter getSquare() {
        return square;
    }

    public void setSquare(StringFilter square) {
        this.square = square;
    }

    public StringFilter getRealEstateNumber() {
        return realEstateNumber;
    }

    public void setRealEstateNumber(StringFilter realEstateNumber) {
        this.realEstateNumber = realEstateNumber;
    }

    public StringFilter getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(StringFilter activityNumber) {
        this.activityNumber = activityNumber;
    }

    public StringFilter getAreaOfTheRealEstate() {
        return areaOfTheRealEstate;
    }

    public void setAreaOfTheRealEstate(StringFilter areaOfTheRealEstate) {
        this.areaOfTheRealEstate = areaOfTheRealEstate;
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
        final GeographicalDataCriteria that = (GeographicalDataCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(state, that.state) &&
            Objects.equals(units, that.units) &&
            Objects.equals(district, that.district) &&
            Objects.equals(square, that.square) &&
            Objects.equals(realEstateNumber, that.realEstateNumber) &&
            Objects.equals(activityNumber, that.activityNumber) &&
            Objects.equals(areaOfTheRealEstate, that.areaOfTheRealEstate) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        state,
        units,
        district,
        square,
        realEstateNumber,
        activityNumber,
        areaOfTheRealEstate,
        customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeographicalDataCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (units != null ? "units=" + units + ", " : "") +
                (district != null ? "district=" + district + ", " : "") +
                (square != null ? "square=" + square + ", " : "") +
                (realEstateNumber != null ? "realEstateNumber=" + realEstateNumber + ", " : "") +
                (activityNumber != null ? "activityNumber=" + activityNumber + ", " : "") +
                (areaOfTheRealEstate != null ? "areaOfTheRealEstate=" + areaOfTheRealEstate + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
