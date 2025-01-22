package org.green.career.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * UserLogin Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDto {
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String id;
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String pw;
    private String name;
    private String email;
    private String userGbnCd;
    private String birth;
    private String phone;
    private String addr;
    private String addr2;
    private String zip_cd;

}
