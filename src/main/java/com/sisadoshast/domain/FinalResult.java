package com.sisadoshast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FinalResult.
 */
@Entity
@Table(name = "final_result")
public class FinalResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avrage_result")
    private Integer avrageResult;

    @OneToMany(mappedBy = "finalResult")
    private Set<FinalQuestionGroupResult> questionGroupResultes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("results")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("finalResults")
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvrageResult() {
        return avrageResult;
    }

    public FinalResult avrageResult(Integer avrageResult) {
        this.avrageResult = avrageResult;
        return this;
    }

    public void setAvrageResult(Integer avrageResult) {
        this.avrageResult = avrageResult;
    }

    public Set<FinalQuestionGroupResult> getQuestionGroupResultes() {
        return questionGroupResultes;
    }

    public FinalResult questionGroupResultes(Set<FinalQuestionGroupResult> finalQuestionGroupResults) {
        this.questionGroupResultes = finalQuestionGroupResults;
        return this;
    }

    public FinalResult addQuestionGroupResulte(FinalQuestionGroupResult finalQuestionGroupResult) {
        this.questionGroupResultes.add(finalQuestionGroupResult);
        finalQuestionGroupResult.setFinalResult(this);
        return this;
    }

    public FinalResult removeQuestionGroupResulte(FinalQuestionGroupResult finalQuestionGroupResult) {
        this.questionGroupResultes.remove(finalQuestionGroupResult);
        finalQuestionGroupResult.setFinalResult(null);
        return this;
    }

    public void setQuestionGroupResultes(Set<FinalQuestionGroupResult> finalQuestionGroupResults) {
        this.questionGroupResultes = finalQuestionGroupResults;
    }

    public Employee getEmployee() {
        return employee;
    }

    public FinalResult employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Period getPeriod() {
        return period;
    }

    public FinalResult period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinalResult)) {
            return false;
        }
        return id != null && id.equals(((FinalResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FinalResult{" +
            "id=" + getId() +
            ", avrageResult=" + getAvrageResult() +
            "}";
    }
}
