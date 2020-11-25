package com.sisadoshast.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sisadoshast.web.rest.TestUtil;

public class QuestionGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroup.class);
        QuestionGroup questionGroup1 = new QuestionGroup();
        questionGroup1.setId(1L);
        QuestionGroup questionGroup2 = new QuestionGroup();
        questionGroup2.setId(questionGroup1.getId());
        assertThat(questionGroup1).isEqualTo(questionGroup2);
        questionGroup2.setId(2L);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
        questionGroup1.setId(null);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
    }
}
