package com.sisadoshast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight;

    @OneToMany(mappedBy = "question")
    private Set<ResulteQuestion> results = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private QuestionGroup questionGroup;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Question description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public Question weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Set<ResulteQuestion> getResults() {
        return results;
    }

    public Question results(Set<ResulteQuestion> resulteQuestions) {
        this.results = resulteQuestions;
        return this;
    }

    public Question addResult(ResulteQuestion resulteQuestion) {
        this.results.add(resulteQuestion);
        resulteQuestion.setQuestion(this);
        return this;
    }

    public Question removeResult(ResulteQuestion resulteQuestion) {
        this.results.remove(resulteQuestion);
        resulteQuestion.setQuestion(null);
        return this;
    }

    public void setResults(Set<ResulteQuestion> resulteQuestions) {
        this.results = resulteQuestions;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public Question questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Period getPeriod() {
        return period;
    }

    public Question period(Period period) {
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
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", weight=" + getWeight() +
            "}";
    }
}
