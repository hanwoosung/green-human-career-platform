package org.green.career.dto.resume;

import lombok.Data;

@Data
public class FileDto {
    private Long fileId;        // file_id
    private String fileGbnCd;   // file_gbn_cd
    private String fileRefId;   // file_ref_id
    private String fileName;    // file_name
    private String fileExt;     // file_ext
    private String fileUrl;     // file_url
}
