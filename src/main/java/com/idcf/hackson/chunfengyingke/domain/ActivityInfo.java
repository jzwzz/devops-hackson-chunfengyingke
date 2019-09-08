package com.idcf.hackson.chunfengyingke.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ActivityInfo.
 */
@Entity
@Table(name = "activity_info")
public class ActivityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "pay_status")
    private String payStatus;

    @Column(name = "add_date")
    private String addDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public ActivityInfo activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUser() {
        return user;
    }

    public ActivityInfo user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTelephone() {
        return telephone;
    }

    public ActivityInfo telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public ActivityInfo payStatus(String payStatus) {
        this.payStatus = payStatus;
        return this;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getAddDate() {
        return addDate;
    }

    public ActivityInfo addDate(String addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityInfo)) {
            return false;
        }
        return id != null && id.equals(((ActivityInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActivityInfo{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            ", user='" + getUser() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", payStatus='" + getPayStatus() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
