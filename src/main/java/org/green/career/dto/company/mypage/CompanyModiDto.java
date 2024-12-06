package org.green.career.dto.company.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyModiDto {

    private String id;
    private String pw;
    private String name;
    private String email;
    private String birth;
    private String phone;
    private String addr;
    private String addr2;
    private String zipCd;
    private String fileUrl;
    private String fileName;
    private String fileId;

    private MultipartFile file;

}
