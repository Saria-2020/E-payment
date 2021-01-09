package com.fu.epayment.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ActivityInformation> activityInformations = new HashSet<>();

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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ActivityInformation> getActivityInformations() {
        return activityInformations;
    }

    public Category activityInformations(Set<ActivityInformation> activityInformations) {
        this.activityInformations = activityInformations;
        return this;
    }

    public Category addActivityInformation(ActivityInformation activityInformation) {
        this.activityInformations.add(activityInformation);
        activityInformation.setCategory(this);
        return this;
    }

    public Category removeActivityInformation(ActivityInformation activityInformation) {
        this.activityInformations.remove(activityInformation);
        activityInformation.setCategory(null);
        return this;
    }

    public void setActivityInformations(Set<ActivityInformation> activityInformations) {
        this.activityInformations = activityInformations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
