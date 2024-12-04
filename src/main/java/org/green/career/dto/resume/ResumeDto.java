package org.green.career.dto.resume;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResumeDto {
    private String resumeId;
    //사용자정보 tbl_user
    private String name;
    private String email;
    private String phone;
    private Date birth;
    private String addr;

    private String title;
    private String representativeYn;
    private String careerCode;
    private String deleteYn;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

    private List<EducationDto> educations  = new ArrayList<>(); ;
    private List<CareerDto> careers  = new ArrayList<>();;
    private List<QualificationDto> qualifications  = new ArrayList<>();;
    private List<PortfolioDto> portfolios  = new ArrayList<>();;
    private List<TechnicalStackDto> technicalStacks  = new ArrayList<>();;

}
