package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;

@Data
public class QualificationDto {
    @JsonProperty("rq_no")
    private Long qualificationId;

    @JsonProperty("r_no")
    private Long resumeId;

    @JsonProperty("rq_dt")
    private Date acquisitionDate;

    @JsonProperty("rq_nm")
    private String qualificationName;

    @JsonProperty("rq_place")
    private String issuingBody;

    @JsonProperty("rq_gbn_cd")
    private String qualificationType;
}
