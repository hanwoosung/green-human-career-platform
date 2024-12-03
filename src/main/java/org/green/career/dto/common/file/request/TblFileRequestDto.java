package org.green.career.dto.common.file.request;

import lombok.Data;

/**
 * 작성자: 한우성
 * 작성일: 2024-12-01
 * 파일 등록 시 사용 중
 */
@Data
public class TblFileRequestDto {
    // 파일 ID
    private Long fileId;
    // 파일구분
    private String fileGbnCd;
    // 파일영향번호
    private String fileRefId;
    // 파일명
    private String fileName;
    // 확장자
    private String fileExt;
    // 파일경로
    private String fileUrl;
    // 입력자
    private String instId;
}