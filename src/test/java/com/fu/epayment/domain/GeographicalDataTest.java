package com.fu.epayment.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.epayment.web.rest.TestUtil;

public class GeographicalDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicalData.class);
        GeographicalData geographicalData1 = new GeographicalData();
        geographicalData1.setId(1L);
        GeographicalData geographicalData2 = new GeographicalData();
        geographicalData2.setId(geographicalData1.getId());
        assertThat(geographicalData1).isEqualTo(geographicalData2);
        geographicalData2.setId(2L);
        assertThat(geographicalData1).isNotEqualTo(geographicalData2);
        geographicalData1.setId(null);
        assertThat(geographicalData1).isNotEqualTo(geographicalData2);
    }
}
