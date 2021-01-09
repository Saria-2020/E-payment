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
 * Criteria class for the {@link com.fu.epayment.domain.ActivityInformation} entity. This class is used
 * in {@link com.fu.epayment.web.rest.ActivityInformationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activity-informations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivityInformationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter sector;

    private StringFilter typeOfActivity;

    private StringFilter propertyType;

    private StringFilter areaClass;

    private StringFilter architectureType;

    private StringFilter numberOfFloors;

    private StringFilter features;

    private LongFilter customerId;

    private LongFilter categoryId;

    public ActivityInformationCriteria() {
    }

    public ActivityInformationCriteria(ActivityInformationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.sector = other.sector == null ? null : other.sector.copy();
        this.typeOfActivity = other.typeOfActivity == null ? null : other.typeOfActivity.copy();
        this.propertyType = other.propertyType == null ? null : other.propertyType.copy();
        this.areaClass = other.areaClass == null ? null : other.areaClass.copy();
        this.architectureType = other.architectureType == null ? null : other.architectureType.copy();
        this.numberOfFloors = other.numberOfFloors == null ? null : other.numberOfFloors.copy();
        this.features = other.features == null ? null : other.features.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public ActivityInformationCriteria copy() {
        return new ActivityInformationCriteria(this);
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

    public StringFilter getSector() {
        return sector;
    }

    public void setSector(StringFilter sector) {
        this.sector = sector;
    }

    public StringFilter getTypeOfActivity() {
        return typeOfActivity;
    }

    public void setTypeOfActivity(StringFilter typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public StringFilter getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(StringFilter propertyType) {
        this.propertyType = propertyType;
    }

    public StringFilter getAreaClass() {
        return areaClass;
    }

    public void setAreaClass(StringFilter areaClass) {
        this.areaClass = areaClass;
    }

    public StringFilter getArchitectureType() {
        return architectureType;
    }

    public void setArchitectureType(StringFilter architectureType) {
        this.architectureType = architectureType;
    }

    public StringFilter getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(StringFilter numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public StringFilter getFeatures() {
        return features;
    }

    public void setFeatures(StringFilter features) {
        this.features = features;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActivityInformationCriteria that = (ActivityInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(sector, that.sector) &&
            Objects.equals(typeOfActivity, that.typeOfActivity) &&
            Objects.equals(propertyType, that.propertyType) &&
            Objects.equals(areaClass, that.areaClass) &&
            Objects.equals(architectureType, that.architectureType) &&
            Objects.equals(numberOfFloors, that.numberOfFloors) &&
            Objects.equals(features, that.features) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        sector,
        typeOfActivity,
        propertyType,
        areaClass,
        architectureType,
        numberOfFloors,
        features,
        customerId,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (sector != null ? "sector=" + sector + ", " : "") +
                (typeOfActivity != null ? "typeOfActivity=" + typeOfActivity + ", " : "") +
                (propertyType != null ? "propertyType=" + propertyType + ", " : "") +
                (areaClass != null ? "areaClass=" + areaClass + ", " : "") +
                (architectureType != null ? "architectureType=" + architectureType + ", " : "") +
                (numberOfFloors != null ? "numberOfFloors=" + numberOfFloors + ", " : "") +
                (features != null ? "features=" + features + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
