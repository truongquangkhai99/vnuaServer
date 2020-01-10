package com.luantm.vnua.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.luantm.vnua.web.rest.TestUtil;

public class MeanScoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeanScore.class);
        MeanScore meanScore1 = new MeanScore();
        meanScore1.setId(1L);
        MeanScore meanScore2 = new MeanScore();
        meanScore2.setId(meanScore1.getId());
        assertThat(meanScore1).isEqualTo(meanScore2);
        meanScore2.setId(2L);
        assertThat(meanScore1).isNotEqualTo(meanScore2);
        meanScore1.setId(null);
        assertThat(meanScore1).isNotEqualTo(meanScore2);
    }
}
