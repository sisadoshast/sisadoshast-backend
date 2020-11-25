package com.sisadoshast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ResulteQuestion.
 */
@Entity
@Table(name = "resulte_question")
public class ResulteQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_weight")
    private Integer employeeWeight;

    @Column(name = "result")
    private Integer result;

    @ManyToOne
    @JsonIgnoreProperties("voters")
    private Employee employeeVoter;

    @ManyToOne
    @JsonIgnoreProperties("owners")
    private Employee employeeOwner;

    @ManyToOne
    @JsonIgnoreProperties("results")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeWeight() {
        return employeeWeight;
    }

    public ResulteQuestion employeeWeight(Integer employeeWeight) {
        this.employeeWeight = employeeWeight;
        return this;
    }

    public void setEmployeeWeight(Integer employeeWeight) {
        this.employeeWeight = employeeWeight;
    }

    public Integer getResult() {
        return result;
    }

    public ResulteQuestion result(Integer result) {
        this.result = result;
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Employee getEmployeeVoter() {
        return employeeVoter;
    }

    public ResulteQuestion employeeVoter(Employee employee) {
        this.employeeVoter = employee;
        return this;
    }

    public void setEmployeeVoter(Employee employee) {
        this.employeeVoter = employee;
    }

    public Employee getEmployeeOwner() {
        return employeeOwner;
    }

    public ResulteQuestion employeeOwner(Employee employee) {
        this.employeeOwner = employee;
        return this;
    }

    public void setEmployeeOwner(Employee employee) {
        this.employeeOwner = employee;
    }

    public Question getQuestion() {
        return question;
    }

    public ResulteQuestion question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResulteQuestion)) {
            return false;
        }
        return id != null && id.equals(((ResulteQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResulteQuestion{" +
            "id=" + getId() +
            ", employeeWeight=" + getEmployeeWeight() +
            ", result=" + getResult() +
            "}";
    }
}
