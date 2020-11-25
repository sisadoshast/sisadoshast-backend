package com.sisadoshast.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.sisadoshast.domain.enumeration.PeriodStatus;

/**
 * A Period.
 */
@Entity
@Table(name = "period")
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private PeriodStatus state;

    @OneToMany(mappedBy = "period")
    private Set<FinalResult> finalResults = new HashSet<>();

    @OneToMany(mappedBy = "period")
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public Period start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public Period end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public PeriodStatus getState() {
        return state;
    }

    public Period state(PeriodStatus state) {
        this.state = state;
        return this;
    }

    public void setState(PeriodStatus state) {
        this.state = state;
    }

    public Set<FinalResult> getFinalResults() {
        return finalResults;
    }

    public Period finalResults(Set<FinalResult> finalResults) {
        this.finalResults = finalResults;
        return this;
    }

    public Period addFinalResult(FinalResult finalResult) {
        this.finalResults.add(finalResult);
        finalResult.setPeriod(this);
        return this;
    }

    public Period removeFinalResult(FinalResult finalResult) {
        this.finalResults.remove(finalResult);
        finalResult.setPeriod(null);
        return this;
    }

    public void setFinalResults(Set<FinalResult> finalResults) {
        this.finalResults = finalResults;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Period questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Period addQuestions(Question question) {
        this.questions.add(question);
        question.setPeriod(this);
        return this;
    }

    public Period removeQuestions(Question question) {
        this.questions.remove(question);
        question.setPeriod(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Period)) {
            return false;
        }
        return id != null && id.equals(((Period) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
