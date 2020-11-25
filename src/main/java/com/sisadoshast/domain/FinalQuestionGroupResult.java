package com.sisadoshast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FinalQuestionGroupResult.
 */
@Entity
@Table(name = "final_question_group_result")
public class FinalQuestionGroupResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avrage_question_group_result")
    private Integer avrageQuestionGroupResult;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    @JsonIgnoreProperties("questionGroupResults")
    private QuestionGroup questionGroup;

    @ManyToOne
    @JsonIgnoreProperties("questionGroupResultes")
    private FinalResult finalResult;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvrageQuestionGroupResult() {
        return avrageQuestionGroupResult;
    }

    public FinalQuestionGroupResult avrageQuestionGroupResult(Integer avrageQuestionGroupResult) {
        this.avrageQuestionGroupResult = avrageQuestionGroupResult;
        return this;
    }

    public void setAvrageQuestionGroupResult(Integer avrageQuestionGroupResult) {
        this.avrageQuestionGroupResult = avrageQuestionGroupResult;
    }

    public Integer getWeight() {
        return weight;
    }

    public FinalQuestionGroupResult weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public FinalQuestionGroupResult questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }

    public FinalResult getFinalResult() {
        return finalResult;
    }

    public FinalQuestionGroupResult finalResult(FinalResult finalResult) {
        this.finalResult = finalResult;
        return this;
    }

    public void setFinalResult(FinalResult finalResult) {
        this.finalResult = finalResult;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinalQuestionGroupResult)) {
            return false;
        }
        return id != null && id.equals(((FinalQuestionGroupResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FinalQuestionGroupResult{" +
            "id=" + getId() +
            ", avrageQuestionGroupResult=" + getAvrageQuestionGroupResult() +
            ", weight=" + getWeight() +
            "}";
    }
}
