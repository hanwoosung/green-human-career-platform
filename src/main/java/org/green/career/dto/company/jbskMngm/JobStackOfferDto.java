package org.green.career.dto.company.jbskMngm;

import lombok.Data;
import org.green.career.dto.jobseeker.mypage.ScrapStackDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobStackOfferDto {

    private String rNo; // a.r_no
    private String rsNo; // a.rs_no
    private String name; // c.name
    private String rTitle; // a.r_title
    private String career; // Calculated career
    private String addr; // c.addr

    private String matchPer; // c.addr

    private String id;

    private String fileId;
    private String fileName;
    private String fileExt;
    private String fileUrl;

    private List<JobStackOfferStackDto> stacks = new ArrayList<>(); // 기술 스택 정보

}
