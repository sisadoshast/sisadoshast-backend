package com.sisadoshast.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sisadoshast.web.rest.TestUtil;

public class ResulteQuestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResulteQuestion.class);
        ResulteQuestion resulteQuestion1 = new ResulteQuestion();
        resulteQuestion1.setId(1L);
        ResulteQuestion resulteQuestion2 = new ResulteQuestion();
        resulteQuestion2.setId(resulteQuestion1.getId());
        assertThat(resulteQuestion1).isEqualTo(resulteQuestion2);
        resulteQuestion2.setId(2L);
        assertThat(resulteQuestion1).isNotEqualTo(resulteQuestion2);
        resulteQuestion1.setId(null);
        assertThat(resulteQuestion1).isNotEqualTo(resulteQuestion2);
    }
}
