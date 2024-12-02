package org.green.career.dto.common.file.request;

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
    private String name;
    private String type;
    private long size;
}