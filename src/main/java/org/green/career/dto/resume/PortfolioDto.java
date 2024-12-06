package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data

public class PortfolioDto {

    @JsonProperty("re_no")
    private Long portfolioId;

    @JsonProperty("r_no")
    private Long resumeId;

    @JsonProperty("rp_str_dt") // JavaScript에서 'rp_str_dt'에 해당
    private Date startDate;

    @JsonProperty("rp_end_dt") // JavaScript에서 'rp_end_dt'에 해당
    private Date endDate;

    @JsonProperty("rp_url") // JavaScript에서 'rp_url'에 해당
    private String portfolioUrl;

    @JsonProperty("rp_cnt") // JavaScript에서 'rp_cnt'에 해당
    private int teamSize;

    @JsonProperty("rp_content") // JavaScript에서 'rp_content'에 해당
    private String description;

    @JsonProperty("files") // JavaScript에서 'files'에 해당
    private List<ResumeFileDto> files;

}

