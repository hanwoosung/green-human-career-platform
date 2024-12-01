package org.green.career.dto.jobopen.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.green.career.dto.common.request.FileRequestDto;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobOpeningRequestDto {
    // 채용공고 번호
    private Long jNo;
    // 아이디
    private String id;


    // 공고명
    private String jTitle;
    // 서브타이틀
    private String jStitle;
    // 내용
    private String jContent;
    // 채용구분(C: 마감, O: 모집중, S:모집 일시중지)
    private String jGbnCd;
    // 시작일시
    private LocalDate sDt;
    // 마감일시
    private LocalDate eDt;
    // 학력
    private String educatCd;
    // 경력(S:신입.G:경력,SG:신입/경력)
    private String careerCd;
    // 우대사항
    private String preferences;
    // 근무지
    private String workPlace;
    // 근무시간
    private String workTime;
    // 근무형태 (C: 계약직, F: 정규직, A: 계약/정규직)
    private String workType;
    // 삭제여부
    private String delYn;

    private List<FileRequestDto> companyImages; // 파일 관련 필드 추가


    private JobOpenSkillStackDto skillList;

    public Long getjNo() {
        return jNo;
    }

    public void setjNo(Long jNo) {
        this.jNo = jNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getjTitle() {
        return jTitle;
    }

    public void setjTitle(String jTitle) {
        this.jTitle = jTitle;
    }

    public String getjStitle() {
        return jStitle;
    }

    public void setjStitle(String jStitle) {
        this.jStitle = jStitle;
    }

    public String getjContent() {
        return jContent;
    }

    public void setjContent(String jContent) {
        this.jContent = jContent;
    }

    public String getjGbnCd() {
        return jGbnCd;
    }

    public void setjGbnCd(String jGbnCd) {
        this.jGbnCd = jGbnCd;
    }

    public LocalDate getsDt() {
        return sDt;
    }

    public void setsDt(LocalDate sDt) {
        this.sDt = sDt;
    }

    public LocalDate geteDt() {
        return eDt;
    }

    public void seteDt(LocalDate eDt) {
        this.eDt = eDt;
    }

    public String getEducatCd() {
        return educatCd;
    }

    public void setEducatCd(String educatCd) {
        this.educatCd = educatCd;
    }

    public String getCareerCd() {
        return careerCd;
    }

    public void setCareerCd(String careerCd) {
        this.careerCd = careerCd;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public List<FileRequestDto> getCompanyImages() {
        return companyImages;
    }

    public void setCompanyImages(List<FileRequestDto> companyImages) {
        this.companyImages = companyImages;
    }

    public JobOpenSkillStackDto getSkillList() {
        return skillList;
    }

    public void setSkillList(JobOpenSkillStackDto skillList) {
        this.skillList = skillList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobOpenSkillStackDto {
        private Long jNo;
        private List<String> skills;
    }
}
