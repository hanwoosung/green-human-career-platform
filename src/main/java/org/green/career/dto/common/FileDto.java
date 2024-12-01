package org.green.career.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * 작성자: 한우성
 * 작성일: 2024-12-01
 * 파일 관련 DTO
 */
public class FileDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FileRequestDto {
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FileResponseDto {
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
        // 입력일시
        private LocalDate instDt;
        // 수정자
        private String updtId;
        // 수정일시
        private LocalDate updtDt;
    }
}