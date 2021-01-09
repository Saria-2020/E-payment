package com.fu.epayment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ActivityInformation.
 */
@Entity
@Table(name = "activity_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "type_of_activity")
    private String typeOfActivity;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "area_class")
    private String areaClass;

    @Column(name = "architecture_type")
    private String architectureType;

    @Column(name = "number_of_floors")
    private String numberOfFloors;

    @Column(name = "features")
    private String features;

    @Lob
    @Column(name = "description_of_the_features")
    private String descriptionOfTheFeatures;

    @ManyToOne
    @JsonIgnoreProperties(value = "activityInformations", allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "activityInformations", allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ActivityInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public ActivityInformation sector(String sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public ActivityInformation typeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
        return this;
    }

    public void setTypeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public ActivityInformation propertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getAreaClass() {
        return areaClass;
    }

    public ActivityInformation areaClass(String areaClass) {
        this.areaClass = areaClass;
        return this;
    }

    public void setAreaClass(String areaClass) {
        this.areaClass = areaClass;
    }

    public String getArchitectureType() {
        return architectureType;
    }

    public ActivityInformation architectureType(String architectureType) {
        this.architectureType = architectureType;
        return this;
    }

    public void setArchitectureType(String architectureType) {
        this.architectureType = architectureType;
    }

    public String getNumberOfFloors() {
        return numberOfFloors;
    }

    public ActivityInformation numberOfFloors(String numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        return this;
    }

    public void setNumberOfFloors(String numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public String getFeatures() {
        return features;
    }

    public ActivityInformation features(String features) {
        this.features = features;
        return this;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDescriptionOfTheFeatures() {
        return descriptionOfTheFeatures;
    }

    public ActivityInformation descriptionOfTheFeatures(String descriptionOfTheFeatures) {
        this.descriptionOfTheFeatures = descriptionOfTheFeatures;
        return this;
    }

    public void setDescriptionOfTheFeatures(String descriptionOfTheFeatures) {
        this.descriptionOfTheFeatures = descriptionOfTheFeatures;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ActivityInformation customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Category getCategory() {
        return category;
    }

    public ActivityInformation category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityInformation)) {
            return false;
        }
        return id != null && id.equals(((ActivityInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityInformation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sector='" + getSector() + "'" +
            ", typeOfActivity='" + getTypeOfActivity() + "'" +
            ", propertyType='" + getPropertyType() + "'" +
            ", areaClass='" + getAreaClass() + "'" +
            ", architectureType='" + getArchitectureType() + "'" +
            ", numberOfFloors='" + getNumberOfFloors() + "'" +
            ", features='" + getFeatures() + "'" +
            ", descriptionOfTheFeatures='" + getDescriptionOfTheFeatures() + "'" +
            "}";
    }
}
