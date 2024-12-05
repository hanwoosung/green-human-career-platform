package org.green.career.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyHistoryResponseDto {
    private String id;
    private String cHistory;
    private String cContent;
}
