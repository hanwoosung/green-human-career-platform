package org.green.career.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResumeDto {
    private Long resumeId;
    //사용자정보 tbl_user
    private String id;
    private String name;
    private String createdBy;
    private String email;
    private String phone;
    private Date birth;
    private String addr;

    private String title;
    private String representativeYn;
    private String careerCode;
    private String deleteYn;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private ResumeFileDto profilePhoto;

    private List<EducationDto> educations  = new ArrayList<>();
    private List<CareerDto> careers  = new ArrayList<>();
    private List<QualificationDto> qualifications  = new ArrayList<>();
    private List<PortfolioDto> portfolios  = new ArrayList<>();
    private List<TechnicalStackDto> technicalStacks  = new ArrayList<>();
    private List<TreatDto> treats  = new ArrayList<>();
    private List<IntroduceMeDto> introduces  = new ArrayList<>();

    public int getBirthYear() {
        if (birth == null) {
            return 0; // 기본값 설정
        }
        return birth.toLocalDate().getYear();
    }
    
    public int calculateAge() {
        if (birth == null) {
            return 0; // 기본값 설정
        }
        LocalDate birthDate = birth.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public String getFormattedPhone() {
        return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
    }

    // 날짜 및 시간 포매팅
    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return ""; // 기본값 설정
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 작성됨");
        return createdDate.format(formatter);
    }

}
