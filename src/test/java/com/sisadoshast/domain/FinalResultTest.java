package com.sisadoshast.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sisadoshast.web.rest.TestUtil;

public class FinalResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinalResult.class);
        FinalResult finalResult1 = new FinalResult();
        finalResult1.setId(1L);
        FinalResult finalResult2 = new FinalResult();
        finalResult2.setId(finalResult1.getId());
        assertThat(finalResult1).isEqualTo(finalResult2);
        finalResult2.setId(2L);
        assertThat(finalResult1).isNotEqualTo(finalResult2);
        finalResult1.setId(null);
        assertThat(finalResult1).isNotEqualTo(finalResult2);
    }
}
