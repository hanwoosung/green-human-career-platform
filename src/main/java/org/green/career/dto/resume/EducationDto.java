package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class EducationDto {
    @JsonProperty("re_no")
    private Long educationId;

    @JsonProperty("r_no")
    private Long resumeId;

    @JsonProperty("re_gbn_cd")
    private String educationType;

    @JsonProperty("re_school_nm")
    private String schoolName;

    @JsonProperty("re_major")
    private String major;

    @JsonProperty("re_score")
    private Float score;

    @JsonProperty("re_indt")
    private Date admissionDate;

    @JsonProperty("re_outdt")
    private Date graduationDate;

    @JsonProperty("re_transfer_yn")
    private String transferYn;

    @JsonProperty("re_resion")
    private String region;

}
