package org.green.career.dto.common.file.request;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 한우성
 * 작성일: 2024-12-01
 * TODO: 놔뒀다가 필요없을 때 삭제진행
 */
@Description("사용안함")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileName;
    private String fileUrl;
}
