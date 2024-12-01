package org.green.career.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeInfoDto {
    private String cd;
    private String cdNm;
    private String upCd;
}