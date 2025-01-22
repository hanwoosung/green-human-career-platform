package org.green.career.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInfoResponseDto {

    private String id;
    private String cno;
    private String cGbnCd;
    private String homepage;
    private String cDetail;
    private String cCnt;
    private String cBusiness;
    private String gbn;

}
