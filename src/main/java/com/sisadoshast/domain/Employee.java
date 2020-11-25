package com.sisadoshast.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "employeeVoter")
    private Set<ResulteQuestion> voters = new HashSet<>();

    @OneToMany(mappedBy = "employeeOwner")
    private Set<ResulteQuestion> owners = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<FinalResult> results = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "employee_group",
               joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    private Set<Department> departments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public Employee personalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public Employee weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Set<ResulteQuestion> getVoters() {
        return voters;
    }

    public Employee voters(Set<ResulteQuestion> resulteQuestions) {
        this.voters = resulteQuestions;
        return this;
    }

    public Employee addVoter(ResulteQuestion resulteQuestion) {
        this.voters.add(resulteQuestion);
        resulteQuestion.setEmployeeVoter(this);
        return this;
    }

    public Employee removeVoter(ResulteQuestion resulteQuestion) {
        this.voters.remove(resulteQuestion);
        resulteQuestion.setEmployeeVoter(null);
        return this;
    }

    public void setVoters(Set<ResulteQuestion> resulteQuestions) {
        this.voters = resulteQuestions;
    }

    public Set<ResulteQuestion> getOwners() {
        return owners;
    }

    public Employee owners(Set<ResulteQuestion> resulteQuestions) {
        this.owners = resulteQuestions;
        return this;
    }

    public Employee addOwner(ResulteQuestion resulteQuestion) {
        this.owners.add(resulteQuestion);
        resulteQuestion.setEmployeeOwner(this);
        return this;
    }

    public Employee removeOwner(ResulteQuestion resulteQuestion) {
        this.owners.remove(resulteQuestion);
        resulteQuestion.setEmployeeOwner(null);
        return this;
    }

    public void setOwners(Set<ResulteQuestion> resulteQuestions) {
        this.owners = resulteQuestions;
    }

    public Set<FinalResult> getResults() {
        return results;
    }

    public Employee results(Set<FinalResult> finalResults) {
        this.results = finalResults;
        return this;
    }

    public Employee addResult(FinalResult finalResult) {
        this.results.add(finalResult);
        finalResult.setEmployee(this);
        return this;
    }

    public Employee removeResult(FinalResult finalResult) {
        this.results.remove(finalResult);
        finalResult.setEmployee(null);
        return this;
    }

    public void setResults(Set<FinalResult> finalResults) {
        this.results = finalResults;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Employee groups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Employee addGroup(Group group) {
        this.groups.add(group);
        group.getGroups().add(this);
        return this;
    }

    public Employee removeGroup(Group group) {
        this.groups.remove(group);
        group.getGroups().remove(this);
        return this;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Employee departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Employee addDepartment(Department department) {
        this.departments.add(department);
        department.getEmployees().add(this);
        return this;
    }

    public Employee removeDepartment(Department department) {
        this.departments.remove(department);
        department.getEmployees().remove(this);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", personalNumber='" + getPersonalNumber() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
