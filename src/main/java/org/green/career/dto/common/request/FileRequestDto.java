package org.green.career.dto.common.request;

import lombok.*;

/**
 * 작성자: 한우성
 * 작성일: 2024-12-01
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public  class FileRequestDto {
    private String name; // 파일 이름
    private String type; // 파일 타입 (예: image/jpeg)
    private long size; // 파일 크기 (바이트 단위)
    private String content; // Base64로 인코딩된 파일 내용
}