package com.fu.epayment.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.epayment.web.rest.TestUtil;

public class ActivityInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityInformation.class);
        ActivityInformation activityInformation1 = new ActivityInformation();
        activityInformation1.setId(1L);
        ActivityInformation activityInformation2 = new ActivityInformation();
        activityInformation2.setId(activityInformation1.getId());
        assertThat(activityInformation1).isEqualTo(activityInformation2);
        activityInformation2.setId(2L);
        assertThat(activityInformation1).isNotEqualTo(activityInformation2);
        activityInformation1.setId(null);
        assertThat(activityInformation1).isNotEqualTo(activityInformation2);
    }
}
