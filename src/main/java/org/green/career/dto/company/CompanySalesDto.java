package org.green.career.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySalesDto {

    private String id;
    private String cSaleDt;
    private String cPay;

}
