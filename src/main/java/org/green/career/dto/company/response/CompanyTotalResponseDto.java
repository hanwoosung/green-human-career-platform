package org.green.career.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyTotalResponseDto {

    private String id;
    private String homepage;
    private String cDetail;
    private String cCnt;
    private String cBusiness;
    private String gbn;

    private List<String> cSaleDt;
    private List<String> cPay;

    private List<String> cHistory;
    private List<String> cContent;

    private List<String> fileName;
    private List<String> fileUrl;

}
