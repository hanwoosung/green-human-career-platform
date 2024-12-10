package org.green.career.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {

    private String id;
    private String pw;
    private String name;
    private String email;
    private String user_gbn_cd = "C";
    private String birth;
    private String phone;
    private String addr;
    private String addr2;
    private String zip_cd;

}
