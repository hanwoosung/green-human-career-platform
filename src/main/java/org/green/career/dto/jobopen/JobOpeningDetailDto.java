package org.green.career.dto.jobopen;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.green.career.dto.common.file.response.FileResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOpeningDetailDto {

    private String id;
    private int jNo;
    private String jTitle;
    private String jStitle;
    private String jContent;
    private LocalDateTime sDt;
    private LocalDateTime eDt;
    private String educatCd;
    private String careerCd;
    private String preferences;
    private String workType;
    private String workPlace;
    private String workTime;
    private String delYn;
    private List<String> skills;
    private List<FileResponseDto> files;

    /**
     * 디비에서 스킬이 [JAVA,AWS]이런식으로 날라오게 만들어 놓아서
     * List<String> 형태로 변환
     */
    public void setSkills(String skillsString) {
        if (skillsString != null && !skillsString.isEmpty()) {
            String[] skillsArray = skillsString.replaceAll("[\\[\\]]", "").split(",");
            this.skills = Arrays.stream(skillsArray)
                    .map(String::trim)
                    .filter(skill -> !skill.isEmpty())
                    .collect(Collectors.toList());
        } else {
            this.skills = new ArrayList<>();
        }
    }

}
