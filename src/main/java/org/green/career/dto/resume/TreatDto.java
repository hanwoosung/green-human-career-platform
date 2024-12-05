package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TreatDto {
    @JsonProperty("rpr_no")
    private Long treatId;

    @JsonProperty("r_no")
    private Long resumeId;

    @JsonProperty("prf_cd")
    private String preferenceCode;

    @JsonProperty("rpr_content")
    private String details;
}
