package org.green.career.dto.resume;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationDto {
    //이력서 학력 번호
    private String educationId;
    //이력서 번호
    private String resumeId;
    //학력 구분
    private String educationLevel; // H: 고등학교, U: 대학교, S: 석사, D: 박사, J: 전문대
    //학교 이름
    private String schoolName;
    //전공
    private String major;
    //성적
    private Float score; //비필수
    //입학날짜
    private LocalDate admissionDate;
    //졸업날짜
    private LocalDate graduationDate;
    private String transferYn; // Y: 편입, N: 비편입
    //지역
    private String region;

}
