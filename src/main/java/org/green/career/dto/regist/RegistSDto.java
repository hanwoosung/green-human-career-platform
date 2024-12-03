package org.green.career.dto.regist;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistSDto {
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    @Size(max = 20, message = "아이디는 20자 이내여야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(max = 100, message = "비밀번호는 100자 이내여야 합니다.")
    private String pw;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 20, message = "이름은 20자 이내여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Size(max = 20, message = "이메일은 20자 이내여야 합니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private String birth;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Size(max = 20, message = "전화번호는 20자 이내여야 합니다.")
    private String phone;

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    @Size(max = 100, message = "주소는 100자 이내여야 합니다.")
    private String addr;

    @Size(max = 100, message = "상세주소는 100자 이내여야 합니다.")
    private String addr2;

    @NotBlank(message = "우편번호는 필수 입력 항목입니다.")
    @Size(max = 10, message = "우편번호는 10자 이내여야 합니다.")
    private String zipCd;

}
