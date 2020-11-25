package com.sisadoshast.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A QuestionGroup.
 */
@Entity
@Table(name = "question_group")
public class QuestionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "questionGroup")
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "questionGroup")
    private Set<FinalQuestionGroupResult> questionGroupResults = new HashSet<>();

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

    public QuestionGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public QuestionGroup order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getWeight() {
        return weight;
    }

    public QuestionGroup weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public QuestionGroup questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuestionGroup addQuestion(Question question) {
        this.questions.add(question);
        question.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestion(Question question) {
        this.questions.remove(question);
        question.setQuestionGroup(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<FinalQuestionGroupResult> getQuestionGroupResults() {
        return questionGroupResults;
    }

    public QuestionGroup questionGroupResults(Set<FinalQuestionGroupResult> finalQuestionGroupResults) {
        this.questionGroupResults = finalQuestionGroupResults;
        return this;
    }

    public QuestionGroup addQuestionGroupResult(FinalQuestionGroupResult finalQuestionGroupResult) {
        this.questionGroupResults.add(finalQuestionGroupResult);
        finalQuestionGroupResult.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestionGroupResult(FinalQuestionGroupResult finalQuestionGroupResult) {
        this.questionGroupResults.remove(finalQuestionGroupResult);
        finalQuestionGroupResult.setQuestionGroup(null);
        return this;
    }

    public void setQuestionGroupResults(Set<FinalQuestionGroupResult> finalQuestionGroupResults) {
        this.questionGroupResults = finalQuestionGroupResults;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionGroup)) {
            return false;
        }
        return id != null && id.equals(((QuestionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", weight=" + getWeight() +
            "}";
    }
}
