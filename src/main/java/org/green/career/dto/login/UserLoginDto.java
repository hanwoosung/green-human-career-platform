package org.green.career.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-01
 * UserLogin Dto
 */
@Data
public class UserLoginDto {
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String id;
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String pw;
    private String name;
    private String userGbnCd;
}
