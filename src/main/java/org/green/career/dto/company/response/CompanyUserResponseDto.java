package org.green.career.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUserResponseDto {

    private String name;
    private String birth;
    private String zip_cd;
    private String addr;
    private String addr2;

}
