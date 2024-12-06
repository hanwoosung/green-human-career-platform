package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;

@Data
public class CareerDto {
    @JsonProperty("rc_no")
    private Long careerId;

    @JsonProperty("r_no")
    private Long resumeId;

    @JsonProperty("rc_company_nm")
    private String companyName;

    @JsonProperty("rc_join_dt")
    private Date joinDate;

    @JsonProperty("rc_out_dt")
    private Date outDate;

    @JsonProperty("rc_dmpt")
    private String department;

    @JsonProperty("rc_pstn")
    private String position;

    @JsonProperty("rc_duties")
    private String duties;
}
