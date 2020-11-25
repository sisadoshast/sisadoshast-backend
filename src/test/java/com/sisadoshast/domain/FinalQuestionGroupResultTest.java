package com.sisadoshast.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sisadoshast.web.rest.TestUtil;

public class FinalQuestionGroupResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinalQuestionGroupResult.class);
        FinalQuestionGroupResult finalQuestionGroupResult1 = new FinalQuestionGroupResult();
        finalQuestionGroupResult1.setId(1L);
        FinalQuestionGroupResult finalQuestionGroupResult2 = new FinalQuestionGroupResult();
        finalQuestionGroupResult2.setId(finalQuestionGroupResult1.getId());
        assertThat(finalQuestionGroupResult1).isEqualTo(finalQuestionGroupResult2);
        finalQuestionGroupResult2.setId(2L);
        assertThat(finalQuestionGroupResult1).isNotEqualTo(finalQuestionGroupResult2);
        finalQuestionGroupResult1.setId(null);
        assertThat(finalQuestionGroupResult1).isNotEqualTo(finalQuestionGroupResult2);
    }
}
