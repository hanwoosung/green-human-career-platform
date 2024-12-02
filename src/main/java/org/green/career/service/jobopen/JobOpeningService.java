package org.green.career.service.jobopen;

import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;

import java.util.List;

public interface JobOpeningService {

    int insertJobOpening(JobOpeningRequestDto jobOpeningRequestDto);

    int selectMax();

    int insertSkills(int jNo, List<String> skillList);

    int insertFiles(List<TblFileRequestDto> fileList);


}
